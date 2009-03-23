package es.eucm.eadventure.editor.control.controllers.general;

import java.util.List;

import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.atrezzo.AtrezzoListDataControl;
import es.eucm.eadventure.editor.control.controllers.book.BooksListDataControl;
import es.eucm.eadventure.editor.control.controllers.character.NPCsListDataControl;
import es.eucm.eadventure.editor.control.controllers.character.PlayerDataControl;
import es.eucm.eadventure.editor.control.controllers.conversation.ConversationsListDataControl;
import es.eucm.eadventure.editor.control.controllers.cutscene.CutscenesListDataControl;
import es.eucm.eadventure.editor.control.controllers.globalstate.GlobalStateListDataControl;
import es.eucm.eadventure.editor.control.controllers.item.ItemsListDataControl;
import es.eucm.eadventure.editor.control.controllers.macro.MacroListDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ScenesListDataControl;
import es.eucm.eadventure.editor.control.controllers.timer.TimersListDataControl;
import es.eucm.eadventure.editor.control.tools.general.chapters.ChangeSelectedProfileTool;
import es.eucm.eadventure.editor.control.tools.general.chapters.SetNoSelectedProfileTool;
import es.eucm.eadventure.editor.control.tools.general.commontext.ChangeDescriptionTool;
import es.eucm.eadventure.editor.control.tools.general.commontext.ChangeTargetIdTool;
import es.eucm.eadventure.editor.control.tools.general.commontext.ChangeTitleTool;
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
	 * Atrezzo items list data controller.
	 */
	private AtrezzoListDataControl atrezzoListDataControl;	
	
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
	 * List of Global States
	 */
	private GlobalStateListDataControl globalStatesListDataControl;
	
	/**
	 * List of macro
	 */
	private MacroListDataControl macrosListDataControl;

	
	/**
	 * Constructor.
	 * 
	 * @param chapter
	 *            Contained chapter data
	 */
	public ChapterDataControl( Chapter chapter ) {
		update ( chapter );
	}
	
	/**
	 * Updates the data contained in the data control with a new chapter. 
	 * This method is essential for some undo/redo tools
	 * @param chaper
	 */
	public void update ( Chapter chapter ){
		this.chapter = chapter;

		// Create the subcontrollers
		playerDataControl = new PlayerDataControl( chapter.getPlayer( ) );
		scenesListDataControl = new ScenesListDataControl( chapter.getScenes( ), this.getPlayer().getPreviewImage());
		cutscenesListDataControl = new CutscenesListDataControl( chapter.getCutscenes( ) );
		booksListDataControl = new BooksListDataControl( chapter.getBooks( ) );
		itemsListDataControl = new ItemsListDataControl( chapter.getItems( ) );
		atrezzoListDataControl = new AtrezzoListDataControl(chapter.getAtrezzo());
		npcsListDataControl = new NPCsListDataControl( chapter.getCharacters( ) );
		conversationsListDataControl = new ConversationsListDataControl( chapter.getConversations( ) );
		timersListDataControl = new TimersListDataControl( chapter.getTimers( ) );
		globalStatesListDataControl = new GlobalStateListDataControl( chapter.getGlobalStates() );
		macrosListDataControl = new MacroListDataControl ( chapter.getMacros( ) );		
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
		return chapter.getTargetId( );
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
	 * Returns the atrezzo items list controller.
	 * 
	 * @return Atrezzo list controller
	 */
	public AtrezzoListDataControl getAtrezzoList( ) {
		return atrezzoListDataControl;
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
		ChangeTitleTool tool = new ChangeTitleTool(chapter, title);
		controller.addTool(tool);
	}

	/**
	 * Sets the new description of the chapter.
	 * 
	 * @param description
	 *            Description of the chapter
	 */
	public void setDescription( String description ) {
		ChangeDescriptionTool tool = new ChangeDescriptionTool(chapter, description);
		controller.addTool(tool);
	}

	/**
	 * Sets the new assessment file for the chapter, showing a dialog to the user.
	 */
	public void setAssessmentPath( ) {
		Controller.getInstance().addTool(new ChangeSelectedProfileTool(chapter, ChangeSelectedProfileTool.MODE_ASSESSMENT));
	}

	/**
	 * Sets the new adaptation file for the chapter, showing a dialog to the user.
	 */
	public void setAdaptationPath( ) {
		Controller.getInstance().addTool(new ChangeSelectedProfileTool(chapter, ChangeSelectedProfileTool.MODE_ADAPTATION));
	}

	/**
	 * Sets the new initial scene identifier for the chapter.
	 * 
	 * @param initialScene
	 *            Initial scene identifier
	 */
	public void setInitialScene( String initialScene ) {
		Controller.getInstance().addTool(new ChangeTargetIdTool(chapter, initialScene));
	}

	/**
	 * Deletes the assessment file reference of the chapter.
	 */
	public void deleteAssessmentPath( ) {
		Controller.getInstance().addTool(new SetNoSelectedProfileTool(chapter, SetNoSelectedProfileTool.MODE_ASSESSMENT ));
	}

	/**
	 * Deletes the adaptation file reference of the chapter.
	 */
	public void deleteAdaptationPath( ) {
		Controller.getInstance().addTool(new SetNoSelectedProfileTool(chapter, SetNoSelectedProfileTool.MODE_ADAPTATION ));
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
	public boolean deleteElement( DataControl dataControl , boolean askConfirmation) {
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
	public String renameElement( String name ) {
		return null;
	}

	@Override
	public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {
		// First of all, clear the summary
		varFlagSummary.clear( );

		// Update the summary with the elements
		scenesListDataControl.updateVarFlagSummary( varFlagSummary );
		cutscenesListDataControl.updateVarFlagSummary( varFlagSummary );
		itemsListDataControl.updateVarFlagSummary( varFlagSummary );
		npcsListDataControl.updateVarFlagSummary( varFlagSummary );
		conversationsListDataControl.updateVarFlagSummary( varFlagSummary );
		//assessmentProfilesDataControl.updateVarFlagSummary( varFlagSummary );
		//adaptationProfilesDataControl.updateFlagSummary( flagSummary );
		timersListDataControl.updateVarFlagSummary( varFlagSummary );
		globalStatesListDataControl.updateVarFlagSummary( varFlagSummary );
		macrosListDataControl.updateVarFlagSummary( varFlagSummary );
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
		valid &= atrezzoListDataControl.isValid(currentPath, incidences);
		valid &= playerDataControl.isValid( playerPath, incidences );
		valid &= npcsListDataControl.isValid( currentPath, incidences );
		valid &= conversationsListDataControl.isValid( currentPath, incidences );
		valid &= timersListDataControl.isValid( currentPath, incidences );
		valid &= globalStatesListDataControl.isValid( currentPath, incidences );
		valid &= macrosListDataControl.isValid( currentPath, incidences );

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
		count += atrezzoListDataControl.countAssetReferences(assetPath);
		count += playerDataControl.countAssetReferences( assetPath );
		count += npcsListDataControl.countAssetReferences( assetPath );
		count += conversationsListDataControl.countAssetReferences( assetPath );
		count += timersListDataControl.countAssetReferences( assetPath );
		count += globalStatesListDataControl.countAssetReferences( assetPath );
		count += macrosListDataControl.countAssetReferences( assetPath );

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
		atrezzoListDataControl.getAssetReferences(assetPaths, assetTypes);
		playerDataControl.getAssetReferences( assetPaths, assetTypes );
		npcsListDataControl.getAssetReferences( assetPaths, assetTypes );
		conversationsListDataControl.getAssetReferences( assetPaths, assetTypes );
		timersListDataControl.getAssetReferences( assetPaths, assetTypes );
		globalStatesListDataControl.getAssetReferences( assetPaths, assetTypes );
		macrosListDataControl.getAssetReferences( assetPaths, assetTypes );
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
		atrezzoListDataControl.deleteAssetReferences(assetPath);
		playerDataControl.deleteAssetReferences( assetPath );
		npcsListDataControl.deleteAssetReferences( assetPath );
		conversationsListDataControl.deleteAssetReferences( assetPath );
		timersListDataControl.deleteAssetReferences( assetPath );
		globalStatesListDataControl.deleteAssetReferences( assetPath );
		macrosListDataControl.deleteAssetReferences( assetPath );
		//assessmentProfilesDataControl.deleteAssetReferences( assetPath );
		//adaptationProfilesDataControl.deleteAssetReferences( assetPath );
	}

	@Override
	public int countIdentifierReferences( String id ) {
		int count = 0;

		// Count the initial scene
		if( chapter.getTargetId( ).equals( id ) )
			count++;

		// Spread the call to the rest of the elements
		count += scenesListDataControl.countIdentifierReferences( id );
		count += cutscenesListDataControl.countIdentifierReferences( id );
		count += itemsListDataControl.countIdentifierReferences( id );
		count += atrezzoListDataControl.countIdentifierReferences(id);
		count += npcsListDataControl.countIdentifierReferences( id );
		count += conversationsListDataControl.countIdentifierReferences( id );
		count += timersListDataControl.countIdentifierReferences( id );
		count += globalStatesListDataControl.countIdentifierReferences( id );
		count += macrosListDataControl.countIdentifierReferences( id );

		return count;
	}

	@Override
	public void replaceIdentifierReferences( String oldId, String newId ) {
		// If the initial scene identifier has changed, update it
		if( chapter.getTargetId( ).equals( oldId ) )
			chapter.setTargetId( newId );

		// Spread the call to the rest of the elements
		scenesListDataControl.replaceIdentifierReferences( oldId, newId );
		cutscenesListDataControl.replaceIdentifierReferences( oldId, newId );
		itemsListDataControl.replaceIdentifierReferences( oldId, newId );
		atrezzoListDataControl.replaceIdentifierReferences(oldId, newId);
		npcsListDataControl.replaceIdentifierReferences( oldId, newId );
		conversationsListDataControl.replaceIdentifierReferences( oldId, newId );
		timersListDataControl.replaceIdentifierReferences( oldId, newId );
		globalStatesListDataControl.replaceIdentifierReferences( oldId, newId );
		macrosListDataControl.replaceIdentifierReferences( oldId, newId );
	}

	
	
	@Override
	public void deleteIdentifierReferences( String id ) {
		// If the initial scene has been deleted, change the value to the first one in the scenes list
		if( chapter.getTargetId( ).equals( id ) )
			chapter.setTargetId( controller.getIdentifierSummary( ).getGeneralSceneIds( )[0] );

		// Spread the call to the rest of the elements
		scenesListDataControl.deleteIdentifierReferences( id );
		cutscenesListDataControl.deleteIdentifierReferences( id );
		itemsListDataControl.deleteIdentifierReferences( id );
		atrezzoListDataControl.deleteIdentifierReferences(id);
		npcsListDataControl.deleteIdentifierReferences( id );
		conversationsListDataControl.deleteIdentifierReferences( id );
		timersListDataControl.deleteIdentifierReferences( id );
		globalStatesListDataControl.deleteIdentifierReferences( id );
		macrosListDataControl.deleteIdentifierReferences( id );
	}

	@Override
	public boolean canBeDuplicated( ) {
		return true;
	}

	/**
	 * @return the globalStatesListDataControl
	 */
	public GlobalStateListDataControl getGlobalStatesListDataControl() {
		return globalStatesListDataControl;
	}

	/**
	 * @return the globalStatesListDataControl
	 */
	public MacroListDataControl getMacrosListDataControl() {
		return macrosListDataControl;
	}

	@Override
	public void recursiveSearch() {
		check(this.getAdaptationPath(), TextConstants.getText("Search.AdaptationPath"));
		check(this.getAssessmentPath(), TextConstants.getText("Search.AssessmentPath"));
		check(this.getDescription(), TextConstants.getText("Search.Description"));
		check(this.getInitialScene(), TextConstants.getText("Search.InitialScene"));
		check(this.getTitle(), TextConstants.getText("Search.Title"));
		this.getAtrezzoList().recursiveSearch();
		this.getBooksList().recursiveSearch();
		this.getConversationsList().recursiveSearch();
		this.getCutscenesList().recursiveSearch();
		this.getGlobalStatesListDataControl().recursiveSearch();
		this.getItemsList().recursiveSearch();
		this.getMacrosListDataControl().recursiveSearch();
		this.getNPCsList().recursiveSearch();
		this.getPlayer().recursiveSearch();
		this.getScenesList().recursiveSearch();
		this.getTimersList().recursiveSearch();
	}
}
