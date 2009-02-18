package es.eucm.eadventure.editor.control.tools.general;

import es.eucm.eadventure.common.data.Detailed;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class ChangeDetailedDescriptionTool extends Tool {

	private Detailed detailed;
	
	private String description;
	
	private String oldDescription;
	
	private Controller controller;
	
	public ChangeDetailedDescriptionTool(Detailed described, String description) {
		this.detailed = described;
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
		if( !description.equals( detailed.getDetailedDescription( ) ) ) {
			oldDescription = detailed.getDetailedDescription();
			detailed.setDetailedDescription( description );
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
		detailed.setDetailedDescription( description );
		controller.reloadPanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		detailed.setDetailedDescription( oldDescription );
		controller.reloadPanel();
		return true;
	}

	@Override
	public boolean combine(Tool other) {
		if (other instanceof ChangeDetailedDescriptionTool) {
			ChangeDetailedDescriptionTool cnt = (ChangeDetailedDescriptionTool) other;
			if (cnt.detailed == detailed && cnt.oldDescription == description) {
				description = cnt.description;
				timeStamp = cnt.timeStamp;
				return true;
			}
		}
		return false;
	}

}
