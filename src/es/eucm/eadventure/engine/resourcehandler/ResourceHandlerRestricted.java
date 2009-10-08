/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author López Mañas, E., Pérez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, available at http://e-adventure.e-ucm.es.
 * 
 * <e-Adventure> is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * <e-Adventure>; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */
package es.eucm.eadventure.engine.resourcehandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

    @Override
    public URL getResourceAsURL( String path ) {

        if( !path.startsWith( "/" ) )
            path = "/" + path;
        InputStream is = this.getClass( ).getResourceAsStream( path );
        byte[] data = new byte[ 1024 ];
        File osFile = new File( path.substring( path.lastIndexOf( "/" ) + 1 ) );

        boolean copy = true;
        for( TempFile file : tempFiles ) {
            if( file.getOriginalAssetPath( ).equals( path ) ) {
                copy = false;
                break;
            }
        }

        if( copy ) {
            // Search the file name. If exists, change name
            int i = 0;
            while( osFile.exists( ) ) {
                i++;
                osFile = new File( i + "_" + path.substring( path.lastIndexOf( "/" ) + 1 ) );
            }
            TempFile tempFile = new TempFile( ( osFile ).getAbsolutePath( ) );
            tempFile.setOriginalAssetPath( path );
            tempFiles.add( tempFile );
        }

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

    }

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