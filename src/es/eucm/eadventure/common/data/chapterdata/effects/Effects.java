package es.eucm.eadventure.common.data.chapterdata.effects;

import java.util.ArrayList;
import java.util.List;

/**
 * A list of effects that can be triggered by an unique player's action during the game.
 */
public class Effects {

	/**
	 * List of effects to be triggered
	 */
	private List<Effect> effects;

	/**
	 * Creates a new list of Effects.
	 */
	public Effects( ) {
		effects = new ArrayList<Effect>( );
	}

	/**
	 * Returns whether the effects block is empty or not.
	 * 
	 * @return True if the block has no effects, false otherwise
	 */
	public boolean isEmpty( ) {
		return effects.isEmpty( );
	}

	/**
	 * Clear the list of effects.
	 */
	public void clear( ) {
		effects.clear( );
	}

	/**
	 * Adds a new effect to the list.
	 * 
	 * @param effect
	 *            the effect to be added
	 */
	public void add( Effect effect ) {
		effects.add( effect );
	}

	/**
	 * Returns the contained list of effects
	 * 
	 * @return List of effects
	 */
	public List<Effect> getEffects( ) {
		return effects;
	}
	
	public void storeAllEffects(){
		
	}
}
