package es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects;

import es.eucm.eadventure.common.data.chapterdata.effects.MoveNPCEffect;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalNPC;

/**
 * An effect that makes a character to walk to a given position.
 */
public class FunctionalMoveNPCEffect extends FunctionalEffect {


    /**
     * Creates a new FunctionalMoveNPCEffect.
     * @param idTarget the id of the character who will walk
     * @param x X final position for the NPC
     * @param y Y final position for the NPC
     */
    public FunctionalMoveNPCEffect( MoveNPCEffect effect ) {
    	super(effect);
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#triggerEffect()
     */
    public void triggerEffect( ) {
        FunctionalNPC npc = Game.getInstance( ).getFunctionalScene( ).getNPC( ((MoveNPCEffect)effect).getIdTarget() );
        if( npc != null ) {
            npc.setDestiny( ((MoveNPCEffect)effect).getX(), ((MoveNPCEffect)effect).getY());
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
     * @see es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects.FunctionalEffect#isStillRunning()
     */
    public boolean isStillRunning( ) {
        boolean stillRunning = false;
        
        FunctionalNPC npc = Game.getInstance( ).getFunctionalScene( ).getNPC( ((MoveNPCEffect)effect).getIdTarget() );
        if( npc != null ) {
            stillRunning = npc.isWalking();
        }
        return stillRunning;
    }

}
