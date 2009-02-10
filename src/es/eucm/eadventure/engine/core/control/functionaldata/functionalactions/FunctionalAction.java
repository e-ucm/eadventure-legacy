package es.eucm.eadventure.engine.core.control.functionaldata.functionalactions;

import es.eucm.eadventure.common.data.chapter.Action;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalElement;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalPlayer;

public abstract class FunctionalAction {
	
	protected Action originalAction;
	
	protected FunctionalPlayer functionalPlayer;
	
	protected boolean finished = false;
	
	protected boolean requiersAnotherElement = false;
	
	protected boolean needsGoTo = false;
	
	protected int type;
	
	protected int keepDistance = 0;
	
	public FunctionalAction(Action action) {
		originalAction = action;
	}
	
	public abstract void start(FunctionalPlayer functionalPlayer);
	
	public boolean isStarted() {
		return functionalPlayer != null;
	}
	
	public boolean isRequiersAnotherElement() {
		return requiersAnotherElement;
	}
	
	public boolean isFinished() {
		return finished;	
	}
	
	public boolean isNeedsGoTo() {
		return needsGoTo;
	}
	
	public abstract void update(long elapsedTime);
	
	public abstract void setAnotherElement(FunctionalElement element);
	
	public int getType() {
		return type;
	}
	
	public abstract void stop();
	
	public abstract void drawAditionalElements();
	
	public int getKeepDistance() {
		return keepDistance;
	}

	public void setKeepDistance(int keepDistance) {
		this.keepDistance = keepDistance;
	}

	public FunctionalElement getAnotherElement() {
		return null;
	}
}