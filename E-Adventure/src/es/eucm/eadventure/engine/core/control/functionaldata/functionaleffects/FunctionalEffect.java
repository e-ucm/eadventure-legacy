package es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects;

import es.eucm.eadventure.common.data.chapterdata.effects.Effect;

/**
 * This abstract class defines how a certain effect must be triggered
 * in the game engine.
 */
public abstract class FunctionalEffect {

	/**
	 * The effect to be ruled
	 */
	protected Effect effect;
	
	/**
	 * Constructor
	 */
	public FunctionalEffect ( Effect effect ){
		this.effect = effect;
	}
	
    /**
     * triggers the effect
     */
    public abstract void triggerEffect( );

    /**
     * Returns whether the effect is instantaneous.
     * @return whether the effect is instantaneous
     */
    public abstract boolean isInstantaneous( );
    
    /**
     * Returns whether the effect is still running
     * @return true if the effect is still running, false otherwise
     */
    public abstract boolean isStillRunning();

}
