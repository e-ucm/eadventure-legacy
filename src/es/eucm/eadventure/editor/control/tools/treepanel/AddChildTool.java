package es.eucm.eadventure.editor.control.tools.treepanel;

import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.gui.treepanel.TreePanel;
import es.eucm.eadventure.editor.gui.treepanel.nodes.TreeNode;

public class AddChildTool implements Tool {

	private TreeNode treeNode;
	
	private int type;
	
	private TreePanel ownerPanel;
	
	private TreeNode addedTreeNode;
	
	public AddChildTool(TreeNode treeNode, int type, TreePanel ownerPanel) {
		this.treeNode = treeNode;
		this.type = type;
		this.ownerPanel = ownerPanel;
	}

	@Override
	public boolean canUndo() {
		return true;
	}

	@Override
	public boolean doTool() {
		if( treeNode.getDataControl( ).canAddElement( type ) && treeNode.getDataControl( ).addElement( type ) ) {
			addedTreeNode = treeNode.checkForNewChild( type );
			ownerPanel.updateTreePanel( );
			ownerPanel.selectChildOfSelectedElement( addedTreeNode );
			return true;
		}
		return false;
	}

	@Override
	public String getToolName() {
		return "Add child";
	}

	@Override
	public boolean undoTool() {
		addedTreeNode.delete(false);
		return true;
	}
	
	public boolean canRedo() {
		return false;
	}
	
	@Override
	public boolean redoTool() {
		return false;
	}

}
