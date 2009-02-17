package es.eucm.eadventure.editor.control.tools.treepanel;

import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.gui.treepanel.TreePanel;

public class RenameElementTool implements Tool {

	private DataControl dataControl;
	
	private TreePanel ownerPanel;
	
	private String oldName;
	
	private String newName;
	
	public RenameElementTool(DataControl dataControl, TreePanel ownerPanel) {
		this.dataControl = dataControl;
		this.ownerPanel = ownerPanel;
	}

	@Override
	public boolean canRedo() {
		// TODO Auto-generated method stub
		return false;
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
					ownerPanel.updateTreePanel( );
					return true;
			}
		}
		return false;
	}

	@Override
	public String getToolName() {
		return "Rename element";
	}

	@Override
	public boolean redoTool() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean undoTool() {
		if( dataControl.canBeRenamed( )) {
			newName = dataControl.renameElement( oldName );
			if (newName != null) {
				ownerPanel.updateTreePanel( );
				return true;
			}
		}		
		return false;
	}

}
