package es.eucm.eadventure.engine.core.control.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import es.eucm.eadventure.common.auxiliar.ReleaseFolders;

/**
 * This class is the one developed by Bruno, used in the Editor, modified to allow the use in 
 * the Engine
 */

public class ConfigData {

    private boolean showStartDialog;
    
    private static ConfigData instance;

    private String configFile;
    
    private String exportsPath;
    
    private String reportsPath;

    /**
     * Stores the file that contains the GUI strings.
     */
    private String languageFile;
    
    /**
     * Stores the file that contains the about document.
     */
    private String aboutFile;
    
    /**
     * Stores the file that contains the loading screen.
     */
    private String loadingImage;

    /**
     * Stores whether the item references must be displayed by default.
     */
    private boolean showItemReferences;

    /**
     * Stores whether the character references must be displayed by default.
     */
    private boolean showNPCReferences;

    public static boolean showNPCReferences( ) {
        return instance.showNPCReferences;
    }

    public static boolean showItemReferences( ) {
        return instance.showItemReferences;
    }

    public static String getLanguangeFile( ) {
        return instance.languageFile;
    }
    
    public static String getAboutFile( ) {
        return instance.aboutFile;
    }
    
    public static String getLoadingImage( ) {
        return instance.loadingImage;
    }

    public static boolean showStartDialog( ) {
        return instance.showStartDialog;
    }

    public static void setShowNPCReferences( boolean b ) {
        instance.showNPCReferences = b;
    }

    public static void setShowItemReferences( boolean b ) {
        instance.showItemReferences = b;
    }

    public static void setLanguangeFile( String language, String about, String loadingImage ) {
        instance.languageFile = language;
        instance.aboutFile = about;
        instance.loadingImage = loadingImage;
    }
    
    public static void setAboutFile( String s ) {
        instance.aboutFile = s;
    }
    
    public static void setLoadingImage( String s ) {
        instance.loadingImage = s;
    }

    public static void setShowStartDialog( boolean b ) {
        instance.showStartDialog = b;
    }

    public static void loadFromXML( String configFile ) {
        instance = new ConfigData( configFile );
    }

    public static void loadFromData( String languageFile, String aboutFile ){
        instance = new ConfigData( languageFile, aboutFile );
    }
    
    public static void storeToXML( ) {

        if (instance.configFile!=null){
            // Load the current configuration
            Properties configuration = new Properties( );
            configuration.setProperty( "LanguageFile", instance.languageFile );
            configuration.setProperty( "AboutFile", instance.aboutFile );
    		configuration.setProperty( "ExportsDirectory", instance.exportsPath );
    		configuration.setProperty( "ReportsDirectory", instance.reportsPath );
            // Store the configuration into a file
            try {
                configuration.storeToXML( new FileOutputStream( instance.configFile ), "<e-Adventure> engine configuration" );
            } catch( FileNotFoundException e ) {} catch( IOException e ) {}
        }
    }

    private ConfigData( String fileName ) {
        this.configFile = fileName;
        Properties configuration = new Properties( );
        try {
            configuration.loadFromXML( new FileInputStream( configFile ) );
            languageFile = configuration.getProperty( "LanguageFile" );
            aboutFile = configuration.getProperty( "AboutFile" );
            exportsPath = configuration.getProperty("ExportsDirectory");
            if (exportsPath!=null)
            	ReleaseFolders.setExportsPath(exportsPath);
            reportsPath = configuration.getProperty("ReportsDirectory");
            if (reportsPath!=null)
            	ReleaseFolders.setReportsPath(reportsPath);
        } catch( InvalidPropertiesFormatException e ) {} catch( FileNotFoundException e ) {} catch( IOException e ) {}

    }

   private ConfigData ( String languageFile, String aboutFile ){
       configFile = null;
       this.languageFile = languageFile;
       this.aboutFile = aboutFile;
   }

}
