package es.eucm.eadventure.editor.control.tools.general.conditions;

import java.util.List;

import es.eucm.eadventure.common.data.chapter.conditions.Condition;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class ReplaceConditionTool extends Tool{
	protected List<Condition> conditions;
	
	protected Condition newCondition;
	
	protected Condition oldCondition;
	
	protected int conditionIndex;
	
	protected VarFlagSummary varFlagSummary;
	
	public ReplaceConditionTool (List<Condition> conditions, Condition conditionToAdd, int conditionIndex, VarFlagSummary varFlagSummary){
		this.conditions = conditions;
		this.newCondition = conditionToAdd;
		this.conditionIndex = conditionIndex;
		this.varFlagSummary = varFlagSummary;
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
		oldCondition = conditions.remove(conditionIndex);
		conditions.add(conditionIndex, newCondition);
		// Update references
		varFlagSummary.deleteReference(oldCondition.getId());
		varFlagSummary.addReference(newCondition.getId());

		return true;
	}

	@Override
	public boolean redoTool() {
		conditions.remove(conditionIndex);
		conditions.add(conditionIndex, newCondition);
		// Update references
		varFlagSummary.deleteReference(oldCondition.getId());
		varFlagSummary.addReference(newCondition.getId());

		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		// Delete the added condition
		conditions.remove(conditionIndex);
		conditions.add(conditionIndex, oldCondition);
		// Update references
		varFlagSummary.deleteReference(newCondition.getId());
		varFlagSummary.addReference(oldCondition.getId());
		Controller.getInstance().updatePanel();
		return true;
	}
}
