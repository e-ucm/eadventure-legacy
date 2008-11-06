package es.eucm.eadventure.engine.core.data.gamedata.effects;

import java.util.ArrayList;

import es.eucm.eadventure.engine.core.control.Game;

/**
 * A list of effects that can be triggered by an unique
 * player's action during the game.
 */
public class Effects {

    /**
     * Stores if the effect cancel the normal course of the action
     */
    private boolean hasCancelAction;

    /**
     * List of effects to be triggered
     */
    private ArrayList<Effect> effects;

    /**
     * Creates a new list of Effects.
     */
    public Effects( ) {
        effects = new ArrayList<Effect>( );
        hasCancelAction = false;
    }

    /**
     * Adds a new effect to the list.
     * @param effect the effect to be added
     */
    public void add( Effect effect ) {
        effects.add( effect );
    }

    /**
     * Return the effect in the given position.
     * @param index the effect position
     * @return the effect in the given position
     */
    public Effect getEffect( int index ) {
        return effects.get( index );
    }

    /**
     * Sets whether the list of effects has a cancel action.
     * @param hasCancelAction true if the list of effects has a cancel action, false otherwise
     */
    public void setHasCancelAction( boolean hasCancelAction ) {
        this.hasCancelAction = hasCancelAction;
    }

    /**
     * Returns whether the list of effects has a cancel action.
     * @return true if the list of effects has a cancel action, false otherwise
     */
    public boolean hasCancelAction( ) {
        return hasCancelAction;
    }

    /**
     * Queues the effects in the game effects queue to be done when possible.
     */
    public void storeAllEffects( ) {
        Game.getInstance( ).storeEffectsInQueue( effects );
    }
    
    public void storeAllEffects( boolean fromConversation ) {
        Game.getInstance( ).storeEffectsInQueue( effects, fromConversation );
    }

}
