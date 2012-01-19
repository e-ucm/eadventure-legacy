/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *          research group.
 *   
 *    Copyright 2005-2012 <e-UCM> research group.
 *  
 *     <e-UCM> is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *  
 *          C Profesor Jose Garcia Santesmases sn,
 *          28040 Madrid (Madrid), Spain.
 *  
 *          For more info please visit:  <http://e-adventure.e-ucm.es> or
 *          <http://www.e-ucm.es>
 *  
 *  ****************************************************************************
 * This file is part of <e-Adventure>, version 1.4.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       <e-Adventure> is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      <e-Adventure> is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.control.security.jarsigner;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import sun.misc.BASE64Encoder;
import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
import sun.security.util.ManifestDigester;
import sun.security.util.SignatureFileVerifier;
import sun.security.x509.CertificateIssuerName;
import sun.security.x509.NetscapeCertTypeExtension;
import sun.security.x509.X500Name;
import sun.security.x509.X509CertInfo;

/**
 * Class to sign JAR files.
 */
public class JSSigner {

    //store directory folder META_INF
    private static final String META_INF = "META-INF/";

    // read zip entry raw bytes
    private static ByteArrayOutputStream baos = new ByteArrayOutputStream( 2048 );

    private static byte[] buffer = new byte[ 8192 ];

    // prefix for new signature-related files in META-INF directory
    private static final String SIG_PREFIX = META_INF + "SIG-";

    // name of digest algorithm
    static String digestalg = "SHA1";

    // "sign" the whole manifest
    static boolean signManifest = true;

    // signer's certificate chain (when composing)
    static X509Certificate[] certChain;

    /**
     * Main method to perform the signature program
     * 
     * @param param
     * @throws JarSignerException
     */
    public static void signJar( JSParameters param ) throws JarSignerException {

        //variable that controls the character's name invalid
        boolean aliasUsed = false;

        X509Certificate tsaCert = null;
        ZipFile zipFile = null;

        //DSA and SF file names  
        String sigfile = null;

        //file name corresponded with alias
        if( sigfile == null ) {
            sigfile = param.getAlias( );
            aliasUsed = true;
        }

        // if the name is too long, we short it
        if( sigfile.length( ) > 8 ) {
            sigfile = sigfile.substring( 0, 8 ).toUpperCase( );
        }
        else {
            sigfile = sigfile.toUpperCase( );
        }

        //remove invalid characters
        StringBuilder tmpSigFile = new StringBuilder( sigfile.length( ) );
        for( int j = 0; j < sigfile.length( ); j++ ) {
            char c = sigfile.charAt( j );
            if( !( ( c >= 'A' && c <= 'Z' ) || ( c >= '0' && c <= '9' ) || ( c == '-' ) || ( c == '_' ) ) ) {
                if( aliasUsed ) {
                    // convert illegal characters from the alias to be _'s
                    c = '_';
                }
                else {
                    throw new JarSignerException( "signature filename must consist of the following characters: A-Z, 0-9, _ or -" );
                }
            }
            tmpSigFile.append( c );
        }

        // new file with all valid characters
        sigfile = tmpSigFile.toString( );

        String tmpJarName;
        if( param.getSignedJARName( ) == null )
            tmpJarName = param.getJarName( ) + ".sig";
        else
            tmpJarName = param.getSignedJARName( );

        //unsigned jar
        File jarFile = new File( param.getJarName( ) );
        //signed jar
        File signedJarFile = new File( tmpJarName );

        // Open the jar (zip) file
        try {
            String nombre = param.getJarName( );
            zipFile = new ZipFile( nombre );
        }
        catch( IOException ioe ) {
            throw new JarSignerException( "unable to open jar file: " + param.getJarName( ) );
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream( signedJarFile );
        }
        catch( IOException ioe ) {
            throw new JarSignerException( "unable to create: " + tmpJarName );
        }

        PrintStream ps = new PrintStream( fos );
        ZipOutputStream zos = new ZipOutputStream( ps );

        // sf and dsa building files in the folder of META_INF
        String sfFilename = ( META_INF + sigfile + ".SF" ).toUpperCase( );
        String bkFilename = ( META_INF + sigfile + ".DSA" ).toUpperCase( );

        //created manifest
        Manifest manifest = new Manifest( );
        Map<String, Attributes> mfEntries = manifest.getEntries( );

        // The Attributes of manifest before updating
        Attributes oldAttr = null;

        boolean mfCreated = false;
        boolean mfModified = false;
        byte[] mfRawBytes = null;

        try {
            MessageDigest digests[] = { MessageDigest.getInstance( digestalg ) };

            // Check if manifest exists
            ZipEntry mfFile;
            if((mfFile = getManifestFile(zipFile)) != null) {
                //Manifest exists. Read its raw bytes.
                mfRawBytes = getBytes( zipFile, mfFile );
                manifest.read( new ByteArrayInputStream( mfRawBytes ) );
                oldAttr = (Attributes) ( manifest.getMainAttributes( ).clone( ) );
            }
            else {
                // Create new manifest with number version, creator and java version
                Attributes mattr = manifest.getMainAttributes( );
                //version from method
                mattr.putValue( Attributes.Name.MANIFEST_VERSION.toString( ), "1.0" );
                String javaVendor = System.getProperty( "java.vendor" );
                String jdkVersion = System.getProperty( "java.version" );
                mattr.putValue( "Created by", jdkVersion + " (" + javaVendor + ")" );
                mfFile = new ZipEntry( JarFile.MANIFEST_NAME );
                mfCreated = true;
            }

            /*
             * For each entry in jar
             * (except for signature-related META-INF entries),
             * do the following:
             *
             * - if entry is not contained in manifest, add it to manifest;
             * - if entry is contained in manifest, calculate its hash and
             *   compare it with the one in the manifest; if they are
             *   different, replace the hash in the manifest with the newly
             *   generated one. (This may invalidate existing signatures!)
             */
            BASE64Encoder encoder = new JarBASE64Encoder( );
            Vector<ZipEntry> mfFiles = new Vector<ZipEntry>( );

            for( Enumeration<? extends ZipEntry> enum_ = zipFile.entries( ); enum_.hasMoreElements( ); ) {
                ZipEntry ze = enum_.nextElement( );

                if( ze.getName( ).startsWith( META_INF ) ) {
                    // Store META-INF files in vector, so they can be written
                    // out first
                    mfFiles.addElement( ze );

                    if( signatureRelated( ze.getName( ) ) ) {
                        // ignore signature-related and manifest files
                        continue;
                    }
                }

                if( manifest.getAttributes( ze.getName( ) ) != null ) {
                    // jar entry is contained in manifest, check and
                    // possibly update its digest attributes
                    if( updateDigests( ze, zipFile, digests, encoder, manifest ) == true ) {
                        mfModified = true;
                    }
                }
                else if( !ze.isDirectory( ) ) {
                    // Add entry to manifest
                    Attributes attrs = getDigestAttributes( ze, zipFile, digests, encoder );
                    mfEntries.put( ze.getName( ), attrs );
                    mfModified = true;
                }
            }

            // Recalculate the manifest raw bytes if necessary
            if( mfModified ) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream( );
                manifest.write( baos );
                byte[] newBytes = baos.toByteArray( );
                if( mfRawBytes != null && oldAttr.equals( manifest.getMainAttributes( ) ) ) {

                    /*
                     * The Attributes object is based on HashMap and can handle
                     * continuation columns. Therefore, even if the contents are
                     * not changed (in a Map view), the bytes that it write()
                     * may be different from the original bytes that it read()
                     * from. Since the signature on the main attributes is based
                     * on raw bytes, we must retain the exact bytes.
                     */
                    mfRawBytes = newBytes; // NEW, there was a problem with arraycopy
                    int newPos = findHeaderEnd( newBytes );
                    int oldPos = findHeaderEnd( mfRawBytes );

                    if( newPos == oldPos ) {
                        System.arraycopy( mfRawBytes, 0, newBytes, 0, oldPos );
                    }
                    else {
                        // cat oldHead newTail > newBytes
                        byte[] lastBytes = new byte[ oldPos + newBytes.length - newPos ];
                        System.arraycopy( mfRawBytes, 0, lastBytes, 0, oldPos );
                        System.arraycopy( newBytes, newPos, lastBytes, oldPos, newBytes.length - newPos );
                        newBytes = lastBytes;
                    }
                }
                mfRawBytes = newBytes;
            }

            // Write out the manifest
            if( mfModified ) {
                // manifest file has new length
                mfFile = new ZipEntry( JarFile.MANIFEST_NAME );
            }
            if( param.isVerbose( ) ) {
                if( mfCreated ) {
                    System.out.println( ( " adding: " ) + mfFile.getName( ) );
                }
                else if( mfModified ) {
                    System.out.println( ( " updating: " ) + mfFile.getName( ) );
                }
            }
            zos.putNextEntry( mfFile );
            zos.write( mfRawBytes );

            // Calculate SignatureFile (".SF") and SignatureBlockFile
            ManifestDigester manDig = new ManifestDigester( mfRawBytes );
            SignatureFile sf = new SignatureFile( digests, manifest, manDig, sigfile, signManifest );
            /*
                        if (tsaAlias != null) {
                            tsaCert = getTsaCert(tsaAlias);
                        }
            */

            SignatureFile.Block block = null;

            try {//                      sigAlg            externalSF tsaURL         signingMechanism
                block = sf.generateBlock( null, certChain, true, null, tsaCert, null, param, zipFile );
            }
            catch( SocketTimeoutException e ) {
                // Provide a helpful message when TSA is beyond a firewall
                throw new JarSignerException( "unable to sign jar: " + "no response from the Timestamping Authority. " + "When connecting from behind a firewall then an HTTP proxy may need to be specified. " + "Supply the following options to jarsigner: " + "\n  -J-Dhttp.proxyHost=<hostname> " + "\n  -J-Dhttp.proxyPort=<portnumber> ", e );
            }
            catch( InvalidKeyException e ) {
                throw new JarSignerException( "unable to sign jar: " + e.getMessage( ) );
            }
            catch( UnrecoverableKeyException e ) {
                throw new JarSignerException( "unable to sign jar: " + e.getMessage( ) );
            }
            catch( NoSuchAlgorithmException e ) {
                throw new JarSignerException( "unable to sign jar: " + e.getMessage( ) );
            }
            catch( SignatureException e ) {
                throw new JarSignerException( "unable to sign jar: " + e.getMessage( ) );
            }
            catch( CertificateException e ) {
                throw new JarSignerException( "unable to sign jar: " + e.getMessage( ) );
            }
            catch( KeyStoreException e ) {
                throw new JarSignerException( "unable to sign jar: " + e.getMessage( ) );
            }
            catch( JarSignerException e ) {
                throw new JarSignerException( "unable to sign jar: " + e.getMessage( ) );
            }

            sfFilename = sf.getMetaName( );
            bkFilename = block.getMetaName( );

            ZipEntry sfFile = new ZipEntry( sfFilename );
            ZipEntry bkFile = new ZipEntry( bkFilename );

            long time = System.currentTimeMillis( );
            sfFile.setTime( time );
            bkFile.setTime( time );

            // signature file
            zos.putNextEntry( sfFile );
            sf.write( zos );

            // debería borrarse
            /*    if (verbose) {
                    if (zipFile.getEntry(sfFilename) != null) {
                        System.out.println(rb.getString(" updating: ") +
                                    sfFilename);
                    } else {
                        System.out.println(rb.getString("   adding: ") +
                                    sfFilename);
                    }
                }

                if (verbose) {
                    if (tsaUrl != null || tsaCert != null) {
                        System.out.println(
                            rb.getString("requesting a signature timestamp"));
                    }
                    if (tsaUrl != null) {
                        System.out.println(rb.getString("TSA location: ") + tsaUrl);
                    }
                    if (tsaCert != null) {
                        String certUrl =
                            TimestampedSigner.getTimestampingUrl(tsaCert);
                        if (certUrl != null) {
                            System.out.println(rb.getString("TSA location: ") +
                                certUrl);
                        }
                        System.out.println(
                            rb.getString("TSA certificate: ") + printCert(tsaCert));
                    }
                    if (signingMechanism != null) {
                        System.out.println(
                            rb.getString("using an alternative signing mechanism"));
                    }
                }
                */

            // signature block file
            zos.putNextEntry( bkFile );
            block.write( zos );

            //debería borrarse
            /*    if (verbose) {
                    if (zipFile.getEntry(bkFilename) != null) {
                        System.out.println(rb.getString(" updating: ") +
                            bkFilename);
                    } else {
                        System.out.println(rb.getString("   adding: ") +
                            bkFilename);
                    }
                }*/

            // Write out all other META-INF files that we stored in the
            // vector
            for( int i = 0; i < mfFiles.size( ); i++ ) {
                ZipEntry ze = mfFiles.elementAt( i );
                if( !ze.getName( ).equalsIgnoreCase( JarFile.MANIFEST_NAME ) && !ze.getName( ).equalsIgnoreCase( sfFilename ) && !ze.getName( ).equalsIgnoreCase( bkFilename ) ) {
                    writeEntry( zipFile, zos, ze );
                }
            }

            // Write out all other files
            for( Enumeration<? extends ZipEntry> enum_ = zipFile.entries( ); enum_.hasMoreElements( ); ) {
                ZipEntry ze = enum_.nextElement( );

                if( !ze.getName( ).startsWith( META_INF ) ) {
                    /*  if( verbose ) {
                          if( manifest.getAttributes( ze.getName( ) ) != null )
                              System.out.println( rb.getString( "  signing: " ) + ze.getName( ) );
                          else
                              System.out.println( rb.getString( "   adding: " ) + ze.getName( ) );
                      }*/
                    writeEntry( zipFile, zos, ze );
                }
            }
        }
        catch( IOException ioe ) {
            throw new JarSignerException( "unable to sign jar: " + ioe, ioe );
        }
        catch( NoSuchAlgorithmException e ) {
            throw new JarSignerException( "unable to sign jar: " + e, e );
        }
        finally {
            // close the resouces
            if( zipFile != null ) {
                try {
                    zipFile.close( );
                }
                catch( IOException e ) {
                    throw new JarSignerException( "Exception with zipFile" );
                }
                zipFile = null;
            }
            if( zos != null ) {
                try {
                    zos.close( );
                }
                catch( IOException e ) {
                    throw new JarSignerException( "Exception with zipFile" );
                }
            }
        }

        if( param.getSignedJARName( ) == null ) {
            // attempt an atomic rename. If that fails,
            // rename the original jar file, then the signed
            // one, then delete the original.
            if( !signedJarFile.renameTo( jarFile ) ) {
                File origJar = new File( param.getJarName( ) + ".orig" );

                if( jarFile.renameTo( origJar ) ) {
                    if( signedJarFile.renameTo( jarFile ) ) {
                        origJar.delete( );
                    }
                    else {
                        MessageFormat form = new MessageFormat( "attempt to rename signedJarFile to jarFile failed" );
                        Object[] source = { signedJarFile, jarFile };
                        throw new JarSignerException( form.format( source ) );
                    }
                }
                else {
                    MessageFormat form = new MessageFormat( "attempt to rename jarFile to origJar failed" );
                    Object[] source = { jarFile, origJar };
                    throw new JarSignerException( form.format( source ) );
                }
            }
        }

    }

    /**
     * Obtained the Manifest file
     * 
     * @param zf
     *            ZipFile
     * @return ZipEntry
     */
    private static ZipEntry getManifestFile( ZipFile zf ) {

        ZipEntry ze = zf.getEntry( JarFile.MANIFEST_NAME );
        if( ze == null ) {
            // Check all entries for matching name
            Enumeration<? extends ZipEntry> enum_ = zf.entries( );
            while( enum_.hasMoreElements( ) && ze == null ) {
                ze = enum_.nextElement( );
                if( !JarFile.MANIFEST_NAME.equalsIgnoreCase( ze.getName( ) ) ) {
                    ze = null;
                }
            }
        }
        return ze;
    }

    /**
     * Get Bytes from a file
     * 
     * @param zf
     *            ZipFile
     * @param ze
     *            ZipEntry
     * @return Array with bytes
     * @throws IOException
     */
    private synchronized static byte[] getBytes( ZipFile zf, ZipEntry ze ) throws IOException {

        int n;

        InputStream is = null;
        try {
            is = zf.getInputStream( ze );
            baos.reset( );
            long left = ze.getSize( );

            while( ( left > 0 ) && ( n = is.read( buffer, 0, buffer.length ) ) != -1 ) {
                baos.write( buffer, 0, n );
                left -= n;
            }
        }
        finally {
            if( is != null ) {
                is.close( );
            }
        }

        return baos.toByteArray( );
    }

    /**
     * signature-related files include: . META-INF/MANIFEST.MF . META-INF/SIG-*
     * . META-INF/*.SF . META-INF/*.DSA . META-INF/*.RSA
     */
    private static boolean signatureRelated( String name ) {

        String ucName = name.toUpperCase( );
        if( ucName.equals( JarFile.MANIFEST_NAME ) || ucName.equals( META_INF ) || ( ucName.startsWith( SIG_PREFIX ) && ucName.indexOf( "/" ) == ucName.lastIndexOf( "/" ) ) ) {
            return true;
        }

        if( ucName.startsWith( META_INF ) && SignatureFileVerifier.isBlockOrSF( ucName ) ) {
            // .SF/.DSA/.RSA files in META-INF subdirs
            // are not considered signature-related
            return ( ucName.indexOf( "/" ) == ucName.lastIndexOf( "/" ) );
        }

        return false;
    }

    /*
     * Updates the digest attributes of a manifest entry, by adding or
     * replacing digest values.
     * A digest value is added if the manifest entry does not contain a digest
     * for that particular algorithm.
     * A digest value is replaced if it is obsolete.
     *
     * Returns true if the manifest entry has been changed, and false
     * otherwise.
     */
    private static boolean updateDigests( ZipEntry ze, ZipFile zf, MessageDigest[] digests, BASE64Encoder encoder, Manifest mf ) throws IOException {

        boolean update = false;

        Attributes attrs = mf.getAttributes( ze.getName( ) );
        String[] base64Digests = getDigests( ze, zf, digests, encoder );

        for( int i = 0; i < digests.length; i++ ) {
            String name = digests[i].getAlgorithm( ) + "-Digest";
            String mfDigest = attrs.getValue( name );
            if( mfDigest == null && digests[i].getAlgorithm( ).equalsIgnoreCase( "SHA" ) ) {
                // treat "SHA" and "SHA1" the same
                mfDigest = attrs.getValue( "SHA-Digest" );
            }
            if( mfDigest == null ) {
                // compute digest and add it to list of attributes
                attrs.putValue( name, base64Digests[i] );
                update = true;
            }
            else {
                // compare digests, and replace the one in the manifest
                // if they are different
                if( !mfDigest.equalsIgnoreCase( base64Digests[i] ) ) {
                    attrs.putValue( name, base64Digests[i] );
                    update = true;
                }
            }
        }
        return update;
    }

    /*
     * Computes the digests of a zip entry, and returns them as an array
     * of base64-encoded strings.
     */
    private synchronized static String[] getDigests( ZipEntry ze, ZipFile zf, MessageDigest[] digests, BASE64Encoder encoder ) throws IOException {

        int n, i;
        InputStream is = null;
        try {
            is = zf.getInputStream( ze );
            long left = ze.getSize( );
            while( ( left > 0 ) && ( n = is.read( buffer, 0, buffer.length ) ) != -1 ) {
                for( i = 0; i < digests.length; i++ ) {
                    digests[i].update( buffer, 0, n );
                }
                left -= n;
            }
        }
        finally {
            if( is != null ) {
                is.close( );
            }
        }
        // complete the digests
        String[] base64Digests = new String[ digests.length ];
        for( i = 0; i < digests.length; i++ ) {
            base64Digests[i] = encoder.encode( digests[i].digest( ) );
        }
        return base64Digests;
    }

    /*
     * Computes the digests of a zip entry, and returns them as a list of
     * attributes
     */
    private static Attributes getDigestAttributes( ZipEntry ze, ZipFile zf, MessageDigest[] digests, BASE64Encoder encoder ) throws IOException {

        String[] base64Digests = getDigests( ze, zf, digests, encoder );
        Attributes attrs = new Attributes( );

        for( int i = 0; i < digests.length; i++ ) {
            attrs.putValue( digests[i].getAlgorithm( ) + "-Digest", base64Digests[i] );
        }
        return attrs;
    }

    /**
     * Find the position of an empty line inside bs
     */
    private static int findHeaderEnd( byte[] bs ) {

        // An empty line can be at the beginning...
        if( bs.length > 1 && bs[0] == '\r' && bs[1] == '\n' ) {
            return 0;
        }
        // ... or after another line
        for( int i = 0; i < bs.length - 3; i++ ) {
            if( bs[i] == '\r' && bs[i + 1] == '\n' && bs[i + 2] == '\r' && bs[i + 3] == '\n' ) {
                return i;
            }
        }
        // If header end is not found, return 0,
        // which means no behavior change.
        return 0;
    }

    private static void writeEntry( ZipFile zf, ZipOutputStream os, ZipEntry ze ) throws IOException {

        ZipEntry ze2 = new ZipEntry( ze.getName( ) );
        ze2.setMethod( ze.getMethod( ) );
        ze2.setTime( ze.getTime( ) );
        ze2.setComment( ze.getComment( ) );
        ze2.setExtra( ze.getExtra( ) );
        if( ze.getMethod( ) == ZipEntry.STORED ) {
            ze2.setSize( ze.getSize( ) );
            ze2.setCrc( ze.getCrc( ) );
        }
        os.putNextEntry( ze2 );
        writeBytes( zf, ze, os );
    }

    /**
     * Writes all the bytes for a given entry to the specified output stream.
     */
    private synchronized static void writeBytes( ZipFile zf, ZipEntry ze, ZipOutputStream os ) throws IOException {

        int n;

        InputStream is = null;
        try {
            is = zf.getInputStream( ze );
            long left = ze.getSize( );

            while( ( left > 0 ) && ( n = is.read( buffer, 0, buffer.length ) ) != -1 ) {
                os.write( buffer, 0, n );
                left -= n;
            }
        }
        finally {
            if( is != null ) {
                is.close( );
            }
        }
    }
}

/**
 * This is a BASE64Encoder that does not insert a default newline at the end of
 * every output line. This is necessary because java.util.jar does its own line
 * management (see Manifest.make72Safe()). Inserting additional new lines can
 * cause line-wrapping problems (see CR 6219522).
 */
class JarBASE64Encoder extends BASE64Encoder {

    /**
     * Encode the suffix that ends every output line.
     */
    @Override
    protected void encodeLineSuffix( OutputStream aStream ) throws IOException {

    }
}

class SignatureFile {

    /** SignatureFile */
    Manifest sf;

    /** .SF base name */
    String baseName;

    public SignatureFile( MessageDigest digests[], Manifest mf, ManifestDigester md, String baseName, boolean signManifest ) {

        this.baseName = baseName;

        //Java version
        String version = System.getProperty( "java.version" );
        String javaVendor = System.getProperty( "java.vendor" );

        sf = new Manifest( );
        Attributes mattr = sf.getMainAttributes( );
        BASE64Encoder encoder = new JarBASE64Encoder( );

        //our version
        mattr.putValue( Attributes.Name.SIGNATURE_VERSION.toString( ), "1.0" );

        mattr.putValue( "Created-By", version + " (" + javaVendor + ")" );

        if( signManifest ) {
            // sign the whole manifest
            for( int i = 0; i < digests.length; i++ ) {
                mattr.putValue( digests[i].getAlgorithm( ) + "-Digest-Manifest", encoder.encode( md.manifestDigest( digests[i] ) ) );
            }
        }

        // create digest of the manifest main attributes
        ManifestDigester.Entry mde = md.get( ManifestDigester.MF_MAIN_ATTRS, false );
        if( mde != null ) {
            for( int i = 0; i < digests.length; i++ ) {
                mattr.putValue( digests[i].getAlgorithm( ) + "-Digest-" + ManifestDigester.MF_MAIN_ATTRS, encoder.encode( mde.digest( digests[i] ) ) );
            }
        }
        else {
            throw new IllegalStateException( "ManifestDigester failed to create " + "Manifest-Main-Attribute entry" );
        }

        /* go through the manifest entries and create the digests */

        Map<String, Attributes> entries = sf.getEntries( );
        Iterator<Map.Entry<String, Attributes>> mit = mf.getEntries( ).entrySet( ).iterator( );
        while( mit.hasNext( ) ) {
            Map.Entry<String, Attributes> e = mit.next( );
            String name = e.getKey( );
            mde = md.get( name, false );
            if( mde != null ) {
                Attributes attr = new Attributes( );
                for( int i = 0; i < digests.length; i++ ) {
                    attr.putValue( digests[i].getAlgorithm( ) + "-Digest", encoder.encode( mde.digest( digests[i] ) ) );
                }
                entries.put( name, attr );
            }
        }
    }

    /**
     * Writes the SignatureFile to the specified OutputStream.
     * 
     * @param out
     *            the output stream
     * @exception IOException
     *                if an I/O error has occurred
     */

    public void write( OutputStream out ) throws IOException {

        sf.write( out );
    }

    /**
     * get .SF file name
     */
    public String getMetaName( ) {

        return "META-INF/" + baseName + ".SF";
    }

    /**
     * get base file name
     */
    public String getBaseName( ) {

        return baseName;
    }

    /**
     * Check if userCert is designed to be a code signer
     * 
     * @param userCert
     *            the certificate to be examined
     * @param bad
     *            3 booleans to show if the KeyUsage, ExtendedKeyUsage,
     *            NetscapeCertType has codeSigning flag turned on. If null, the
     *            class field badKeyUsage, badExtendedKeyUsage,
     *            badNetscapeCertType will be set.
     */
    void checkCertUsage( X509Certificate userCert, boolean[] bad ) {

        // Can act as a signer?
        // 1. if KeyUsage, then [0] should be true
        // 2. if ExtendedKeyUsage, then should contains ANY or CODE_SIGNING
        // 3. if NetscapeCertType, then should contains OBJECT_SIGNING
        // 1,2,3 must be true

        if( bad != null ) {
            bad[0] = bad[1] = bad[2] = false;
        }

        boolean[] keyUsage = userCert.getKeyUsage( );
        if( keyUsage != null ) {
            if( keyUsage.length < 1 || !keyUsage[0] ) {
                if( bad != null ) {
                    bad[0] = true;
                }
                else {
                    System.out.println( "badkeyusage ERROR - The signer certificate's KeyUsage extension doesn't allow code signing." );
                }
            }
        }

        try {
            List<String> xKeyUsage = userCert.getExtendedKeyUsage( );
            if( xKeyUsage != null ) {
                if( !xKeyUsage.contains( "2.5.29.37.0" ) // anyExtendedKeyUsage
                        && !xKeyUsage.contains( "1.3.6.1.5.5.7.3.3" ) ) { // codeSigning
                    if( bad != null ) {
                        bad[1] = true;
                    }
                    else {
                        System.out.println( "badExtendedKeyUsage ERROR - The signer certificate's ExtendedKeyUsage extension doesn't allow code signing. " );
                    }
                }
            }
        }
        catch( java.security.cert.CertificateParsingException e ) {
            // shouldn't happen
            e.printStackTrace( );
        }

        try {
            // OID_NETSCAPE_CERT_TYPE
            byte[] netscapeEx = userCert.getExtensionValue( "2.16.840.1.113730.1.1" );
            if( netscapeEx != null ) {
                DerInputStream in = new DerInputStream( netscapeEx );
                byte[] encoded = in.getOctetString( );
                encoded = new DerValue( encoded ).getUnalignedBitString( ).toByteArray( );

                NetscapeCertTypeExtension extn = new NetscapeCertTypeExtension( encoded );

                Boolean val = (Boolean) extn.get( NetscapeCertTypeExtension.OBJECT_SIGNING );
                if( !val ) {
                    if( bad != null ) {
                        bad[2] = true;
                    }
                    else {
                        System.out.println( "badNetscapeCertType ERROR - The signer certificate's NetscapeCertType extension doesn't allow code signing." );
                    }
                }
            }
        }
        catch( IOException e ) {
            //
        }
    }

    /*
     * Generate a signed data block.
     * If a URL or a certificate (containing a URL) for a Timestamping
     * Authority is supplied then a signature timestamp is generated and
     * inserted into the signed data block.
     *
     * @param sigalg signature algorithm to use, or null to use default
     * @param tsaUrl The location of the Timestamping Authority. If null
     *               then no timestamp is requested.
     * @param tsaCert The certificate for the Timestamping Authority. If null
     *               then no timestamp is requested.
     * @param signingMechanism The signing mechanism to use.
     * @param args The command-line arguments to jarsigner.
     * @param zipFile The original source Zip file.
     */
    public Block generateBlock( String sigalg, X509Certificate[] certChain, boolean externalSF, String tsaUrl, X509Certificate tsaCert, TimestampedSigner signingMechanism, JSParameters param, ZipFile zipFile ) throws NoSuchAlgorithmException, InvalidKeyException, IOException, SignatureException, CertificateException, UnrecoverableKeyException, KeyStoreException, JarSignerException {

        long SIX_MONTHS = 180 * 24 * 60 * 60 * 1000L; //milliseconds

        java.security.cert.Certificate[] cs = null;

        try {
            cs = param.getKeyStore( ).getCertificateChain( param.getAlias( ) );
        }
        catch( KeyStoreException kse ) {
            // this never happens, because keystore has been loaded
        }
        if( cs == null ) {
            MessageFormat form = new MessageFormat( ( "Certificate chain not found for: alias.  alias must reference a valid KeyStore key entry containing a private key and corresponding public key certificate chain." ) );
            Object[] source = { param.getAlias( ), param.getAlias( ) };
            throw new JarSignerException( form.format( source ) );
        }

        certChain = new X509Certificate[ cs.length ];
        for( int i = 0; i < cs.length; i++ ) {
            if( !( cs[i] instanceof X509Certificate ) ) {
                throw new JarSignerException( ( "found non-X.509 certificate in signer's chain" ) );
            }
            certChain[i] = (X509Certificate) cs[i];
        }

        // order the cert chain if necessary (put user cert first,
        // root-cert last in the chain)
        X509Certificate userCert = (X509Certificate) param.getKeyStore( ).getCertificate( param.getAlias( ) );

        // check validity of signer certificate
        try {
            userCert.checkValidity( );

            if( userCert.getNotAfter( ).getTime( ) < System.currentTimeMillis( ) + SIX_MONTHS ) {
                System.out.println( "hasExpiringCert ERROR - This jar contains entries whose signer certificate will expire within six months. " );
            }
        }
        catch( CertificateExpiredException cee ) {
            System.out.println( "hasExpiredCert ERROR - The signer certificate has expired. " );

        }
        catch( CertificateNotYetValidException cnyve ) {
            System.out.println( "notYetValidCert ERROR - The signer certificate is not yet valid. " );
        }

        checkCertUsage( userCert, null );

        if( !userCert.equals( certChain[0] ) ) {
            // need to order ...
            X509Certificate[] certChainTmp = new X509Certificate[ certChain.length ];
            certChainTmp[0] = userCert;
            Principal issuer = userCert.getIssuerDN( );
            for( int i = 1; i < certChain.length; i++ ) {
                int j;
                // look for the cert whose subject corresponds to the
                // given issuer
                for( j = 0; j < certChainTmp.length; j++ ) {
                    if( certChainTmp[j] == null )
                        continue;
                    Principal subject = certChainTmp[j].getSubjectDN( );
                    if( issuer.equals( subject ) ) {
                        certChain[i] = certChainTmp[j];
                        issuer = certChainTmp[j].getIssuerDN( );
                        certChainTmp[j] = null;
                        break;
                    }
                }
                if( j == certChainTmp.length ) {
                    throw new JarSignerException( ( "incomplete certificate chain" ) );
                }

            }
            certChain = certChainTmp; // ordered
        }

        PrivateKey privateKey = null;
        Key key = null;

        key = param.getKeyStore( ).getKey( param.getAlias( ), param.getKeyPass( ) );

        if( !( key instanceof PrivateKey ) ) {
            MessageFormat form = new MessageFormat( ( "key associated with alias not a private key" ) );
            Object[] source = { param.getAlias( ) };
            throw new JarSignerException( form.format( source ) );
        }
        else {
            privateKey = (PrivateKey) key;
        }

        return new Block( this, privateKey, sigalg, certChain, externalSF, tsaUrl, tsaCert, signingMechanism, param, zipFile );
    }

    public static class Block {

        private byte[] block;

        private String blockFileName;

        /*
         * Construct a new signature block.
         */
        Block( SignatureFile sfg, PrivateKey privateKey, String sigalg, X509Certificate[] certChain, boolean externalSF, String tsaUrl, X509Certificate tsaCert, TimestampedSigner signingMechanism, JSParameters args, ZipFile zipFile ) throws NoSuchAlgorithmException, InvalidKeyException, IOException, SignatureException, CertificateException, JarSignerException {

            Principal issuerName = certChain[0].getIssuerDN( );
            if( !( issuerName instanceof X500Name ) ) {
                // must extract the original encoded form of DN for subsequent
                // name comparison checks (converting to a String and back to
                // an encoded DN could cause the types of String attribute
                // values to be changed)
                X509CertInfo tbsCert = new X509CertInfo( certChain[0].getTBSCertificate( ) );
                issuerName = (Principal) tbsCert.get( CertificateIssuerName.NAME + "." + CertificateIssuerName.DN_NAME );
            }
            //BigInteger serial = certChain[0].getSerialNumber( );

            String digestAlgorithm;
            String signatureAlgorithm;
            String keyAlgorithm = privateKey.getAlgorithm( );
            /*
             * If no signature algorithm was specified, we choose a
             * default that is compatible with the private key algorithm.
             */
            if( sigalg == null ) {

                if( keyAlgorithm.equalsIgnoreCase( "DSA" ) )
                    digestAlgorithm = "SHA1";
                else if( keyAlgorithm.equalsIgnoreCase( "RSA" ) )
                    digestAlgorithm = "SHA1";
                else {
                    throw new JarSignerException( "private key is not a DSA or " + "RSA key" );
                }
                signatureAlgorithm = digestAlgorithm + "with" + keyAlgorithm;
            }
            else {
                signatureAlgorithm = sigalg;
            }

            // check common invalid key/signature algorithm combinations
            String sigAlgUpperCase = signatureAlgorithm.toUpperCase( );
            if( ( sigAlgUpperCase.endsWith( "WITHRSA" ) && !keyAlgorithm.equalsIgnoreCase( "RSA" ) ) || ( sigAlgUpperCase.endsWith( "WITHDSA" ) && !keyAlgorithm.equalsIgnoreCase( "DSA" ) ) ) {
                throw new SignatureException( "private key algorithm is not compatible with signature algorithm" );
            }

            blockFileName = "META-INF/" + sfg.getBaseName( ) + "." + keyAlgorithm;

            // AlgorithmId sigAlg = AlgorithmId.get( signatureAlgorithm );
            //AlgorithmId digEncrAlg = AlgorithmId.get( keyAlgorithm );

            Signature sig = Signature.getInstance( signatureAlgorithm );
            sig.initSign( privateKey );

            ByteArrayOutputStream baos = new ByteArrayOutputStream( );
            sfg.write( baos );

            byte[] content = baos.toByteArray( );

            sig.update( content );
            byte[] signature = sig.sign( );

            // Timestamp the signature and generate the signature block file
            if( signingMechanism == null ) {
                signingMechanism = new TimestampedSigner( );
            }
            URI tsaUri = null;
            try {
                if( tsaUrl != null ) {
                    tsaUri = new URI( tsaUrl );
                }
            }
            catch( URISyntaxException e ) {
                IOException ioe = new IOException( );
                ioe.initCause( e );
                throw ioe;
            }

            // Assemble parameters for the signing mechanism
            JarSignerParameters params = new JarSignerParameters( tsaUri, tsaCert, signature, signatureAlgorithm, certChain, content, zipFile );

            // Generate the signature block
            block = signingMechanism.generateSignedData( params, externalSF, ( tsaUrl != null || tsaCert != null ) );
        }

        /*
         * get block file name.
         */
        public String getMetaName( ) {

            return blockFileName;
        }

        /**
         * Writes the block file to the specified OutputStream.
         * 
         * @param out
         *            the output stream
         * @exception IOException
         *                if an I/O error has occurred
         */

        public void write( OutputStream out ) throws IOException {

            out.write( block );
        }
    }
}

/*
 * This object encapsulates the parameters used to perform content signing.
 */
class JarSignerParameters {

    private URI tsa;

    private X509Certificate tsaCertificate;

    private byte[] signature;

    private String signatureAlgorithm;

    private X509Certificate[] signerCertificateChain;

    private byte[] content;

    private ZipFile source;

    /**
     * Create a new object.
     */
    JarSignerParameters( URI tsa, X509Certificate tsaCertificate, byte[] signature, String signatureAlgorithm, X509Certificate[] signerCertificateChain, byte[] content, ZipFile source ) {

        if( signature == null || signatureAlgorithm == null || signerCertificateChain == null ) {
            throw new NullPointerException( );
        }
        this.tsa = tsa;
        this.tsaCertificate = tsaCertificate;
        this.signature = signature;
        this.signatureAlgorithm = signatureAlgorithm;
        this.signerCertificateChain = signerCertificateChain;
        this.content = content;
        this.source = source;
    }

    /**
     * Retrieves the identifier for a Timestamping Authority (TSA).
     * 
     * @return The TSA identifier. May be null.
     */
    public URI getTimestampingAuthority( ) {

        return tsa;
    }

    /**
     * Retrieves the certificate for a Timestamping Authority (TSA).
     * 
     * @return The TSA certificate. May be null.
     */
    public X509Certificate getTimestampingAuthorityCertificate( ) {

        return tsaCertificate;
    }

    /**
     * Retrieves the signature.
     * 
     * @return The non-null signature bytes.
     */
    public byte[] getSignature( ) {

        return signature;
    }

    /**
     * Retrieves the name of the signature algorithm.
     * 
     * @return The non-null string name of the signature algorithm.
     */
    public String getSignatureAlgorithm( ) {

        return signatureAlgorithm;
    }

    /**
     * Retrieves the signer's X.509 certificate chain.
     * 
     * @return The non-null array of X.509 public-key certificates.
     */
    public X509Certificate[] getSignerCertificateChain( ) {

        return signerCertificateChain;
    }

    /**
     * Retrieves the content that was signed.
     * 
     * @return The content bytes. May be null.
     */
    public byte[] getContent( ) {

        return content;
    }

    /**
     * Retrieves the original source ZIP file before it was signed.
     * 
     * @return The original ZIP file. May be null.
     */
    public ZipFile getSource( ) {

        return source;
    }
}
