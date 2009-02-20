package es.eucm.eadventure.editor.control.tools.general.conditions;

import java.util.List;

import es.eucm.eadventure.common.data.chapter.conditions.Condition;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

/**
 * Edition Tool for adding a new condition
 * @author Javier
 *
 */
public class AddConditionTool extends Tool{

	protected List<Condition> conditions;
	
	protected Condition conditionToAdd;
	
	protected VarFlagSummary varFlagSummary;
	
	public AddConditionTool (List<Condition> conditions, Condition conditionToAdd, VarFlagSummary varFlagSummary){
		this.conditions = conditions;
		this.conditionToAdd = conditionToAdd;
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
		conditions.add(conditionToAdd);
		varFlagSummary.addReference(conditionToAdd.getId());
		return true;
	}

	@Override
	public boolean redoTool() {
		conditions.add(conditionToAdd);
		varFlagSummary.addReference(conditionToAdd.getId());
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		// Delete the added condition
		conditions.remove(conditionToAdd);
		varFlagSummary.deleteReference(conditionToAdd.getId());
		Controller.getInstance().updatePanel();
		return true;
	}

}
