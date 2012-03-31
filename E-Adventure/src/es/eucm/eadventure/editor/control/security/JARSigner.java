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
package es.eucm.eadventure.editor.control.security;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.Set;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import sun.security.tools.KeyTool;
import es.eucm.eadventure.common.auxiliar.File;
import es.eucm.eadventure.common.auxiliar.ReportDialog;

public class JARSigner {

    private static final String KEY_STORE_NAME = "eAdventure.keystore";

    private static final String KEY_STORE_PASSWORD = "3Adv3ntur3_K3YSTOR3";

    private static KeyStore keyStore;

    private static SecureRandom pwdGenerator;

    private static void initRandom( ) {

        SecureRandom sr = new SecureRandom( );
        byte[] seed = sr.generateSeed( 12 );
        pwdGenerator = new SecureRandom( );
        pwdGenerator.setSeed( seed );
    }

    private static boolean init( ) {

        boolean created = false;
        try {
            keyStore = KeyStore.getInstance( KeyStore.getDefaultType( ) );
            keyStore.load( new FileInputStream( KEY_STORE_NAME ), KEY_STORE_PASSWORD.toCharArray( ) );
            created = true;
            initRandom( );

        }
        catch( Exception e ) {
            try {
                keyStore.load( null, KEY_STORE_PASSWORD.toCharArray( ) );
                keyStore.store( new FileOutputStream( KEY_STORE_NAME ), KEY_STORE_PASSWORD.toCharArray( ) );
                created = true;
                initRandom( );
            }
            catch( Exception e1 ) {
                ReportDialog.GenerateErrorReport( e1, true, "UNKNOWERROR" );
            }
        }
        return created;
    }

    private static String validateDistinguisedNameEntry( String entry ) {

        return entry.replace( ",", "\\," );
    }

    private static String generatePassword( ) {

        String password = "";
        for( int i = 0; i < 8; i++ ) {
            //Generate
            switch( pwdGenerator.nextInt( 4 ) ) {
                case 0: {
                    //INT
                    password += Integer.toString( pwdGenerator.nextInt( 10 ) );
                    break;
                }
                case 1: {
                    //LOWER CHAR
                    password += (char) ( pwdGenerator.nextInt( 'z' - 'a' + 1 ) + 'a' );
                    break;
                }
                case 2: {
                    //UPPER CHAR
                    password += (char) ( pwdGenerator.nextInt( 'Z' - 'A' + 1 ) + 'A' );
                    break;
                }
                case 3: {
                    //SPECIAL CHARS: _ &
                    switch( pwdGenerator.nextInt( 2 ) ) {
                        case 0:
                            password += "_";
                            break;
                        case 1:
                            password += "&";
                            break;
                    }
                    break;
                }
            }
        }

        return password;
    }

    public static String generateAlias( String completeName ) {

        String alias = "";
        int i = 0;
        try {
            do {
                alias = "";

                for( char currentChar : completeName.toCharArray( ) ) {
                    if( ( currentChar >= 'a' && currentChar <= 'z' ) || ( currentChar >= 'A' && currentChar <= 'Z' ) || ( currentChar >= '0' && currentChar <= '9' ) ) {
                        alias += currentChar;
                    }
                    else
                        alias += "_";
                }
                alias += "_" + i;
                i++;
            } while( keyStore.containsAlias( alias ) );
        }
        catch( Exception e ) {
            alias = KEY_STORE_PASSWORD;
        }
        return alias;
    }
    public static boolean signJar( String completeName, String organization, String originJarPath, String signedJarPath) {
        return signJar(completeName, organization, originJarPath, signedJarPath, null, null);
    }
    
    public static boolean signJar( String completeName, String organization, String originJarPath, String signedJarPath,  String originJarPath2, String signedJarPath2) {

        boolean created = false;
        boolean initiated = init( );
        String alias = null;
        if( !initiated || completeName.length( ) < 6 ) {
            //REPORT ERROR
        }
        else {
            try {
                //Calculate dName
                String dName = "CN=" + validateDistinguisedNameEntry( completeName ) + ", OU=" + validateDistinguisedNameEntry( organization ) + ", O=" + validateDistinguisedNameEntry( organization );

                //Calculate password
                String password = generatePassword( );

                //Calculate alias
                alias = generateAlias( completeName );

                //Generate the key pair using KeyTool
                KeyTool.main( new String[] { "-genkeypair", "-alias", alias, "-dname", dName, "-keypass", password, "-validity", "1000", "-keystore", KEY_STORE_NAME, "-storepass", KEY_STORE_PASSWORD } );

                //Sign the jar
                
                if (new File(originJarPath2).exists()){
                    if (new File(originJarPath2).isFile( ))
                        System.out.println( "MIERDA PUTA DEL TO" );
                }
                
                if (signedJarPath2!=null && originJarPath2!=null )
                    es.eucm.eadventure.editor.control.security.jarsigner.Main.main( new String[] { "-keystore", KEY_STORE_NAME, "-storepass", KEY_STORE_PASSWORD, "-keypass", password, "-signedjar", new File( signedJarPath2 ).getAbsolutePath( ), "-sigfile", new File( originJarPath2 ).getAbsolutePath( ), alias } );

                es.eucm.eadventure.editor.control.security.jarsigner.Main.main( new String[] { "-keystore", KEY_STORE_NAME, "-storepass", KEY_STORE_PASSWORD, "-keypass", password, "-signedjar", new File( signedJarPath ).getAbsolutePath( ), "-sigfile", new File( originJarPath ).getAbsolutePath( ), alias } );
                //Verify it has been successfully signed
                JarFile signedJar = new JarFile( signedJarPath, true );
                Manifest manifest = signedJar.getManifest( );
                Set<String> entries = manifest.getEntries( ).keySet( );
                for( String entry : entries ) {
                    signedJar.getJarEntry( entry ).getAttributes( );
                }

                created = true;
                System.out.println( "Successfully signed Jar" );
            }
            catch( Exception e ) {
                ReportDialog.GenerateErrorReport( e, true, "UNKNOWERROR" );
                created = false;
            }

        }
        return created;

    }
}
