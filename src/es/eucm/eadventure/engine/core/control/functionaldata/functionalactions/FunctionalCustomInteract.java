package es.eucm.eadventure.engine.core.control.functionaldata.functionalactions;

import es.eucm.eadventure.common.data.chapter.Action;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalElement;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalItem;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalNPC;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalPlayer;
import es.eucm.eadventure.engine.core.data.GameText;

public class FunctionalCustomInteract extends FunctionalAction {

	private FunctionalElement element;
	
	private String customActionName;
	
	private FunctionalElement anotherElement;
	
	private long totalTime;
	
	public FunctionalCustomInteract(FunctionalElement element,
			String customActionName) {
		super(null);
		this.type = Action.CUSTOM_INTERACT;
		this.customActionName = customActionName;
		originalAction = element.getFirstValidCustomInteraction(customActionName);
		this.element = element;
		this.requiersAnotherElement = true;
		this.needsGoTo = false;
		this.finished = false;
	}

	@Override
	public void drawAditionalElements() {
	}

	@Override
	public void setAnotherElement(FunctionalElement element) {
		this.anotherElement = element;
		this.requiersAnotherElement = false;
		this.needsGoTo = originalAction.isNeedsGoTo();
		this.keepDistance = originalAction.getKeepDistance();
	}

	@Override
	public void start(FunctionalPlayer functionalPlayer) {
		if (!requiersAnotherElement) {
			this.functionalPlayer = functionalPlayer;
			this.totalTime = 0;
		}
	}

	@Override
	public void stop() {
		finished = true;
	}

	@Override
	public void update(long elapsedTime) {
        if (anotherElement != null) {
			totalTime += elapsedTime;
	        if( totalTime > 1000 ) {
	            FunctionalItem item1 = (FunctionalItem) element;
	            if (anotherElement instanceof FunctionalItem) {
		            FunctionalItem item2 = (FunctionalItem) anotherElement;
		            if( !item1.customInteract(customActionName, item2) && !item2.customInteract(customActionName, item1) )
		                functionalPlayer.speak( GameText.getTextCustomCannot() );
	            }
	            if (anotherElement instanceof FunctionalNPC) {
	            	FunctionalNPC npc = (FunctionalNPC) anotherElement;
	            	if (!item1.customInteract(customActionName, npc))
	            		functionalPlayer.speak( GameText.getTextCustomCannot() );
	            }
	            finished = true;
	        }
        }
	}

}