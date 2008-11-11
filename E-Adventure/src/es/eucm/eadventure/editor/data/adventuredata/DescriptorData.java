package es.eucm.eadventure.editor.data.adventuredata;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapterdata.Chapter;

/**
 * This class holds all the information of the adventure, including the chapters and the configuration of the HUD.
 * 
 * @author Bruno Torijano Bueno
 */
public class DescriptorData {

	/**
	 * Constant for traditional GUI.
	 */
	public static final int GUI_TRADITIONAL = 0;

	/**
	 * Constant for contextual GUI.
	 */
	public static final int GUI_CONTEXTUAL = 1;

	public static final int PLAYER_VISIBLE = 0;

	public static final int PLAYER_TRANSPARENT = 1;

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
	 * Empty constructor. Sets all values to null.
	 */
	public DescriptorData( ) {
		title = null;
		description = null;
		guiType = -1;
		playerMode = PLAYER_VISIBLE;
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
	public DescriptorData( String adventureTitle, String sceneId, int playerMode ) {
		title = adventureTitle;
		description = "";
		guiType = GUI_TRADITIONAL;
		this.playerMode = playerMode;

	}

	public DescriptorData( String adventureTitle, String sceneId ) {
		this( adventureTitle, sceneId, PLAYER_VISIBLE );
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
}
