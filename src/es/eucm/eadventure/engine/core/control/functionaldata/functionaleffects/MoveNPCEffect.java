package es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects;

import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalNPC;

/**
 * An effect that makes a character to walk to a given position.
 */
public class MoveNPCEffect implements Effect {

    /**
     * Id of the npc who will walk
     */
    private String idTarget;

    /**
     * The destination of the npc
     */
    private int x;
    private int y;

    /**
     * Creates a new MoveNPCEffect.
     * @param idTarget the id of the character who will walk
     * @param x X final position for the NPC
     * @param y Y final position for the NPC
     */
    public MoveNPCEffect( String idTarget, int x, int y ) {
        this.idTarget = idTarget;
        this.x = x;
        this.y = y;
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#triggerEffect()
     */
    public void triggerEffect( ) {
        FunctionalNPC npc = Game.getInstance( ).getFunctionalScene( ).getNPC( idTarget );
        if( npc != null ) {
            npc.setDestiny( x, y);
            npc.setState( FunctionalNPC.WALK );
        }
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
        
        FunctionalNPC npc = Game.getInstance( ).getFunctionalScene( ).getNPC( idTarget );
        if( npc != null ) {
            stillRunning = npc.isWalking();
        }
        return stillRunning;
    }

}
