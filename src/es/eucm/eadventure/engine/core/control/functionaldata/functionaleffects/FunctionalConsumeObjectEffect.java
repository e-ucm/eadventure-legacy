package es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects;

import es.eucm.eadventure.common.data.chapterdata.effects.ConsumeObjectEffect;
import es.eucm.eadventure.engine.core.control.Game;

/**
 * An effect that consumes an object in the inventory
 */
public class FunctionalConsumeObjectEffect extends FunctionalEffect {

    /**
     * Creates a new FunctionalConsumeObjectEffect.
     * @param the ConsumeObjectEffect
     */
    public FunctionalConsumeObjectEffect( ConsumeObjectEffect effect) {
    	super(effect);
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#triggerEffect()
     */
    public void triggerEffect( ) {
        Game.getInstance( ).consumeItem( ((ConsumeObjectEffect)effect).getIdTarget() );
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
