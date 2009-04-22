package es.eucm.eadventure.common.data.chapter.effects;

import java.util.ArrayList;
import java.util.List;

/**
 * A list of effects that can be triggered by an unique player's action during the game.
 */
public class Effects implements Cloneable {

	/**
	 * List of effects to be triggered
	 */
	private List<AbstractEffect> effects;

	/**
	 * Creates a new list of Effects.
	 */
	public Effects( ) {
		effects = new ArrayList<AbstractEffect>( );
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
	public void add( AbstractEffect effect ) {
		effects.add( effect );
	}

	/**
	 * Returns the contained list of effects
	 * 
	 * @return List of effects
	 */
	public List<AbstractEffect> getEffects( ) {
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
	
	public Object clone() throws CloneNotSupportedException {
		Effects e = (Effects) super.clone();
		if (effects != null) {
			e.effects = new ArrayList<AbstractEffect>();
			for (Effect ef : effects)
				e.effects.add((AbstractEffect) ef.clone());
		}
		return e; 
	}
	
}
