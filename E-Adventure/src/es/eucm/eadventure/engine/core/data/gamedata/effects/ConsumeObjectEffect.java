package es.eucm.eadventure.engine.core.data.gamedata.effects;

import es.eucm.eadventure.engine.core.control.Game;

/**
 * An effect that consumes an object in the inventory
 */
public class ConsumeObjectEffect implements Effect {

    /**
     * Id of the item to be consumed
     */
    private String idTarget;

    /**
     * Creates a new ConsumeObjectEffect.
     * @param idTarget the id of the object to be consumed
     */
    public ConsumeObjectEffect( String idTarget ) {
        this.idTarget = idTarget;
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#triggerEffect()
     */
    public void triggerEffect( ) {
        Game.getInstance( ).consumeItem( idTarget );
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
     * @see es.eucm.eadventure.engine.core.data.gamedata.effects.Effect#isStillRunning()
     */
    public boolean isStillRunning( ) {
        return false;
    }
}
