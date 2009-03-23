package es.eucm.eadventure.editor.control.tools.assessment;

import es.eucm.eadventure.common.data.assessment.TimedAssessmentRule;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class ChangeUsesEndCondition extends Tool {

	private TimedAssessmentRule assessmentRule;
	
	private boolean newValue;
	
	private boolean oldValue;

	public ChangeUsesEndCondition(TimedAssessmentRule assessmentRule, boolean value) {
		this.assessmentRule = assessmentRule;
		this.newValue = value;
		this.oldValue = assessmentRule.isUsesEndConditions();
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
		return false;
	}

	@Override
	public boolean doTool() {
		if (newValue == oldValue)
			return false;
		assessmentRule.setUsesEndConditions(newValue);
		return true;
	}

	@Override
	public boolean redoTool() {
		assessmentRule.setUsesEndConditions(newValue);
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		assessmentRule.setUsesEndConditions(oldValue);
		Controller.getInstance().updatePanel();
		return true;
	}

}
