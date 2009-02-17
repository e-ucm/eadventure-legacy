package es.eucm.eadventure.editor.control.tools.treepanel;

import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.gui.treepanel.TreePanel;
import es.eucm.eadventure.editor.gui.treepanel.nodes.TreeNode;

public class MoveTreeNodeDownTool implements Tool {


	private DataControl dataControl;
	
	private TreeNode parent;
	
	private TreePanel ownerPanel;
	
	private TreeNode treeNode;
	
	public MoveTreeNodeDownTool(TreeNode treeNode, DataControl dataControl, TreeNode parent, TreePanel ownerPanel) {
		this.treeNode = treeNode;
		this.dataControl = dataControl;
		this.parent = parent;
		this.ownerPanel = ownerPanel;
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
		if( dataControl.canBeMoved( ) && parent.getDataControl( ).moveElementDown( dataControl ) ) {
			// If the element was moved, move the child node as well
			int index = parent.getChildren().indexOf( treeNode );
			parent.getChildren().add( index + 1, parent.getChildren().remove( index ) );

			// Update the tree panel and the row selected
			ownerPanel.updateTreePanel( );
			ownerPanel.updateSelectedRow( );
			return true;
		}
		return false;
	}

	@Override
	public String getToolName() {
		return "Move tree node up";
	}

	@Override
	public boolean redoTool() {
		if( dataControl.canBeMoved( ) && parent.getDataControl( ).moveElementDown( dataControl ) ) {
			// If the element was moved, move the child node as well
			int index = parent.getChildren().indexOf( treeNode );
			parent.getChildren().add( index + 1, parent.getChildren().remove( index ) );

			// Update the tree panel and the row selected
			ownerPanel.updateTreePanel( );
			ownerPanel.updateSelectedRow( );
			return true;
		}
		return false;
	}

	@Override
	public boolean undoTool() {
		if( dataControl.canBeMoved( ) && parent.getDataControl( ).moveElementUp( dataControl ) ) {
			int index = parent.getChildren().indexOf( treeNode );
			parent.getChildren().add( index - 1, parent.getChildren().remove( index ) );

			ownerPanel.updateTreePanel( );
			ownerPanel.updateSelectedRow( );
			return true;
		}
		return false;
	}

}
