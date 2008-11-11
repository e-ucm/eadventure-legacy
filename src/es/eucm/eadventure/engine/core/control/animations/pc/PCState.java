package es.eucm.eadventure.engine.core.control.animations.pc;

import es.eucm.eadventure.engine.core.control.animations.AnimationState;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalPlayer;

/**
 * An state for the player
 */
public abstract class PCState extends AnimationState {

    /**
     * The player that owns this animation
     */
    protected FunctionalPlayer player;

    /**
     * Creates a new PCState
     * @param player the reference to the player
     */
    public PCState( FunctionalPlayer player ) {
        this.player = player;
        loadResources( );
    }
    
    protected float getVelocityX() {
        return player.getSpeedX( );
    }
    
    protected float getVelocityY() {
        return player.getSpeedY( );
    }
    
    protected int getCurrentDirection() {
        return player.getDirection();
    }
    
    protected void setCurrentDirection( int direction ) {
        player.setDirection( direction );
    }
    
    public void draw( int x, int y ) {
        if (!player.isTransparent( ))
            super.draw( x, y );
    }
}
