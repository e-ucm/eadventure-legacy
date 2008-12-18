package es.eucm.eadventure.engine.core.control.functionaldata.functionalactions;

import es.eucm.eadventure.common.data.chapter.Exit;
import es.eucm.eadventure.common.data.chapter.NextScene;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalConditions;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalElement;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalPlayer;
import es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects.FunctionalEffects;
import es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects.FunctionalNextSceneEffect;

public class FunctionalExit extends FunctionalAction {

	private Exit exit;
	
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
		if( exit != null ) {
            NextScene nextScene = null;

            // Pick the FIRST valid next-scene structure
            for( int i = 0; i < exit.getNextScenes( ).size( ) && nextScene == null; i++ )
                if( new FunctionalConditions ( exit.getNextScenes( ).get( i ).getConditions( ) ).allConditionsOk( ) )
                    nextScene = exit.getNextScenes( ).get( i );

            if( nextScene != null ) {
            	FunctionalEffects.storeAllEffects(nextScene.getEffects( ));
                Game.getInstance().enqueueEffect( new FunctionalNextSceneEffect( nextScene ) );
            }
        }
		finished = true;
	}

	@Override
	public void stop() {
		finished = true;
	}

	@Override
	public void update(long elapsedTime) {
	}

}
