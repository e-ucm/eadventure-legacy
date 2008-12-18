package es.eucm.eadventure.engine.core.control.functionaldata.functionalactions;

import es.eucm.eadventure.common.data.chapter.Action;
import es.eucm.eadventure.common.data.chapter.elements.Player;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.engine.core.control.ActionManager;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalElement;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalItem;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalPlayer;
import es.eucm.eadventure.engine.core.data.GameText;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

public class FunctionalUseWith extends FunctionalAction {

	private FunctionalElement element;
	
	private FunctionalElement anotherElement;
	
	private long totalTime;
	
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
		this.needsGoTo = true;
		anotherElement = element;
	}

	@Override
	public void start(FunctionalPlayer functionalPlayer) {
		this.functionalPlayer = functionalPlayer;
		this.totalTime = 0;
		this.finished = false;
		
        Resources resources = functionalPlayer.getResources( );
        MultimediaManager multimedia = MultimediaManager.getInstance( );

        if (functionalPlayer.getX() > element.getX())
        	functionalPlayer.setAnimation(multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_USE_RIGHT ), false, MultimediaManager.IMAGE_PLAYER ), -1);
        else 
        	functionalPlayer.setAnimation(multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_USE_RIGHT ), true, MultimediaManager.IMAGE_PLAYER ), -1);

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
	            FunctionalItem item2 = (FunctionalItem) anotherElement;
	    
	            if( !item1.useWith( item2 ) && !item2.useWith( item1 ) )
	                functionalPlayer.speak( GameText.getTextUseCannot( ) );
	            finished = true;
	        }
        }
	}

	public FunctionalElement getAnotherElement() {
		return anotherElement;
	}

}
