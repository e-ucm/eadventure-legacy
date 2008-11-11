package es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects;

import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalPlayer;

/**
 * An effect that makes the player to move to the given position.
 */
public class MovePlayerEffect implements Effect {

    /**
     * The destination of the player
     */
    private int x;
    private int y;

    /**
     * Creates a new MovePlayerEffect.
     * @param x X final position for the player
     * @param y Y final position for the player
     */
    public MovePlayerEffect( int x, int y ) {
        this.x = x;
        this.y = y;
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#triggerEffect()
     */
    public void triggerEffect( ) {
        FunctionalPlayer player = Game.getInstance( ).getFunctionalPlayer( );
        player.setDestiny( x, y );
        player.setState( FunctionalPlayer.WALK );
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#isInstantaneous()
     */
    public boolean isInstantaneous( ) {
        return false;
    }
    
    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects.Effect#isStillRunning()
     */
    public boolean isStillRunning( ) {
        boolean stillRunning = false;
        
        FunctionalPlayer player = Game.getInstance( ).getFunctionalPlayer( );
        stillRunning = player.isWalking( );
        
        return stillRunning;
    }

}
