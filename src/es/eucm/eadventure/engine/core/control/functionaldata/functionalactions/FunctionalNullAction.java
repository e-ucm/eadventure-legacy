package es.eucm.eadventure.engine.core.control.functionaldata.functionalactions;

import es.eucm.eadventure.common.data.chapter.Action;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalElement;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalPlayer;

public class FunctionalNullAction extends FunctionalAction {

	public FunctionalNullAction(Action action) {
		super(action);
		finished = true;
		type = -1;
	}

	@Override
	public void start(FunctionalPlayer functionalPlayer) {
		this.functionalPlayer = functionalPlayer;
	}

	@Override
	public void update(long elapsedTime) {
	}

	@Override
	public void drawAditionalElements() {
	}

	@Override
	public void stop() {
	}

	@Override
	public void setAnotherElement(FunctionalElement element) {
	}

}
