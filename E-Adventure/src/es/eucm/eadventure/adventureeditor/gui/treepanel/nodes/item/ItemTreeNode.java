package es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.item;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import es.eucm.eadventure.adventureeditor.control.Controller;
import es.eucm.eadventure.adventureeditor.control.controllers.DataControl;
import es.eucm.eadventure.adventureeditor.control.controllers.general.ResourcesDataControl;
import es.eucm.eadventure.adventureeditor.control.controllers.item.ItemDataControl;
import es.eucm.eadventure.adventureeditor.gui.elementpanels.item.ItemPanel;
import es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.TreeNode;
import es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.general.ActionsListTreeNode;

//import es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.general.ResourcesTreeNode;

public class ItemTreeNode extends TreeNode {

	/**
	 * Contained micro-controller.
	 */
	private ItemDataControl dataControl;

	/**
	 * The icon for this node class.
	 */
	private static Icon icon;

	/**
	 * Loads the icon of the node class.
	 */
	public static void loadIcon( ) {
		icon = new ImageIcon( "img/icons/item.png" );;
	}

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            Parent node
	 * @param dataControl
	 *            Item to be contained
	 */
	public ItemTreeNode( TreeNode parent, ItemDataControl dataControl ) {
		super( parent );
		this.dataControl = dataControl;

		//for( ResourcesDataControl resourcesDataControl : dataControl.getResources( ) )
		//	children.add( new ResourcesTreeNode( this, resourcesDataControl ) );

		children.add( new ActionsListTreeNode( this, dataControl.getActionsList( ) ) );
	}

	@Override
	public TreeNode checkForNewChild( int type ) {
		TreeNode addedTreeNode = null;

		// If a resources block was added
		/*if( type == Controller.RESOURCES ) {
			// Add the new resources node to last place of the resources elements
			addedTreeNode = new ResourcesTreeNode( this, dataControl.getLastResources( ) );
			children.add( dataControl.getResources( ).size( ) - 1, addedTreeNode );

			// Spread the owner panel to the children
			spreadOwnerPanel( );
		}*/

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
		return Controller.ITEM;
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
		return new ItemPanel( dataControl );
	}

	@Override
	public String toString( ) {
		return dataControl.getId( );
	}
}