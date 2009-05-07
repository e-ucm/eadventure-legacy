package es.eucm.eadventure.editor.control.tools.general.conditions;

import es.eucm.eadventure.common.data.chapter.conditions.Condition;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

/**
 * Edition tool for duplicating a condition
 * @author Javier Torrente
 *
 */
public class DuplicateConditionTool extends Tool{

	private Conditions conditions;
	private int index1;
	private int index2;
	
	private Condition duplicate;
	
	public DuplicateConditionTool (Conditions conditions, int index1, int index2){
		this.conditions = conditions;
		this.index1 = index1;
		this.index2 = index2;
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
		try {
			if (duplicate==null)
				duplicate = (Condition)(conditions.get(index1).get(index2).clone());
			conditions.get(index1).add(index2+1, duplicate);
			Controller.getInstance().updateVarFlagSummary();
			Controller.getInstance().updatePanel();
			return true;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean redoTool() {
		return doTool();
	}

	@Override
	public boolean undoTool() {
		conditions.get(index1).remove(index2+1);
		Controller.getInstance().updateVarFlagSummary();
		Controller.getInstance().updatePanel();
		return true;
	}
}
