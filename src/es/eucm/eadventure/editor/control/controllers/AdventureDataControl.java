package es.eucm.eadventure.editor.control.controllers;

import java.util.List;

import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationProfileDataControl;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationProfilesDataControl;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentProfileDataControl;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentProfilesDataControl;
import es.eucm.eadventure.editor.control.controllers.lom.LOMDataControl;
import es.eucm.eadventure.common.data.adventure.AdventureData;
import es.eucm.eadventure.common.data.adventure.CustomCursor;
import es.eucm.eadventure.common.data.adventure.DescriptorData;
import es.eucm.eadventure.common.data.assessment.AssessmentProfile;
import es.eucm.eadventure.common.data.adaptation.AdaptationProfile;
import es.eucm.eadventure.editor.gui.assetchooser.AssetChooser;

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
		adventureData.setPlayerMode( DescriptorData.MODE_PLAYER_3RDPERSON );
		 
		lomController = new LOMDataControl();
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
	public int getGUIType( ) {
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
		this.adventureData.setTitle(title);
	}

	/**
	 * Sets the description of the adventure.
	 * 
	 * @param description
	 *            New description for the adventure
	 */
	public void setDescription( String description ) {
		adventureData.setDescription(description);
	}

	/**
	 * Sets the GUI type of the adventure.
	 * 
	 * @param guiType
	 *            New GUI type for the adventure
	 */
	public void setGUIType( int guiType ) {
		adventureData.setGUIType( guiType );
	}

	/**
	 * Sets the list of chapters of the adventure.
	 * 
	 * @param chapters
	 *            New chapters list for the adventure
	 */
	public void setChapters( List<Chapter> chapters ) {
		adventureData.setChapters(chapters);
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
		adventureData.setPlayerMode(playerMode);
	}
	
    public void addCursor(CustomCursor cursor){
        adventureData.getCursors().add( cursor );
    }
    
    public List<CustomCursor> getCursors(){
        return adventureData.getCursors();
    }
    
    public void addCursor(String type, String path){
        addCursor(new CustomCursor(type,path));
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
    	if (position>=0)
    		adventureData.getCursors().remove( position );
    }

    public void editCursorPath(int t){
    	if (isCursorTypeAllowed(t)){
    		String type = DescriptorData.getCursorTypeString(t);

    		String selectedCursor = null;
    		AssetChooser chooser = AssetsController.getAssetChooser( AssetsController.CATEGORY_CURSOR, AssetsController.FILTER_NONE );
    		int option = chooser.showAssetChooser( Controller.getInstance().peekWindow( ) );
    		//In case the asset was selected from the zip file
    		if( option == AssetChooser.ASSET_FROM_ZIP ) {
    			selectedCursor = chooser.getSelectedAsset( );
    		}

    		//In case the asset was not in the zip file: first add it
    		else if( option == AssetChooser.ASSET_FROM_OUTSIDE ) {
    			boolean added = AssetsController.addSingleAsset( AssetsController.CATEGORY_CURSOR, chooser.getSelectedFile( ).getAbsolutePath( ) );
    			if( added ) {
    				selectedCursor = chooser.getSelectedFile( ).getName( );
    			}
    		}

    		// If a file was selected
    		if( selectedCursor != null ) {
    			// Take the index of the selected asset
    			String[] assetFilenames = AssetsController.getAssetFilenames( AssetsController.CATEGORY_CURSOR );
    			String[] assetPaths = AssetsController.getAssetsList( AssetsController.CATEGORY_CURSOR );
    			int assetIndex = -1;
    			for( int i = 0; i < assetFilenames.length; i++ )
    				if( assetFilenames[i].equals( selectedCursor ) )
    					assetIndex = i;

    			boolean exists = false;
    			for (int i=0; i<adventureData.getCursors().size( ); i++){
    				if (adventureData.getCursors().get( i ).getType( ).equals( type )){
    					adventureData.getCursors().get( i ).setPath( assetPaths[assetIndex] );
    					exists=true;
    				}
    			}
    			if (!exists)
    				this.addCursor( type, assetPaths[assetIndex] );
    			Controller.getInstance( ).dataModified( );
    		}

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
		adventureData.setCommentaries(commentaries);
	}
}
