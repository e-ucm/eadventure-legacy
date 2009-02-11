package es.eucm.eadventure.editor.gui.treepanel.nodes.atrezzo;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.atrezzo.AtrezzoDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.atrezzo.AtrezzoPanel;
import es.eucm.eadventure.editor.gui.treepanel.nodes.TreeNode;


public class AtrezzoTreeNode extends TreeNode {


	/**
	 * Contained micro-controller.
	 */
	private AtrezzoDataControl dataControl;

	/**
	 * The icon for this node class.
	 */
	private static Icon icon;

	/**
	 * Loads the icon of the node class.
	 */
	public static void loadIcon( ) {
		icon = new ImageIcon( "img/icons/Atrezzo-1.png" );;
	}

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            Parent node
	 * @param dataControl
	 *            Item to be contained
	 */
	public AtrezzoTreeNode( TreeNode parent, AtrezzoDataControl dataControl ) {
		super( parent );
		this.dataControl = dataControl;

		//for( ResourcesDataControl resourcesDataControl : dataControl.getResources( ) )
		//	children.add( new ResourcesTreeNode( this, resourcesDataControl ) );

		
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
		return Controller.ATREZZO;
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
		return new AtrezzoPanel( dataControl );
	}

	@Override
	public String toString( ) {
		return dataControl.getId( );
	}
	
	@Override
	public TreeNode isObjectTreeNode(Object object) {
		if (dataControl == object)
			return this;
		return null;
	}
	
}
