package es.eucm.eadventure.editor.control.tools.assessment;

import java.util.List;

import es.eucm.eadventure.common.data.assessment.AssessmentProperty;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class DeleteAssessmentPropertyTool extends Tool{

	protected AssessmentProperty propertyDeleted;
	
	protected List<AssessmentProperty> parent;
	
	protected int index;
	
	public DeleteAssessmentPropertyTool (List<AssessmentProperty> parent, int index){
		this.parent = parent;
		this.index = index;
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
		if (index >=0 && index <parent.size( )){
			propertyDeleted = parent.remove( index );
			return true;
		}
		return false;
	}

	@Override
	public boolean redoTool() {
		parent.remove(index);
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		parent.add(index, propertyDeleted);
		Controller.getInstance().updatePanel();
		return true;
	}

}
