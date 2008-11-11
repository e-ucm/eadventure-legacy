package es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects;

import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.data.gamedata.NextScene;

/**
 * An effect that triggers a cutscene
 */
public class TriggerSceneEffect extends FunctionalEffect {

    /**
     * Id of the cutscene to be played
     */
    protected String targetSceneId;
    
    protected int x;

    protected int y;

    /**
     * Creates a new TriggerCutsceneEffect
     * @param targetCutsceneId the id of the cutscene to be triggered
     */
    public TriggerSceneEffect( String targetSceneId, int x, int y ) {
    	super(null);
        this.targetSceneId = targetSceneId;
        this.x = x;
        this.y = y;      
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#triggerEffect()
     */
    public void triggerEffect( ) {
        if( targetSceneId!=null && !Game.getInstance( ).getGameData( ).isCutscene( targetSceneId ) ) {
            Game.getInstance( ).setNextScene( new NextScene( targetSceneId, x, y ) );
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
