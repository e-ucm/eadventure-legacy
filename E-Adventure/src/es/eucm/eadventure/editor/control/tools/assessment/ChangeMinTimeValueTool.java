package es.eucm.eadventure.editor.control.tools.assessment;

import es.eucm.eadventure.common.data.assessment.TimedAssessmentEffect;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class ChangeMinTimeValueTool extends Tool {

	private TimedAssessmentEffect effect;

	private int newMinTime;
	
	private int oldMinTime;
	
	private int newMaxTime;
	
	private int oldMaxTime;
	
	public ChangeMinTimeValueTool(TimedAssessmentEffect timedAssessmentEffect,
			int time) {
		this.effect = timedAssessmentEffect;
		this.newMinTime = time;
		this.oldMinTime = effect.getMinTime();
		this.oldMaxTime = effect.getMaxTime();
		this.newMaxTime = (oldMaxTime > newMinTime ? oldMaxTime : newMinTime + 1);
	}

	@Override
	public boolean canRedo() {
		return true;
	}

	@Override
	public boolean canUndo() {
		return true;
	}

	@Override
	public boolean combine(Tool other) {
		if (other instanceof ChangeMinTimeValueTool) {
			ChangeMinTimeValueTool cmtvt = (ChangeMinTimeValueTool) other;
			if (cmtvt.effect == effect) {
				this.newMinTime = cmtvt.newMinTime;
				this.newMaxTime = cmtvt.newMaxTime;
				this.timeStamp = cmtvt.timeStamp;
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean doTool() {
		effect.setMaxTime(newMaxTime);
		effect.setMinTime(newMinTime);
		return true;
	}

	@Override
	public boolean redoTool() {
		effect.setMaxTime(newMaxTime);
		effect.setMinTime(newMinTime);
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		effect.setMaxTime(oldMaxTime);
		effect.setMinTime(oldMinTime);
		Controller.getInstance().updatePanel();
		return true;
	}

}
