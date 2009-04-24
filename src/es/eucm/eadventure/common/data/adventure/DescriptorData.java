package es.eucm.eadventure.common.data.adventure;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.Described;
import es.eucm.eadventure.common.data.Titled;

/**
 * Stores the description of the eAdventure file
 */
public class DescriptorData implements Cloneable, Described, Titled {
	
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
    
    public static final String TALK_BUTTON="talk";
    public static final String USE_GRAB_BUTTON="use-grab";
    public static final String EXAMINE_BUTTON="examine";
    
    public static final String HIGHLIGHTED_BUTTON="highlighted";
    public static final String NORMAL_BUTTON="normal";
    public static final String PRESSED_BUTTON="pressed";
    
    public static final String NORMAL_ARROW_RIGHT="normalright";
    public static final String NORMAL_ARROW_LEFT="normalleft";
    public static final String HIGHLIGHTED_ARROW_RIGHT="highlightedright";
    public static final String HIGHLIGHTED_ARROW_LEFT="highlightedleft";

    public static final int INVENTORY_NONE = 0;
    public static final int INVENTORY_TOP_BOTTOM = 1;
    public static final int INVENTORY_TOP = 2;
    public static final int INVENTORY_BOTTOM = 3;
	
    public static String getCursorTypeString (int index){
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
    
    public static int getCursorTypeIndex(String type){
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
    
    private static final String[] actionTypes = {TALK_BUTTON, USE_GRAB_BUTTON, EXAMINE_BUTTON};
    
    public static String[] getActionTypes() {
    	return actionTypes;
    }
    
    private static final String[] buttonTypes = {NORMAL_BUTTON, HIGHLIGHTED_BUTTON, PRESSED_BUTTON};
    
    public static String[] getButtonTypes() {
    	return buttonTypes;
    }
    
    private static final String[] arrowTypes = {NORMAL_ARROW_RIGHT, NORMAL_ARROW_LEFT, HIGHLIGHTED_ARROW_RIGHT, HIGHLIGHTED_ARROW_LEFT};
    
	public static String[] getArrowTypes() {
		return arrowTypes;
	}

    public static final boolean[][] typeAllowed = {
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
	 * Constant for traditional GUI.
	 */
	public static final int GUI_TRADITIONAL = 0;

	/**
	 * Constant for contextual GUI.
	 */
	public static final int GUI_CONTEXTUAL = 1;

	
	public static final int GRAPHICS_WINDOWED = 0;
	
	public static final int GRAPHICS_BLACKBKG = 1;
	
	public static final int GRAPHICS_FULLSCREEN = 2;

	
	/**
	 * Constant for 1st person adventure mode
	 */
	public static final int MODE_PLAYER_1STPERSON = 0;

	/**
	 * Constant for 3rd person adventure mode
	 */
	public static final int MODE_PLAYER_3RDPERSON = 1;

	/**
	 * Title of the adventure.
	 */
	protected String title;

	/**
	 * Description of the adventure.
	 */
	protected String description;

	/**
	 * Type of the GUI (Traditional or contextual)
	 */
	protected int guiType;

	
	/**
	 * Default graphic configuration (fullscreen, windowed, etc)
	 */
	private int graphicConfig;

	/**
	 * Adventure mode (1st person/3rd person)
	 */
	protected int playerMode;

    /**
     * Stores if the GUI's graphics are customized
     */
    protected boolean guiCustomized;
    
    /**
     * List of contents of the game
     */
    protected List<ChapterSummary> contents;
    
    /**
     * List of custom cursors
     */
    protected List<CustomCursor> cursors;
    
    /**
     * List of custom buttons
     */
    protected List<CustomButton> buttons;

    protected List<CustomArrow> arrows;
    
    /**
     * This flag tells if the adventure should show automatic commentaries.
     */
    protected boolean commentaries = false;

    /**
     * The name of the player, only used when reports are send by e-mail.
     */
    protected String playerName = "";
    
    protected Integer inventoryPosition = INVENTORY_TOP_BOTTOM;
       
    /**
     * Constructor
     */
    public DescriptorData( ) {
        contents = new ArrayList<ChapterSummary>( );
        cursors = new ArrayList<CustomCursor>();
        buttons = new ArrayList<CustomButton>();
        arrows = new ArrayList<CustomArrow>();
		title = null;
		description = null;
		guiType = -1;
		playerMode = MODE_PLAYER_1STPERSON;
		graphicConfig = GRAPHICS_WINDOWED;
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
	public Integer getGUIType( ) {
		return guiType;
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
	public void setGUIType( Integer guiType ) {
		this.guiType = guiType;
	}

	/**
	 * @return the playerMode
	 */
	public Integer getPlayerMode( ) {
		return playerMode;
	}

	/**
	 * @param playerMode the playerMode to set
	 */
	public void setPlayerMode( Integer playerMode ) {
		this.playerMode = playerMode;
	}
    
    /**
     * Returns whether the GUI is customized
     * @return True if the GUI is customized, false otherwise
     */
    public boolean isGUICustomized( ) {
        return guiCustomized;
    }

    /**
     * Sets the parameters of the GUI
     * @param guiType Type of the GUI
     * @param guiCustomized False if the GUI should be customized, false otherwise
     */
    public void setGUI( int guiType, boolean guiCustomized ) {
        this.guiType = guiType;
        this.guiCustomized = guiCustomized;
    }
    
    /**
     * Returns the list of chapters of the game
     * @return List of chapters of the game
     */
    public List<ChapterSummary> getChapterSummaries( ) {
        return contents;
    }

    /**
     * Adds a new chapter to the list
     * @param chapter Chapter to be added
     */
    public void addChapterSummary( ChapterSummary chapter ) {
        contents.add( chapter );
    }

    public void addCursor(CustomCursor cursor){
        cursors.add( cursor );
        this.guiCustomized=true;
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
    
    public void addButton(CustomButton button){
        buttons.add( button );
        this.guiCustomized=true;
    }
    
    public List<CustomButton> getButtons(){
        return buttons;
    }
    
    public void addButton(String action, String type, String path){
        addButton(new CustomButton(action, type,path));
    }
    
    public String getButtonPath(String action,String type){
        for (CustomButton button: buttons){
            if (button.getType( ).equals( type ) && button.getAction().equals(action)){
                return button.getPath( );
            }
        }
        return null;
    }
    
    public void addArrow(CustomArrow arrow) {
    	arrows.add(arrow);
    }
    
    public void addArrow(String type, String path) {
    	arrows.add(new CustomArrow(type, path));
    }
    
    public List<CustomArrow> getArrows() {
    	return arrows;
    }
    
    public String getArrowPath(String type){
        for (CustomArrow arrow: arrows){
            if (arrow.getType( ).equals( type )){
                return arrow.getPath( );
            }
        }
        return null;
    }

    
    public Boolean isCommentaries() {
        return commentaries;
    }

    public void setCommentaries(Boolean commentaries) {
        this.commentaries = commentaries;
    }
    
	public Integer getGraphicConfig() {
		return this.graphicConfig;
	}
	 
	public void setGraphicConfig(Integer graphicConfig) {
		this.graphicConfig = graphicConfig;
	}

	/**
	 * @return the playerName
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * @param playerName the playerName to set
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	public Integer getInventoryPosition() {
		return inventoryPosition;
	}
	
	public void setInventoryPosition(Integer inventoryPosition) {
		this.inventoryPosition = inventoryPosition;
	}
	
	public int countAssetReferences(String path) {
		int count = 0;
		for (CustomButton cb : buttons) {
			if (cb.getPath().equals(path))
				count++;
		}
		for (CustomArrow a : arrows) {
			if (a.getPath().equals(path))
				count++;
		}
		for (CustomCursor cc : cursors) {
			if (cc.getPath().equals(path))
				count++;
		}
		
		return count;
	}

	public Object clone() throws CloneNotSupportedException {
		DescriptorData dd = (DescriptorData) super.clone();
		if (buttons != null) {
			dd.buttons = new ArrayList<CustomButton>();
			for (CustomButton cb : buttons)
				dd.buttons.add((CustomButton) cb.clone());
		}
		dd.commentaries = commentaries;
		if (contents != null) {
			dd.contents = new ArrayList<ChapterSummary>();
			for (ChapterSummary cs : contents)
				dd.contents.add((ChapterSummary) cs.clone());
		}
		if (cursors != null) {
			dd.cursors = new ArrayList<CustomCursor>();
			for (CustomCursor cc : cursors)
				dd.cursors.add((CustomCursor) cc.clone());
		}
		if (arrows != null) {
			dd.arrows = new ArrayList<CustomArrow>();
			for (CustomArrow ca : arrows)
				dd.arrows.add((CustomArrow) ca.clone());
		}
		dd.description = (description != null ? new String(description) : null);
		dd.graphicConfig = graphicConfig;
		dd.guiCustomized = guiCustomized;
		dd.guiType = guiType;
		dd.playerMode = playerMode;
		dd.playerName = (playerName != null ? new String(playerName) : null);
		dd.title = (title != null ? new String(title) : null);
		dd.inventoryPosition = new Integer(inventoryPosition);
		return dd;
	}

}
