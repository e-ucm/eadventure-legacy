package es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.general;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import es.eucm.eadventure.adventureeditor.control.Controller;
import es.eucm.eadventure.adventureeditor.control.controllers.DataControl;
import es.eucm.eadventure.adventureeditor.control.controllers.general.ActionDataControl;
import es.eucm.eadventure.adventureeditor.gui.elementpanels.general.ActionPanel;
import es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.TreeNode;

public class GrabActionTreeNode extends TreeNode {

	/**
	 * Contained micro-controller.
	 */
	private ActionDataControl dataControl;

	/**
	 * The icon for this node class.
	 */
	private static Icon icon;

	/**
	 * Loads the icon of the node class.
	 */
	public static void loadIcon( ) {
		icon = new ImageIcon( "img/icons/action.png" );;
	}

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            Parent node
	 * @param dataControl
	 *            Action to be contained
	 */
	public GrabActionTreeNode( TreeNode parent, ActionDataControl dataControl ) {
		super( parent );
		this.dataControl = dataControl;
	}

	@Override
	public TreeNode checkForNewChild( int type ) {
		// This node can't add children
		return null;
	}

	@Override
	public void checkForDeletedReferences( ) {
	// Do nothing
	}

	@Override
	protected int getNodeType( ) {
		return Controller.ACTION_GRAB;
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
		return new ActionPanel( dataControl );
	}
}