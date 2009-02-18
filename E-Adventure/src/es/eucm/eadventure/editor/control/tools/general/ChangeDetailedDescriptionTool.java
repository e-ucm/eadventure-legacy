package es.eucm.eadventure.editor.control.tools.general;

import es.eucm.eadventure.common.data.Detailed;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class ChangeDetailedDescriptionTool implements Tool {

	private Detailed described;
	
	private String description;
	
	private String oldDescription;
	
	private Controller controller;
	
	public ChangeDetailedDescriptionTool(Detailed described, String description) {
		this.described = described;
		this.description = description;
		this.controller = Controller.getInstance();
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
	public boolean doTool() {
		if( !description.equals( described.getDetailedDescription( ) ) ) {
			oldDescription = described.getDetailedDescription();
			described.setDetailedDescription( description );
			return true;
		}
		return false;
	}

	@Override
	public String getToolName() {
		return "Change description";
	}

	@Override
	public boolean redoTool() {
		described.setDetailedDescription( description );
		controller.reloadPanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		described.setDetailedDescription( oldDescription );
		controller.reloadPanel();
		return true;
	}

	@Override
	public boolean combine(Tool other) {
		return false;
	}

}
