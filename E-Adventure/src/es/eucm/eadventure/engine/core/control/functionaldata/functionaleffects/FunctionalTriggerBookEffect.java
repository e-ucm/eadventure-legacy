package es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects;

import es.eucm.eadventure.common.data.chapter.effects.TriggerBookEffect;
import es.eucm.eadventure.engine.core.control.Game;

/**
 * An effect that raises a "bookscene".
 */
public class FunctionalTriggerBookEffect extends FunctionalEffect {

    /**
     * Creates a new TriggerBookEffect
     * @param targetBookId the id of the book to be shown
     */
    public FunctionalTriggerBookEffect( TriggerBookEffect effect ) {
    	super(effect);
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#triggerEffect()
     */
    public void triggerEffect( ) {
        Game.getInstance( ).setBook( ((TriggerBookEffect)effect).getTargetId() );
        Game.getInstance( ).setState( Game.STATE_BOOK );
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
        return false;
    }

}
