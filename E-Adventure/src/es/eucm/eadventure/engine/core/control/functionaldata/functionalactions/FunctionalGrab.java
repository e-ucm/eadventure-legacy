package es.eucm.eadventure.engine.core.control.functionaldata.functionalactions;

import es.eucm.eadventure.common.data.chapter.Action;
import es.eucm.eadventure.common.data.chapter.elements.Player;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.engine.core.control.ActionManager;
import es.eucm.eadventure.engine.core.control.DebugLog;
import es.eucm.eadventure.engine.core.control.animations.Animation;
import es.eucm.eadventure.engine.core.control.animations.AnimationState;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalElement;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalItem;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalPlayer;
import es.eucm.eadventure.engine.core.data.GameText;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

public class FunctionalGrab extends FunctionalAction {

	private FunctionalElement element;
	
    private long totalTime;

    private boolean canGrab = false;
    
	public FunctionalGrab(Action action, FunctionalElement element) {
		super(action);
		this.element = element;
		this.type = ActionManager.ACTION_GRAB;
		originalAction = element.getFirstValidAction(Action.GRAB);
		if (originalAction != null) {
			this.needsGoTo = originalAction.isNeedsGoTo();
			this.keepDistance = originalAction.getKeepDistance();
		} else {
			this.needsGoTo = true;
			this.keepDistance = 35;
		}
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
		
		// TODO always loads the default animation and walks with the defualt speed
        Resources resources = functionalPlayer.getResources( );
        MultimediaManager multimedia = MultimediaManager.getInstance( );
        if( functionalPlayer.getX( ) < element.getX() ) {
        	functionalPlayer.setDirection(AnimationState.EAST);
        } else {
        	functionalPlayer.setDirection(AnimationState.WEST);
        }
        Animation[] animations = new Animation[4];
        animations[AnimationState.EAST] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_USE_RIGHT ), false, MultimediaManager.IMAGE_PLAYER );
        if (resources.getAssetPath(Player.RESOURCE_TYPE_USE_LEFT) != null && resources.getAssetPath(Player.RESOURCE_TYPE_USE_LEFT) != AssetsController.ASSET_EMPTY_ANIMATION)
        	animations[AnimationState.WEST] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_USE_LEFT ), false, MultimediaManager.IMAGE_PLAYER );
        else
        	animations[AnimationState.WEST] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_USE_RIGHT ), true, MultimediaManager.IMAGE_PLAYER );        
       	animations[AnimationState.NORTH] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_USE_RIGHT ), false, MultimediaManager.IMAGE_PLAYER );
        animations[AnimationState.SOUTH] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_USE_RIGHT ), true, MultimediaManager.IMAGE_PLAYER );
		functionalPlayer.setAnimation(animations, -1);
        finished = false;
        
        DebugLog.player("Started grab: " + element.getElement().getId());
	}

	@Override
	public void stop() {
		finished = true;
	}

	@Override
	public void update(long elapsedTime) {
        totalTime += elapsedTime;
        FunctionalItem item = (FunctionalItem) element;
        if (!finished && !canGrab) {
        	canGrab = item.grab();
        	if (!canGrab) {
                finished = true;
                functionalPlayer.popAnimation();
        		functionalPlayer.speak( GameText.getTextGrabCannot( ) );
        	}
        } else if( !finished && totalTime > 1000 ) {
            finished = true;
            functionalPlayer.popAnimation();
        }
    }

}
