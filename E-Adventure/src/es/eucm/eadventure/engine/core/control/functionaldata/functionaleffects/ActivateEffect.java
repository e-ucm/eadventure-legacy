package es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects;

import es.eucm.eadventure.engine.core.control.Game;

/**
 * An effect that activates a flag
 */
public class ActivateEffect implements Effect {

    /**
     * Name of the flag to be activated
     */
    private String idFlag;

    /**
     * Creates a new Activate effect.
     * @param idFlag the id of the flag to be activated
     */
    public ActivateEffect( String idFlag ) {
        this.idFlag = idFlag;
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#triggerEffect()
     */
    public void triggerEffect( ) {
        Game.getInstance( ).getFlags( ).activateFlag( idFlag );
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
