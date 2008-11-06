package es.eucm.eadventure.adventureeditor.data.adventuredata;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.adventureeditor.control.Controller;
import es.eucm.eadventure.adventureeditor.control.controllers.AssetsController;
import es.eucm.eadventure.adventureeditor.control.controllers.adaptation.AdaptationProfilesDataControl;
import es.eucm.eadventure.adventureeditor.control.controllers.assessment.AssessmentProfilesDataControl;
import es.eucm.eadventure.adventureeditor.control.controllers.lom.LOMDataControl;
import es.eucm.eadventure.adventureeditor.data.chapterdata.Chapter;
import es.eucm.eadventure.adventureeditor.data.adventuredata.CustomCursor;
import es.eucm.eadventure.adventureeditor.gui.assetchooser.AssetChooser;

/**
 * This class holds all the information of the adventure, including the chapters and the configuration of the HUD.
 * 
 * @author Bruno Torijano Bueno
 */
public class AdventureDataControl {

	/**
	 * Constant for traditional GUI.
	 */
	public static final int GUI_TRADITIONAL = 0;

	/**
	 * Constant for contextual GUI.
	 */
	public static final int GUI_CONTEXTUAL = 1;

	public static final int PLAYER_3RDPERSON = 0;

	public static final int PLAYER_1STPERSON = 1;
	
	
	public static final String DEFAULT_CURSOR="default";
    public static final String USE_CURSOR="use";
    public static final String LOOK_CURSOR="look";
    public static final String EXAMINE_CURSOR="examine";
    public static final String TALK_CURSOR="talk";
    public static final String GRAB_CURSOR="grab";
    public static final String GIVE_CURSOR="give";
    public static final String EXIT_CURSOR="exit";
    public static final String CURSOR_OVER="over";
    public static final String CURSOR_ACTION="action";
	
    private static String getCursorTypeString (int index){
    	switch(index){
    		case 0:return DEFAULT_CURSOR;
    		case 1:return CURSOR_OVER;
    		case 2:return CURSOR_ACTION;
    		case 3:return EXIT_CURSOR;
    		case 4:return USE_CURSOR;
    		case 5:return LOOK_CURSOR;
    		case 6:return EXAMINE_CURSOR;
    		case 7:return TALK_CURSOR;
    		case 8:return GRAB_CURSOR;
    		case 9:return GIVE_CURSOR;
    		default: return null;
    	}
    }
    
    private static int getCursorTypeIndex(String type){
    	if (type.equals( DEFAULT_CURSOR )){
    		return 0;
    	}else if (type.equals( USE_CURSOR )){
    		return 4;
    	}else if (type.equals( LOOK_CURSOR )){
    		return 5;
    	}else if (type.equals( EXAMINE_CURSOR )){
    		return 6;
    	}else if (type.equals( TALK_CURSOR )){
    		return 7;
    	}else if (type.equals( GRAB_CURSOR )){
    		return 8;
    	}else if (type.equals( GIVE_CURSOR )){
    		return 9;
    	}else if (type.equals( EXIT_CURSOR )){
    		return 3;
    	}else if (type.equals( CURSOR_OVER )){
    		return 1;
    	}else if (type.equals( CURSOR_ACTION )){
    		return 2;
    	}else{
    		return -1;
    	}
    }
    
    private static final String[] cursorTypes ={DEFAULT_CURSOR, CURSOR_OVER, CURSOR_ACTION,EXIT_CURSOR, USE_CURSOR, LOOK_CURSOR, EXAMINE_CURSOR, TALK_CURSOR, GRAB_CURSOR, GIVE_CURSOR }; 

    public static String[] getCursorTypes(){
    	return cursorTypes;
    }
    
    private static final boolean[][] typeAllowed = {
    	//TRADITIONAL GUI
    	{
    		true, false, false, true, true,true,true,true,true,true
    	},
    	//CONTEXTUAL GUI
    	{
    		true, true, true, true, false,false,false,false,false,false
    	}	
    };
    
	/**
	 * Title of the adventure.
	 */
	private String title;

	/**
	 * Description of the adventure.
	 */
	private String description;

	/**
	 * Type of the GUI (Traditional or contextual)
	 */
	private int guiType;

	private int playerMode;

	/**
	 * List of chapters of the adventure.
	 */
	private List<Chapter> chapters;
	
	private List<CustomCursor> cursors;
	
	private LOMDataControl lomController;
	
	/**
	 * This flag tells if the adventure should show automatic commentaries.
	 */
	private boolean commentaries = false;
	
	/**
	 * Assessment file data controller
	 */
	private AssessmentProfilesDataControl assessmentProfilesDataControl;
	
	/**
	 * Adaptation file data controller
	 */
	private AdaptationProfilesDataControl adaptationProfilesDataControl;

	/**
	 * Empty constructor. Sets all values to null.
	 */
	public AdventureDataControl( ) {
		title = null;
		description = null;
		guiType = -1;
		chapters = null;
		playerMode = PLAYER_3RDPERSON;
		 cursors = new ArrayList<CustomCursor>();
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
		title = adventureTitle;
		description = "";
		guiType = GUI_CONTEXTUAL;
		this.playerMode = playerMode;
		 cursors = new ArrayList<CustomCursor>();

		chapters = new ArrayList<Chapter>( );
		chapters.add( new Chapter( chapterTitle, sceneId ) );
		 lomController = new LOMDataControl();
		 assessmentProfilesDataControl = new AssessmentProfilesDataControl();
		 adaptationProfilesDataControl = new AdaptationProfilesDataControl();
	}

	public AdventureDataControl( String adventureTitle, String chapterTitle, String sceneId ) {
		this( adventureTitle, chapterTitle, sceneId, PLAYER_3RDPERSON );
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
		this.title = title;
		this.description = description;
		guiType = GUI_TRADITIONAL;
		this.chapters = chapters;
		this.playerMode = PLAYER_3RDPERSON;
		 cursors = new ArrayList<CustomCursor>();
		 
		 //TODO
		 lomController = new LOMDataControl();
	}

    public boolean isCursorTypeAllowed( String type ){
    	return isCursorTypeAllowed(AdventureDataControl.getCursorTypeIndex( type ));
    }

    public boolean isCursorTypeAllowed( int type ){
    	return typeAllowed[guiType][type];
    }

	
	/**
	 * Returns the title of the adventure
	 * 
	 * @return Adventure's title
	 */
	public String getTitle( ) {
		return title;
	}

	/**
	 * Returns the description of the adventure.
	 * 
	 * @return Adventure's description
	 */
	public String getDescription( ) {
		return description;
	}

	/**
	 * Returns the GUI type of the adventure.
	 * 
	 * @return Adventure's GUI type
	 */
	public int getGUIType( ) {
		return guiType;
	}

	/**
	 * Returns the list of chapters of the adventure.
	 * 
	 * @return Adventure's chapters list
	 */
	public List<Chapter> getChapters( ) {
		return chapters;
	}

	/**
	 * Sets the title of the adventure.
	 * 
	 * @param title
	 *            New title for the adventure
	 */
	public void setTitle( String title ) {
		this.title = title;
	}

	/**
	 * Sets the description of the adventure.
	 * 
	 * @param description
	 *            New description for the adventure
	 */
	public void setDescription( String description ) {
		this.description = description;
	}

	/**
	 * Sets the GUI type of the adventure.
	 * 
	 * @param guiType
	 *            New GUI type for the adventure
	 */
	public void setGUIType( int guiType ) {
		this.guiType = guiType;
	}

	/**
	 * Sets the list of chapters of the adventure.
	 * 
	 * @param chapters
	 *            New chapters list for the adventure
	 */
	public void setChapters( List<Chapter> chapters ) {
		this.chapters = chapters;
	}

	/**
	 * @return the playerMode
	 */
	public int getPlayerMode( ) {
		return playerMode;
	}

	/**
	 * @param playerMode the playerMode to set
	 */
	public void setPlayerMode( int playerMode ) {
		this.playerMode = playerMode;
	}
	
    public void addCursor(CustomCursor cursor){
        cursors.add( cursor );
    }
    
    public List<CustomCursor> getCursors(){
        return cursors;
    }
    
    public void addCursor(String type, String path){
        addCursor(new CustomCursor(type,path));
    }
    
    public String getCursorPath(String type){
        for (CustomCursor cursor: cursors){
            if (cursor.getType( ).equals( type )){
                return cursor.getPath( );
            }
        }
        return null;
    }
    
    public String getCursorPath(int type){
        return getCursorPath(getCursorTypeString(type));
    }
    
    public void deleteCursor(int type){
    	String typeS = getCursorTypeString(type);
    	int position=-1;
    	for (int i=0; i<cursors.size( ); i++){
    		if (cursors.get(i).getType( ).equals( typeS )){
    			position= i;break;
    		}
    	}
    	if (position>=0)
    		cursors.remove( position );
    }

    public void editCursorPath(int t){
    	if (isCursorTypeAllowed(t)){
    		String type = getCursorTypeString(t);

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
    			for (int i=0; i<cursors.size( ); i++){
    				if (cursors.get( i ).getType( ).equals( type )){
    					cursors.get( i ).setPath( assetPaths[assetIndex] );
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
		return commentaries;
	}

	public void setCommentaries(boolean commentaries) {
		this.commentaries = commentaries;
	}
}
