package es.eucm.eadventure.engine.core.control.functionaldata.functionalactions;

import java.util.List;

import es.eucm.eadventure.common.data.chapter.Exit;
import es.eucm.eadventure.common.data.chapter.NextScene;
import es.eucm.eadventure.common.data.chapter.effects.Effects;
import es.eucm.eadventure.engine.core.control.DebugLog;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalConditions;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalElement;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalPlayer;
import es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects.FunctionalEffect;
import es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects.FunctionalEffects;
import es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects.FunctionalNextSceneEffect;

/**
 * The action to exit a scene
 * 
 * @author Eugenio Marchiori
 *
 */
public class FunctionalExit extends FunctionalAction {

	/**
	 * The exit through which the player leaves the scene
	 */
	private Exit exit;
	
	/**
	 * Default constructor, with the exit to use.
	 * 
	 * @param exit The exit through which the player leaves the scene.
	 */
	public FunctionalExit(Exit exit) {
		super(null);
		this.exit = exit;
	}

	@Override
	public void drawAditionalElements() {
	}

	@Override
	public void setAnotherElement(FunctionalElement element) {
	}

	@Override
	public void start(FunctionalPlayer functionalPlayer) {
        this.functionalPlayer = functionalPlayer;
        NextScene nextScene = null;
		if( exit != null ) {

            // Pick the FIRST valid next-scene structure
            for( int i = 0; i < exit.getNextScenes( ).size( ) && nextScene == null; i++ )
                if( new FunctionalConditions ( exit.getNextScenes( ).get( i ).getConditions( ) ).allConditionsOk( ) )
                    nextScene = exit.getNextScenes( ).get( i );

            if( nextScene != null ) {
            	// merge all effects in one structure
            	FunctionalEffects effects =  new FunctionalEffects(nextScene.getEffects( ));
            	effects.addEffect( new FunctionalNextSceneEffect( nextScene ));
            	FunctionalEffects.storeAllEffects(effects);
                //Game.getInstance().enqueueEffect( new FunctionalNextSceneEffect( nextScene ) );
            }
        }
		finished = true;
		
		DebugLog.player("Exit scene to: " + (nextScene != null ? nextScene.getTargetId() : "none") );
	}

	@Override
	public void stop() {
		finished = true;
	}

	@Override
	public void update(long elapsedTime) {
	}

}
