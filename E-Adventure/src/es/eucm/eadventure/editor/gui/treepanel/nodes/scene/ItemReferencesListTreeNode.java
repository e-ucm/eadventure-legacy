package es.eucm.eadventure.editor.gui.treepanel.nodes.scene;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ElementReferenceDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ItemReferencesListDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.scene.ItemReferencesListPanel;
import es.eucm.eadventure.editor.gui.treepanel.nodes.TreeNode;

public class ItemReferencesListTreeNode extends TreeNode {

	/**
	 * Contained micro-controller.
	 */
	private ItemReferencesListDataControl dataControl;

	/**
	 * The icon for this node class.
	 */
	private static Icon icon;

	/**
	 * Loads the icon of the node class.
	 */
	public static void loadIcon( ) {
		icon = new ImageIcon( "img/icons/itemReferences.png" );
	}

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            Parent node
	 * @param dataControl
	 *            List of item references
	 */
	public ItemReferencesListTreeNode( TreeNode parent, ItemReferencesListDataControl dataControl ) {
		super( parent );
		this.dataControl = dataControl;

		for( ElementReferenceDataControl itemReferenceDataControl : dataControl.getItemReferences( ) )
			children.add( new ItemReferenceTreeNode( this, itemReferenceDataControl ) );
	}

	@Override
	public TreeNode checkForNewChild( int type ) {
		TreeNode addedTreeNode = null;

		// If an item reference was added
		if( type == Controller.ITEM_REFERENCE ) {
			// Add the last item reference of the list
			addedTreeNode = new ItemReferenceTreeNode( this, dataControl.getLastItemReference( ) );
			children.add( addedTreeNode );

			// Spread the owner panel to the children
			spreadOwnerPanel( );
		}

		// Return the node created
		return addedTreeNode;
	}

	@Override
	public void checkForDeletedReferences( ) {
		// If some item reference is missing, delete it
		int i = 0;
		while( i < children.size( ) )
			if( i == dataControl.getItemReferences( ).size( ) || !children.get( i ).getDataControl( ).equals( dataControl.getItemReferences( ).get( i ) ) )
				children.remove( i );
			else
				i++;
	}

	@Override
	protected int getNodeType( ) {
		return Controller.ITEM_REFERENCES_LIST;
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
		return new ItemReferencesListPanel( dataControl );
	}
}
