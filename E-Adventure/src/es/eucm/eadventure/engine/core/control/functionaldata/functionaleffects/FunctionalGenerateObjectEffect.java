package es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects;

import es.eucm.eadventure.common.data.chapterdata.effects.GenerateObjectEffect;
import es.eucm.eadventure.engine.core.control.Game;

/**
 * An effect that generates an object in the inventory.
 */
public class FunctionalGenerateObjectEffect extends FunctionalEffect {

    /**
     * Creates a new FunctionalGenerateObjectEffect.
     */
    public FunctionalGenerateObjectEffect( GenerateObjectEffect effect ) {
    	super(effect);
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#triggerEffect()
     */
    public void triggerEffect( ) {
        Game.getInstance( ).generateItem( ((GenerateObjectEffect)effect).getIdTarget() );
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
