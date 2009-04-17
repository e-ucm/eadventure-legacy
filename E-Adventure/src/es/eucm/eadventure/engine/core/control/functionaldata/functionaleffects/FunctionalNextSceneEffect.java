package es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects;

import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.common.data.chapter.Exit;
import es.eucm.eadventure.common.data.chapter.NextScene;

/**
 * An effect that takes the player to a new scene. 
 */
public class FunctionalNextSceneEffect extends FunctionalEffect {

    /**
     * Information about the next scene to be loaded
     */
    private Exit exit;

    /**
     * Creates a new FunctionalNextSceneEffect
     * @param nextScene the next scene
     */
    public FunctionalNextSceneEffect( Exit exit ) {
    	super(null);
        this.exit = exit;
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#triggerEffect()
     */
    public void triggerEffect( ) {
        Game.getInstance( ).setNextScene( exit );
        Game.getInstance( ).setState( Game.STATE_NEXT_SCENE );
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
