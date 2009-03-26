package es.eucm.eadventure.editor.control.controllers;

import java.util.List;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationProfileDataControl;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationProfilesDataControl;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentProfileDataControl;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentProfilesDataControl;
import es.eucm.eadventure.editor.control.controllers.metadata.lomes.LOMESDataControl;
import es.eucm.eadventure.editor.control.controllers.metadata.ims.IMSDataControl;
import es.eucm.eadventure.editor.control.controllers.metadata.lom.LOMDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.control.tools.general.assets.DeleteArrowTool;
import es.eucm.eadventure.editor.control.tools.general.assets.DeleteButtonTool;
import es.eucm.eadventure.editor.control.tools.general.assets.DeleteCursorTool;
import es.eucm.eadventure.editor.control.tools.general.assets.SelectArrowTool;
import es.eucm.eadventure.editor.control.tools.general.assets.SelectButtonTool;
import es.eucm.eadventure.editor.control.tools.general.assets.SelectCursorPathTool;
import es.eucm.eadventure.editor.control.tools.general.commontext.ChangeDescriptionTool;
import es.eucm.eadventure.editor.control.tools.general.commontext.ChangeTitleTool;
import es.eucm.eadventure.editor.control.tools.generic.ChangeBooleanValueTool;
import es.eucm.eadventure.editor.control.tools.generic.ChangeIntegerValueTool;
import es.eucm.eadventure.editor.gui.editdialogs.GUIStylesDialog;
import es.eucm.eadventure.common.data.adventure.AdventureData;
import es.eucm.eadventure.common.data.adventure.CustomArrow;
import es.eucm.eadventure.common.data.adventure.CustomButton;
import es.eucm.eadventure.common.data.adventure.CustomCursor;
import es.eucm.eadventure.common.data.adventure.DescriptorData;
import es.eucm.eadventure.common.data.assessment.AssessmentProfile;
import es.eucm.eadventure.common.data.adaptation.AdaptationProfile;

/**
 * This class holds all the information of the adventure, including the chapters and the configuration of the HUD.
 * 
 * @author Bruno Torijano Bueno
 */
public class AdventureDataControl {

	/**
	 * The whole data of the adventure
	 */
	private AdventureData adventureData;
	
	/**
	 * Controller for LOM data (only required when
	 * exporting games to LOM)
	 */
	private LOMDataControl lomController;
	
	/**
	 * Controller for IMS data (only required when exporting
	 * games to SCORM)
	 */
	private IMSDataControl imsController;
	
	/**
	 * Controller for LOM-ES data (require to export games as ODE)
	 */
	private LOMESDataControl lomesController;
	
	/**
	 * Assessment file data controller
	 */
	private AssessmentProfilesDataControl assessmentProfilesDataControl;
	
	/**
	 * Adaptation file data controller
	 */
	private AdaptationProfilesDataControl adaptationProfilesDataControl;

	/**
	 * Constructs the data control with the adventureData
	 */
	public AdventureDataControl( AdventureData data ){
		this( );
		adventureData = data;

		// add profiles subcontrollers
		for ( AssessmentProfile profile: data.getAssessmentProfiles() ){
			assessmentProfilesDataControl.getProfiles().add(new AssessmentProfileDataControl(profile))	;
		}
		
		for ( AdaptationProfile profile: data.getAdaptationProfiles() ){
			adaptationProfilesDataControl.getProfiles().add(new AdaptationProfileDataControl(profile))	;
		}
		

	}
	
	/**
	 * Empty constructor. Sets all values to null.
	 */
	public AdventureDataControl( ) {
		adventureData = new AdventureData();
		lomController = new LOMDataControl();
		imsController = new IMSDataControl();
		lomesController = new LOMESDataControl();
		assessmentProfilesDataControl = new AssessmentProfilesDataControl();
		adaptationProfilesDataControl = new AdaptationProfilesDataControl();
	}

	/**
	 * Constructor which creates an adventure data with default title and description, traditional GUI and one empty
	 * chapter (with a scene).
	 * 
	 * @param adventureTitle
	 *            Default title for the adventure
	 * @param chapterTitle
	 *            Default title for the chapter
	 * @param sceneId
	 *            Default identifier for the scene
	 */
	public AdventureDataControl( String adventureTitle, String chapterTitle, String sceneId, int playerMode ) {
		adventureData = new AdventureData( );
		adventureData.setTitle(adventureTitle);
		adventureData.setDescription("");
		adventureData.setGUIType( DescriptorData.GUI_CONTEXTUAL );
		adventureData.setPlayerMode( playerMode );
		adventureData.addChapter( new Chapter( chapterTitle, sceneId ) );
		lomController = new LOMDataControl();
		imsController = new IMSDataControl();
		assessmentProfilesDataControl = new AssessmentProfilesDataControl();
		adaptationProfilesDataControl = new AdaptationProfilesDataControl();
	}

	public AdventureDataControl( String adventureTitle, String chapterTitle, String sceneId ) {
		this( adventureTitle, chapterTitle, sceneId, DescriptorData.MODE_PLAYER_3RDPERSON );
	}

	/**
	 * Constructor gith given parameters.
	 * 
	 * @param title
	 *            Title of the adventure
	 * @param description
	 *            Description of the adventure
	 * @param guiType
	 *            Type of the GUI
	 * @param chapters
	 *            Chapters of the adventure
	 */
	public AdventureDataControl( String title, String description, List<Chapter> chapters ) {
		adventureData = new AdventureData();
		adventureData.setTitle(title);
		adventureData.setDescription(description);
		adventureData.setGUIType( DescriptorData.GUI_TRADITIONAL );
		adventureData.setChapters( chapters );
		adventureData.setGraphicConfig( DescriptorData.GRAPHICS_WINDOWED);
		adventureData.setPlayerMode( DescriptorData.MODE_PLAYER_3RDPERSON );
		 
		lomController = new LOMDataControl();
		imsController = new IMSDataControl();
	}

    public boolean isCursorTypeAllowed( String type ){
    	return isCursorTypeAllowed(DescriptorData.getCursorTypeIndex( type ));
    }

    public boolean isCursorTypeAllowed( int type ){
    	return DescriptorData.typeAllowed[adventureData.getGUIType()][type];
    }

	
	/**
	 * Returns the title of the adventure
	 * 
	 * @return Adventure's title
	 */
	public String getTitle( ) {
		return adventureData.getTitle();
	}

	/**
	 * Returns the description of the adventure.
	 * 
	 * @return Adventure's description
	 */
	public String getDescription( ) {
		return adventureData.getDescription();
	}

	/**
	 * Returns the GUI type of the adventure.
	 * 
	 * @return Adventure's GUI type
	 */
	public Integer getGUIType( ) {
		return adventureData.getGUIType();
	}

	/**
	 * Returns the list of chapters of the adventure.
	 * 
	 * @return Adventure's chapters list
	 */
	public List<Chapter> getChapters( ) {
		return adventureData.getChapters();
	}

	/**
	 * Sets the title of the adventure.
	 * 
	 * @param title
	 *            New title for the adventure
	 */
	public void setTitle( String title ) {
		Tool tool = new ChangeTitleTool(adventureData, title);
		Controller.getInstance().addTool(tool);
	}

	/**
	 * Sets the description of the adventure.
	 * 
	 * @param description
	 *            New description for the adventure
	 */
	public void setDescription( String description ) {
		Tool tool = new ChangeDescriptionTool(adventureData, description);
		Controller.getInstance().addTool(tool);
	}

	/**
	 * Shows the GUI style selection dialog.
	 */
	public void showGUIStylesDialog( ) {
		// Show the dialog
		GUIStylesDialog guiStylesDialog = new GUIStylesDialog( adventureData.getGUIType( ) );

		// If the new GUI style is different from the current, and valid, change the value
		int optionSelected = guiStylesDialog.getOptionSelected( );
		if( optionSelected != -1 ) {
			Tool tool =new ChangeIntegerValueTool( adventureData, optionSelected, "getGUIType", "setGUIType" );
			Controller.getInstance().addTool (tool);
		}
	}

	/**
	 * @return the playerMode
	 */
	public int getPlayerMode( ) {
		return adventureData.getPlayerMode();
	}

	/**
	 * @param playerMode the playerMode to set
	 */
	public void setPlayerMode( int playerMode ) {
		Tool tool = new ChangeIntegerValueTool(adventureData, playerMode, "getPlayerMode", "setPlayerMode");
		Controller.getInstance().addTool( tool );
	}
	
    public List<CustomCursor> getCursors(){
        return adventureData.getCursors();
    }
    
    public List<CustomButton> getButtons(){
        return adventureData.getButtons();
    }

    public List<CustomArrow> getArrows(){
    	return adventureData.getArrows();
    }
    
    public String getCursorPath(String type){
        for (CustomCursor cursor: adventureData.getCursors()){
            if (cursor.getType( ).equals( type )){
                return cursor.getPath( );
            }
        }
        return null;
    }
    
    public String getCursorPath(int type){
        return getCursorPath(DescriptorData.getCursorTypeString(type));
    }
    
    public void deleteCursor(int type){
    	String typeS = DescriptorData.getCursorTypeString(type);
    	int position=-1;
    	for (int i=0; i<adventureData.getCursors().size( ); i++){
    		if (adventureData.getCursors().get(i).getType( ).equals( typeS )){
    			position= i;break;
    		}
    	}
    	if (position>=0){
    		Controller.getInstance().addTool( new DeleteCursorTool(adventureData, position));
    	}
    }

    public void editCursorPath(int t){
    	try {
			Controller.getInstance().addTool( new SelectCursorPathTool( adventureData, t  ) );
		} catch (CloneNotSupportedException e) {
			ReportDialog.GenerateErrorReport(e, false, "Could not clone cursor-adventureData");
		}
    }

    public String getArrowPath(String type){
    	for (CustomArrow arrow: adventureData.getArrows()) {
    		if (arrow.getType().equals(type)) {
    			return arrow.getPath();
    		}
    	}
    	return null;
    }
    
    public void deleteArrow(String type) {
    	int position=-1;
    	for (int i=0; i<adventureData.getArrows().size( ); i++){
    		if (adventureData.getArrows().get(i).getType( ).equals( type )){
    			position= i;break;
    		}
    	}
    	if (position>=0){
    		Controller.getInstance().addTool( new DeleteArrowTool(adventureData, position));
    	}
    }
    
	/**
	 * @return the lomController
	 */
	public LOMDataControl getLomController( ) {
		return lomController;
	}

	/**
	 * @param lomController the lomController to set
	 */
	public void setLomController( LOMDataControl lomController ) {
		this.lomController = lomController;
	}
	
	/**
	 * @return the imsController
	 */
	public IMSDataControl getImsController() {
		return imsController;
	}
	
	/**
	 * @return the lomesController
	 */
	public LOMESDataControl getLOMESController(){
		return lomesController;
		}

	/**
	 * @param imsController the imsController to set
	 */
	public void setImsController(IMSDataControl imsController) {
		this.imsController = imsController;
	}

	/**
	 * @return the assessmentRulesListDataControl
	 */
	public AssessmentProfilesDataControl getAssessmentRulesListDataControl( ) {
		return assessmentProfilesDataControl;
	}

	/**
	 * @return the adaptationRulesListDataControl
	 */
	public AdaptationProfilesDataControl getAdaptationRulesListDataControl( ) {
		return adaptationProfilesDataControl;
	}

	public boolean isCommentaries() {
		return adventureData.isCommentaries();
	}

	public void setCommentaries(boolean commentaries) {
		Tool tool = new ChangeBooleanValueTool(adventureData, commentaries, "isCommentaries","setCommentaries");
		Controller.getInstance().addTool( tool );
	}
	
	public int getGraphicConfig() {
		return adventureData.getGraphicConfig();
	}
	 
	public void setGraphicConfig(int graphicConfig) {
		Tool tool = new ChangeIntegerValueTool(adventureData, graphicConfig, "getGraphicConfig","setGraphicConfig");
		Controller.getInstance().addTool( tool );
	}

	/**
	 * @return the adventureData
	 */
	public AdventureData getAdventureData() {
		return adventureData;
	}
	
	public String getButtonPath(String action, String type) {
		CustomButton button = new CustomButton(action, type, null);
		for (CustomButton cb : adventureData.getButtons()) {
			if (cb.equals(button))
				return cb.getPath();
		}
		return null;
	}

	public void deleteButton(String action, String type) {
		Controller.getInstance().addTool( new DeleteButtonTool( adventureData, action, type ) );
	}

	public void editButtonPath(String action, String type) {
		try {
			Controller.getInstance().addTool( new SelectButtonTool( adventureData, action, type ) );
		} catch (CloneNotSupportedException e) {
			ReportDialog.GenerateErrorReport(e, false, "Could not clone resources: buttons");
		}
	}
	
	public void editArrowPath(String type) {
		try {
			Controller.getInstance().addTool( new SelectArrowTool( adventureData, type ) );
		} catch (CloneNotSupportedException e) {
			ReportDialog.GenerateErrorReport(e, false, "Could not clone resources: arrows");
		}
	}
	
	public int getInventoryPosition() {
		return adventureData.getInventoryPosition();
	}
	
	public void setInventoryPosition(int inventoryPosition) {
		Controller.getInstance().addTool( new ChangeIntegerValueTool(adventureData, inventoryPosition, "getInventoryPosition", "setInventoryPosition"));
	}

	public int countAssetReferences(String assetPath) {
		return adventureData.countAssetReferences(assetPath);
	}


}
