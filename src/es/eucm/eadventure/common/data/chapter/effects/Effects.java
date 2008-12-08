package es.eucm.eadventure.common.data.chapter.effects;

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
	
	/** 
	 * Checks if there is any cancel action effect in the list
	 */
	public boolean hasCancelAction( ){
		boolean hasCancelAction = false;
		for (Effect effect: effects){
			if (effect.getType() == Effect.CANCEL_ACTION){
				hasCancelAction = true; break;
			}
		}
		return hasCancelAction;
	}
	
}
