package es.eucm.eadventure.editor.gui.treepanel.nodes.macro;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.macro.MacroDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.macro.MacroPanel;
import es.eucm.eadventure.editor.gui.treepanel.nodes.TreeNode;

//import es.eucm.eadventure.editor.gui.treepanel.nodes.general.ResourcesTreeNode;

public class MacroTreeNode extends TreeNode {

	/**
	 * Contained micro-controller.
	 */
	private MacroDataControl dataControl;

	/**
	 * The icon for this node class.
	 */
	private static Icon icon;

	/**
	 * Loads the icon of the node class.
	 */
	public static void loadIcon( ) {
		icon = new ImageIcon( "img/icons/macro.png" );
	}

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            Parent node
	 * @param dataControl
	 *            Scene to be contained
	 */
	public MacroTreeNode( TreeNode parent, MacroDataControl dataControl ) {
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
		return Controller.MACRO;
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
		return new MacroPanel( dataControl );
	}

	@Override
	public String toString( ) {
		return TextConstants.getElementName(Controller.MACRO)+":"+dataControl.getId( );
	}
	
}