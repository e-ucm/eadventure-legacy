package es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects;

import es.eucm.eadventure.common.data.chapterdata.effects.ActivateEffect;
import es.eucm.eadventure.engine.core.control.Game;

/**
 * An effect that activates a flag
 */
public class FunctionalActivateEffect extends FunctionalEffect {


    /**
     * Creates a new Activate effect.
     * @param the activate effect
     */
    public FunctionalActivateEffect( ActivateEffect activateEffect ) {
    	super(activateEffect);
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#triggerEffect()
     */
    public void triggerEffect( ) {
        Game.getInstance( ).getFlags( ).activateFlag( ((ActivateEffect)effect).getIdFlag() );
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
     * @see es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects.FunctionalEffect#isStillRunning()
     */
    public boolean isStillRunning( ) {
        return false;
    }

}
