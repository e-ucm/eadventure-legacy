package es.eucm.eadventure.editor.control.tools.assessment;

import java.util.List;

import es.eucm.eadventure.common.data.assessment.AssessmentProperty;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class AddAssessmentPropertyTool extends Tool{

	protected AssessmentProperty propertyAdded;
	
	protected List<AssessmentProperty> parent;
	
	protected int index;
	
	public AddAssessmentPropertyTool (List<AssessmentProperty> parent, int index){
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
		propertyAdded = new AssessmentProperty("PropertyId", 0);
		parent.add( index,  propertyAdded);
		return true;
	}

	@Override
	public boolean redoTool() {
		parent.add(index, propertyAdded);
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		parent.remove(index);
		Controller.getInstance().updatePanel();
		return true;
	}

}
