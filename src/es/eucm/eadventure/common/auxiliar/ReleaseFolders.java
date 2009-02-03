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
	 * recognized, returns {@value #LANGUAGE_UNKNOWN}
	 * @param path
	 * @return
	 */
	public static int getLanguageFromPath ( String path ){
		if (path.toLowerCase().contains(ENGLISH_FILE.toLowerCase())){
			return LANGUAGE_ENGLISH;
		}
		else if (path.toLowerCase().contains(SPANISH_FILE.toLowerCase())){
			return LANGUAGE_SPANISH;
		} else 
			return LANGUAGE_UNKNOWN;

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
