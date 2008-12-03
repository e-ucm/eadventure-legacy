package es.eucm.eadventure.editor.control.controllers.general;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import es.eucm.eadventure.common.auxiliar.File;
import es.eucm.eadventure.common.data.adaptation.AdaptationRule;
import es.eucm.eadventure.common.data.adaptation.AdaptedState;
import es.eucm.eadventure.common.data.assessment.AssessmentRule;
import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationProfileDataControl;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationProfilesDataControl;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentProfileDataControl;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentProfilesDataControl;
import es.eucm.eadventure.editor.control.controllers.book.BooksListDataControl;
import es.eucm.eadventure.editor.control.controllers.character.NPCsListDataControl;
import es.eucm.eadventure.editor.control.controllers.character.PlayerDataControl;
import es.eucm.eadventure.editor.control.controllers.conversation.ConversationsListDataControl;
import es.eucm.eadventure.editor.control.controllers.cutscene.CutscenesListDataControl;
import es.eucm.eadventure.editor.control.controllers.item.ItemsListDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ScenesListDataControl;
import es.eucm.eadventure.editor.control.controllers.timer.TimersListDataControl;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

/**
 * Controller for the main element of the script.
 * 
 * @author Bruno Torijano Bueno
 */
public class ChapterDataControl extends DataControl {

	/**
	 * Chapter data contained.
	 */
	private Chapter chapter;

	/**
	 * Scenes list data controller.
	 */
	private ScenesListDataControl scenesListDataControl;

	/**
	 * Cutscenes list data controller.
	 */
	private CutscenesListDataControl cutscenesListDataControl;

	/**
	 * Books list data controller.
	 */
	private BooksListDataControl booksListDataControl;

	/**
	 * Items list data controller.
	 */
	private ItemsListDataControl itemsListDataControl;

	/**
	 * Player data controller.
	 */
	private PlayerDataControl playerDataControl;

	/**
	 * NPCs list data controller.
	 */
	private NPCsListDataControl npcsListDataControl;

	/**
	 * Conversations list data controller.
	 */
	private ConversationsListDataControl conversationsListDataControl;
	
	/**
	 * Timers data controller
	 */
	private TimersListDataControl timersListDataControl;
	
	/**
	 * Constructor.
	 * 
	 * @param chapter
	 *            Contained chapter data
	 */
	public ChapterDataControl( Chapter chapter ) {
		this.chapter = chapter;

		// Create the subcontrollers
		scenesListDataControl = new ScenesListDataControl( chapter.getScenes( ) );
		cutscenesListDataControl = new CutscenesListDataControl( chapter.getCutscenes( ) );
		booksListDataControl = new BooksListDataControl( chapter.getBooks( ) );
		itemsListDataControl = new ItemsListDataControl( chapter.getItems( ) );
		playerDataControl = new PlayerDataControl( chapter.getPlayer( ) );
		npcsListDataControl = new NPCsListDataControl( chapter.getCharacters( ) );
		conversationsListDataControl = new ConversationsListDataControl( chapter.getConversations( ) );
		timersListDataControl = new TimersListDataControl( chapter.getTimers( ) );
	}

	/**
	 * Returns the title of the chapter.
	 * 
	 * @return Chapter's title
	 */
	public String getTitle( ) {
		return chapter.getTitle( );
	}

	/**
	 * Returns the description of the chapter.
	 * 
	 * @return Chapter's description
	 */
	public String getDescription( ) {
		return chapter.getDescription( );
	}

	/**
	 * Returns the relative path to the assessment file of the chapter.
	 * 
	 * @return Relative path to the assessment file of the chapter
	 */
	public String getAssessmentPath( ) {
		return chapter.getAssessmentPath( );
	}

	/**
	 * Returns the relative path to the adaptation file of the chapter.
	 * 
	 * @return Relative path to the adaptation file of the chapter
	 */
	public String getAdaptationPath( ) {
		return chapter.getAdaptationPath( );
	}

	/**
	 * Returns the initial scene identifier of the chapter.
	 * 
	 * @return Initial scene identifier
	 */
	public String getInitialScene( ) {
		return chapter.getInitialScene( );
	}

	/**
	 * Returns the scenes list controller.
	 * 
	 * @return Scenes list controller
	 */
	public ScenesListDataControl getScenesList( ) {
		return scenesListDataControl;
	}

	/**
	 * Returns the cutscenes list controller.
	 * 
	 * @return Cutscenes list controller
	 */
	public CutscenesListDataControl getCutscenesList( ) {
		return cutscenesListDataControl;
	}

	/**
	 * Returns the books list controller.
	 * 
	 * @return Books list controller
	 */
	public BooksListDataControl getBooksList( ) {
		return booksListDataControl;
	}

	/**
	 * Returns the items list controller.
	 * 
	 * @return Items list controller
	 */
	public ItemsListDataControl getItemsList( ) {
		return itemsListDataControl;
	}

	/**
	 * Returns the player controller.
	 * 
	 * @return Player controller
	 */
	public PlayerDataControl getPlayer( ) {
		return playerDataControl;
	}

	/**
	 * Returns the NPCs list controller.
	 * 
	 * @return NPCs list controller
	 */
	public NPCsListDataControl getNPCsList( ) {
		return npcsListDataControl;
	}

	/**
	 * Returns the conversations list controller.
	 * 
	 * @return Conversations list controller
	 */
	public ConversationsListDataControl getConversationsList( ) {
		return conversationsListDataControl;
	}
	
	/**
	 * Returns the list of timers controller
	 * @return Timers list controller
	 */
	public TimersListDataControl getTimersList(){
		return this.timersListDataControl;
	}

	/**
	 * Sets the new title of the chapter.
	 * 
	 * @param title
	 *            Title of the chapter
	 */
	public void setTitle( String title ) {
		// If the value is different
		if( !title.equals( chapter.getTitle( ) ) ) {
			// Set the new title and modify the data
			chapter.setTitle( title );
			controller.updateTree( );
			controller.updateChapterMenu( );
			controller.dataModified( );
		}
	}

	/**
	 * Sets the new description of the chapter.
	 * 
	 * @param description
	 *            Description of the chapter
	 */
	public void setDescription( String description ) {
		// If the value is different
		if( !description.equals( chapter.getDescription( ) ) ) {
			// Set the new description and modify the data
			chapter.setDescription( description );
			controller.dataModified( );
		}
	}

	/**
	 * Sets the new assessment file for the chapter, showing a dialog to the user.
	 */
	public void setAssessmentPath( ) {
		// Get the list of assets from the ZIP file
		String[] assetFilenames = AssetsController.getAssetFilenames( AssetsController.CATEGORY_ASSESSMENT );
		String[] assetPaths = AssetsController.getAssetsList( AssetsController.CATEGORY_ASSESSMENT );

		// If the list of assets is empty, show an error message
		if( assetFilenames.length == 0 )
			controller.showErrorDialog( TextConstants.getText( "Resources.EditAsset" ), TextConstants.getText( "Resources.ErrorNoAssets" ) );

		// If not empty, select one of them
		else {
			// Let the user choose between the assets
			String selectedAsset = controller.showInputDialog( TextConstants.getText( "Resources.EditAsset" ), TextConstants.getText( "Resources.EditAssetMessage" ), assetFilenames );

			// If a file was selected
			if( selectedAsset != null ) {
				// Take the index of the selected asset
				int assetIndex = -1;
				for( int i = 0; i < assetFilenames.length; i++ )
					if( assetFilenames[i].equals( selectedAsset ) )
						assetIndex = i;

				// Store the data
				chapter.setAssessmentPath( assetPaths[assetIndex]);
				controller.dataModified( );
			}
		}
	}

	/**
	 * Sets the new adaptation file for the chapter, showing a dialog to the user.
	 */
	public void setAdaptationPath( ) {
		// Get the list of assets from the ZIP file
		String[] assetFilenames = AssetsController.getAssetFilenames( AssetsController.CATEGORY_ADAPTATION );
		String[] assetPaths = AssetsController.getAssetsList( AssetsController.CATEGORY_ADAPTATION );

		// If the list of assets is empty, show an error message
		if( assetFilenames.length == 0 )
			controller.showErrorDialog( TextConstants.getText( "Resources.EditAsset" ), TextConstants.getText( "Resources.ErrorNoAssets" ) );

		// If not empty, select one of them
		else {
			// Let the user choose between the assets
			String selectedAsset = controller.showInputDialog( TextConstants.getText( "Resources.EditAsset" ), TextConstants.getText( "Resources.EditAssetMessage" ), assetFilenames );

			// If a file was selected
			if( selectedAsset != null ) {
				// Take the index of the selected asset
				int assetIndex = -1;
				for( int i = 0; i < assetFilenames.length; i++ )
					if( assetFilenames[i].equals( selectedAsset ) )
						assetIndex = i;

				// Store the data
				chapter.setAdaptationPath( assetPaths[assetIndex] );
				controller.dataModified( );
			}
		}
	}

	/**
	 * Sets the new initial scene identifier for the chapter.
	 * 
	 * @param initialScene
	 *            Initial scene identifier
	 */
	public void setInitialScene( String initialScene ) {
		// If the value is different
		if( !initialScene.equals( chapter.getInitialScene( ) ) ) {
			// Set the new initial scene and modify the data
			chapter.setInitialScene( initialScene );
			controller.dataModified( );
		}
	}

	/**
	 * Deletes the assessment file reference of the chapter.
	 */
	public void deleteAssessmentPath( ) {
		// Set the assessment to an empty path
		if( !getAssessmentPath( ).equals( "" ) ) {
			chapter.setAssessmentPath("");
			controller.dataModified( );
		}
	}

	/**
	 * Deletes the adaptation file reference of the chapter.
	 */
	public void deleteAdaptationPath( ) {
		// Set the adaptation to an empty path
		if( !getAdaptationPath( ).equals( "" ) ) {
			chapter.setAdaptationPath("");
			controller.dataModified( );
		}
	}

	@Override
	public Object getContent( ) {
		return chapter;
	}

	@Override
	public int[] getAddableElements( ) {
		return new int[] {};
	}

	@Override
	public boolean canAddElement( int type ) {
		return false;
	}

	@Override
	public boolean canBeDeleted( ) {
		return false;
	}

	@Override
	public boolean canBeMoved( ) {
		return false;
	}

	@Override
	public boolean canBeRenamed( ) {
		return false;
	}

	@Override
	public boolean addElement( int type ) {
		return false;
	}

	@Override
	public boolean deleteElement( DataControl dataControl ) {
		return false;
	}

	@Override
	public boolean moveElementUp( DataControl dataControl ) {
		return false;
	}

	@Override
	public boolean moveElementDown( DataControl dataControl ) {
		return false;
	}

	@Override
	public boolean renameElement( ) {
		return false;
	}

	@Override
	public void updateFlagSummary( VarFlagSummary varFlagSummary ) {
		// First of all, clear the summary
		varFlagSummary.clear( );

		// Update the summary with the elements
		scenesListDataControl.updateFlagSummary( varFlagSummary );
		cutscenesListDataControl.updateFlagSummary( varFlagSummary );
		itemsListDataControl.updateFlagSummary( varFlagSummary );
		npcsListDataControl.updateFlagSummary( varFlagSummary );
		conversationsListDataControl.updateFlagSummary( varFlagSummary );
		//assessmentProfilesDataControl.updateFlagSummary( flagSummary );
		//adaptationProfilesDataControl.updateFlagSummary( flagSummary );
		timersListDataControl.updateFlagSummary( varFlagSummary );
	}

	@Override
	public boolean isValid( String currentPath, List<String> incidences ) {
		boolean valid = true;

		// Set the current path
		currentPath = getTitle( );
		String playerPath = currentPath + " >> " + TextConstants.getElementName( Controller.PLAYER );

		// Spread the call to the rest of te elements
		valid &= scenesListDataControl.isValid( currentPath, incidences );
		valid &= cutscenesListDataControl.isValid( currentPath, incidences );
		valid &= booksListDataControl.isValid( currentPath, incidences );
		valid &= itemsListDataControl.isValid( currentPath, incidences );
		valid &= playerDataControl.isValid( playerPath, incidences );
		valid &= npcsListDataControl.isValid( currentPath, incidences );
		valid &= conversationsListDataControl.isValid( currentPath, incidences );
		valid &= timersListDataControl.isValid( currentPath, incidences );

		return valid;
	}

	@Override
	public int countAssetReferences( String assetPath ) {
		int count = 0;

		// Add the references from the assessment and adaptation files
		if( getAssessmentPath( ).equals( assetPath ) )
			count++;
		if( getAdaptationPath( ).equals( assetPath ) )
			count++;

		// Add the references from the elements
		count += scenesListDataControl.countAssetReferences( assetPath );
		count += cutscenesListDataControl.countAssetReferences( assetPath );
		count += booksListDataControl.countAssetReferences( assetPath );
		count += itemsListDataControl.countAssetReferences( assetPath );
		count += playerDataControl.countAssetReferences( assetPath );
		count += npcsListDataControl.countAssetReferences( assetPath );
		count += conversationsListDataControl.countAssetReferences( assetPath );
		count += timersListDataControl.countAssetReferences( assetPath );

		return count;
	}

	@Override
	public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {
		// Add the references from the assessment and adaptation files
		if( getAssessmentPath( )!=null && !getAssessmentPath().equals( "" ) ){
			String assessmentPath = getAssessmentPath();
			for (String asset: assetPaths){
				boolean add = true;
				if (assessmentPath.equals( asset )){
					add = false; break;
				}
				if (add){
					int last = assetPaths.size( );
					assetPaths.add( last, assessmentPath );
					assetTypes.add( last, AssetsController.CATEGORY_ASSESSMENT );
				}
			}
		}
		if( getAdaptationPath( )!=null && !getAdaptationPath().equals( "" ) ){
			String adaptationPath = getAdaptationPath();
			for (String asset: assetPaths){
				boolean add = true;
				if (adaptationPath.equals( asset )){
					add = false; break;
				}
				if (add){
					int last = assetPaths.size( );
					assetPaths.add( last, adaptationPath );
					assetTypes.add( last, AssetsController.CATEGORY_ADAPTATION );
				}
			}
		}

		// Add the references from the elements
		scenesListDataControl.getAssetReferences( assetPaths, assetTypes );
		cutscenesListDataControl.getAssetReferences( assetPaths, assetTypes );
		booksListDataControl.getAssetReferences( assetPaths, assetTypes );
		itemsListDataControl.getAssetReferences( assetPaths, assetTypes );
		playerDataControl.getAssetReferences( assetPaths, assetTypes );
		npcsListDataControl.getAssetReferences( assetPaths, assetTypes );
		conversationsListDataControl.getAssetReferences( assetPaths, assetTypes );
		timersListDataControl.getAssetReferences( assetPaths, assetTypes );
	}
	
	@Override
	public void deleteAssetReferences( String assetPath ) {
		// Delete the references for the assessment and adaptation files
		if( getAssessmentPath( ).equals( assetPath ) )
			chapter.setAssessmentPath("");
		if( getAdaptationPath( ).equals( assetPath ) )
			chapter.setAdaptationPath("");

		// Delete the asset references in the chapter
		scenesListDataControl.deleteAssetReferences( assetPath );
		cutscenesListDataControl.deleteAssetReferences( assetPath );
		booksListDataControl.deleteAssetReferences( assetPath );
		itemsListDataControl.deleteAssetReferences( assetPath );
		playerDataControl.deleteAssetReferences( assetPath );
		npcsListDataControl.deleteAssetReferences( assetPath );
		conversationsListDataControl.deleteAssetReferences( assetPath );
		timersListDataControl.deleteAssetReferences( assetPath );
		//assessmentProfilesDataControl.deleteAssetReferences( assetPath );
		//adaptationProfilesDataControl.deleteAssetReferences( assetPath );
	}

	@Override
	public int countIdentifierReferences( String id ) {
		int count = 0;

		// Count the initial scene
		if( chapter.getInitialScene( ).equals( id ) )
			count++;

		// Spread the call to the rest of the elements
		count += scenesListDataControl.countIdentifierReferences( id );
		count += cutscenesListDataControl.countIdentifierReferences( id );
		count += itemsListDataControl.countIdentifierReferences( id );
		count += npcsListDataControl.countIdentifierReferences( id );
		count += conversationsListDataControl.countIdentifierReferences( id );
		count += timersListDataControl.countIdentifierReferences( id );

		return count;
	}

	@Override
	public void replaceIdentifierReferences( String oldId, String newId ) {
		// If the initial scene identifier has changed, update it
		if( chapter.getInitialScene( ).equals( oldId ) )
			chapter.setInitialScene( newId );

		// Spread the call to the rest of the elements
		scenesListDataControl.replaceIdentifierReferences( oldId, newId );
		cutscenesListDataControl.replaceIdentifierReferences( oldId, newId );
		itemsListDataControl.replaceIdentifierReferences( oldId, newId );
		npcsListDataControl.replaceIdentifierReferences( oldId, newId );
		conversationsListDataControl.replaceIdentifierReferences( oldId, newId );
		timersListDataControl.replaceIdentifierReferences( oldId, newId );
	}

	@Override
	public void deleteIdentifierReferences( String id ) {
		// If the initial scene has been deleted, change the value to the first one in the scenes list
		if( chapter.getInitialScene( ).equals( id ) )
			chapter.setInitialScene( controller.getIdentifierSummary( ).getGeneralSceneIds( )[0] );

		// Spread the call to the rest of the elements
		scenesListDataControl.deleteIdentifierReferences( id );
		cutscenesListDataControl.deleteIdentifierReferences( id );
		itemsListDataControl.deleteIdentifierReferences( id );
		npcsListDataControl.deleteIdentifierReferences( id );
		conversationsListDataControl.deleteIdentifierReferences( id );
		timersListDataControl.deleteIdentifierReferences( id );
	}

	@Override
	public boolean canBeDuplicated( ) {
		return true;
	}

}
