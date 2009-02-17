package es.eucm.eadventure.editor.gui.treepanel.nodes.item;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.item.ItemDataControl;
import es.eucm.eadventure.editor.control.controllers.item.ItemsListDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.item.ItemsListPanel;
import es.eucm.eadventure.editor.gui.treepanel.nodes.TreeNode;

public class ItemsListTreeNode extends TreeNode {

	/**
	 * Contained micro-controller.
	 */
	private ItemsListDataControl dataControl;

	/**
	 * The icon for this node class.
	 */
	private static Icon icon;

	/**
	 * Loads the icon of the node class.
	 */
	public static void loadIcon( ) {
		icon = new ImageIcon( "img/icons/items.png" );;
	}

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            Parent node
	 * @param dataControl
	 *            List of items
	 */
	public ItemsListTreeNode( TreeNode parent, ItemsListDataControl dataControl ) {
		super( parent );
		this.dataControl = dataControl;

		for( ItemDataControl itemDataControl : dataControl.getItems( ) )
			children.add( new ItemTreeNode( this, itemDataControl ) );
	}

	@Override
	public TreeNode checkForNewChild( int type ) {
		TreeNode addedTreeNode = null;

		// If an item was added
		if( type == Controller.ITEM ) {
			// Add the last scene of the list
			addedTreeNode = new ItemTreeNode( this, dataControl.getLastItem( ) );
			children.add( addedTreeNode );

			// Spread the owner panel to the children
			spreadOwnerPanel( );
		}

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
		return Controller.ITEMS_LIST;
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
		return new ItemsListPanel( dataControl );
	}
	
	@Override
	public TreeNode isObjectTreeNode(Object object) {
		if (dataControl == object)
			return this;
		return null;
	}
	
	@Override
	public TreeNode isObjectContentTreeNode(Object object) {
		if (dataControl.getContent() == object)
			return this;
		return null;
	}

}
