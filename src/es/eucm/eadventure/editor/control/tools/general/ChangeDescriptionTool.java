package es.eucm.eadventure.editor.control.tools.general;

import es.eucm.eadventure.common.data.Described;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class ChangeDescriptionTool implements Tool {

	private Described described;
	
	private String description;
	
	private String oldDescription;
	
	private Controller controller;
	
	public ChangeDescriptionTool(Described described, String description) {
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
		if( !description.equals( described.getDescription( ) ) ) {
			oldDescription = described.getDescription();
			described.setDescription( description );
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
		described.setDescription( description );
		controller.reloadPanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		described.setDescription( oldDescription );
		controller.reloadPanel();
		return true;
	}

	@Override
	public boolean combine(Tool other) {
		return false;
	}

}
