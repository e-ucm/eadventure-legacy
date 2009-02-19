package es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects;

import es.eucm.eadventure.common.data.chapter.effects.IncrementVarEffect;
import es.eucm.eadventure.engine.core.control.Game;

/**
 * An effect that activates a flag
 */
public class FunctionalIncrementVarEffect extends FunctionalEffect {


    /**
     * Creates a new Activate effect.
     * @param the activate effect
     */
    public FunctionalIncrementVarEffect( IncrementVarEffect setValueEffect ) {
    	super(setValueEffect);
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#triggerEffect()
     */
    public void triggerEffect( ) {
    	Game.getInstance( ).getVars( ).incrementVar(((IncrementVarEffect)effect).getTargetId(), ((IncrementVarEffect)effect).getIncrement() );
        Game.getInstance( ).updateDataPendingFromState( false );
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
