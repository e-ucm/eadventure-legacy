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
package es.eucm.eadventure.common.auxiliar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Properties;

/**
 * The only purpose of this class is to keep the path of the folders and files
 * which will be in the release in a common place for both engine and editor
 * 
 * @author Javier
 * 
 */
public class ReleaseFolders {

    private static String PROJECTS_FOLDER = "../Projects";

    private static String EXPORTS_FOLDER = "../Exports";

    private static String REPORTS_FOLDER = "../Reports";

    private static final String WEB_FOLDER = "web";

    private static final String WEB_TEMP_FOLDER = "web/temp";

    private static final String CONFIG_FILE_PATH_EDITOR = "config_editor.xml";

    private static final String CONFIG_FILE_PATH_ENGINE = "config_engine.xml";

    public static final String LANGUAGE_DIR_EDITOR = "i18n/editor";

    public static final String LANGUAGE_DIR_ENGINE = "i18n/engine";
    
    public static final String IMAGE_LOADING_DIR = "img/loading";
    
    private static HashMap<String, String> languageNames = new HashMap<String, String>();

    /**
     * Language constant for Unknown language
     */
    public static final String LANGUAGE_UNKNOWN = "es_ES";

    /**
     * Language constant for Spanish language
     */
    public static final String LANGUAGE_SPANISH = "es_ES";

    /**
     * Language constant for English language
     */
    public static final String LANGUAGE_ENGLISH = "en_EN";

    /**
     * Language constant for Default language
     */
    public static final String LANGUAGE_DEFAULT = LANGUAGE_ENGLISH;

    public static final es.eucm.eadventure.common.auxiliar.File projectsFolder( ) {

        return new es.eucm.eadventure.common.auxiliar.File( PROJECTS_FOLDER );
    }

    public static final es.eucm.eadventure.common.auxiliar.File exportsFolder( ) {

        return new es.eucm.eadventure.common.auxiliar.File( EXPORTS_FOLDER );
    }

    public static final es.eucm.eadventure.common.auxiliar.File reportsFolder( ) {

        return new es.eucm.eadventure.common.auxiliar.File( REPORTS_FOLDER );
    }

    public static final File webFolder( ) {

        return new File( WEB_FOLDER );
    }

    public static final es.eucm.eadventure.common.auxiliar.File webTempFolder( ) {

        return new es.eucm.eadventure.common.auxiliar.File( WEB_TEMP_FOLDER );
    }

    public static final File[] forbiddenFolders( ) {

        return new File[] { webFolder( ), webTempFolder( ) };
    }

    public static final String configFileEditorRelativePath( ) {

        return CONFIG_FILE_PATH_EDITOR;
    }

    public static final String configFileEngineRelativePath( ) {

        return CONFIG_FILE_PATH_ENGINE;
    }

    /**
     * Returns the relative path of a language file for both editor and engine
     * NOTE: To be used only from editor
     */
    public static String getLanguageFilePath4Editor( boolean editor, String language ) {
        String path = LANGUAGE_DIR_EDITOR + "/";
        if( editor )
            path += language + ".xml";
        else {
            path = LANGUAGE_DIR_ENGINE + File.separator;
            path += language + ".xml";
        }
        return path;
    }

    /**
     * Returns the relative path of a language file NOTE: To be used only from
     * engine
     */
    public static String getLanguageFilePath4Engine( String language ) {

        String path = LANGUAGE_DIR_ENGINE + "/";
        path += language + ".xml";
        return path;
    }

    /**
     * Returns the language ({@link #LANGUAGE_ENGLISH} or
     * {@value #LANGUAGE_SPANISH}) associated to the relative path passed as
     * argument. If no language is recognized, or if path is null, the method
     * returns {@value #LANGUAGE_DEFAULT}
     * 
     * @param path
     * @return
     */
    public static String getLanguageFromPath( String path ) {
        if( path != null && path.endsWith( ".xml" ) ) {
            return path.substring( path.length( ) - 9, path.length() - 4 );
        }
        else
            return LANGUAGE_DEFAULT;

    }

    public static final String getAboutFilePath( String string ) {
        
        return "about-" + string + ".html";
    }
    
    public static final String getDefaultAboutFilePath( ) {
        
        return "about-" + LANGUAGE_DEFAULT + ".html";
    }

    public static final String getLanguageFilePath( String language ) {
        
        return language + ".xml";
    }

    /**
     * @param projects_folder
     *            the pROJECTS_FOLDER to set
     */
    public static void setProjectsPath( String projects_folder ) {

        PROJECTS_FOLDER = projects_folder;
    }

    /**
     * @param exports_folder
     *            the eXPORTS_FOLDER to set
     */
    public static void setExportsPath( String exports_folder ) {

        EXPORTS_FOLDER = exports_folder;
    }

    /**
     * @param reports_folder
     *            the rEPORTS_FOLDER to set
     */
    public static void setReportsPath( String reports_folder ) {

        REPORTS_FOLDER = reports_folder;
    }

    public static List<String> getLanguages(String where ) {
        File directory = new File("i18n" + File.separator + where); 
        List<String> languages = new ArrayList<String>();
        for (File file : directory.listFiles()) {
            if (file.getName().endsWith("xml")) {
                String identifier = file.getName().substring(0, file.getName().length() - 4);
                languages.add( identifier );
                Properties prop = new Properties();
                try {
                    prop.loadFromXML(new FileInputStream(file));
                    languageNames.put( identifier,(String )prop.get( "Language.Name" ));
                } catch (InvalidPropertiesFormatException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return languages;
    }
    
    public static String getLanguageName(String language) {
        return languageNames.get( language );
    }
}
