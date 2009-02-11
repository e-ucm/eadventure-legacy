package es.eucm.eadventure.editor.gui.treepanel.nodes.globalstate;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.globalstate.GlobalStateDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.globalstate.GlobalStatePanel;
import es.eucm.eadventure.editor.gui.treepanel.nodes.TreeNode;

//import es.eucm.eadventure.editor.gui.treepanel.nodes.general.ResourcesTreeNode;

public class GlobalStateTreeNode extends TreeNode {

	/**
	 * Contained micro-controller.
	 */
	private GlobalStateDataControl dataControl;

	/**
	 * The icon for this node class.
	 */
	private static Icon icon;

	/**
	 * Loads the icon of the node class.
	 */
	public static void loadIcon( ) {
		icon = new ImageIcon( "img/icons/group16.png" );
	}

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            Parent node
	 * @param dataControl
	 *            Scene to be contained
	 */
	public GlobalStateTreeNode( TreeNode parent, GlobalStateDataControl dataControl ) {
		super( parent );
		this.dataControl = dataControl;
	}

	@Override
	public TreeNode checkForNewChild( int type ) {
		TreeNode addedTreeNode = null;

		// Return the node created
		return addedTreeNode;
	}

	@Override
	public void checkForDeletedReferences( ) {
		// Spread the call to the children
		for( TreeNode treeNode : children )
			treeNode.checkForDeletedReferences( );
	}

	@Override
	protected int getNodeType( ) {
		return Controller.GLOBAL_STATE;
	}

	@Override
	public DataControl getDataControl( ) {
		return dataControl;
	}

	@Override
	public Icon getIcon( ) {
		return icon;
	}

	@Override
	public String getToolTipText( ) {
		return null;
	}

	@Override
	public JComponent getEditPanel( ) {
		return new GlobalStatePanel( dataControl );
	}

	@Override
	public String toString( ) {
		return TextConstants.getElementName(Controller.GLOBAL_STATE)+":"+dataControl.getId( );
	}
	
	@Override
	public TreeNode isObjectTreeNode(Object object) {
		if (dataControl == object)
			return this;
		return null;
	}
}