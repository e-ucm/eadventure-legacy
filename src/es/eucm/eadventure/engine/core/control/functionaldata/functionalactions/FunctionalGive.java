package es.eucm.eadventure.engine.core.control.functionaldata.functionalactions;

import es.eucm.eadventure.common.data.chapter.Action;
import es.eucm.eadventure.common.data.chapter.elements.Player;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.engine.core.control.ActionManager;
import es.eucm.eadventure.engine.core.control.animations.Animation;
import es.eucm.eadventure.engine.core.control.animations.AnimationState;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalElement;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalItem;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalNPC;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalPlayer;
import es.eucm.eadventure.engine.core.data.GameText;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

public class FunctionalGive extends FunctionalAction {

	private FunctionalElement element;
	
	private FunctionalElement anotherElement;
	
	private long totalTime;
	
	private boolean canGive = false;
	
	public FunctionalGive(Action action, FunctionalElement element) {
		super(action);
		this.element = element;
		this.type = ActionManager.ACTION_GIVE;
		requiersAnotherElement = true;
	}

	@Override
	public void drawAditionalElements() {
	}

	@Override
	public void setAnotherElement(FunctionalElement element) {
		requiersAnotherElement = false;
		needsGoTo = true;
		anotherElement = element;
	}

	@Override
	public void start(FunctionalPlayer functionalPlayer) {
		this.functionalPlayer = functionalPlayer;

		Resources resources = functionalPlayer.getResources( );

		MultimediaManager multimedia = MultimediaManager.getInstance( );
		if (anotherElement.getX() > functionalPlayer.getX()) {
			functionalPlayer.setDirection(AnimationState.EAST);
		} else {
			functionalPlayer.setDirection(AnimationState.WEST);
		}
		Animation[] animations = new Animation[4];
		animations[AnimationState.EAST] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_USE_RIGHT ), false, MultimediaManager.IMAGE_PLAYER) ;
		animations[AnimationState.WEST] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_USE_RIGHT ), true, MultimediaManager.IMAGE_PLAYER );
		animations[AnimationState.NORTH] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_USE_RIGHT ), false, MultimediaManager.IMAGE_PLAYER) ;
		animations[AnimationState.SOUTH] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_USE_RIGHT ), false, MultimediaManager.IMAGE_PLAYER );
 		functionalPlayer.setAnimation(animations, -1);

	}

	@Override
	public void stop() {
		if (functionalPlayer != null && !finished) {
			functionalPlayer.popAnimation();
		}
		finished = true;
	}

	@Override
	public void update(long elapsedTime) {
		if (anotherElement != null) {
	        totalTime += elapsedTime;
	        if (element instanceof FunctionalItem && anotherElement instanceof FunctionalNPC) {
	            FunctionalItem item = (FunctionalItem) element;
	            FunctionalNPC npc = (FunctionalNPC) anotherElement;
		        
	            if (!finished && !canGive) {
	            	canGive = item.giveTo(npc);
	            	if (!canGive) {
		                functionalPlayer.speak( GameText.getTextGiveCannot( ) );
		                functionalPlayer.popAnimation();
		                finished = true;
	            	}
	            } else if ( !finished && totalTime > 1000 ) {
		            finished = true;
		            functionalPlayer.popAnimation();
		        }
	        } else
	        	finished = true;
        }
		
	}
	
	public FunctionalElement getAnotherElement() {
		return anotherElement;
	}


}
