package es.eucm.eadventure.engine.core.control.functionaldata.functionalactions;

import es.eucm.eadventure.common.data.chapter.Action;
import es.eucm.eadventure.common.data.chapter.elements.Player;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.engine.core.control.ActionManager;
import es.eucm.eadventure.engine.core.control.animations.Animation;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalElement;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalItem;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalPlayer;
import es.eucm.eadventure.engine.core.data.GameText;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

public class FunctionalUse extends FunctionalAction {

	private FunctionalElement element;
	
	private long totalTime;
	
	public FunctionalUse(FunctionalElement element) {
		super(null);
		this.type = ActionManager.ACTION_USE;
		this.element = element;
        FunctionalItem item = (FunctionalItem) element;
        originalAction = item.getFirstValidAction(ActionManager.ACTION_USE);
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
		this.needsGoTo = true;
		this.finished = false;
		totalTime = 0;
		
		// TODO always loads the default animation and walks with the defualt speed
        Resources resources = functionalPlayer.getResources( );
        MultimediaManager multimedia = MultimediaManager.getInstance( );
        if( functionalPlayer.getX( ) < element.getX() ) {
            Animation animation = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_USE_RIGHT ), false, MultimediaManager.IMAGE_PLAYER );
            functionalPlayer.setAnimation(animation, -1);
        } else {
            Animation animation = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_USE_RIGHT ), true, MultimediaManager.IMAGE_PLAYER );
            functionalPlayer.setAnimation(animation, -1);
        }

	}

	@Override
	public void stop() {
		finished = true;
	}

	@Override
	public void update(long elapsedTime) {
        totalTime += elapsedTime;
        if( totalTime > 1000 ) {
            FunctionalItem item = (FunctionalItem) element;
    
            finished = true;
            functionalPlayer.popAnimation();
            if( !item.use( ) )
                functionalPlayer.speak( GameText.getTextUseCannot( ) );
        }
	}

}
