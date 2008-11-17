package es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects;

import es.eucm.eadventure.common.data.chapterdata.effects.MovePlayerEffect;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalPlayer;

/**
 * An effect that makes the player to move to the given position.
 */
public class FunctionalMovePlayerEffect extends FunctionalEffect {

    /**
     * Creates a new FunctionalMovePlayerEffect.
     * @param x X final position for the player
     * @param y Y final position for the player
     */
    public FunctionalMovePlayerEffect( MovePlayerEffect effect ) {
    	super(effect);
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#triggerEffect()
     */
    public void triggerEffect( ) {
        FunctionalPlayer player = Game.getInstance( ).getFunctionalPlayer( );
        player.setDestiny( ((MovePlayerEffect)effect).getX(),
        		((MovePlayerEffect)effect).getY() );
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
     * @see es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects.FunctionalEffect#isStillRunning()
     */
    public boolean isStillRunning( ) {
        boolean stillRunning = false;
        
        FunctionalPlayer player = Game.getInstance( ).getFunctionalPlayer( );
        stillRunning = player.isWalking( );
        
        return stillRunning;
    }

}
