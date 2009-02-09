package es.eucm.eadventure.engine.core.control.animations.npc;

import es.eucm.eadventure.engine.core.control.animations.AnimationState;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalNPC;

/**
 * A state for a non player character (npc)
 */
public abstract class NPCState extends AnimationState {

    /**
     * The npc that owns this state
     */
    protected FunctionalNPC npc;
    
    /**
     * Creates a new NPCState
     * @param npc the reference to the npc 
     */
    public NPCState( FunctionalNPC npc ) {
        this.npc = npc;
        loadResources( );
    }
    
    protected float getVelocityX() {
        return npc.getSpeedX();
    }
    
    protected float getVelocityY() {
        return npc.getSpeedY();
    }
    
    protected int getCurrentDirection() {
        return npc.getDirection();
    }
    
    protected void setCurrentDirection( int direction ) {
    	npc.setDirection( direction );
    }
}
