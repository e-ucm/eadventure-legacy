package es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects;

import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.common.data.chapter.NextScene;

/**
 * An effect that takes the player to a new scene. 
 */
public class FunctionalNextSceneEffect extends FunctionalEffect {

    /**
     * Information about the next scene to be loaded
     */
    private NextScene nextScene;

    /**
     * Creates a new FunctionalNextSceneEffect
     * @param nextScene the next scene
     */
    public FunctionalNextSceneEffect( NextScene nextScene ) {
    	super(null);
        this.nextScene = nextScene;
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#triggerEffect()
     */
    public void triggerEffect( ) {
        Game.getInstance( ).setNextScene( nextScene );
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
