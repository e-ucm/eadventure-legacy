package es.eucm.eadventure.editor.control.tools.structurepanel;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class RenameElementTool extends Tool {

	private DataControl dataControl;
	
	private String oldName;
	
	private String newName;
		
	public RenameElementTool(DataControl dataControl, String string) {
		this.dataControl = dataControl;
		this.newName = string;
	}

	@Override
	public boolean canRedo() {
		return true;
	}

	@Override
	public boolean canUndo() {
		return (oldName != null);
	}

	@Override
	public boolean doTool() {
		if( dataControl.canBeRenamed( )) {
			oldName = dataControl.renameElement( newName );
			if (oldName != null) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean redoTool() {
		if( dataControl.canBeRenamed( )) {
			oldName = dataControl.renameElement( newName );
			if (oldName != null) {
				Controller.getInstance().updateStructure();
				return true;
			}
		}		
		return false;
	}

	@Override
	public boolean undoTool() {
		if( dataControl.canBeRenamed( )) {
			newName = dataControl.renameElement( oldName );
			if (newName != null) {
				Controller.getInstance().updateStructure();
				return true;
			}
		}		
		return false;
	}
	
	@Override
	public boolean combine(Tool other) {
		return false;
	}


}
