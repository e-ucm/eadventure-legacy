package es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects;

import es.eucm.eadventure.common.data.chapter.NextScene;
import es.eucm.eadventure.common.data.chapter.effects.TriggerSceneEffect;
import es.eucm.eadventure.engine.core.control.Game;

/**
 * An effect that triggers a cutscene
 */
public class FunctionalTriggerSceneEffect extends FunctionalEffect {

    /**
     * Creates a new TriggerCutsceneEffect
     * @param targetCutsceneId the id of the cutscene to be triggered
     */
    public FunctionalTriggerSceneEffect( TriggerSceneEffect effect ) {
    	super(effect);
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#triggerEffect()
     */
    public void triggerEffect( ) {
        if( ((TriggerSceneEffect)effect).getTargetId()!=null && !Game.getInstance( ).getCurrentChapterData( ).isCutscene( ((TriggerSceneEffect)effect).getTargetId() ) ) {
            Game.getInstance( ).setNextScene( new NextScene( ((TriggerSceneEffect)effect).getTargetId()
            		, ((TriggerSceneEffect)effect).getX(), ((TriggerSceneEffect)effect).getY() ) );
            Game.getInstance( ).setState( Game.STATE_NEXT_SCENE );
        }
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
