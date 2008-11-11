package es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects;

import es.eucm.eadventure.engine.core.control.Game;

/**
 * An effect that generates an object in the inventory.
 */
public class GenerateObjectEffect implements Effect {

    /**
     * Id of the item to be generated
     */
    private String idTarget;

    /**
     * Creates a new GenerateObjectEffect.
     * @param idTarget the id of the object to be generated
     */
    public GenerateObjectEffect( String idTarget ) {
        this.idTarget = idTarget;
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#triggerEffect()
     */
    public void triggerEffect( ) {
        Game.getInstance( ).generateItem( idTarget );
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#isInstantaneous()
     */
    public boolean isInstantaneous( ) {
        return true;
    }
    
    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects.Effect#isStillRunning()
     */
    public boolean isStillRunning( ) {
        return false;
    }
}
