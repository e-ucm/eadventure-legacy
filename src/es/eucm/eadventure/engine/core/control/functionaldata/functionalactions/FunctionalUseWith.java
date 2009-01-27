package es.eucm.eadventure.engine.core.control.functionaldata.functionalactions;

import es.eucm.eadventure.common.data.chapter.Action;
import es.eucm.eadventure.common.data.chapter.elements.Player;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.engine.core.control.ActionManager;
import es.eucm.eadventure.engine.core.control.animations.Animation;
import es.eucm.eadventure.engine.core.control.animations.AnimationState;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalElement;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalItem;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalPlayer;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

public class FunctionalUseWith extends FunctionalAction {

	private FunctionalElement element;
	
	private FunctionalElement anotherElement;
	
	private long totalTime;
	
	private boolean canUseWith = false;
	
	public FunctionalUseWith(Action action, FunctionalElement element) {
		super(action);
		this.element = element;
		this.type = ActionManager.ACTION_USE_WITH;
		this.requiersAnotherElement = true;
		this.needsGoTo = false;
	}

	@Override
	public void drawAditionalElements() {
	}

	@Override
	public void setAnotherElement(FunctionalElement element) {
		anotherElement = element;
		if (!anotherElement.isInInventory())
			this.needsGoTo = true;
	}

	@Override
	public void start(FunctionalPlayer functionalPlayer) {
		this.functionalPlayer = functionalPlayer;
		this.totalTime = 0;
		this.finished = false;
		
        Resources resources = functionalPlayer.getResources( );
        MultimediaManager multimedia = MultimediaManager.getInstance( );

        Animation[] animations = new Animation[4];
        animations[AnimationState.EAST] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_USE_RIGHT ), false, MultimediaManager.IMAGE_PLAYER );
        animations[AnimationState.WEST] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_USE_RIGHT ), true, MultimediaManager.IMAGE_PLAYER );
        animations[AnimationState.NORTH] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_USE_RIGHT ), false, MultimediaManager.IMAGE_PLAYER );
        animations[AnimationState.SOUTH] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_USE_RIGHT ), true, MultimediaManager.IMAGE_PLAYER );

        if (functionalPlayer.getX() > element.getX())
        	functionalPlayer.setDirection(AnimationState.EAST);
        else 
        	functionalPlayer.setDirection(AnimationState.WEST);

        functionalPlayer.setAnimation(animations, -1);
	}

	@Override
	public void stop() {
		finished = true;
	}

	@Override
	public void update(long elapsedTime) {
        if (anotherElement != null) {
			totalTime += elapsedTime;
			if (element instanceof FunctionalItem && anotherElement instanceof FunctionalItem) {
			
	            FunctionalItem item1 = (FunctionalItem) element;
	            FunctionalItem item2 = (FunctionalItem) anotherElement;
	
	            if (!finished && !canUseWith) {
					canUseWith = item1.useWith( item2 ) || item2.useWith( item1 );
					if (!canUseWith) {
						finished = true;
						functionalPlayer.popAnimation();
					}
				} else if(!finished && totalTime > 1000 ) {
		        	functionalPlayer.popAnimation();
		            finished = true;
		        }
			} else
				finished = true;
        }
	}

	public FunctionalElement getAnotherElement() {
		return anotherElement;
	}

}
