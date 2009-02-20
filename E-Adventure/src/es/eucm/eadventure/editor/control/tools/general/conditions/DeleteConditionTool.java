package es.eucm.eadventure.editor.control.tools.general.conditions;

import java.util.List;

import es.eucm.eadventure.common.data.chapter.conditions.Condition;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

/**
 * Edition tool for deleting a condition
 * @author Javier
 *
 */
public class DeleteConditionTool extends Tool{
	protected List<Condition> conditions;
	
	protected Condition conditionToDelete;
	
	protected VarFlagSummary varFlagSummary;
	
	protected int conditionIndex;
	
	public DeleteConditionTool (List<Condition> conditions, int conditionIndex,VarFlagSummary varFlagSummary){
		this.conditions = conditions;
		this.varFlagSummary = varFlagSummary;
		this.conditionIndex = conditionIndex;
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
		String conditionId = conditions.get( conditionIndex ).getId( );
		conditionToDelete = conditions.get( conditionIndex );
		conditions.remove( conditionIndex );
		varFlagSummary.deleteReference(conditionId);
		return true;
	}

	@Override
	public boolean redoTool() {
		conditions.remove( conditionToDelete );
		varFlagSummary.deleteReference(conditionToDelete.getId());
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		conditions.add(conditionIndex, conditionToDelete);
		String conditionId = conditionToDelete.getId( );
		varFlagSummary.addReference(conditionId);
		Controller.getInstance().updatePanel();
		return true;
	}
}
