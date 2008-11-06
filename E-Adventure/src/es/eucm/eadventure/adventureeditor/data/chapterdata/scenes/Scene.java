package es.eucm.eadventure.adventureeditor.data.chapterdata.scenes;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.adventureeditor.data.chapterdata.ElementReference;
import es.eucm.eadventure.adventureeditor.data.chapterdata.Exit;
import es.eucm.eadventure.adventureeditor.data.chapterdata.elements.ActiveArea;
import es.eucm.eadventure.adventureeditor.data.chapterdata.elements.Barrier;

/**
 * This class holds the data for a scene in eAdventure
 */
public class Scene extends GeneralScene {

	/**
	 * Default X position for the player
	 */
	private int defaultX;

	/**
	 * Default Y position for the player
	 */
	private int defaultY;

	/**
	 * List of exits
	 */
	private List<Exit> exits;

	/**
	 * List of item references
	 */
	private List<ElementReference> itemReferences;

	/**
	 * List of character references
	 */
	private List<ElementReference> characterReferences;
	
	/**
	 * List of active areas
	 */
	private List<ActiveArea> activeAreas;
	
    /**
     * List of barriers
     */
    private List<Barrier> barriers;

	/**
	 * Creates a new Scene
	 * 
	 * @param id
	 *            the scene's id
	 */
	public Scene( String id ) {
		super( GeneralScene.SCENE, id );

		defaultX = Integer.MIN_VALUE;
		defaultY = Integer.MIN_VALUE;
		exits = new ArrayList<Exit>( );
		itemReferences = new ArrayList<ElementReference>( );
		characterReferences = new ArrayList<ElementReference>( );
		activeAreas = new ArrayList<ActiveArea>();
		barriers = new ArrayList<Barrier>();
	}

	/**
	 * Returns whether this scene has a default position for the player
	 * 
	 * @return true if this scene has a default position for the player, false otherwise
	 */
	public boolean hasDefaultPosition( ) {
		return ( defaultX != Integer.MIN_VALUE ) && ( defaultY != Integer.MIN_VALUE );
	}

	/**
	 * Returns the horizontal coordinate of the default position for the player
	 * 
	 * @return the horizontal coordinate of the default position for the player
	 */
	public int getDefaultX( ) {
		return defaultX;
	}

	/**
	 * Returns the vertical coordinate of the default position for the player
	 * 
	 * @return the vertical coordinate of the default position for the player
	 */
	public int getDefaultY( ) {
		return defaultY;
	}

	/**
	 * Returns the list of exits
	 * 
	 * @return the list of exits
	 */
	public List<Exit> getExits( ) {
		return exits;
	}

	/**
	 * Returns the list of item references
	 * 
	 * @return the list of item references
	 */
	public List<ElementReference> getItemReferences( ) {
		return itemReferences;
	}

	/**
	 * Returns the list of character references
	 * 
	 * @return the list of character references
	 */
	public List<ElementReference> getCharacterReferences( ) {
		return characterReferences;
	}

	/**
	 * Changes the player's default position
	 * 
	 * @param defaultX
	 *            the horizontal coordinate
	 * @param defaultY
	 *            the vertical coordinate
	 */
	public void setDefaultPosition( int defaultX, int defaultY ) {
		this.defaultX = defaultX;
		this.defaultY = defaultY;
	}

	/**
	 * Adds an exit to the list of exits
	 * 
	 * @param exit
	 *            the exit to add
	 */
	public void addExit( Exit exit ) {
		exits.add( exit );
	}

	/**
	 * Adds an item reference to the list of item references
	 * 
	 * @param itemReference
	 *            the item reference to add
	 */
	public void addItemReference( ElementReference itemReference ) {
		itemReferences.add( itemReference );
	}

	/**
	 * Adds a character reference to the list of character references
	 * 
	 * @param characterReference
	 *            the character reference to add
	 */
	public void addCharacterReference( ElementReference characterReference ) {
		characterReferences.add( characterReference );
	}
	
	/**
	 * Adds an active area
	 * 
	 * @param activeArea
	 *            the active area to add
	 */
	public void addActiveArea( ActiveArea activeArea ) {
		activeAreas.add( activeArea );
	}

	/**
	 * @return the activeAreas
	 */
	public List<ActiveArea> getActiveAreas( ) {
		return activeAreas;
	}
	
    /**
     * Adds a new barrier
     * 
     * @param barrier
     *            the barrier to add
     */
    public void addBarrier( Barrier barrier ) {
        barriers.add( barrier );
    }

    /**
     * @return the barriers
     */
    public List<Barrier> getBarriers( ) {
        return barriers;
    }

}
