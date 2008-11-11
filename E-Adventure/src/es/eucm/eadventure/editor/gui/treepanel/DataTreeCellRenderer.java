package es.eucm.eadventure.editor.gui.treepanel;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import es.eucm.eadventure.editor.gui.treepanel.nodes.TreeNode;

/**
 * The cell renderer for the tree. It provides with icons for each type of node.
 * 
 * @author Bruno Torijano Bueno
 */
class DataTreeCellRenderer extends DefaultTreeCellRenderer {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.DefaultTreeCellRenderer#getTreeCellRendererComponent(javax.swing.JTree, java.lang.Object,
	 *      boolean, boolean, boolean, int, boolean)
	 */
	public Component getTreeCellRendererComponent( JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus ) {
		super.getTreeCellRendererComponent( tree, value, sel, expanded, leaf, row, hasFocus );

		// Pick the icon and the tool tip text from the selected node
		TreeNode node = (TreeNode) value;
		setIcon( node.getIcon( ) );
		setToolTipText( node.getToolTipText( ) );

		return this;
	}
}
