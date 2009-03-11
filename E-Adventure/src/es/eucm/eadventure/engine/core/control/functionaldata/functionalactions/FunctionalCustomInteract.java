package es.eucm.eadventure.engine.core.control.functionaldata.functionalactions;

import es.eucm.eadventure.common.data.chapter.Action;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalElement;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalItem;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalNPC;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalPlayer;
import es.eucm.eadventure.engine.core.data.GameText;

/**
 * A custom action defined by the user that needs to
 * objects to be performed.
 * 
 * @author Eugenio Marchiori
 *
 */
public class FunctionalCustomInteract extends FunctionalAction {

	/**
	 * The first element of the action
	 */
	private FunctionalElement element;
	
	/**
	 * The name of the custom action
	 */
	private String customActionName;
	
	/**
	 * The other element of the action
	 */
	private FunctionalElement anotherElement;
	
	/**
	 * The total time of the action
	 */
	private long totalTime;
	
	/**
	 * Default constructor for a custom interact action
	 * 
	 * @param element The first element of the action
	 * @param customActionName The name of the action
	 */
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
		            if( !item1.customInteract(customActionName, item2) && !item2.customInteract(customActionName, item1) ) {
		                if (functionalPlayer.isAlwaysSynthesizer())
		                	functionalPlayer.speakWithFreeTTS(GameText.getTextCustomCannot(),functionalPlayer.getPlayerVoice());
		                else
		                	functionalPlayer.speak( GameText.getTextCustomCannot() );
		            }
	            }
	            if (anotherElement instanceof FunctionalNPC) {
	            	FunctionalNPC npc = (FunctionalNPC) anotherElement;
	            	if (!item1.customInteract(customActionName, npc)) {
	            		if (functionalPlayer.isAlwaysSynthesizer())
	            			functionalPlayer.speakWithFreeTTS(GameText.getTextCustomCannot(), functionalPlayer.getPlayerVoice());
	            		else
	            			functionalPlayer.speak( GameText.getTextCustomCannot() );
	            	}
	            }
	            finished = true;
	        }
        }
	}

}