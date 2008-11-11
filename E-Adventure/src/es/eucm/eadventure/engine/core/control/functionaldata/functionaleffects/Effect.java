package es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects;

/**
 * This interface defines any individual effect that can
 * be triggered by a player's action during the game.
 */
public interface Effect {

    /**
     * triggers the effect
     */
    public void triggerEffect( );

    /**
     * Returns whether the effect is instantaneous.
     * @return whether the effect is instantaneous
     */
    public boolean isInstantaneous( );
    
    /**
     * Returns whether the effect is still running
     * @return true if the effect is still running, false otherwise
     */
    boolean isStillRunning();

}
