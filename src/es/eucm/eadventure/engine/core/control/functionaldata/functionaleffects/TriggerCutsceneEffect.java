package es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects;

import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.data.gamedata.NextScene;

/**
 * An effect that triggers a cutscene
 */
public class TriggerCutsceneEffect extends FunctionalEffect {

    /**
     * Id of the cutscene to be played
     */
    private String targetCutsceneId;

    /**
     * Creates a new TriggerCutsceneEffect
     * @param targetCutsceneId the id of the cutscene to be triggered
     */
    public TriggerCutsceneEffect( String targetCutsceneId ) {
    	super(null);
        this.targetCutsceneId = targetCutsceneId;
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#triggerEffect()
     */
    public void triggerEffect( ) {
        if( Game.getInstance( ).getGameData( ).isCutscene( targetCutsceneId ) ) {
            Game.getInstance( ).setNextScene( new NextScene( targetCutsceneId ) );
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
