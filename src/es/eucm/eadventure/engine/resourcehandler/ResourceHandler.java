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

import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.imageio.ImageIO;

import es.eucm.eadventure.common.loader.InputStreamCreator;

/**
 * Abstract class in charge of loading the resources (files) of the game
 */
public abstract class ResourceHandler implements InputStreamCreator {

    /**
     * Path of the default background image
     */
    public static final String DEFAULT_BACKGROUND = "gui/defaultassets/NRB_background.jpg";

    /**
     * Path of the default slides fileset
     */
    public static final String DEFAULT_SLIDES = "gui/defaultassets/NRB_slides_01.jpg";

    /**
     * Path of the default animation fileset
     */
    public static final String DEFAULT_ANIMATION = "gui/defaultassets/NRB_animation_01.png";

    /**
     * Path of the default image
     */
    public static final String DEFAULT_IMAGE = "gui/defaultassets/NRB_image.png";

    /**
     * Path of the default icon image
     */
    public static final String DEFAULT_ICON = "gui/defaultassets/NRB_icon.png";

    /**
     * Path of the default foreground image
     */
    public static final String DEFAULT_FOREGROUND = "gui/defaultassets/NRB_foreground.png";

    /**
     * Path of the default hardmap image
     */
    public static final String DEFAULT_HARDMAP = "gui/defaultassets/NRB_hardmap.png";

    /**
     * Stores if the resource handler should load the files for an applet or for
     * an application
     */
    protected static boolean isRestrictedMode = false;

    protected static boolean extraRestriction = true;

    /**
     * Stores the zip file containing the needed files for the game
     */
    protected static ZipFile zipFile = null;

    /**
     * Stores the zip file containing the needed files for the game
     */
    protected static String zipPath = null;

    protected static boolean isExternalMode = false;

    /**
     * Returns the instance of the resource handler
     * 
     * @return Instance of the resource handler
     */
    public static ResourceHandler getInstance( ) {

        ResourceHandler handler = null;
        if( !isExternalMode && isRestrictedMode ) {
            handler = ResourceHandlerRestricted.getInstance( );
        }
        else if( !isExternalMode ) {
            handler = ResourceHandlerUnrestricted.getInstance( );
        }
        else {
            handler = ResourceHandlerExternalSource.getInstance( );
        }
        return handler;
    }

    /**
     * Sets the restricted value of the resource handler
     * 
     * @param isRestrictedMode
     *            New value for restricted
     */
    public static void setRestrictedMode( boolean isRestrictedMode, boolean extra ) {

        ResourceHandler.isRestrictedMode = isRestrictedMode;
        ResourceHandler.extraRestriction = extra;
        if( isRestrictedMode ) {
            ResourceHandlerRestricted.create( );
        }
        else {
            ResourceHandlerUnrestricted.create( );
        }
    }

    public static void setRestrictedMode( boolean isRestrictedMode ) {

        setRestrictedMode( isRestrictedMode, true );
    }

    public static void setExternalMode( InputStreamCreator isCreator ) {

        isExternalMode = true;
        ResourceHandlerExternalSource.create( isCreator );
    }

    /**
     * Deletes the resource handler.
     */
    public static void delete( ) {

        ResourceHandlerRestricted.delete( );
        ResourceHandlerUnrestricted.delete( );
    }

    /**
     * Sets the new zip file to load the game files
     * 
     * @param zipFilename
     *            Filename of the zip
     */
    public abstract void setZipFile( String zipFilename );

    /**
     * Closes the open zip file in use.
     */
    public void closeZipFile( ) {

        try {
            if( zipFile != null )
                zipFile.close( );
        }
        catch( IOException e ) {
            e.printStackTrace( );
        }
    }

    /**
     * Returns if the resource handler is restricted (for use with applets)
     * 
     * @return True if the resource handler is restricted, false otherwise
     */
    public boolean isRestrictedMode( ) {

        return isRestrictedMode;
    }

    /**
     * Returns an output stream specified
     * 
     * @param path
     *            Name of the file
     * @return The output stream if it could be loaded, null otherwise
     */
    public abstract OutputStream getOutputStream( String path );

    /**
     * Loads a file as an input stream
     * 
     * @param path
     *            Path of the file
     * @return The file as an input stream
     */
    public abstract InputStream getResourceAsStream( String path );

    /**
     * Loads a file as an image
     * 
     * @param path
     *            Path of the file
     * @return The file as an image
     */
    public Image getResourceAsImage( String path ) {

        Image image = null;

        if( !path.startsWith( "/" ) ) {
            path = "/" + path;
        }

        try {
            InputStream inputStream = getResourceAsStream( path );
            if( inputStream != null ) {
                image = ImageIO.read( inputStream );
                inputStream.close( );
            }
        }
        catch( IOException e ) {
            e.printStackTrace( );
        }

        return image;
    }

    /**
     * Loads a file as an input stream from the Zip file
     * 
     * @param path
     *            Path of the file
     * @return The file as an input stream
     */
    /*public InputStream getResourceAsStreamFromZip( String path ) {
        InputStream inputStream = null;

        try {
        
            // Load the input stream from the file (if it exists)
            if( new File( path ).exists( ) )
                inputStream = new FileInputStream( path );
            else {
                File file = new File( zipPath+File.separator+ path ); 
                if( !file.exists( ) ){
                    System.out.println(" ARCHIVO NO EXISTE : "+file.getAbsolutePath( ) );
                    
                }
                if (!file.getParentFile( ).exists( )){
                    System.out.println(" PADRE NO EXISTE"+file.getParentFile( ).getAbsolutePath( ) );
                }
                inputStream = new FileInputStream( zipPath + "/" + path );
            }
            
        } catch( FileNotFoundException e ) {
            e.printStackTrace( );
        }

        return inputStream;
        
    }*/
    public InputStream getResourceAsStreamFromZip( String path ) {

        InputStream inputStream = null;

        if( path.startsWith( "/" ) )
            path = path.substring( 1 );
        
        try {
            if( zipFile != null && zipFile.getEntry( path ) != null )
                inputStream = zipFile.getInputStream( zipFile.getEntry( path ) );
            else {
                String ext = getExtension (path);
                String path2 = setExtension(path, ext.toLowerCase( ));
                if( zipFile != null && zipFile.getEntry( path2 ) != null )
                    inputStream = zipFile.getInputStream( zipFile.getEntry( path2 ) );
                else {
                    path2=setExtension(path, ext.toUpperCase( ));
                    if( zipFile != null && zipFile.getEntry( path2 ) != null )
                        inputStream = zipFile.getInputStream( zipFile.getEntry( path2 ) );
                    else
                        inputStream = getResourceAsStream( path );
                }
            }
                
        }
        catch( IOException e ) {
            e.printStackTrace( );
            inputStream = null;
        }

        if (inputStream==null && zipFile!=null){
            Enumeration<? extends ZipEntry> en = zipFile.entries( );
            while (en.hasMoreElements( )){
                ZipEntry entry=en.nextElement( );
                String lowerCaseEntryName = entry.getName( ).toLowerCase( );
                if (lowerCaseEntryName.startsWith( "assets" ) || lowerCaseEntryName.startsWith( "gui" )){
                    if (lowerCaseEntryName.equals( path.toLowerCase( ) )){
                        try {
                            inputStream = zipFile.getInputStream( entry );
                        }
                        catch( IOException e ) {
                            inputStream=null;
                            inputStream = getResourceAsStream( entry.getName( ) );
                        }
                    }
                }
            }
            
        }
        
        return inputStream;
    }

    /**
     * Loads a file as an image from the Zip file
     * 
     * @param path
     *            Path of the file
     * @return The file as an image
     */
    public Image getResourceAsImageFromZip( String path ) {

        Image image = null;

        if( path.startsWith( "/" ) ) {
            path = path.substring( 1 );
        }

        try {
            InputStream inputStream = getResourceAsStreamFromZip( path );
            if( inputStream != null ) {
                image = ImageIO.read( inputStream );
                inputStream.close( );
            }
            else {
                //System.out.println( "IMAGE NULL = "+path );
            }
        }
        catch( IOException e ) {
            e.printStackTrace( );
        }

        return image;
    }

    //////////////////////////////////////////NEW/////////////////////////////////////////////////////////
    public abstract URL getResourceAsURLFromZip( String path );

    /**
     * Returns the extension of the given asset.
     * 
     * @param assetPath
     *            Path to the asset
     * @return Extension of the file
     */
    public static String getExtension( String assetPath ) {

        return assetPath.substring( assetPath.lastIndexOf( '.' ) + 1, assetPath.length( ) );
    }
    
    public static String setExtension( String assetPath, String extension ) {
        if (assetPath.lastIndexOf( '.' )>=0)
            assetPath=assetPath.substring( 0, assetPath.lastIndexOf( '.' ) + 1 )+extension;
        else
            assetPath+="."+extension;
        return assetPath;
    }

    private static Random random = new Random( );

    private static int MAX_RANDOM = 100000;

    protected ArrayList<TempFile> tempFiles = new ArrayList<TempFile>( );

    private static final String TEMP_FILE_NAME = "$temp_ead_";

    //public abstract URL getResourceAsURL( String path );

    protected String generateTempFileAbsolutePath( String extension ) {

        String tempDirectory = null;
        if( System.getenv( "TEMP" ) != null && !System.getenv( "TEMP" ).equals( "" ) ) {
            tempDirectory = System.getenv( "TEMP" );
        }
        else if( System.getenv( "HOME" ) != null && !System.getenv( "HOME" ).equals( "" ) ) {
            tempDirectory = System.getenv( "HOME" );
        }
        else if( System.getenv( "ROOT" ) != null && !System.getenv( "ROOT" ).equals( "" ) ) {
            tempDirectory = System.getenv( "ROOT" );
        }
        else {
            tempDirectory = "";
        }

        String fileName = TEMP_FILE_NAME + random.nextInt( MAX_RANDOM ) + "." + extension;
        File file = new File( tempDirectory + java.io.File.separatorChar + fileName );
        while( file.exists( ) ) {
            fileName = TEMP_FILE_NAME + random.nextInt( MAX_RANDOM ) + "." + extension;
            file = new File( tempDirectory + java.io.File.separatorChar + fileName );
        }
        return tempDirectory + java.io.File.separatorChar + fileName;

    }

    public boolean isExtraRestriction( ) {

        return ResourceHandler.extraRestriction;
    }

    public URL buildURL( String path ) {

        return getResourceAsURLFromZip( path );
    }

    public class TempFile extends java.io.File {

        private String originalAssetPath;

        /**
         * @return the originalAssetPath
         */
        public String getOriginalAssetPath( ) {

            return originalAssetPath;
        }

        /**
         * @param originalAssetPath
         *            the originalAssetPath to set
         */
        public void setOriginalAssetPath( String originalAssetPath ) {

            this.originalAssetPath = originalAssetPath;
        }

        public TempFile( String pathname ) {

            super( pathname );
        }

        /**
         * 
         */
        private static final long serialVersionUID = 896282044492374745L;

    }
    
    public URL getResourceAsURL( String path ) {

        if( !path.startsWith( "/" ) )
            path = "/" + path;
        //InputStream is = this.getClass( ).getResourceAsStream( path );
        InputStream is = buildInputStream( path );
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

    }
    
}
