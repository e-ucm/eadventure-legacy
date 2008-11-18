package es.eucm.eadventure.common.data.adventure;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.Chapter;

/**
 * Stores the description of the eAdventure file
 */
public class AdventureData{
	/**
	 * Constant for traditional GUI.
	 */
	public static final int GUI_TRADITIONAL = 0;

	/**
	 * Constant for contextual GUI.
	 */
	public static final int GUI_CONTEXTUAL = 1;

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
	 * Adventure mode (1st person/3rd person)
	 */
	protected int playerMode;

    /**
     * Stores if the GUI's graphics are customized
     */
    private boolean guiCustomized;
    
    /**
     * List of chapters of the game
     */
    private List<Chapter> chapters;
    
    /**
     * List of custom cursors
     */
    private List<CustomCursor> cursors;

    /**
     * This flag tells if the adventure should show automatic commentaries.
     */
    private boolean commentaries = false;

    /**
     * Constructor
     */
    public AdventureData( ) {
        chapters = new ArrayList<Chapter>( );
        cursors = new ArrayList<CustomCursor>();
		title = null;
		description = null;
		guiType = -1;
		playerMode = MODE_PLAYER_1STPERSON;
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
    public List<Chapter> getChapters( ) {
        return chapters;
    }

    /**
     * Adds a new chapter to the list
     * @param chapter Chapter to be added
     */
    public void addChapter( Chapter chapter ) {
        chapters.add( chapter );
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
    
    public boolean isCommentaries() {
        return commentaries;
    }

    public void setCommentaries(boolean commentaries) {
        this.commentaries = commentaries;
    }
    
    
}
