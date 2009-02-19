package es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects;

import es.eucm.eadventure.common.data.chapter.effects.DecrementVarEffect;
import es.eucm.eadventure.engine.core.control.Game;

/**
 * An effect that activates a flag
 */
public class FunctionalDecrementVarEffect extends FunctionalEffect {


    /**
     * Creates a new Activate effect.
     * @param the activate effect
     */
    public FunctionalDecrementVarEffect( DecrementVarEffect setValueEffect ) {
    	super(setValueEffect);
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#triggerEffect()
     */
    public void triggerEffect( ) {
    	Game.getInstance( ).getVars( ).decrementVar(((DecrementVarEffect)effect).getTargetId(), ((DecrementVarEffect)effect).getDecrement() );
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
