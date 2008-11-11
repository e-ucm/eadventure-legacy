package es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects;

import es.eucm.eadventure.engine.core.control.Game;

/**
 * An effect that deactivates a flag.
 */
public class DeactivateEffect implements Effect {

    /**
     * Name of the flag to be activated
     */
    private String idFlag;

    /**
     * Creates a new DeactivateEffect.
     * @param idFlag the id of the flag to be deactivated
     */
    public DeactivateEffect( String idFlag ) {
        this.idFlag = idFlag;
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#triggerEffect()
     */
    public void triggerEffect( ) {
        Game.getInstance( ).getFlags( ).deactivateFlag( idFlag );
        Game.getInstance( ).updateDataPendingFromFlags( false );
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
