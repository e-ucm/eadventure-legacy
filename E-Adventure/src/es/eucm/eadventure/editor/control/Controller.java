package es.eucm.eadventure.editor.control;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import es.eucm.eadventure.common.auxiliar.File;
import es.eucm.eadventure.common.auxiliar.filefilters.EADFileFilter;
import es.eucm.eadventure.common.auxiliar.filefilters.FolderFileFilter;
import es.eucm.eadventure.common.auxiliar.filefilters.XMLFileFilter;
import es.eucm.eadventure.common.data.adventure.AdventureData;
import es.eucm.eadventure.common.data.adventure.DescriptorData;
import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.common.data.chapter.elements.Player;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.common.loader.Loader;
import es.eucm.eadventure.common.loader.incidences.Incidence;
import es.eucm.eadventure.editor.control.config.ConfigData;
import es.eucm.eadventure.editor.control.config.ProjectConfigData;
import es.eucm.eadventure.editor.control.controllers.AdventureDataControl;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.FlagsController;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationProfilesDataControl;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentProfilesDataControl;
import es.eucm.eadventure.editor.control.controllers.character.NPCDataControl;
import es.eucm.eadventure.editor.control.controllers.general.ChapterDataControl;
import es.eucm.eadventure.editor.control.controllers.item.ItemDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.SceneDataControl;
import es.eucm.eadventure.editor.control.writer.Writer;
import es.eucm.eadventure.editor.data.support.FlagSummary;
import es.eucm.eadventure.editor.data.support.IdentifierSummary;
import es.eucm.eadventure.editor.gui.LoadingScreen;
import es.eucm.eadventure.editor.gui.MainWindow;
import es.eucm.eadventure.editor.gui.ProjectFolderChooser;
import es.eucm.eadventure.editor.gui.displaydialogs.InvalidReportDialog;
import es.eucm.eadventure.editor.gui.editdialogs.AdventureDataDialog;
import es.eucm.eadventure.editor.gui.editdialogs.ExportToLOMDialog;
import es.eucm.eadventure.editor.gui.editdialogs.FlagsDialog;
import es.eucm.eadventure.editor.gui.editdialogs.GUIStylesDialog;
import es.eucm.eadventure.editor.gui.editdialogs.assetsdialogs.AnimationAssetsDialog;
import es.eucm.eadventure.editor.gui.editdialogs.assetsdialogs.AudioAssetsDialog;
import es.eucm.eadventure.editor.gui.editdialogs.assetsdialogs.ImageAssetsDialog;
import es.eucm.eadventure.editor.gui.editdialogs.assetsdialogs.VideoAssetsDialog;
import es.eucm.eadventure.editor.gui.editdialogs.assetsdialogs.XMLAssetsDialog;
import es.eucm.eadventure.editor.gui.editdialogs.customizeguidialog.CustomizeGUIDialog;
import es.eucm.eadventure.editor.gui.lomdialog.LOMDialog;
import es.eucm.eadventure.editor.gui.startdialog.StartDialog;
import es.eucm.eadventure.engine.EAdventureDebug;

/**
 * This class is the main controller of the application. It holds the main operations and data to control the editor.
 * 
 * @author Bruno Torijano Bueno
 */
/*
 * @updated by Javier Torrente. New functionalities added - Support for .ead files. Therefore <e-Adventure> files are no
 * longer .zip but .ead
 */

public class Controller {

	/**
	 * Id for the complete chapter data element.
	 */
	public static final int CHAPTER = 0;

	/**
	 * Id for the scenes list element.
	 */
	public static final int SCENES_LIST = 1;

	/**
	 * Id for the scene element.
	 */
	public static final int SCENE = 2;

	/**
	 * Id for the exits list element.
	 */
	public static final int EXITS_LIST = 3;

	/**
	 * Id for the exit element.
	 */
	public static final int EXIT = 4;

	/**
	 * Id for the item references list element.
	 */
	public static final int ITEM_REFERENCES_LIST = 5;

	/**
	 * Id for the item reference element.
	 */
	public static final int ITEM_REFERENCE = 6;

	/**
	 * Id for the NPC references list element.
	 */
	public static final int NPC_REFERENCES_LIST = 7;

	/**
	 * Id for the NPC reference element.
	 */
	public static final int NPC_REFERENCE = 8;

	/**
	 * Id for the cutscenes list element.
	 */
	public static final int CUTSCENES_LIST = 9;

	/**
	 * Id for the slidescene element.
	 */
	public static final int CUTSCENE_SLIDES = 10;

	public static final int CUTSCENE_VIDEO = 37;

	/**
	 * Id for the books list element.
	 */
	public static final int BOOKS_LIST = 11;

	/**
	 * Id for the book element.
	 */
	public static final int BOOK = 12;

	/**
	 * Id for the book paragraphs list element.
	 */
	public static final int BOOK_PARAGRAPHS_LIST = 13;

	/**
	 * Id for the title paragraph element.
	 */
	public static final int BOOK_TITLE_PARAGRAPH = 14;

	/**
	 * Id for the text paragraph element.
	 */
	public static final int BOOK_TEXT_PARAGRAPH = 15;

	/**
	 * Id for the bullet paragraph element.
	 */
	public static final int BOOK_BULLET_PARAGRAPH = 16;

	/**
	 * Id for the image paragraph element.
	 */
	public static final int BOOK_IMAGE_PARAGRAPH = 17;

	/**
	 * Id for the items list element.
	 */
	public static final int ITEMS_LIST = 18;

	/**
	 * Id for the item element.
	 */
	public static final int ITEM = 19;

	/**
	 * Id for the actions list element.
	 */
	public static final int ACTIONS_LIST = 20;

	/**
	 * Id for the "Examine" action element.
	 */
	public static final int ACTION_EXAMINE = 21;

	/**
	 * Id for the "Grab" action element.
	 */
	public static final int ACTION_GRAB = 22;

	/**
	 * Id for the "Use" action element.
	 */
	public static final int ACTION_USE = 23;

	/**
	 * Id for the "Use with" action element.
	 */
	public static final int ACTION_USE_WITH = 24;

	/**
	 * Id for the "Give to" action element.
	 */
	public static final int ACTION_GIVE_TO = 25;

	/**
	 * Id for the player element.
	 */
	public static final int PLAYER = 26;

	/**
	 * Id for the NPCs list element.
	 */
	public static final int NPCS_LIST = 27;

	/**
	 * Id for the NPC element.
	 */
	public static final int NPC = 28;

	/**
	 * Id for the conversation references list element.
	 */
	public static final int CONVERSATION_REFERENCES_LIST = 29;

	/**
	 * Id for the conversation reference element.
	 */
	public static final int CONVERSATION_REFERENCE = 30;

	/**
	 * Id for the conversations list element.
	 */
	public static final int CONVERSATIONS_LIST = 31;

	/**
	 * Id for the tree conversation element.
	 */
	public static final int CONVERSATION_TREE = 32;

	/**
	 * Id for the graph conversation element.
	 */
	public static final int CONVERSATION_GRAPH = 33;

	/**
	 * Id for the resources element.
	 */
	public static final int RESOURCES = 34;

	/**
	 * Id for the next scene element.
	 */
	public static final int NEXT_SCENE = 35;

	/**
	 * If for the end scene element.
	 */
	public static final int END_SCENE = 36;
	
	/**
	 * Id for Assessment Rule
	 */
	public static final int ASSESSMENT_RULE = 38;
	
	/**
	 * Id for Adaptation Rule
	 */
	public static final int ADAPTATION_RULE = 39;
	
	/**
	 * Id for Assessment Rules
	 */
	public static final int ASSESSMENT_PROFILE = 40;
	
	/**
	 * Id for Adaptation Rules
	 */
	public static final int ADAPTATION_PROFILE = 41;

	/**
	 * Id for the styled book element.
	 */
	public static final int STYLED_BOOK = 42;
	
	/**
	 * Id for the page of a STYLED_BOK.
	 */
	public static final int BOOK_PAGE = 43;
	
	/**
	 * Id for timers.
	 */
	public static final int TIMER = 44;
	
	/**
	 * Id for the list of timers.
	 */
	public static final int TIMERS_LIST = 45;
	
	/**
	 * Id for the advanced features node.
	 */
	public static final int ADVANCED_FEATURES = 46;
	
	/**
	 * Id for the assessment profiles node.
	 */
	public static final int ASSESSSMENT_PROFILES = 47;
	
	/**
	 * Id for the adaptation profiles node.
	 */
	public static final int ADAPTATION_PROFILES = 48;
	
	/**
	 * Id for timed assessment rules
	 */
	public static final int TIMED_ASSESSMENT_RULE = 49;
	
	/**
	 * Id for active areas list.
	 */
	public static final int ACTIVE_AREAS_LIST = 50;
	
	/**
	 * Id for active area
	 */
	public static final int ACTIVE_AREA = 51;
	
	/**
	 * Id for barriers list.
	 */
	public static final int BARRIERS_LIST = 52;
	
	/**
	 * Id for barrier
	 */
	public static final int BARRIER = 53;


	//TYPES OF EAD FILES
	public static final int FILE_ADVENTURE_1STPERSON_PLAYER = 0;

	public static final int FILE_ADVENTURE_3RDPERSON_PLAYER = 1;
	
	public static final int FILE_ASSESSMENT = 2;
	
	public static final int FILE_ADAPTATION = 3;

	private static final String TEMP_NAME = "_$temp";
	
	private static final String PROJECTS_FOLDER = "../Projects";
	
	private static final String EXPORTS_FOLDER = "../Exports";
	
	private static final String WEB_FOLDER = "web";
	
	private static final String WEB_TEMP_FOLDER="web/temp";
	
	private static final String CONFIG_FILE_PATH = "config_editor.xml";
	
	private static final String LANGUAGE_DIR = "laneditor";
	
	public static final int LANGUAGE_UNKNOWN = -1;
	public static final int LANGUAGE_SPANISH = 0;
	public static final int LANGUAGE_ENGLISH = 1;
	
	public static final File projectsFolder(){
		return new File(PROJECTS_FOLDER);
	}
	
	public static final File exportsFolder(){
		return new File(EXPORTS_FOLDER);
	}

	public static final File webFolder(){
		return new File(WEB_FOLDER);
	}

	public static final File webTempFolder(){
		return new File(WEB_TEMP_FOLDER);
	}
	
	/**
	 * Singleton instance.
	 */
	private static Controller controllerInstance = null;

	/**
	 * The main window of the application.
	 */
	private MainWindow mainWindow;

	/**
	 * The complete path to the current open ZIP file.
	 */
	private String currentZipFile;

	/**
	 * The path to the folder that holds the open file.
	 */
	private String currentZipPath;

	/**
	 * The name of the file that is being currently edited. Used only to display info.
	 */
	private String currentZipName;

	/**
	 * The data of the adventure being edited.
	 */
	private AdventureDataControl adventureData;

	/**
	 * Stores the index of the selected chapter in the editor.
	 */
	private int selectedChapter;

	/**
	 * Controller for the chapters of the adventure.
	 */
	private List<ChapterDataControl> chapterDataControlList;

	/**
	 * Summary of identifiers.
	 */
	private IdentifierSummary identifierSummary;

	/**
	 * Summary of flags.
	 */
	private FlagSummary flagSummary;

	/**
	 * Stores if the data has been modified since the last save.
	 */
	private boolean dataModified;

	/**
	 * Stores the file that contains the GUI strings.
	 */
	private String languageFile;
	
	private LoadingScreen loadingScreen;
	
	private String lastDialogDirectory;

	/*private boolean isTempFile = false;

	public boolean isTempFile( ) {
		return isTempFile;
	}*/

	/**
	 * Void and private constructor.
	 */
	private Controller( ) {}
	
	private String getCurrentExportSaveFolder(){
		return exportsFolder().getAbsolutePath( );
	}
	
	private String getCurrentLoadFolder(){
		return projectsFolder( ).getAbsolutePath( );
	}
	
	public void setLastDirectory (String directory){
		this.lastDialogDirectory = directory;
	}
	
	public String getLastDirectory (){
		if (lastDialogDirectory!=null){
			return lastDialogDirectory;
		} else 
			return projectsFolder().getAbsolutePath( );
	}

	/**
	 * Returns the instance of the controller.
	 * 
	 * @return The instance of the controller
	 */
	public static Controller getInstance( ) {
		if( controllerInstance == null )
			controllerInstance = new Controller( );

		return controllerInstance;
	}

	/**
	 * Initializing function.
	 */
	public void init( ) {
		// Create necessary folders if no created befor
		File projectsFolder = projectsFolder( );
		if (!projectsFolder.exists( )){
			projectsFolder.mkdirs( );
		}
		File tempFolder = webTempFolder();
		if (!tempFolder.exists( )){
			projectsFolder.mkdirs( );
		}
		File exportsFolder = exportsFolder();
		if (!exportsFolder.exists( )){
			exportsFolder.mkdirs( );
		}
		
		// Set default values for the item and NPC references

		// Load the configuration
		ConfigData.loadFromXML( CONFIG_FILE_PATH );
		languageFile = ConfigData.getLanguangeFile( );
		loadingScreen = new LoadingScreen("PRUEBA",ConfigData.getLoadingImage( ),null);

		// Init the strings of the application
		TextConstants.loadStrings( LANGUAGE_DIR+"/"+languageFile );

		// Create a list for the chapters
		chapterDataControlList = new ArrayList<ChapterDataControl>( );

		// Inits the controller with empty data
		currentZipFile = null;
		currentZipPath = null;
		currentZipName = null;
		adventureData = new AdventureDataControl( TextConstants.getText( "DefaultValue.AdventureTitle" ), TextConstants.getText( "DefaultValue.ChapterTitle" ), TextConstants.getText( "DefaultValue.SceneId" ) );
		selectedChapter = 0;
		chapterDataControlList.add( new ChapterDataControl( getSelectedChapterData( ) ) );
		identifierSummary = new IdentifierSummary( getSelectedChapterData( ) );
		flagSummary = new FlagSummary( );
		dataModified = false;

		mainWindow = new MainWindow( );
		mainWindow.setVisible( false );

		// Prompt the user to create a new adventure or to load one
		//while( currentZipFile == null ) {
			// Load the options and show the dialog
			String[] options = { TextConstants.getText( "StartDialog.Option0" ), TextConstants.getText( "StartDialog.Option1" ) };

			StartDialog start = new StartDialog( );
			
			//mainWindow.setEnabled( false );
			mainWindow.setVisible( false );

			if( ConfigData.showStartDialog( ) ) {
				int op = start.showOpenDialog( null );
				//start.end();
				if( op == StartDialog.NEW_FILE_OPTION ) {
					newFile( start.getFileType( ) );
				} else if( op == StartDialog.OPEN_FILE_OPTION ) {
					java.io.File selectedFile = start.getSelectedFile( );
					if (selectedFile.isDirectory( ) && selectedFile.exists( ))
						loadFile( start.getSelectedFile( ).getAbsolutePath( ), true );
					else {
						this.importGame( selectedFile.getAbsolutePath( ) );
					}
				} else if( op == StartDialog.RECENT_FILE_OPTION ) {
					loadFile( start.getRecentFile( ).getAbsolutePath( ), true );
				} else if( op == StartDialog.CANCEL_OPTION ) {
					exit( );
				}
				selectedChapter = 0;
			} 

			if ( currentZipFile == null ){
			//newFile( FILE_ADVENTURE_3RDPERSON_PLAYER );
				selectedChapter = -1;
				mainWindow.reloadData( );
			}
			/*
			 * int optionSelected = mainWindow.showOptionDialog( TextConstants.getText( "StartDialog.Title" ),
			 * TextConstants.getText( "StartDialog.Message" ), options );
			 *  // If the user wants to create a new file, show the dialog if( optionSelected == 0 ) newFile( );
			 *  // If the user wants to load a existing adventure, show the load dialog else if( optionSelected == 1 )
			 * loadFile( );
			 *  // If the dialog was closed, exit the aplication else if( optionSelected == JOptionPane.CLOSED_OPTION )
			 * exit( );
			 */
		//}

		// Show the window
		/*mainWindow.setEnabled( true );
		mainWindow.reloadData( );
		try {
			Thread.sleep( 1 );
		} catch( InterruptedException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		// Create the main window and hide it
		
		//mainWindow.setVisible( false );

		mainWindow.setResizable( true );
		mainWindow.setEnabled( true );
		mainWindow.setVisible( true );

	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////

	// General data functions of the aplication

	/**
	 * Returns the complete path to the currently open Project.
	 * 
	 * @return The complete path to the ZIP file, null if none is open
	 */
	public String getProjectFolder( ) {
		return currentZipFile;
	}
	
	/**
	 * Returns the File object representing the currently open Project.
	 * 
	 * @return The complete path to the ZIP file, null if none is open
	 */
	public File getProjectFolderFile( ) {
		return new File(currentZipFile);
	}

	/**
	 * Returns the name of the file currently open.
	 * 
	 * @return The name of the current file
	 */
	public String getFileName( ) {
		String filename;

		// Show "New" if no current file is currently open
		if( currentZipName != null )
			filename = currentZipName;
		else
			filename = "http://e-adventure.e-ucm.es";

		return filename;
	}

	/**
	 * Returns the parent path of the file being currently edited.
	 * 
	 * @return Parent path of the current file
	 */
	public String getFilePath( ) {
		return currentZipPath;
	}

	/**
	 * Returns an array with the chapter titles.
	 * 
	 * @return Array with the chapter titles
	 */
	public String[] getChapterTitles( ) {
		List<String> chapterNames = new ArrayList<String>( );

		// Add the chapter titles
		for( Chapter chapter : adventureData.getChapters( ) )
			chapterNames.add( chapter.getTitle( ) );

		return chapterNames.toArray( new String[] {} );
	}

	/**
	 * Returns the index of the chapter currently selected.
	 * 
	 * @return Index of the selected chapter
	 */
	public int getSelectedChapter( ) {
		return selectedChapter;
	}

	/**
	 * Returns the data of the selected chapter.
	 * 
	 * @return Selected chapter data
	 */
	private Chapter getSelectedChapterData( ) {
		return adventureData.getChapters( ).get( selectedChapter );
	}

	/**
	 * Returns the selected chapter data controller.
	 * 
	 * @return The selected chapter data controller
	 */
	public ChapterDataControl getSelectedChapterDataControl( ) {
		return chapterDataControlList.get( selectedChapter );
	}

	/**
	 * Returns the identifier summary.
	 * 
	 * @return The identifier summary
	 */
	public IdentifierSummary getIdentifierSummary( ) {
		return identifierSummary;
	}

	/**
	 * Returns the flag summary.
	 * 
	 * @return The flag summary
	 */
	public FlagSummary getFlagSummary( ) {
		return flagSummary;
	}

	/**
	 * Returns whether the data has been modified since the last save.
	 * 
	 * @return True if the data has been modified, false otherwise
	 */
	public boolean isDataModified( ) {
		return dataModified;
	}

	/**
	 * Called when the data has been modified, it sets the value to true.
	 */
	public void dataModified( ) {
		// If the data were not modified, change the value and set the new title of the window
		if( !dataModified ) {
			dataModified = true;
			mainWindow.updateTitle( );
		}
	}

	/**
	 * Returns whether the item references must be displayed by default.
	 * 
	 * @return True if the item references must be displayed, false otherwise
	 */
	public boolean getShowItemReferences( ) {
		return ConfigData.showItemReferences( );
	}

	/**
	 * Sets whether the item references must be displayed by default.
	 * 
	 * @param showItemReferences
	 *            True if the item references must be displayed, false otherwise
	 */
	public void setShowItemReferences( boolean showItemReferences ) {
		ConfigData.setShowItemReferences( showItemReferences );
	}

	/**
	 * Returns whether the NPC references must be displayed by default.
	 * 
	 * @return True if the NPC references must be displayed, false otherwise
	 */
	public boolean getShowNPCReferences( ) {
		return ConfigData.showNPCReferences( );
	}

	/**
	 * Sets whether the NPC references must be displayed by default.
	 * 
	 * @param showNPCReferences
	 *            True if the NPC references must be displayed, false otherwise
	 */
	public void setShowNPCReferences( boolean showNPCReferences ) {
		ConfigData.setShowNPCReferences( showNPCReferences );
	}

	public void setShowStartDialog( boolean showStartDialog ) {
		ConfigData.setShowStartDialog( showStartDialog );
	}

	public boolean getShowStartDialog( ) {
		return ConfigData.showStartDialog( );
	}

	public boolean isPlayTransparent( ) {
		return adventureData.getPlayerMode( ) == DescriptorData.MODE_PLAYER_1STPERSON;
	}

	public void swapPlayerMode( boolean showConfirmation ) {
		boolean swap = true;
		if( showConfirmation )
			swap = showStrictConfirmDialog( TextConstants.getText( "SwapPlayerMode.Title" ), TextConstants.getText( "SwapPlayerMode.Message" ) );

		if( swap ) {
			if( adventureData.getPlayerMode( ) == DescriptorData.MODE_PLAYER_1STPERSON ) {
				adventureData.setPlayerMode( DescriptorData.MODE_PLAYER_3RDPERSON );
			} else if( adventureData.getPlayerMode( ) == DescriptorData.MODE_PLAYER_3RDPERSON ) {
				adventureData.setPlayerMode( DescriptorData.MODE_PLAYER_1STPERSON );
			}
			dataModified( );
		}

	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////

	// Functions that perform usual aplication actions

	/**
	 * This method creates a new file with it's respective data.
	 * 
	 * @return True if the new data was created succesfully, false otherwise
	 */
	public boolean newFile( int fileType ) {
		boolean fileCreated = false;
		boolean createNewFile = true;

		// If the data was not saved, ask for an action (save, discard changes...)
		if( dataModified ) {
			int option = mainWindow.showConfirmDialog( TextConstants.getText( "Operation.NewFileTitle" ), TextConstants.getText( "Operation.NewFileMessage" ) );

			String oldZipFile = currentZipFile;

			// If the data must be saved, create the new file only if the save was successful
			if( option == JOptionPane.YES_OPTION )
				createNewFile = saveFile( false );

			// If the data must not be saved, create the new data directly
			else if( option == JOptionPane.NO_OPTION )
				createNewFile = true;

			// Cancel the action if selected
			else if( option == JOptionPane.CANCEL_OPTION )
				createNewFile = false;

		}

		// If the file must be created
		if( createNewFile ) {
			if ( fileType == Controller.FILE_ADVENTURE_1STPERSON_PLAYER || 
					fileType == Controller.FILE_ADVENTURE_3RDPERSON_PLAYER)
				fileCreated = newAdventureFile ( fileType );
			else if ( fileType == Controller.FILE_ASSESSMENT){
				//fileCreated = newAssessmentFile();
			}
			else if ( fileType == Controller.FILE_ADAPTATION){
				//fileCreated = newAdaptationFile();
			}

		}
		if( fileCreated )
			AssetsController.resetCache( );

		return fileCreated;

	}

	private boolean newAdventureFile( int fileType ){
		boolean fileCreated = false;
		
		// Decide the directory of the temp file and give a name for it

		// If there is a valid temp directory in the system, use it
		//String tempDir = System.getenv( "TEMP" );
		//String completeFilePath = "";

		//isTempFile = true;
		//if( tempDir != null ) {
		//	completeFilePath = tempDir + "/" + TEMP_NAME;
		//}

		// If the temp directory is not valid, use the home directory
		//else {

		//	completeFilePath = FileSystemView.getFileSystemView( ).getHomeDirectory( ) + "/" + TEMP_NAME;
		//}

		boolean create = false;
		java.io.File selectedDir = null;
		// Prompt main folder of the project
		ProjectFolderChooser folderSelector = new ProjectFolderChooser(false, false);
		// If some folder is selected, check all characters are correct  
		if ( folderSelector.showOpenDialog( mainWindow ) == JFileChooser.APPROVE_OPTION ){
			java.io.File selectedFolder = folderSelector.getSelectedFile( );
			selectedDir = selectedFolder;

			// Check the parent folder is not forbidden
			if ( isValidTargetProject( selectedFolder ) ){

				if (FolderFileFilter.checkCharacters( selectedFolder.getName( ) )){
					// Folder can be created/used
					// Does the folder exist?
					if (selectedFolder.exists( )){
						//Is the folder empty?
						if (selectedFolder.list( ).length > 0){
							// Delete content?
							if ( this.showStrictConfirmDialog( TextConstants.getText( "Operation.NewProject.FolderNotEmptyTitle" ), TextConstants.getText( "Operation.NewProject.FolderNotEmptyMessage" ) )){
								File directory = new File (selectedFolder.getAbsolutePath( ));
								if( !directory.deleteAll( ) ){
									this.showStrictConfirmDialog( TextConstants.getText( "Error.Title" ), TextConstants.getText( "Error.DeletingFolderContents" ));
								} 
							}
						}
						create = true;
					} else {
						// Create new folder?
						if ( this.showStrictConfirmDialog( TextConstants.getText( "Operation.NewProject.FolderNotCreatedTitle" ), TextConstants.getText( "Operation.NewProject.FolderNotCreatedMessage" ) )){
							File directory = new File (selectedFolder.getAbsolutePath( ));
							if( directory.mkdirs( ) ){
								create = true;	
							} else {
								this.showStrictConfirmDialog( TextConstants.getText( "Error.Title" ), TextConstants.getText( "Error.CreatingFolder" ));
							}
							
						} else {
							create = false;
						}
					}
				}else{
					// Display error message
					this.showErrorDialog( TextConstants.getText( "Error.Title" ), 
							TextConstants.getText( "Error.ProjectFolderName" ) );
				}
			} else{
				// Show error: The target dir cannot be contained 
				mainWindow.showErrorDialog( TextConstants.getText( "Operation.NewProject.ForbiddenParent.Title" ), 
						TextConstants.getText( "Operation.NewProject.ForbiddenParent.Message" ) );
				create = false;
			}
		}
		
		// Create the new project?
		//LoadingScreen loadingScreen = new LoadingScreen(TextConstants.getText( "Operation.CreateProject" ), getLoadingImage( ), mainWindow);
		
		if (create){
			//loadingScreen.setVisible( true );
			loadingScreen.setMessage( TextConstants.getText( "Operation.CreateProject" ) );
			loadingScreen.setVisible( true );
			
			// Set the new file, path and create the new adventure
			currentZipFile = selectedDir.getAbsolutePath( );
			currentZipPath = selectedDir.getParent( );
			currentZipName = selectedDir.getName( );
			int playerMode = -1;
			if( fileType == FILE_ADVENTURE_3RDPERSON_PLAYER )
				playerMode = DescriptorData.MODE_PLAYER_3RDPERSON;
			else if( fileType == FILE_ADVENTURE_1STPERSON_PLAYER )
				playerMode = DescriptorData.MODE_PLAYER_1STPERSON;
			adventureData = new AdventureDataControl( TextConstants.getText( "DefaultValue.AdventureTitle" ), TextConstants.getText( "DefaultValue.ChapterTitle" ), TextConstants.getText( "DefaultValue.SceneId" ), playerMode );

			// Select the first chapter
			selectedChapter = 0;

			// Clear the list of data controllers and refill it
			chapterDataControlList.clear( );
			chapterDataControlList.add( new ChapterDataControl( getSelectedChapterData( ) ) );
			identifierSummary.loadIdentifiers( getSelectedChapterData( ) );
			flagSummary.clear( );

			// Init project properties (empty)
			ProjectConfigData.init();
			
			AssetsController.createFolderStructure();
			
			// Check the consistency of the chapters
			boolean valid = true;
			for( ChapterDataControl chapterDataControl : chapterDataControlList )
				valid &= chapterDataControl.isValid( null, null );

			// Save the data
			if( Writer.writeData( currentZipFile, adventureData, valid ) ) {
				// Set modified to false and update the window title
				dataModified = false;
				try {
					Thread.sleep( 1 );
				} catch( InterruptedException e ) {
					e.printStackTrace( );
				}

				
				mainWindow.reloadData( );

				// The file was saved
				fileCreated = true;

			} else 
				fileCreated = false;
		}
		loadingScreen.setVisible( false );
		if (fileCreated){
			ConfigData.fileLoaded( currentZipFile );			
			// Feedback
			mainWindow.showInformationDialog( 
					TextConstants.getText( "Operation.FileLoadedTitle" ), 
					TextConstants.getText( "Operation.FileLoadedMessage" ) );
		} else {
			// Feedback
			mainWindow.showInformationDialog( 
					TextConstants.getText( "Operation.FileNotLoadedTitle" ), 
					TextConstants.getText( "Operation.FileNotLoadedMessage" ) );
		}

		
		return fileCreated;
 
	}
		/*// If the file exists, append a random number
		File file = new File( completeFilePath + ".ead" );
		Random random = new Random( );
		while( file.exists( ) ) {
			int rNumber = random.nextInt( 10000 ) + 1;
			file = new File( completeFilePath + "_" + rNumber + ".ead" );
			if( !file.exists( ) ) {
				completeFilePath += "_" + rNumber;
			}
		}

		// If some file was selected set the new file
		if( completeFilePath != null ) {

			// Add the ".ead" if it is not present in the name
			if( !completeFilePath.toLowerCase( ).endsWith( ".ead" ) )
				completeFilePath += ".ead";

			// Create a file to extract the name and path
			File newFile = new File( completeFilePath );

			// If the temp file exists it is deleted
			if( newFile.exists( ) ) {
				newFile.delete( );
			}
			// Set the new file, path and create the new adventure
			currentZipFile = newFile.getAbsolutePath( );
			currentZipPath = newFile.getParent( );
			currentZipName = newFile.getName( );
			int playerMode = -1;
			if( fileType == FILE_ADVENTURE_VISIBLE_PLAYER )
				playerMode = AdventureDataControl.PLAYER_VISIBLE;
			else if( fileType == FILE_ADVENTURE_TRANSPARENT_PLAYER )
				playerMode = AdventureDataControl.PLAYER_TRANSPARENT;
			adventureData = new AdventureDataControl( TextConstants.getText( "DefaultValue.AdventureTitle" ), TextConstants.getText( "DefaultValue.ChapterTitle" ), TextConstants.getText( "DefaultValue.SceneId" ), playerMode );

			// Select the first chapter
			selectedChapter = 0;

			// Clear the list of data controllers and refill it
			chapterDataControlList.clear( );
			chapterDataControlList.add( new ChapterDataControl( getSelectedChapterData( ) ) );
			identifierSummary.loadIdentifiers( getSelectedChapterData( ) );
			flagSummary.clear( );

			// Check the consistency of the chapters
			boolean valid = true;
			for( ChapterDataControl chapterDataControl : chapterDataControlList )
				valid &= chapterDataControl.isValid( null, null );

			// Save the data
			if( Writer.writeData( currentZipFile, adventureData, valid ) ) {
				// Set modified to false and update the window title
				dataModified = false;
				try {
					Thread.sleep( 1 );
				} catch( InterruptedException e ) {
					e.printStackTrace( );
				}

				
				mainWindow.reloadData( );

				// The file was saved
				fileCreated = true;

			}
		}
		
		return fileCreated;
	}*/
	
	public boolean fixIncidences(List<Incidence> incidences ){
		boolean abort = false;
		List<Chapter> chapters = this.adventureData.getChapters( );
		
		for (int i=0; i<incidences.size( ); i++){
			Incidence current = incidences.get( i );
			// Critical importance: abort operation, the game could not be loaded
			if (current.getImportance( ) == Incidence.IMPORTANCE_CRITICAL){
				abort = true;break;
			}
			// High importance: the game is partially unreadable, but it is possible to continue.
			else if (current.getImportance( ) == Incidence.IMPORTANCE_HIGH){
				// An error occurred relating to the load of a chapter which is unreadable.
				// When this happens the chapter returned in the adventure data structure is corrupted.
				// Options: 1) Delete chapter. 2) Select chapter from other file. 3) Abort
				if (current.getAffectedArea( ) == Incidence.CHAPTER_INCIDENCE && current.getType( ) == Incidence.XML_INCIDENCE){
					String dialogTitle = TextConstants.getText( "ErrorSolving.Chapter.Title" )+" - Error "+(i+1)+"/"+incidences.size( );
					String dialogMessage = TextConstants.getText( "ErrorSolving.Chapter.Message", new String[]{current.getMessage( ), current.getAffectedResource( )} );
					String[] options = {TextConstants.getText( "GeneralText.Delete" ), 
										TextConstants.getText( "GeneralText.Replace" ),
										TextConstants.getText( "GeneralText.Abort" )};
					
					int option = showOptionDialog( dialogTitle, dialogMessage, options );
					// Delete chapter
					if (option == 0){
						String chapterName = current.getAffectedResource( );
						for (int j=0; j<chapters.size( ); j++){
							if (chapters.get( j ).getName( ).equals( chapterName )){
								chapters.remove( j );
								this.chapterDataControlList.remove( j );
								// Update selected chapter if necessary
								if (selectedChapter == j){
									if (chapterDataControlList.size( )>0){
										if (j>0)
											selectedChapter--;
										else
											selectedChapter=0;
									} else {
										// When there are no more chapters, add a new, blank one
										Chapter newChapter = new Chapter(TextConstants.getText( "DefaultValue.ChapterTitle" ), TextConstants.getText( "DefaultValue.SceneId" ));
										chapters.add( newChapter );
										chapterDataControlList.add( new ChapterDataControl (newChapter) );
									}
									this.setSelectedChapter( selectedChapter );
								}
								
								dataModified( );
								break;
							}
						}
					}

					// Replace chapter
					else if (option == 1){
						boolean replaced = false;
						JFileChooser xmlChooser = new JFileChooser();
						xmlChooser.setDialogTitle( TextConstants.getText( "GeneralText.Select" ) );
						xmlChooser.setFileFilter( new XMLFileFilter() );
						xmlChooser.setMultiSelectionEnabled( false );
						// A file is selected
						if (xmlChooser.showOpenDialog( mainWindow ) == JFileChooser.APPROVE_OPTION){
							// Get absolute path
							String absolutePath = xmlChooser.getSelectedFile( ).getAbsolutePath( );
							// Try to load chapter with it
							List<Incidence> newChapterIncidences = new ArrayList<Incidence>();
							Chapter chapter = Loader.loadChapterData( AssetsController.getInputStreamCreator(), absolutePath, incidences );
							// IF no incidences occurred
							if (chapter!=null && newChapterIncidences.size( ) == 0){
								// Try comparing names
								
								int found = -1;
								for (int j=0; found==-1 && j<chapters.size( ); j++) {
									if (chapters.get( j ).getName( ).equals( current.getAffectedResource( ) )){
										found = j;
									}
								}
								// Replace it if found
								if (found>=0){
									chapters.remove( found );
									this.chapterDataControlList.remove( found );
									chapters.add( found, chapter );
									chapterDataControlList.add( found, new ChapterDataControl(chapter) );
									
									// Copy original file to project
									File destinyFile = new File(this.getProjectFolder( ), chapter.getName( ));
									if (destinyFile.exists( ))
										destinyFile.delete( );
									File sourceFile = new File (absolutePath);
									sourceFile.copyTo( destinyFile );
									replaced = true;
									dataModified( );
								}
								
								
							}
						}
						// The chapter was not replaced: inform
						if (!replaced){
							mainWindow.showWarningDialog( TextConstants.getText( "ErrorSolving.Chapter.NotReplaced.Title" ), TextConstants.getText( "ErrorSolving.Chapter.NotReplaced.Message" ) );
						}
					} 
					// Other case: abort
					else {
						abort = true; break;
					}
				}
			}
			// Medium importance: the game might be slightly affected
			else if (current.getImportance( ) == Incidence.IMPORTANCE_MEDIUM){
				// If an asset is missing or damaged. Delete references
				if (current.getType( ) == Incidence.ASSET_INCIDENCE){
					this.deleteAssetReferences( current.getAffectedResource( ) );
					mainWindow.showInformationDialog( TextConstants.getText( "ErrorSolving.Asset.Deleted.Title" )+" - Error "+(i+1)+"/"+incidences.size( ), TextConstants.getText( "ErrorSolving.Asset.Deleted.Message", current.getAffectedResource( ) ) );
				} 
				// If it was an assessment profile (referenced) delete the assessment configuration of the chapter
				else if (current.getAffectedArea( ) == Incidence.ASSESSMENT_INCIDENCE){
					mainWindow.showInformationDialog( TextConstants.getText( "ErrorSolving.AssessmentReferenced.Deleted.Title" )+" - Error "+(i+1)+"/"+incidences.size( ), TextConstants.getText( "ErrorSolving.AssessmentReferenced.Deleted.Message", current.getAffectedResource( ) ) );
					for (int j=0; j<chapters.size( ); j++){
						if (chapters.get( j ).getAssessmentPath( ).equals( current.getAffectedResource( ) )){
							chapters.get( j ).setAssessmentPath( "" );
							dataModified( );
						}
					}
					adventureData.getAssessmentRulesListDataControl( ).deleteIdentifierReferences( current.getAffectedResource( ) );
				}
				// If it was an assessment profile (referenced) delete the assessment configuration of the chapter
				else if (current.getAffectedArea( ) == Incidence.ADAPTATION_INCIDENCE){
					mainWindow.showInformationDialog( TextConstants.getText( "ErrorSolving.AdaptationReferenced.Deleted.Title" )+" - Error "+(i+1)+"/"+incidences.size( ), TextConstants.getText( "ErrorSolving.AdaptationReferenced.Deleted.Message", current.getAffectedResource( ) ) );
					for (int j=0; j<chapters.size( ); j++){
						if (chapters.get( j ).getAdaptationPath( ).equals( current.getAffectedResource( ) )){
							chapters.get( j ).setAdaptationPath( "" );
							dataModified( );
						}
					}
					adventureData.getAdaptationRulesListDataControl( ).deleteIdentifierReferences( current.getAffectedResource( ) );
				}

				// Abort
				else {
					abort = true; break;
				}
			}
			// Low importance: the game will not be affected
			else if (current.getImportance( ) == Incidence.IMPORTANCE_LOW){
				if (current.getAffectedArea( ) == Incidence.ADAPTATION_INCIDENCE){
					adventureData.getAdaptationRulesListDataControl( ).deleteIdentifierReferences( current.getAffectedResource( ) );
					dataModified( );
				}
				if (current.getAffectedArea( ) == Incidence.ASSESSMENT_INCIDENCE){
					adventureData.getAssessmentRulesListDataControl( ).deleteIdentifierReferences( current.getAffectedResource( ) );
					dataModified( );
				}
			}

		}
		return abort;
	}
	
	/**
	 * Called when the user wants to load data from a file.
	 * 
	 * @return True if a file was loaded successfully, false otherwise
	 */
	public boolean loadFile( ) {
		return loadFile( null, true );
	}

	private boolean loadFile( String completeFilePath, boolean loadingImage ) {
		boolean fileLoaded = false;
		boolean loadFile = true;
		// If the data was not saved, ask for an action (save, discard changes...)
		if( dataModified ) {
			int option = mainWindow.showConfirmDialog( TextConstants.getText( "Operation.LoadFileTitle" ), TextConstants.getText( "Operation.LoadFileMessage" ) );
			String oldZipFile = currentZipFile;

			// If the data must be saved, load the new file only if the save was succesful
			if( option == JOptionPane.YES_OPTION )
				loadFile = saveFile( false );

			// If the data must not be saved, load the new data directly
			else if( option == JOptionPane.NO_OPTION )
				loadFile = true;

			// Cancel the action if selected
			else if( option == JOptionPane.CANCEL_OPTION )
				loadFile = false;

		}

		if( loadFile && completeFilePath == null ) {
			// Show dialog
			ProjectFolderChooser projectChooser = new ProjectFolderChooser(false, false);
			if ( projectChooser.showOpenDialog( mainWindow ) == JFileChooser.APPROVE_OPTION ){
				completeFilePath = projectChooser.getSelectedFile( ).getAbsolutePath( );
				String folderName = projectChooser.getSelectedFile( ).getName( );
				// Check the parent folder is not forbidden
				if ( isValidTargetProject( projectChooser.getSelectedFile() ) ){
					// Check characters are ok. Otherwise, show error
					if ( !FolderFileFilter.checkCharacters( folderName )){
						// Display error message
						this.showErrorDialog( TextConstants.getText( "Error.Title" ), 
								TextConstants.getText( "Error.ProjectFolderName" ) );
						completeFilePath = null;
					}
				}
				else{
					// Show error: The target dir cannot be contained 
					mainWindow.showErrorDialog( TextConstants.getText( "Operation.NewProject.ForbiddenParent.Title" ), 
							TextConstants.getText( "Operation.NewProject.ForbiddenParent.Message" ) );
					completeFilePath = null;
				}
				
			}
			//completeFilePath = mainWindow.showSingleSelectionLoadDialog( System.getenv( "HOME" ), new ProjectFileFilter( ) );

		}

		//LoadingScreen loadingScreen = new LoadingScreen(TextConstants.getText( "Operation.LoadProject" ), getLoadingImage( ), mainWindow);
		// If some file was selected
		if( completeFilePath != null ) {
			if (loadingImage){
				//ls = new LoadingScreen2(TextConstants.getText( "Operation.LoadProject" ), getLoadingImage( ), mainWindow);
				loadingScreen.setMessage( TextConstants.getText( "Operation.LoadProject" ) );
				this.loadingScreen.setVisible( true );
				
								//loadingScreen.close( );
				//loadingScreen = new LoadingScreen(TextConstants.getText( "Operation.LoadProject" ), getLoadingImage( ), mainWindow);
				//loadingScreen.setVisible( true );
				//loadingScreen.repaint( );
				//loadingScreen.setVisible( true );
				
			}
			// Create a file to extract the name and path
			File newFile = new File( completeFilePath );

			// Load the data from the file, and update the info
			List<Incidence> incidences = new ArrayList<Incidence>();
			//ls.start( );
			AdventureData loadedAdventureData = Loader.loadAdventureData( AssetsController.getInputStreamCreator(completeFilePath), 
					AssetsController.getCategoryFolder(AssetsController.CATEGORY_ASSESSMENT),
					AssetsController.getCategoryFolder(AssetsController.CATEGORY_ADAPTATION),incidences );

			//mainWindow.setNormalState( );
			
			
			// If the adventure was loaded without problems, update the data
			if( loadedAdventureData != null ) {
				// Update the values of the controller
				currentZipFile = newFile.getAbsolutePath( );
				currentZipPath = newFile.getParent( );
				currentZipName = newFile.getName( );
				adventureData = new AdventureDataControl(loadedAdventureData);
				
				// Select the first chapter
				selectedChapter = 0;

				// Clear the list and load the chapters
				chapterDataControlList.clear( );
				for( Chapter chapter : adventureData.getChapters( ) )
					chapterDataControlList.add( new ChapterDataControl( chapter ) );
				identifierSummary.loadIdentifiers( getSelectedChapterData( ) );
				getSelectedChapterDataControl( ).updateFlagSummary( flagSummary );
				
				// Check asset files
				AssetsController.checkAssetFilesConsistency( incidences );
				Incidence.sortIncidences( incidences );
				// If there is any incidence
				if (incidences.size( )>0){
					boolean abort = fixIncidences( incidences );
					if (abort)
						mainWindow.showInformationDialog( TextConstants.getText( "Error.LoadAborted.Title" ), TextConstants.getText( "Error.LoadAborted.Message" ) );
				}

				ProjectConfigData.loadFromXML( );
				AssetsController.createFolderStructure();
				
				dataModified = false;

				// The file was loaded
				fileLoaded = true;

				// Reloads the view of the window
				mainWindow.reloadData(  );
			}
		}

		//if the file was loaded, update the RecentFiles list:
		if( fileLoaded ) {
			ConfigData.fileLoaded( currentZipFile );
			AssetsController.resetCache( );
			// Load project config file
			ProjectConfigData.loadFromXML( );
			
			// Feedback
			//loadingScreen.close( );
			mainWindow.showInformationDialog( 
					TextConstants.getText( "Operation.FileLoadedTitle" ), 
					TextConstants.getText( "Operation.FileLoadedMessage" ) );
		} else {
			// Feedback
			//loadingScreen.close( );
			mainWindow.showInformationDialog( 
					TextConstants.getText( "Operation.FileNotLoadedTitle" ), 
					TextConstants.getText( "Operation.FileNotLoadedMessage" ) );
		}

		if (loadingImage)
			//ls.close( );
			loadingScreen.setVisible(false);
		return fileLoaded;
	}

	/**
	 * Called when the user wants to save data to a file.
	 * 
	 * @param saveAs
	 *            True if the destiny file must be chosen inconditionally
	 * @return True if a file was saved successfully, false otherwise
	 */
	public boolean saveFile( boolean saveAs ) {
		boolean fileSaved = false;
		boolean saveFile = true;
		String oldZipFile = this.currentZipFile;

		// Select a new file if it is a "Save as" action
		if( saveAs ) {
			//loadingScreen = new LoadingScreen(TextConstants.getText( "Operation.SaveProjectAs" ), getLoadingImage( ), mainWindow);
			//loadingScreen.setVisible( true );
			String completeFilePath = null;
			completeFilePath = mainWindow.showSaveDialog( getCurrentLoadFolder(), new FolderFileFilter( false, false) );

			// If some file was selected set the new file
			if( completeFilePath != null ) {
				// Create a file to extract the name and path
				File newFile = new File( completeFilePath );
					// Check the selectedFolder is not inside a forbidden one
					if ( isValidTargetProject( newFile ) ){
						if (FolderFileFilter.checkCharacters( newFile.getName( ) )){
		
						// Add the ".ead" if it is not present in the name
						//if( !completeFilePath.toLowerCase( ).endsWith( ".ead" ) )
						//	completeFilePath += ".ead";
		
						// If the file doesn't exist, or if the user confirms the writing in the file
						if( !newFile.exists( ) || newFile.list( ).length == 0 || mainWindow.showStrictConfirmDialog( TextConstants.getText( "Operation.SaveFileTitle" ), TextConstants.getText( "Operation.FolderNotEmpty", newFile.getName( ) ) ) ) {
							// If the file exists, delete it so it's clean in the first save
							//if( newFile.exists( ) )
							//	newFile.delete( );
		
							// If this is a "Save as" operation, copy the assets from the old file to the new one
							if( saveAs ){
								loadingScreen.setMessage( TextConstants.getText( "Operation.SaveProjectAs" ) );
								loadingScreen.setVisible( true );
								
								AssetsController.copyAssets( currentZipFile, newFile.getAbsolutePath( ) );
							}
		
							// Set the new file and path
							currentZipFile = newFile.getAbsolutePath( );
							currentZipPath = newFile.getParent( );
							currentZipName = newFile.getName( );
						}
		
						// If the file was not overwritten, don't save the data
						else
							saveFile = false;
					} else {
						this.showErrorDialog( TextConstants.getText( "Error.Title" ), 
								TextConstants.getText( "Error.ProjectFolderName" ) );
						saveFile = false;
					}
				} else {
					// Show error: The target dir cannot be contained 
					mainWindow.showErrorDialog( TextConstants.getText( "Operation.NewProject.ForbiddenParent.Title" ), 
							TextConstants.getText( "Operation.NewProject.ForbiddenParent.Message" ) );
					saveFile = false;
				}
			}

			// If no file was selected, don't save the data
			else
				saveFile = false;
		} else {
			//loadingScreen = new LoadingScreen(TextConstants.getText( "Operation.SaveProject" ), getLoadingImage( ), mainWindow);
		
			//loadingScreen.setVisible( true );
			loadingScreen.setMessage( TextConstants.getText( "Operation.SaveProject" ) );
			loadingScreen.setVisible( true );
		}

		// If the data must be saved
		if( saveFile ) {
			// If the zip was temp file, delete it
			//if( isTempFile( ) ) {
			//	File file = new File( oldZipFile );
			//	file.deleteOnExit( );
			//	isTempFile = false;
			//}

			// Check the consistency of the chapters
			boolean valid = true;
			for( ChapterDataControl chapterDataControl : chapterDataControlList )
				valid &= chapterDataControl.isValid( null, null );

			// If the data is not valid, show an error message
			if( !valid )
				mainWindow.showWarningDialog( TextConstants.getText( "Operation.AdventureConsistencyTitle" ), TextConstants.getText( "Operation.AdventurInconsistentWarning" ) );

			// Save the data
			if( Writer.writeData( currentZipFile, adventureData, valid ) ) {
				// Set modified to false and update the window title
				dataModified = false;
				mainWindow.updateTitle( );

				// The file was saved
				fileSaved = true;
			}
		}

		//If the file was saved, update the recent files list:
		if( fileSaved ) {
			ConfigData.fileLoaded( currentZipFile );
			ProjectConfigData.storeToXML( );
			AssetsController.resetCache( );
		}
		//loadingScreen.close( );
		loadingScreen.setVisible( false );

		return fileSaved;
	}

	public void importChapter(){
		
	}

	public void importGame(){
		importGame(null);
	}
	
	public void importGame(String eadPath){
		boolean importGame = true;

		if (dataModified){
			int option = mainWindow.showConfirmDialog( TextConstants.getText( "Operation.SaveChangesTitle" ), TextConstants.getText( "Operation.SaveChangesMessage" ) );
			// If the data must be saved, load the new file only if the save was succesful
			if( option == JOptionPane.YES_OPTION )
				importGame = saveFile( false );

			// If the data must not be saved, load the new data directly
			else if( option == JOptionPane.NO_OPTION )
				importGame = true;

			// Cancel the action if selected
			else if( option == JOptionPane.CANCEL_OPTION )
				importGame = false;

		}
		
		if (importGame){
			// Ask origin file
			JFileChooser chooser = new JFileChooser();
			chooser.setFileFilter( new EADFileFilter() );
			chooser.setMultiSelectionEnabled( false );
			chooser.setCurrentDirectory( new File(getCurrentExportSaveFolder()) );
			int option = JFileChooser.APPROVE_OPTION;
			if (eadPath==null)
				option = chooser.showOpenDialog( mainWindow );
			if (option == JFileChooser.APPROVE_OPTION ){
				java.io.File originFile = null;
				if (eadPath==null)
					originFile = chooser.getSelectedFile( );
				else
					originFile = new File(eadPath);
				
				if (!originFile.getAbsolutePath( ).endsWith( ".ead" ))
					originFile = new java.io.File (originFile.getAbsolutePath( )+".ead");
					// If the file not exists display error
				if ( !originFile.exists( ) )
					mainWindow.showErrorDialog( TextConstants.getText( "Error.Import.FileNotFound.Title" ), TextConstants.getText( "Error.Import.FileNotFound.Title", originFile.getName( ) ) );
				// Otherwise ask folder for the new project
				else {
					boolean create = false;
					java.io.File selectedDir = null;
					// Prompt main folder of the project
					ProjectFolderChooser folderSelector = new ProjectFolderChooser(false, false);
					// If some folder is selected, check all characters are correct  
					if ( folderSelector.showOpenDialog( mainWindow ) == JFileChooser.APPROVE_OPTION ){
						java.io.File selectedFolder = folderSelector.getSelectedFile( );
						selectedDir = selectedFolder;
						
						// Check the selectedFolder is not inside a forbidden one
						if ( isValidTargetProject( selectedFolder ) ){
							if (FolderFileFilter.checkCharacters( selectedFolder.getName( ) )){
								// Folder can be created/used
								// Does the folder exist?
								if (selectedFolder.exists( )){
									//Is the folder empty?
									if (selectedFolder.list( ).length > 0){
										// Delete content?
										if ( this.showStrictConfirmDialog( TextConstants.getText( "Operation.NewProject.FolderNotEmptyTitle" ), TextConstants.getText( "Operation.NewProject.FolderNotEmptyMessage" ) )){
											File directory = new File (selectedFolder.getAbsolutePath( ));
											if( directory.deleteAll( ) ){
												create = true;	
											} else {
												this.showStrictConfirmDialog( TextConstants.getText( "Error.Title" ), TextConstants.getText( "Error.DeletingFolderContents" ));
											}
										}
									}else{
										create = true;
									}
								} else {
									// Create new folder?
									File directory = new File (selectedFolder.getAbsolutePath( ));
									if( directory.mkdirs( ) ){
										create = true;	
									} else {
										this.showStrictConfirmDialog( TextConstants.getText( "Error.Title" ), TextConstants.getText( "Error.CreatingFolder" ));
									}
								}
							}else{
								// Display error message
								this.showErrorDialog( TextConstants.getText( "Error.Title" ), 
										TextConstants.getText( "Error.ProjectFolderName" ) );
							}
						} else {
							// Show error: The target dir cannot be contained 
							mainWindow.showErrorDialog( TextConstants.getText( "Operation.NewProject.ForbiddenParent.Title" ), 
									TextConstants.getText( "Operation.NewProject.ForbiddenParent.Message" ) );
							create = false;
						}
					}
					
					// Create the new project?
					if (create){
						//LoadingScreen loadingScreen = new LoadingScreen(TextConstants.getText( "Operation.ImportProject" ), getLoadingImage( ), mainWindow);
						loadingScreen.setMessage( TextConstants.getText( "Operation.ImportProject" ) );
						loadingScreen.setVisible(true);
						//AssetsController.createFolderStructure();
						if (!selectedDir.exists( ))
							selectedDir.mkdirs( );
						
						// Unzip directory
						File.unzipDir( originFile.getAbsolutePath( ), selectedDir.getAbsolutePath( ) );
						
						//ProjectConfigData.loadFromXML( );
						
						// Load new project
						loadFile( selectedDir.getAbsolutePath( ), false );
						//loadingScreen.close( );
						loadingScreen.setVisible( false );
					}
						
				}
			}
		}
	}
	
	public boolean exportGame( ){
		return exportGame (null);
	}
	
	public boolean exportGame( String targetFilePath ){
		boolean exportGame = true;
		boolean exported = false;
		if (dataModified){
			
			int option = mainWindow.showConfirmDialog( TextConstants.getText( "Operation.SaveChangesTitle" ), TextConstants.getText( "Operation.SaveChangesMessage" ) );
			// If the data must be saved, load the new file only if the save was succesful
			if( option == JOptionPane.YES_OPTION )
				exportGame = saveFile( false );

			// If the data must not be saved, load the new data directly
			else if( option == JOptionPane.NO_OPTION )
				exportGame = true;

			// Cancel the action if selected
			else if( option == JOptionPane.CANCEL_OPTION )
				exportGame = false;
		}
		
		if (exportGame){
			// Ask destiny file
			/*JFileChooser chooser = new JFileChooser();
			chooser.setFileFilter( new EADFileFilter() );
			chooser.setMultiSelectionEnabled( false );
			int option = chooser.showSaveDialog( mainWindow );
			if (option == JFileChooser.APPROVE_OPTION){
				java.io.File destinyFile = chooser.getSelectedFile( );
				if (!destinyFile.getAbsolutePath( ).toLowerCase( ).endsWith( ".ead" )){
					destinyFile = new java.io.File (destinyFile.getAbsolutePath( )+".ead");*/
			
			String selectedPath = targetFilePath; 
			if (selectedPath==null)
				selectedPath = mainWindow.showSaveDialog( getCurrentExportSaveFolder(), new EADFileFilter() );
			
			if (selectedPath != null){
				if (!selectedPath.toLowerCase( ).endsWith( ".ead" ))
					selectedPath = selectedPath + ".ead";

					java.io.File destinyFile = new File (selectedPath);

					// Check the destinyFile is not in the project folder
					if ( targetFilePath !=null || isValidTargetFile( destinyFile ) ){
						
						// If the file exists, ask to overwrite
						if ( !destinyFile.exists( ) || targetFilePath!=null ||  
							  mainWindow.showStrictConfirmDialog( TextConstants.getText( "Operation.SaveFileTitle" ), TextConstants.getText( "Operation.OverwriteExistingFile", destinyFile.getName( ) ) ) ){
							destinyFile.delete( );
							
							// Finally, export it
							//LoadingScreen loadingScreen = new LoadingScreen(TextConstants.getText( "Operation.ExportProject.AsEAD" ), getLoadingImage( ), mainWindow);
							if (targetFilePath == null){
								loadingScreen.setMessage( TextConstants.getText( "Operation.ExportProject.AsEAD" ) );
								loadingScreen.setVisible( true );
							}
							if (Writer.export( getProjectFolder(), destinyFile.getAbsolutePath( ) )){
								exported = true;
								if (targetFilePath == null)
									mainWindow.showInformationDialog( TextConstants.getText( "Operation.ExportT.Success.Title" ), 
											TextConstants.getText( "Operation.ExportT.Success.Message" ) );
							} else {
								mainWindow.showInformationDialog( TextConstants.getText( "Operation.ExportT.NotSuccess.Title" ), 
										TextConstants.getText( "Operation.ExportT.NotSuccess.Message" ) );
							}
							//loadingScreen.close( );
							if (targetFilePath == null)
								loadingScreen.setVisible( false );
						}
					} else {
						// Show error: The target dir cannot be contained 
						mainWindow.showErrorDialog( TextConstants.getText( "Operation.ExportT.TargetInProjectDir.Title" ), 
								TextConstants.getText( "Operation.ExportT.TargetInProjectDir.Message" ) );
					}
				}
			}
		return exported;
	}

	public void exportStandaloneGame(){
		boolean exportGame = true;

		if (dataModified){
			int option = mainWindow.showConfirmDialog( TextConstants.getText( "Operation.SaveChangesTitle" ), TextConstants.getText( "Operation.SaveChangesMessage" ) );
			// If the data must be saved, load the new file only if the save was succesful
			if( option == JOptionPane.YES_OPTION )
				exportGame = saveFile( false );

			// If the data must not be saved, load the new data directly
			else if( option == JOptionPane.NO_OPTION )
				exportGame = true;

			// Cancel the action if selected
			else if( option == JOptionPane.CANCEL_OPTION )
				exportGame = false;

		}
		
		if (exportGame){
			// Ask destiny file
			//JFileChooser chooser = new JFileChooser();
			//chooser.setFileFilter( new JARFileFilter() );
			//chooser.setMultiSelectionEnabled( false );
			String completeFilePath = null;
			completeFilePath = mainWindow.showSaveDialog( getCurrentExportSaveFolder(), new FileFilter(){

					@Override
					public boolean accept( java.io.File arg0 ) {
						return arg0.getAbsolutePath().toLowerCase().endsWith( ".jar" ) || arg0.isDirectory( );
					}

					@Override
					public String getDescription( ) {
						return "Java ARchive files (*.jar)";
					}
				});
			
			//int option = chooser.showSaveDialog( mainWindow );
			//if (option == JFileChooser.APPROVE_OPTION){
			if (completeFilePath != null){
				//java.io.File destinyFile = chooser.getSelectedFile( );
				//if (!destinyFile.getAbsolutePath( ).toLowerCase( ).endsWith( ".jar" )){
				//	destinyFile = new java.io.File (destinyFile.getAbsolutePath( )+".jar");
				if (!completeFilePath.toLowerCase( ).endsWith( ".jar" ))
					completeFilePath = completeFilePath + ".jar";
					// If the file exists, ask to overwrite
					java.io.File destinyFile = new File (completeFilePath);
					
					// Check the destinyFile is not in the project folder
					if ( isValidTargetFile( destinyFile ) ){
					
						if ( !destinyFile.exists( ) || 
							  mainWindow.showStrictConfirmDialog( TextConstants.getText( "Operation.SaveFileTitle" ), TextConstants.getText( "Operation.OverwriteExistingFile", destinyFile.getName( ) ) ) ){
							destinyFile.delete( );
							
							// Finally, export it
							loadingScreen.setMessage( TextConstants.getText( "Operation.ExportProject.AsJAR" ) );
							loadingScreen.setVisible( true );
							if (Writer.exportStandalone( getProjectFolder(), destinyFile.getAbsolutePath( ) )){
								mainWindow.showInformationDialog( TextConstants.getText( "Operation.ExportT.Success.Title" ), 
										TextConstants.getText( "Operation.ExportT.Success.Message" ) );
							} else {
								mainWindow.showInformationDialog( TextConstants.getText( "Operation.ExportT.NotSuccess.Title" ), 
										TextConstants.getText( "Operation.ExportT.NotSuccess.Message" ) );
							}
							loadingScreen.setVisible( false );
							
						}
					} else {
						// Show error: The target dir cannot be contained 
						mainWindow.showErrorDialog( TextConstants.getText( "Operation.ExportT.TargetInProjectDir.Title" ), 
								TextConstants.getText( "Operation.ExportT.TargetInProjectDir.Message" ) );
					}
				}
		}

	}

	
	public void exportToLOM( ) {
	
		boolean exportFile = true;
		if (dataModified){
			int option = mainWindow.showConfirmDialog( TextConstants.getText( "Operation.SaveChangesTitle" ), TextConstants.getText( "Operation.SaveChangesMessage" ) );
			// If the data must be saved, load the new file only if the save was succesful
			if( option == JOptionPane.YES_OPTION )
				exportFile = saveFile( false );

			// If the data must not be saved, load the new data directly
			else if( option == JOptionPane.NO_OPTION )
				exportFile = true;

			// Cancel the action if selected
			else if( option == JOptionPane.CANCEL_OPTION )
				exportFile = false;

		}
		
		if (exportFile){
			// Ask the data of the Learning Object:
			ExportToLOMDialog dialog = new ExportToLOMDialog(TextConstants.getText( "Operation.ExportToLOM.DefaultValue" ));
			String loName = dialog.getLomName( );
			String authorName = dialog.getAuthorName( );
			String organization = dialog.getOrganizationName( );
			
			boolean validated = dialog.isValidated( );
			
			if (validated){
				//String loName = this.showInputDialog( TextConstants.getText( "Operation.ExportToLOM.Title" ), TextConstants.getText( "Operation.ExportToLOM.Message" ), TextConstants.getText( "Operation.ExportToLOM.DefaultValue" ));
				if (loName!=null && !loName.equals( "" ) && !loName.contains( " " )){
					//Check authorName & organization
					if (authorName!=null && authorName.length( )>5 && organization!=null && organization.length( )>5){
					
						//Ask for the name of the zip
						String completeFilePath = null;
						completeFilePath = mainWindow.showSaveDialog( getCurrentExportSaveFolder(), new FileFilter(){
		
								@Override
								public boolean accept( java.io.File arg0 ) {
									return arg0.getAbsolutePath().toLowerCase().endsWith( ".zip" ) || arg0.isDirectory( );
								}
		
								@Override
								public String getDescription( ) {
									return "Zip files (*.zip)";
								}
							});
		
						// If some file was selected set the new file
						if( completeFilePath != null ) {
							// Add the ".zip" if it is not present in the name
							if( !completeFilePath.toLowerCase( ).endsWith( ".zip" ) )
								completeFilePath += ".zip";
		
							// Create a file to extract the name and path
							File newFile = new File( completeFilePath );
							
							// Check the selected file is contained in a valid folder
							if ( isValidTargetFile( newFile ) ){
		
								// If the file doesn't exist, or if the user confirms the writing in the file
								if( !newFile.exists( ) || mainWindow.showStrictConfirmDialog( TextConstants.getText( "Operation.SaveFileTitle" ), TextConstants.getText( "Operation.OverwriteExistingFile", newFile.getName( ) ) ) ) {
									// If the file exists, delete it so it's clean in the first save
									
									try {						
										if (newFile.exists( ))
											newFile.delete( );
										//LoadingScreen loadingScreen = new LoadingScreen(TextConstants.getText( "Operation.ExportProject.AsJAR" ), getLoadingImage( ), mainWindow);
										loadingScreen.setMessage( TextConstants.getText( "Operation.ExportProject.AsLO" ) );
										loadingScreen.setVisible( true );
										this.updateLOMLanguage( );
										if (Writer.exportAsLearningObject( completeFilePath, loName, authorName, organization, this.currentZipFile, adventureData )){
											mainWindow.showInformationDialog( TextConstants.getText( "Operation.ExportT.Success.Title" ), 
													TextConstants.getText( "Operation.ExportT.Success.Message" ) );
										} else {
											mainWindow.showInformationDialog( TextConstants.getText( "Operation.ExportT.NotSuccess.Title" ), 
													TextConstants.getText( "Operation.ExportT.NotSuccess.Message" ) );
										}
										//loadingScreen.close( );
										loadingScreen.setVisible( false );
			
									} catch( Exception e ) {
										this.showErrorDialog( TextConstants.getText( "Operation.ExportToLOM.LONameNotValid.Title" ), TextConstants.getText( "Operation.ExportToLOM.LONameNotValid.Title" ) );
										e.printStackTrace();
									}
									
								} 
							} else {
								// Show error: The target dir cannot be contained 
								mainWindow.showErrorDialog( TextConstants.getText( "Operation.ExportT.TargetInProjectDir.Title" ), 
										TextConstants.getText( "Operation.ExportT.TargetInProjectDir.Message" ) );
							}
						
						}
					} else {
						this.showErrorDialog( TextConstants.getText( "Operation.ExportToLOM.AuthorNameOrganizationNotValid.Title" ), TextConstants.getText( "Operation.ExportToLOM.AuthorNameOrganizationNotValid.Message" ) );
					}
				}else{
					this.showErrorDialog( TextConstants.getText( "Operation.ExportToLOM.LONameNotValid.Title" ), TextConstants.getText( "Operation.ExportToLOM.LONameNotValid.Message" ) );
				}
			}
		}
	}

	/**
	 * Executes the current project. Firstly, it checks that the game does not present any consistency errors. 
	 * Then exports the project to the web dir as a temp .ead file and gets it running
	 */
	public void run(){
		// Check adventure consistency
		if (checkAdventureConsistency( false )){
			
			// Create temp file for exportation
			String tempFileName = "$temp_EAD_";
			File tempFile = null;
			for (int i=0; i<10000000; i++){
				if (!new File("web/temp/"+tempFileName+i+".ead").exists()){
					tempFile = new File("web/temp/"+tempFileName+i+".ead");
					break;
				}
			}
			
			if (tempFile!=null){
				// Export game
				if (exportGame( tempFile.getAbsolutePath( ) )){
					while (!tempFile.exists( ) || tempFile.length( ) <=0){
						try {
							Thread.sleep( 250 );
						} catch( InterruptedException e ) {
							e.printStackTrace();
						}
					}
					String engineLangaugeFile = "en_EN_engine.xml";
					if (getLanguage( ) == LANGUAGE_SPANISH){
						engineLangaugeFile = "es_ES_engine.xml";
					}
					String []args =  new String[]{
						"cd ..",
						"cd EAdventure2D",
						"java -jar eAdventure-engine.jar "+tempFile.getAbsolutePath( )
					};
					try {
						Runtime.getRuntime( ).exec(args);
					} catch( IOException e ) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//new RunEADThread ( tempFile.getAbsolutePath( ), engineLangaugeFile ).start( );
				}
			}
		}
	}
	
	private class RunEADThread extends Thread {
		private String file;
		private String engineLangaugeFile;
		
		public RunEADThread (String path, String language){
			file = path;
			this.engineLangaugeFile = language;
		}
		public void start(){
			EAdventureDebug.main(new String[]{file, new File(engineLangaugeFile).getAbsolutePath( )});
		}

	}
	
	/**
	 * Determines if the target file of an exportation process is valid.
	 * The file cannot be located neither inside the project folder, nor
	 * inside the web folder
	 * @param targetFile
	 * @return
	 */
	private boolean isValidTargetFile ( java.io.File targetFile ){
		java.io.File[] forbiddenParents = new java.io.File[]{new java.io.File(WEB_FOLDER), new java.io.File(WEB_TEMP_FOLDER), getProjectFolderFile()};
		boolean isValid = true;
		for (java.io.File forbiddenParent: forbiddenParents){
			if (targetFile.getAbsolutePath( ).toLowerCase( ).startsWith( forbiddenParent.getAbsolutePath( ).toLowerCase( ) )){
				isValid=false; break;
			}
		}
		return isValid;
	}
	
	/**
	 * Determines if the target folder for a new project is valid.
	 * The folder cannot be located inside the web folder
	 * @param targetFile
	 * @return
	 */
	private boolean isValidTargetProject ( java.io.File targetFile ){
		java.io.File[] forbiddenParents = new java.io.File[]{new java.io.File(WEB_FOLDER), new java.io.File(WEB_TEMP_FOLDER)};
		boolean isValid = true;
		for (java.io.File forbiddenParent: forbiddenParents){
			if (targetFile.getAbsolutePath( ).toLowerCase( ).startsWith( forbiddenParent.getAbsolutePath( ).toLowerCase( ) )){
				isValid=false; break;
			}
		}
		return isValid;
	}


	/**
	 * Exits from the aplication.
	 */
	public void exit( ) {
		boolean exit = true;

		// If the data was not saved, ask for an action (save, discard changes...)
		if( dataModified ) {
			int option = mainWindow.showConfirmDialog( TextConstants.getText( "Operation.ExitTitle" ), TextConstants.getText( "Operation.ExitMessage" ) );

			String oldZipFile = currentZipFile;
			// If the data must be saved, lexit only if the save was succesful
			if( option == JOptionPane.YES_OPTION )
				exit = saveFile( false );

			// If the data must not be saved, exit directly
			else if( option == JOptionPane.NO_OPTION )
				exit = true;

			// Cancel the action if selected
			else if( option == JOptionPane.CANCEL_OPTION )
				exit = false;

			//if( isTempFile( ) ) {
			//	File file = new File( oldZipFile );
			//	file.deleteOnExit( );
			//	isTempFile = false;
			//}
		}

		// Exit the aplication
		if( exit ) {
			ConfigData.storeToXML( );
			ProjectConfigData.storeToXML( );
			//AssetsController.cleanVideoCache( );
			System.exit( 0 );
		}
	}

	/**
	 * Checks if the adventure is valid or not. It shows information to the user, whether the data is valid or not.
	 */
	public boolean checkAdventureConsistency(  ) {
		return checkAdventureConsistency( false );
	}
	
	public boolean checkAdventureConsistency( boolean showSuccessFeedback ) {
		// Create a list to store the incidences
		List<String> incidences = new ArrayList<String>( );

		// Check all the chapters
		boolean valid = true;
		for( ChapterDataControl chapterDataControl : chapterDataControlList )
			valid &= chapterDataControl.isValid( null, incidences );

		// If the data is valid, show a dialog with the information
		if( valid ){
			if ( showSuccessFeedback )
				mainWindow.showInformationDialog( TextConstants.getText( "Operation.AdventureConsistencyTitle" ), TextConstants.getText( "Operation.AdventureConsistentReport" ) );

		// If it is not valid, show a dialog with the problems
		} else
			new InvalidReportDialog( incidences );
		
		return valid;
	}
	
	public void checkFileConsistency( ){
		
	}

	/**
	 * Shows the adventure data dialog editor.
	 */
	public void showAdventureDataDialog( ) {
		new AdventureDataDialog( );
	}
	
	/**
	 * Shows the adventure data dialog editor.
	 */
	public void showLOMDataDialog( ) {
		new LOMDialog( adventureData.getLomController( ) );
	}


	/**
	 * Shows the GUI style selection dialog.
	 */
	public void showGUIStylesDialog( ) {
		// Show the dialog
		GUIStylesDialog guiStylesDialog = new GUIStylesDialog( adventureData.getGUIType( ) );

		// If the new GUI style is different from the current, and valid, change the value
		int optionSelected = guiStylesDialog.getOptionSelected( );
		if( optionSelected != -1 && adventureData.getGUIType( ) != optionSelected ) {
			if( optionSelected == 0 ) {
				adventureData.setGUIType( DescriptorData.GUI_TRADITIONAL );
				dataModified( );
			} else if( optionSelected == 1 ) {
				adventureData.setGUIType( DescriptorData.GUI_CONTEXTUAL );
				dataModified( );
			}
		}
	}

	/**
	 * Shows the assessment files dialog.
	 */
	public void showAssessmentFilesDialog( ) {
		// Show the dialog
		new XMLAssetsDialog( XMLAssetsDialog.ASSESSMENT );
	}

	/**
	 * Shows the adaptation files dialog.
	 */
	public void showAdaptationFilesDialog( ) {
		// Show the dialog
		new XMLAssetsDialog( XMLAssetsDialog.ADAPTATION );
	}

	/**
	 * Shows the background assets dialog.
	 */
	public void showBackgroundAssetsDialog( ) {
		// Show the dialog
		new ImageAssetsDialog( ImageAssetsDialog.BACKGROUND );
	}

	/**
	 * Shows the animation assets dialog.
	 */
	public void showAnimationAssetsDialog( ) {
		// Show the dialog
		new AnimationAssetsDialog( );
	}

	/**
	 * Shows the image assets dialog.
	 */
	public void showImageAssetsDialog( ) {
		// Show the dialog
		new ImageAssetsDialog( ImageAssetsDialog.IMAGE );
	}

	/**
	 * Shows the icon assets dialog.
	 */
	public void showIconAssetsDialog( ) {
		// Show the dialog
		new ImageAssetsDialog( ImageAssetsDialog.ICON );
	}

	/**
	 * Shows the audio assets dialog.
	 */
	public void showAudioAssetsDialog( ) {
		// Show the dialog
		new AudioAssetsDialog( );
	}

	/**
	 * Shows the audio assets dialog.
	 */
	public void showVideoAssetsDialog( ) {
		// Show the dialog
		new VideoAssetsDialog( );
	}

	/**
	 * Shows the flags dialog.
	 */
	public void showEditFlagDialog( ) {
		new FlagsDialog( new FlagsController( flagSummary ) );
	}

	/**
	 * Sets a new selected chapter with the given index.
	 * 
	 * @param selectedChapter
	 *            Index of the new selected chapter
	 */
	public void setSelectedChapter( int selectedChapter ) {
		this.selectedChapter = selectedChapter;

		// Update the identifier and flag summary
		identifierSummary.loadIdentifiers( getSelectedChapterData( ) );
		getSelectedChapterDataControl( ).updateFlagSummary( flagSummary );

		// Reload the main window
		mainWindow.reloadData(  );
	}

	public void updateFlagSummary( ) {
		getSelectedChapterDataControl( ).updateFlagSummary( flagSummary );
	}

	/**
	 * Adds a new chapter to the adventure. This method asks for the title of the chapter to the user, and updates the
	 * view of the application if a new chapter was added.
	 */
	public void addChapter( ) {
		// Show a dialog asking for the chapter title
		String chapterTitle = mainWindow.showInputDialog( TextConstants.getText( "Operation.AddChapterTitle" ), TextConstants.getText( "Operation.AddChapterMessage" ), TextConstants.getText( "Operation.AddChapterDefaultValue" ) );

		// If some value was typed
		if( chapterTitle != null ) {
			// Create the new chapter, and the controller
			Chapter newChapter = new Chapter( chapterTitle, TextConstants.getText( "DefaultValue.SceneId" ) );
			adventureData.getChapters( ).add( newChapter );
			chapterDataControlList.add( new ChapterDataControl( newChapter ) );

			// Select the new chapter, and add the new data the data
			selectedChapter = adventureData.getChapters( ).size( ) - 1;
			identifierSummary.loadIdentifiers( getSelectedChapterData( ) );
			getSelectedChapterDataControl( ).updateFlagSummary( flagSummary );

			// Update the main window
			dataModified( );
			mainWindow.reloadData( );
		}
	}

	/**
	 * Deletes the selected chapter from the adventure. This method asks the user for confirmation, and updates the view
	 * if needed.
	 */
	public void deleteChapter( ) {
		// Check the number of chapters, the chapters can be deleted when there are more than one
		if( adventureData.getChapters( ).size( ) > 1 ) {
			// Ask for confirmation
			if( mainWindow.showStrictConfirmDialog( TextConstants.getText( "Operation.DeleteChapterTitle" ), TextConstants.getText( "Operation.DeleteChapterMessage" ) ) ) {
				// Delete the chapter and the controller
				adventureData.getChapters( ).remove( selectedChapter );
				chapterDataControlList.remove( selectedChapter );

				// Update the selected chapter when needed
				if( selectedChapter > 0 )
					selectedChapter--;

				// Update the data
				identifierSummary.loadIdentifiers( getSelectedChapterData( ) );
				getSelectedChapterDataControl( ).updateFlagSummary( flagSummary );

				// Update the main window
				dataModified( );
				mainWindow.reloadData( );
			}
		}

		// If there is only one chapter, show an error message
		else
			mainWindow.showErrorDialog( TextConstants.getText( "Operation.DeleteChapterTitle" ), TextConstants.getText( "Operation.DeleteChapterErrorLastChapter" ) );
	}

	/**
	 * Moves the selected chapter to the previous position of the chapter's list.
	 */
	public void moveChapterUp( ) {
		// If the chapter can be moved
		if( selectedChapter > 0 ) {
			// Move the chapter and update the selected chapter
			adventureData.getChapters( ).add( selectedChapter - 1, adventureData.getChapters( ).remove( selectedChapter ) );
			chapterDataControlList.add( selectedChapter - 1, chapterDataControlList.remove( selectedChapter ) );
			selectedChapter--;

			// Update the data
			identifierSummary.loadIdentifiers( getSelectedChapterData( ) );
			getSelectedChapterDataControl( ).updateFlagSummary( flagSummary );

			// Update the main window
			dataModified( );
			mainWindow.reloadData(  );
		}
	}

	/**
	 * Moves the selected chapter to the next position of the chapter's list.
	 * 
	 */
	public void moveChapterDown( ) {
		// If the chapter can be moved
		if( selectedChapter < adventureData.getChapters( ).size( ) - 1 ) {
			// Move the chapter and update the selected chapter
			adventureData.getChapters( ).add( selectedChapter + 1, adventureData.getChapters( ).remove( selectedChapter ) );
			chapterDataControlList.add( selectedChapter + 1, chapterDataControlList.remove( selectedChapter ) );
			selectedChapter++;

			// Update the data
			identifierSummary.loadIdentifiers( getSelectedChapterData( ) );
			getSelectedChapterDataControl( ).updateFlagSummary( flagSummary );

			// Update the main window
			dataModified( );
			mainWindow.reloadData(  );
		}
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////

	// Methods to edit and get the adventure general data (title and description)

	/**
	 * Returns the title of the adventure.
	 * 
	 * @return Adventure's title
	 */
	public String getAdventureTitle( ) {
		return adventureData.getTitle( );
	}

	/**
	 * Returns the description of the adventure.
	 * 
	 * @return Adventure's description
	 */
	public String getAdventureDescription( ) {
		return adventureData.getDescription( );
	}

	/**
	 * Sets the new title of the adventure.
	 * 
	 * @param title
	 *            Title of the adventure
	 */
	public void setAdventureTitle( String title ) {
		// If the value is different
		if( !title.equals( adventureData.getTitle( ) ) ) {
			// Set the new title and modify the data
			adventureData.setTitle( title );
			dataModified( );
		}
	}

	/**
	 * Sets the new description of the adventure.
	 * 
	 * @param description
	 *            Description of the adventure
	 */
	public void setAdventureDescription( String description ) {
		// If the value is different
		if( !description.equals( adventureData.getDescription( ) ) ) {
			// Set the new description and modify the data
			adventureData.setDescription( description );
			dataModified( );
		}
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////

	// Methods that perform specific tasks for the microcontrollers

	/**
	 * Returns whether the given identifier is valid or not. If the element identifier is not valid, this method shows
	 * an error message to the user.
	 * 
	 * @param elementId
	 *            Element identifier to be checked
	 * @return True if the identifier is valid, false otherwise
	 */
	public boolean isElementIdValid( String elementId ) {
		boolean elementIdValid = false;

		// Check if the identifier has no spaces
		if( !elementId.contains( " " ) ) {

			// If the identifier doesn't exist already
			if( !getIdentifierSummary( ).existsId( elementId ) ) {

				// If the identifier is not a reserved identifier
				if( !elementId.equals( Player.IDENTIFIER ) && !elementId.equals( TextConstants.getText( "ConversationLine.PlayerName" ) ) ) {

					// If the first character is a letter
					if( Character.isLetter( elementId.charAt( 0 ) ) )
						elementIdValid = true;

					// Show non-letter first character error
					else
						mainWindow.showErrorDialog( TextConstants.getText( "Operation.IdErrorTitle" ), TextConstants.getText( "Operation.IdErrorFirstCharacter" ) );
				}

				// Show invalid identifier error
				else
					mainWindow.showErrorDialog( TextConstants.getText( "Operation.IdErrorTitle" ), TextConstants.getText( "Operation.IdErrorReservedIdentifier", elementId ) );
			}

			// Show repeated identifier error
			else
				mainWindow.showErrorDialog( TextConstants.getText( "Operation.IdErrorTitle" ), TextConstants.getText( "Operation.IdErrorAlreadyUsed" ) );
		}

		// Show blank spaces error
		else
			mainWindow.showErrorDialog( TextConstants.getText( "Operation.IdErrorTitle" ), TextConstants.getText( "Operation.IdErrorBlankSpaces" ) );

		return elementIdValid;
	}

	/**
	 * Returns whether the given identifier is valid or not. If the element identifier is not valid, this method shows
	 * an error message to the user.
	 * 
	 * @param elementId
	 *            Element identifier to be checked
	 * @return True if the identifier is valid, false otherwise
	 */
	public boolean isPropertyIdValid( String elementId ) {
		boolean elementIdValid = false;

		// Check if the identifier has no spaces
		if( !elementId.contains( " " ) ) {

					// If the first character is a letter
					if( Character.isLetter( elementId.charAt( 0 ) ) )
						elementIdValid = true;

					// Show non-letter first character error
					else
						mainWindow.showErrorDialog( TextConstants.getText( "Operation.IdErrorTitle" ), TextConstants.getText( "Operation.IdErrorFirstCharacter" ) );
		}

		// Show blank spaces error
		else
			mainWindow.showErrorDialog( TextConstants.getText( "Operation.IdErrorTitle" ), TextConstants.getText( "Operation.IdErrorBlankSpaces" ) );

		return elementIdValid;
	}

	
	/**
	 * This method returns the absolute path of the background image of the given scene.
	 * 
	 * @param sceneId
	 *            Scene id
	 * @return Path to the background image, null if it was not found
	 */
	public String getSceneImagePath( String sceneId ) {
		String sceneImagePath = null;

		// Search for the image in the list, comparing the identifiers
		for( SceneDataControl scene : getSelectedChapterDataControl( ).getScenesList( ).getScenes( ) )
			if( sceneId.equals( scene.getId( ) ) )
				sceneImagePath = scene.getPreviewBackground( );

		return sceneImagePath;
	}

	/**
	 * This method returns the absolute path of the default image of the player.
	 * 
	 * @return Default image of the player
	 */
	public String getPlayerImagePath( ) {
		return getSelectedChapterDataControl( ).getPlayer( ).getPreviewImage( );
	}

	/**
	 * This method returns the absolute path of the default image of the given element (item or character).
	 * 
	 * @param elementId
	 *            Id of the element
	 * @return Default image of the requested element
	 */
	public String getElementImagePath( String elementId ) {
		String elementImage = null;

		// Search for the image in the items, comparing the identifiers
		for( ItemDataControl item : getSelectedChapterDataControl( ).getItemsList( ).getItems( ) )
			if( elementId.equals( item.getId( ) ) )
				elementImage = item.getPreviewImage( );

		// Search for the image in the characters, comparing the identifiers
		for( NPCDataControl npc : getSelectedChapterDataControl( ).getNPCsList( ).getNPCs( ) )
			if( elementId.equals( npc.getId( ) ) )
				elementImage = npc.getPreviewImage( );

		return elementImage;
	}

	/**
	 * Counts all the references to a given asset in the entire script.
	 * 
	 * @param assetPath
	 *            Path of the asset (relative to the ZIP), without suffix in case of an animation or set of slides
	 * @return Number of references to the given asset
	 */
	public int countAssetReferences( String assetPath ) {
		int count = 0;

		// Search in all the chapters
		for( ChapterDataControl chapterDataControl : chapterDataControlList ){
			count += chapterDataControl.countAssetReferences( assetPath );
		}return count;
	}

	/**
	 * Gets a list with all the assets referenced in the chapter along with the types of those assets
	 * @param assetPaths
	 * @param assetTypes
	 */
	public void getAssetReferences(List<String> assetPaths, List<Integer> assetTypes){
		for( ChapterDataControl chapterDataControl : chapterDataControlList ){
			chapterDataControl.getAssetReferences( assetPaths, assetTypes );
		}
	}
	
	/**
	 * Deletes a given asset from the script, removing all occurrences.
	 * 
	 * @param assetPath
	 *            Path of the asset (relative to the ZIP), without suffix in case of an animation or set of slides
	 */
	public void deleteAssetReferences( String assetPath ) {
		// Delete the asset in all the chapters
		for( ChapterDataControl chapterDataControl : chapterDataControlList )
			chapterDataControl.deleteAssetReferences( assetPath );
	}

	/**
	 * Counts all the references to a given identifier in the entire script.
	 * 
	 * @param id
	 *            Identifier to which the references must be found
	 * @return Number of references to the given identifier
	 */
	public int countIdentifierReferences( String id ) {
		return getSelectedChapterDataControl( ).countIdentifierReferences( id );
	}

	/**
	 * Deletes a given identifier from the script, removing all occurrences.
	 * 
	 * @param id
	 *            Identifier to be deleted
	 */
	public void deleteIdentifierReferences( String id ) {
		if (getSelectedChapterDataControl() != null)
			getSelectedChapterDataControl( ).deleteIdentifierReferences( id );
		else
			this.identifierSummary.deleteAssessmentRuleId( id );
	}

	/**
	 * Replaces a given identifier with another one, in all the occurrences in the script.
	 * 
	 * @param oldId
	 *            Old identifier to be replaced
	 * @param newId
	 *            New identifier to replace the old one
	 */
	public void replaceIdentifierReferences( String oldId, String newId ) {
		getSelectedChapterDataControl( ).replaceIdentifierReferences( oldId, newId );
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////

	// Methods linked with the GUI

	/**
	 * Updates the chapter menu with the new names of the chapters.
	 */
	public void updateChapterMenu( ) {
		mainWindow.updateChapterMenu( );
	}

	/**
	 * Updates the tree of the main window.
	 */
	public void updateTree( ) {
		mainWindow.updateTree( );
	}

	/**
	 * Reloads the panel of the main window currently being used.
	 */
	public void reloadPanel( ) {
		mainWindow.reloadPanel( );
	}
	
	/**
	 * Reloads the panel of the main window currently being used.
	 */
	public void reloadData( ) {
		mainWindow.reloadData( );
	}


	/**
	 * Returns the last window opened by the application.
	 * 
	 * @return Last window opened
	 */
	public Window peekWindow( ) {
		return mainWindow.peekWindow( );
	}

	/**
	 * Pushes a new window in the windows stack.
	 * 
	 * @param window
	 *            Window to push
	 */
	public void pushWindow( Window window ) {
		mainWindow.pushWindow( window );
	}

	/**
	 * Pops the last window pushed into the stack.
	 */
	public void popWindow( ) {
		mainWindow.popWindow( );
	}

	/**
	 * Shows a load dialog to select multiple files.
	 * 
	 * @param filter
	 *            File filter for the dialog
	 * @return Full path of the selected files, null if no files were selected
	 */
	public String[] showMultipleSelectionLoadDialog( FileFilter filter ) {
		return mainWindow.showMultipleSelectionLoadDialog( currentZipPath, filter );
	}

	/**
	 * Shows a dialog with the options "Yes" and "No", with the given title and text.
	 * 
	 * @param title
	 *            Title of the dialog
	 * @param message
	 *            Message of the dialog
	 * @return True if the "Yes" button was pressed, false otherwise
	 */
	public boolean showStrictConfirmDialog( String title, String message ) {
		return mainWindow.showStrictConfirmDialog( title, message );
	}
	
	/**
	 * Shows a dialog with the given set of options.
	 * 
	 * @param title
	 *            Title of the dialog
	 * @param message
	 *            Message of the dialog
	 * @param options
	 *            Array of strings containing the options of the dialog
	 * @return The index of the option selected, JOptionPane.CLOSED_OPTION if the dialog was closed.
	 */
	public int showOptionDialog( String title, String message, String[] options ) {
		return mainWindow.showOptionDialog( title, message, options );
	}

	/**
	 * Uses the GUI to show an input dialog.
	 * 
	 * @param title
	 *            Title of the dialog
	 * @param message
	 *            Message of the dialog
	 * @param defaultValue
	 *            Default value of the dialog
	 * @return String typed in the dialog, null if the cancel button was pressed
	 */
	public String showInputDialog( String title, String message, String defaultValue ) {
		return mainWindow.showInputDialog( title, message, defaultValue );
	}

	/**
	 * Uses the GUI to show an input dialog.
	 * 
	 * @param title
	 *            Title of the dialog
	 * @param message
	 *            Message of the dialog
	 * @param selectionValues
	 *            Possible selection values of the dialog
	 * @return Option selected in the dialog, null if the cancel button was pressed
	 */
	public String showInputDialog( String title, String message, Object[] selectionValues ) {
		return mainWindow.showInputDialog( title, message, selectionValues );
	}

	/**
	 * Uses the GUI to show an error dialog.
	 * 
	 * @param title
	 *            Title of the dialog
	 * @param message
	 *            Message of the dialog
	 */
	public void showErrorDialog( String title, String message ) {
		mainWindow.showErrorDialog( title, message );
	}
	
	public void showCustomizeGUIDialog (){
		CustomizeGUIDialog dialog = new CustomizeGUIDialog(this.adventureData);
	}

	public boolean isFloderLoaded( ) {
		return this.selectedChapter !=-1;
	}

	public String getEditorMinVersion(){
		return "0.7";
	}
	
	public String getEditorVersion(){
		return "0.7";
	}

	public void updateLOMLanguage( ) {
		this.adventureData.getLomController( ).updateLanguage( );
		
	}

	public void showAboutDialog( ) {
		
		try {
			JDialog dialog = new JDialog(Controller.getInstance( ).peekWindow( ), TextConstants.getText( "About" ), Dialog.ModalityType.APPLICATION_MODAL);
			dialog.getContentPane( ).setLayout( new BorderLayout() );
			File file = new File(LANGUAGE_DIR+"/"+ConfigData.getAboutFile( ));
			if (file.exists( )){
				JEditorPane pane =new JEditorPane();
				pane.setPage( file.toURI().toURL( ) );
				pane.setEditable( false );
				dialog.getContentPane( ).add( pane , BorderLayout.CENTER );
				//dialog.pack( );
				dialog.setSize( 275, 560 );
				pane.setMinimumSize( dialog.getSize( ) );
				Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
				dialog.setLocation( ( screenSize.width - dialog.getWidth( ) ) / 2, ( screenSize.height - dialog.getHeight( ) ) / 2 );
				dialog.setVisible( true );
			}
			
		} catch( IOException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public AssessmentProfilesDataControl getAssessmentController(){
		return this.adventureData.getAssessmentRulesListDataControl( );
	}
	
	public AdaptationProfilesDataControl getAdaptationController(){
		return this.adventureData.getAdaptationRulesListDataControl( );
	}

	public boolean isCommentaries( ) {
		return this.adventureData.isCommentaries( );
	}

	public void setCommentaries( boolean b ) {
		this.adventureData.setCommentaries( b );
		
	}
	
	public int getLanguage(){
		if (ConfigData.getLanguangeFile( ).equals( "en_EN.xml" )){
			return Controller.LANGUAGE_ENGLISH;
		} else if (ConfigData.getLanguangeFile( ).equals( "es_ES.xml" )){
			return Controller.LANGUAGE_SPANISH;
		} else
			return Controller.LANGUAGE_UNKNOWN;
		
	}
	
	public void setLanguage ( int language ){
		if (language == LANGUAGE_SPANISH && !ConfigData.getLanguangeFile( ).equals( "es_ES.xml" )){
			ConfigData.setLanguangeFile( "es_ES.xml", "aboutES.html", "img/Editor2D-Loading-Esp.png" );
			TextConstants.loadStrings( "es_ES.xml" );
			loadingScreen.setImage( getLoadingImage() );
			mainWindow.reloadData( );
		}
		else if (language == LANGUAGE_ENGLISH && !ConfigData.getLanguangeFile( ).equals( "en_EN.xml" )){
			ConfigData.setLanguangeFile( "en_EN.xml", "aboutEN.html", "img/Editor2D-Loading-Eng.png" );
			TextConstants.loadStrings( "en_EN.xml" );
			loadingScreen.setImage( getLoadingImage() );
			mainWindow.reloadData( );
		}
	}
	
	public String getLoadingImage (){
		return ConfigData.getLoadingImage( );
	}
}
