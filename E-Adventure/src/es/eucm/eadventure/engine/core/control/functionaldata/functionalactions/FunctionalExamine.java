package es.eucm.eadventure.engine.core.control.functionaldata.functionalactions;

import es.eucm.eadventure.common.data.chapter.Action;
import es.eucm.eadventure.engine.core.control.ActionManager;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalElement;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalPlayer;

public class FunctionalExamine extends FunctionalAction {

	FunctionalElement element;
	
	public FunctionalExamine(Action action, FunctionalElement element) {
		super(action);
		type = ActionManager.ACTION_EXAMINE;
		this.element = element;
		this.needsGoTo = true;
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
		if( !element.examine( ) ) 
			functionalPlayer.speak( element.getElement( ).getDetailedDescription( ) );
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
