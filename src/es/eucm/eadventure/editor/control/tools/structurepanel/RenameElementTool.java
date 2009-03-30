package es.eucm.eadventure.editor.control.tools.structurepanel;

import javax.swing.JTable;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class RenameElementTool extends Tool {

	private DataControl dataControl;
	
	private String oldName;
	
	private String newName;
	
	private JTable table;
		
	public RenameElementTool(JTable table, DataControl dataControl) {
		this.dataControl = dataControl;
		this.table = table;
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
			oldName = dataControl.renameElement( null );
			if (oldName != null) {
				int index = table.getSelectedRow();
				table.changeSelection(index, index, false, false);
				table.editCellAt(index, 0);
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
				Controller.getInstance().updateTree();
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
				Controller.getInstance().updateTree();
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
