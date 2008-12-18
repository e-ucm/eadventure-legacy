package es.eucm.eadventure.editor.gui.treepanel.nodes.atrezzo;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.atrezzo.AtrezzoDataControl;
import es.eucm.eadventure.editor.control.controllers.atrezzo.AtrezzoListDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.atrezzo.AtrezzoListPanel;
import es.eucm.eadventure.editor.gui.treepanel.nodes.TreeNode;


public class AtrezzoListTreeNode extends TreeNode {
	
	/**
	 * Contained micro-controller.
	 */
	private AtrezzoListDataControl dataControl;

	/**
	 * The icon for this node class.
	 */
	private static Icon icon;

	/**
	 * Loads the icon of the node class.
	 */
	public static void loadIcon( ) {
		icon = new ImageIcon( "img/icons/Atrezzo-List-1.png" );;
	}

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            Parent node
	 * @param dataControl
	 *            List of items
	 */
	public AtrezzoListTreeNode( TreeNode parent, AtrezzoListDataControl dataControl ) {
		super( parent );
		this.dataControl = dataControl;

		for( AtrezzoDataControl atrezzoDataControl : dataControl.getAtrezzoList() )
			children.add( new AtrezzoTreeNode( this, atrezzoDataControl ) );
	}

	@Override
	public TreeNode checkForNewChild( int type ) {
		TreeNode addedTreeNode = null;

		// If an atrezzo item was added
		if( type == Controller.ATREZZO ) {
			// Add the last scene of the list
			addedTreeNode = new AtrezzoTreeNode( this, dataControl.getLastAtrezzo( ) );
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
		return Controller.ATREZZO_LIST;
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
		return new AtrezzoListPanel( dataControl );
	}

}
