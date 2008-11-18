package es.eucm.eadventure.common.data.chapter.elements;

/**
 * This class holds the data for the player in eAdventure
 */
public class Player extends NPC {

	/**
	 * Constant identifier of the player (used with conversation lines, also).
	 */
	public static final String IDENTIFIER = "Player";

	/**
	 * Creates a new player
	 */
	public Player( ) {
		super( IDENTIFIER );
	}
}