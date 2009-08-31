/**
 * <e-Adventure> is an <e-UCM> research project.
 * <e-UCM>, Department of Software Engineering and Artificial Intelligence.
 * Faculty of Informatics, Complutense University of Madrid (Spain).
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J.
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009
 * Web-site: http://e-adventure.e-ucm.es
 */

/*
    Copyright (C) 2004-2009 <e-UCM> research group

    This file is part of <e-Adventure> project, an educational game & game-like 
    simulation authoring tool, availabe at http://e-adventure.e-ucm.es. 

    <e-Adventure> is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    <e-Adventure> is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with <e-Adventure>; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

*/
package es.eucm.eadventure.engine.resourcehandler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import javax.media.MediaLocator;

import de.schlichtherle.io.File;
import es.eucm.eadventure.engine.resourcehandler.zipurl.ZipURL;

/**
 * This resource handler loads files for being used on a standard java application
 */
class ResourceHandlerUnrestricted extends ResourceHandler {
    
    /**
     * Singleton
     */
    private static ResourceHandlerUnrestricted instance;

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.resourcehandler.ResourceHandler#getInstance()
     */
    public static ResourceHandler getInstance( ) {
        return instance;
    }
    public static void create(){
        instance = new ResourceHandlerUnrestricted( );
    }
    public static void delete(){
        if (instance!=null && instance.tempFiles!=null){
            for (TempFile file: instance.tempFiles){
                file.delete( );
            }
        }
        instance = null;
    }

    /**
     * Empty constructor
     */
    private ResourceHandlerUnrestricted( ) {
    }
    
    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.resourcehandler.ResourceHandler#setZipFile(java.lang.String)
     */
    public void setZipFile( String zipFilename ) {
        try {
            this.zipPath=zipFilename;
            zipFile = new ZipFile( zipFilename );
            /*Enumeration entries = zipFile.entries( ); 
            int n =0;
            while (entries.hasMoreElements( )){
                n++;
                ZipEntry entry = (ZipEntry)entries.nextElement( );
                System.out.println( n+" "+entry.getName( )+" - "+(entry.isDirectory( )?"DIR":""));
            }*/
        } catch( ZipException e ) {
            e.printStackTrace( );
        } catch( IOException e ) {
            e.printStackTrace( );
        }
    }
    
    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.resourcehandler.ResourceHandler#getOutputStream(java.lang.String)
     */
    public OutputStream getOutputStream( String path ) {
        OutputStream os = null;

        if( path.startsWith( "/" ) ) {
            path = path.substring( 1 );
        }

        try {
            os = new FileOutputStream( path );
        } catch( SecurityException e ) {
            e.printStackTrace( );
        } catch( FileNotFoundException e ) {
            os = null;
        }

        return os;
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.resourcehandler.ResourceHandler#getResourceAsStream(java.lang.String)
     */
    public InputStream getResourceAsStream( String path ) {

        InputStream is = null;

        if( path.startsWith( "/" ) ) {
            path = path.substring( 1 );
        }

        try {
            is = new FileInputStream( path );
        } catch( SecurityException e ) {
            e.printStackTrace( );
        } catch( FileNotFoundException e ) {
            is = null;
        }

        return is;
    }
    @Override
    public MediaLocator getResourceAsMediaLocator( String path ) {
        // Add the file
        String absolutePath=generateTempFileAbsolutePath( getExtension(path ) );
        File sourceFile = new File( zipPath, path );
        File destinyFile = new File( absolutePath );
        if( sourceFile.exists( ) )
            sourceFile.copyTo( destinyFile );
        
        
        if (destinyFile.exists( ))
            try {
                
                MediaLocator mediaLocator= new MediaLocator(destinyFile.toURI( ).toURL( ));
                TempFile tempFile = new TempFile(destinyFile.getAbsolutePath());
                tempFile.setOriginalAssetPath(path);
                tempFiles.add( tempFile );
                return mediaLocator;
            } catch( MalformedURLException e ) {
                e.printStackTrace();
                return null;
            }
        else
            return null;
    }
    
    public URL getResourceAsURLFromZip( String path ){
        try {
            return ZipURL.createAssetURL( zipPath, path );
        } catch( MalformedURLException e ) {
            return null;
        }
    }

	public InputStream buildInputStream( String filePath) {
		return getResourceAsStreamFromZip ( filePath );
	}

	public String[] listNames( String filePath ) {
		File dir = new File ( zipPath, filePath );
		return dir.list();
	}
	
    /**
     * Extracts the resource and get it copied to a file in the local system. 
     * Required when an asset cannot be loaded directly from zip
     * @param assetPath
     * @return
     *      The absolute path of the destiny file where the asset was copied
     */
    public URL getResourceAsURL (String assetPath){
        URL toReturn =null;
        try{
            String filePath = generateTempFileAbsolutePath (getExtension(assetPath));
            File sourceFile = new File(zipPath, assetPath);
            File destinyFile = new File(filePath);
            if (sourceFile.copyTo( destinyFile )){
                toReturn = destinyFile.toURI().toURL();
                TempFile tempFile = new TempFile(destinyFile.getAbsolutePath());
                tempFile.setOriginalAssetPath(assetPath);
                tempFiles.add( tempFile );
            }
            else
                toReturn = null;
        } catch (Exception e){
            toReturn = null;
        }
            
        return toReturn;
    }
}
