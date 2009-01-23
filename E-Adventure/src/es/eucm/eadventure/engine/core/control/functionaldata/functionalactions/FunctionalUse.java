package es.eucm.eadventure.engine.core.control.functionaldata.functionalactions;

import es.eucm.eadventure.common.data.chapter.elements.Player;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.engine.core.control.ActionManager;
import es.eucm.eadventure.engine.core.control.animations.Animation;
import es.eucm.eadventure.engine.core.control.animations.AnimationState;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalElement;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalItem;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalPlayer;
import es.eucm.eadventure.engine.core.data.GameText;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

public class FunctionalUse extends FunctionalAction {

	private FunctionalElement element;
	
	private long totalTime;
	
	private boolean canUse = false;
	
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
        
        Animation[] animations = new Animation[4];
        animations[AnimationState.EAST] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_USE_RIGHT ), false, MultimediaManager.IMAGE_PLAYER );
        animations[AnimationState.WEST] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_USE_RIGHT ), true, MultimediaManager.IMAGE_PLAYER );
        animations[AnimationState.NORTH] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_USE_RIGHT ), false, MultimediaManager.IMAGE_PLAYER );
        animations[AnimationState.SOUTH] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_USE_RIGHT ), true, MultimediaManager.IMAGE_PLAYER );
        
        
        if( functionalPlayer.getX( ) < element.getX() ) {
        	functionalPlayer.setDirection(AnimationState.EAST);
        } else {
        	functionalPlayer.setDirection(AnimationState.WEST);
        }
        functionalPlayer.setAnimation(animations, -1);

	}

	@Override
	public void stop() {
		finished = true;
	}

	@Override
	public void update(long elapsedTime) {
        totalTime += elapsedTime;
        FunctionalItem item = (FunctionalItem) element;
        if (!finished && !canUse) {
        	canUse = item.use();
        	if (!canUse) {
                functionalPlayer.speak( GameText.getTextUseCannot( ) );
                functionalPlayer.popAnimation();
                finished = true;       		
        	}
        } else if(!finished && totalTime > 1000 ) {
            finished = true;
            functionalPlayer.popAnimation();
        }
	}

}
