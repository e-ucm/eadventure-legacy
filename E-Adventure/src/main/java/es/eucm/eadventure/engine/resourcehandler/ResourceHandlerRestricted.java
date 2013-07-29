/*******************************************************************************
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the e-UCM
 *          research group.
 *   
 *    Copyright 2005-2012 e-UCM research group.
 *  
 *     e-UCM is a research group of the Department of Software Engineering
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
 * This file is part of eAdventure, version 1.5.
 * 
 *   You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with Adventure.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.engine.resourcehandler;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * This resource handler loads files for being used on an applet
 */
class ResourceHandlerRestricted extends ResourceHandler {

    /**
     * Singleton
     */
    private static ResourceHandlerRestricted instance;

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.resourcehandler.ResourceHandler#getInstance()
     */
    public static ResourceHandler getInstance( ) {

        return instance;
    }

    public static void create( ) {

        instance = new ResourceHandlerRestricted( );
    }

    public static void delete( ) {

        SecurityManager sm = System.getSecurityManager( );
        if( instance != null && instance.tempFiles != null ) {
            for( File file : instance.tempFiles ) {
                try {
                    sm.checkDelete( file.getPath( ) );
                    file.delete( );
                }
                catch( Exception e ) {
                }
            }
        }
        instance = null;
    }

    /**
     * Empty constructor
     */
    private ResourceHandlerRestricted( ) {

    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.resourcehandler.ResourceHandler#setZipFile(java.lang.String)
     */
    @Override
    public void setZipFile( String zipFilename ) {

        try {
            ResourceHandler.zipPath = zipFilename;
            /*if( !zipFilename.startsWith( "/" ) ) {
                zipFilename = "/" + zipFilename;
            }*/

            /*URL url =this.getClass( ).getResource( "/"+zipFilename );
            //System.out.println((url==null)?"URL IS NULL":"URL IS NOT NULL");
            
            File file = new File(zipFilename);
            //if (ResourceHandler.extraRestriction){
                InputStream xmlInputStream = this.getClass( ).getResourceAsStream( "/"+zipFilename );
            
                FileOutputStream xmlOutputStream = new FileOutputStream( file );
                
                int byt;                
                while ((byt = xmlInputStream.read()) != -1) {
                    xmlOutputStream.write(byt);
                }
                
                xmlOutputStream.close();
            //}*/

            zipFile = null;
            //new ZipFile( file );
            /* } catch( ZipException e ) {
                 e.printStackTrace( );
             } catch( IOException e ) {
                 e.printStackTrace( );
             }*/
        }
        catch( Exception e ) {

        }
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.resourcehandler.ResourceHandler#getOutputStream(java.lang.String)
     */
    @Override
    public OutputStream getOutputStream( String path ) {

        return null;
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.resourcehandler.ResourceHandler#getResourceAsStream(java.lang.String)
     */
    @Override
    public InputStream getResourceAsStream( String path ) {
        
        if( !path.startsWith( "/" ) ) {
            path = "/" + path;
        }

        InputStream is = this.getClass( ).getResourceAsStream( path );

        return is;
    }

    //@Override
    /*public URL getResourceAsURL( String path ) {

        if( !path.startsWith( "/" ) )
            path = "/" + path;
        InputStream is = this.getClass( ).getResourceAsStream( path );
        byte[] data = new byte[ 1024 ];
        String tempFileFolder = es.eucm.eadventure.common.auxiliar.File.getTemporalFileFolder( );
        File osFile;
        System.out.println( tempFileFolder );
        if (tempFileFolder!=null){
            File parentFolder = new File (tempFileFolder);
            osFile = new File( parentFolder, path.substring( path.lastIndexOf( "/" ) + 1 ) );
        } else {
            osFile = new File( path.substring( path.lastIndexOf( "/" ) + 1 ) );
        }
        //File osFile = File.createTempFile( prefix, suffix )
        System.out.println( osFile.getAbsolutePath( ) );
        
        boolean copy = true;
        for( TempFile file : tempFiles ) {
            if( file.getOriginalAssetPath( ).equals( path ) ) {
                osFile = file;
                copy = false;
                break;
            }
        }

        if( copy ) {
            // Search the file name. If exists, change name
            int i = 0;
            while( osFile.exists( ) ) {
                i++;
                if (tempFileFolder!=null){
                    File parentFolder = new File (tempFileFolder);
                    osFile = new File( parentFolder, i + "_" + path.substring( path.lastIndexOf( "/" ) + 1 ) );
                } else {
                    osFile = new File( i + "_" + path.substring( path.lastIndexOf( "/" ) + 1 ) );
                }
                
            }
            TempFile tempFile = new TempFile( ( osFile ).getAbsolutePath( ) );
            tempFile.setOriginalAssetPath( path );
            tempFiles.add( tempFile );
        }

        System.out.println( "Secon execution "+osFile.getAbsolutePath( ) );
        
        FileOutputStream os;
        try {
            if( copy ) {
                os = new FileOutputStream( new File( osFile.getAbsolutePath( ) ) );
                int length = 0;
                while( ( length = is.read( data ) ) != -1 ) {
                    os.write( data, 0, length );
                }
                os.close( );
                is.close( );
            }
            return osFile.toURI( ).toURL( );
        }
        catch( FileNotFoundException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace( );
            return null;
        }
        catch( IOException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace( );
            return null;
        }

    }*/

    @Override
    public URL getResourceAsURLFromZip( String path ) {

        if( !path.startsWith( "/" ) ) {
            path = "/" + path;
        }
        return this.getClass( ).getResource( path );
    }

    public InputStream buildInputStream( String filePath ) {

        return getResourceAsStream( filePath );
    }

    public String[] listNames( String filePath ) {

        return new String[ 0 ];
    }
}
