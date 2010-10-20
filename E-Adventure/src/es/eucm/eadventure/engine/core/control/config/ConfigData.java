/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *         research group.
 *  
 *   Copyright 2005-2010 <e-UCM> research group.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *         http://e-adventure.e-ucm.es/contributors
 * 
 *   <e-UCM> is a research group of the Department of Software Engineering
 *         and Artificial Intelligence at the Complutense University of Madrid
 *         (School of Computer Science).
 * 
 *         C Profesor Jose Garcia Santesmases sn,
 *         28040 Madrid (Madrid), Spain.
 * 
 *         For more info please visit:  <http://e-adventure.e-ucm.es> or
 *         <http://www.e-ucm.es>
 * 
 * ****************************************************************************
 * 
 * This file is part of <e-Adventure>, version 1.2.
 * 
 *     <e-Adventure> is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     <e-Adventure> is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 * 
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.engine.core.control.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import es.eucm.eadventure.common.auxiliar.ReleaseFolders;

/**
 * This class is the one developed by Bruno, used in the Editor, modified to
 * allow the use in the Engine
 */

public class ConfigData {

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

    public static String getLanguangeFile( ) {

        return instance.languageFile;
    }
    
    /**
     * Get the current language of the program
     * @return name of language in standard internationalization
     */
    public static String getLanguage( ) {
       
        // take the xml file name without extension
        return instance.languageFile.substring( 0, instance.languageFile.length( ) - 4 );
    }

    public static String getAboutFile( ) {

        return instance.aboutFile;
    }

    public static void setLanguangeFile( String language, String about ) {

        instance.languageFile = language;
        instance.aboutFile = about;
    }

    public static void setAboutFile( String s ) {

        instance.aboutFile = s;
    }

    public static void loadFromXML( String configFile ) {

        instance = new ConfigData( configFile );
    }

    public static void loadFromData( String languageFile, String aboutFile ) {

        instance = new ConfigData( languageFile, aboutFile );
    }

    public static void storeToXML( ) {

        if( instance.configFile != null ) {
            // Load the current configuration
            Properties configuration = new Properties( );
            if( instance.languageFile != null )
                configuration.setProperty( "LanguageFile", instance.languageFile );
            if( instance.aboutFile != null )
                configuration.setProperty( "AboutFile", instance.aboutFile );
            if( instance.exportsPath != null )
                configuration.setProperty( "ExportsDirectory", instance.exportsPath );
            if( instance.reportsPath != null )
                configuration.setProperty( "ReportsDirectory", instance.reportsPath );
            // Store the configuration into a file
            try {
                configuration.storeToXML( new FileOutputStream( instance.configFile ), "<e-Adventure> engine configuration" );
            }
            catch( FileNotFoundException e ) {
            }
            catch( IOException e ) {
            }
        }
    }

    private ConfigData( String fileName ) {

        this.configFile = fileName;
        Properties configuration = new Properties( );
        try {
            configuration.loadFromXML( new FileInputStream( configFile ) );
            languageFile = configuration.getProperty( "LanguageFile" );
            aboutFile = configuration.getProperty( "AboutFile" );
            exportsPath = configuration.getProperty( "ExportsDirectory" );
            if( exportsPath != null )
                ReleaseFolders.setExportsPath( exportsPath );
            reportsPath = configuration.getProperty( "ReportsDirectory" );
            if( reportsPath != null )
                ReleaseFolders.setReportsPath( reportsPath );
        }
        catch( InvalidPropertiesFormatException e ) {
            checkConsistency( );
        }
        catch( FileNotFoundException e ) {
            checkConsistency( );
        }
        catch( IOException e ) {
            checkConsistency( );
        }

    }

    private void checkConsistency( ) {
        if( languageFile == null ) {
            languageFile = ReleaseFolders.getLanguageFilePath( ReleaseFolders.LANGUAGE_ENGLISH );
        }
        if( aboutFile == null ) {
            aboutFile = ReleaseFolders.getAboutFilePath( ReleaseFolders.LANGUAGE_ENGLISH );
        }
        if( exportsPath == null ) {

        }
        if( reportsPath == null ) {

        }

    }

    private ConfigData( String languageFile, String aboutFile ) {

        configFile = null;
        this.languageFile = languageFile;
        this.aboutFile = aboutFile;
    }

}
