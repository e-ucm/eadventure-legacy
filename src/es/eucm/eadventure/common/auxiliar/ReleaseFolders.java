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
package es.eucm.eadventure.common.auxiliar;

/**
 * The only purpose of this class is to keep the path of the folders and files which will
 * be in the release in a common place for both engine and editor
 * @author Javier
 *
 */
public class ReleaseFolders {
	
	private static String PROJECTS_FOLDER = "../Projects";
	
	private static String EXPORTS_FOLDER = "../Exports";
	
	private static String REPORTS_FOLDER = "../Reports";
	
	private static final String WEB_FOLDER = "web";
	
	private static final String WEB_TEMP_FOLDER="web/temp";
	
	private static final String CONFIG_FILE_PATH_EDITOR = "config_editor.xml";
	
	private static final String CONFIG_FILE_PATH_ENGINE = "config_engine.xml";
	
	public static final String LANGUAGE_DIR_EDITOR = "laneditor";
	
	public static final String LANGUAGE_DIR_ENGINE = "lanengine";
	
	private static final String ENGLISH_FILE_ENGINE4EDITOR = "en_EN_engine.xml";
	
	private static final String SPANISH_FILE_ENGINE4EDITOR = "es_ES_engine.xml";
	
	private static final String ENGLISH_FILE = "en_EN.xml";
	
	private static final String SPANISH_FILE = "es_ES.xml";
	
	private static final String ENGLISH_ABOUT_FILE = "aboutEN.html";
	
	private static final String SPANISH_ABOUT_FILE = "aboutES.html";
	
	private static final String ENGLISH_LOADING_IMAGE = "img/Editor2D-Loading-Eng.png";
	
	private static final String SPANISH_LOADING_IMAGE = "img/Editor2D-Loading-Esp.png";
	
	/**
	 * Language constant for Unknown language
	 */
	public static final int LANGUAGE_UNKNOWN = -1;
	
	/**
	 * Language constant for Spanish language
	 */
	public static final int LANGUAGE_SPANISH = 0;
	
	/**
	 * Language constant for English language
	 */
	public static final int LANGUAGE_ENGLISH = 1;
	
	/**
	 * Language constant for Default language
	 */
	public static final int LANGUAGE_DEFAULT = LANGUAGE_ENGLISH;

	public static final File projectsFolder(){
		return new File(PROJECTS_FOLDER);
	}
	
	public static final File exportsFolder(){
		return new File(EXPORTS_FOLDER);
	}
	
	public static final File reportsFolder(){
		return new File(REPORTS_FOLDER);
	}

	public static final File webFolder(){
		return new File(WEB_FOLDER);
	}

	public static final File webTempFolder(){
		return new File(WEB_TEMP_FOLDER);
	}
	
	public static final File[] forbiddenFolders (){
		return new File[]{webFolder(), webTempFolder()};
	}
	
	public static final String configFileEditorRelativePath(){
		return CONFIG_FILE_PATH_EDITOR;
	}
	
	public static final String configFileEngineRelativePath(){
		return CONFIG_FILE_PATH_ENGINE;
	}

	
	/**
	 * Returns the relative path of a language file for both editor and engine
	 * NOTE: To be used only from editor
	 */
	public static String getLanguageFilePath4Editor ( boolean editor, int language ){
		String path = LANGUAGE_DIR_EDITOR+"/";
		if (editor)
			path+=((language == LANGUAGE_SPANISH)?SPANISH_FILE:ENGLISH_FILE);
		else
			path+=((language == LANGUAGE_SPANISH)?SPANISH_FILE_ENGINE4EDITOR:ENGLISH_FILE_ENGINE4EDITOR);
		return path;
	}
	
	/**
	 * Returns the relative path of a language file
	 * NOTE: To be used only from engine
	 */
	public static String getLanguageFilePath4Engine ( int language ){
		String path = LANGUAGE_DIR_ENGINE+"/";
		path+=((language == LANGUAGE_SPANISH)?SPANISH_FILE:ENGLISH_FILE);
		return path;
	}
	
	/**
	 * Returns the language ({@link #LANGUAGE_ENGLISH} or {@value #LANGUAGE_SPANISH}) associated to the relative path passed as argument. If no language is
	 * recognized, or if path is null, the method returns {@value #LANGUAGE_DEFAULT}
	 * @param path
	 * @return
	 */
	public static int getLanguageFromPath ( String path ){
		if (path!=null && path.toLowerCase().contains(ENGLISH_FILE.toLowerCase())){
			return LANGUAGE_ENGLISH;
		}
		else if (path!=null && path.toLowerCase().contains(SPANISH_FILE.toLowerCase())){
			return LANGUAGE_SPANISH;
		} else 
			return LANGUAGE_DEFAULT;

	}
	
	public static final String getAboutFilePath ( int language ){
		if (language == LANGUAGE_ENGLISH){
			return ENGLISH_ABOUT_FILE;
		}
		
		else if (language == LANGUAGE_SPANISH){
			return SPANISH_ABOUT_FILE;
		}
		
		else{
			return getAboutFilePath ( LANGUAGE_DEFAULT );
		}
	}
	
	public static final String getLoadingImagePath ( int language ){
		if (language == LANGUAGE_ENGLISH){
			return ENGLISH_LOADING_IMAGE;
		}
		
		else if (language == LANGUAGE_SPANISH){
			return SPANISH_LOADING_IMAGE;
		}
		
		else{
			return getLoadingImagePath ( LANGUAGE_DEFAULT );
		}
	}
	
	public static final String getLanguageFilePath ( int language ){
		if (language == LANGUAGE_ENGLISH){
			return ENGLISH_FILE;
		}
		
		else if (language == LANGUAGE_SPANISH){
			return SPANISH_FILE;
		}
		
		else{
			return getLanguageFilePath ( LANGUAGE_DEFAULT );
		}
	}



	/**
	 * @param projects_folder the pROJECTS_FOLDER to set
	 */
	public static void setProjectsPath(String projects_folder) {
		PROJECTS_FOLDER = projects_folder;
	}

	/**
	 * @param exports_folder the eXPORTS_FOLDER to set
	 */
	public static void setExportsPath(String exports_folder) {
		EXPORTS_FOLDER = exports_folder;
	}

	/**
	 * @param reports_folder the rEPORTS_FOLDER to set
	 */
	public static void setReportsPath(String reports_folder) {
		REPORTS_FOLDER = reports_folder;
	}
}
