package es.eucm.eadventure.editor.gui.treepanel.nodes.scene;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.scene.BarrierDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.BarriersListDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.scene.BarriersListPanel;
import es.eucm.eadventure.editor.gui.treepanel.nodes.TreeNode;

public class BarriersListTreeNode extends TreeNode {

	/**
	 * Contained micro-controller.
	 */
	private BarriersListDataControl dataControl;

	/**
	 * The icon for this node class.
	 */
	private static Icon icon;

	/**
	 * Loads the icon of the node class.
	 */
	public static void loadIcon( ) {
		icon = new ImageIcon( "img/icons/barriers.png" );
	}

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            Parent node
	 * @param dataControl
	 *            List of activeAreas
	 */
	public BarriersListTreeNode( TreeNode parent, BarriersListDataControl dataControl ) {
		super( parent );
		this.dataControl = dataControl;

		for( BarrierDataControl activeAreaDataControl : dataControl.getBarriers( ) )
			children.add( new BarrierTreeNode( this, activeAreaDataControl ) );
	}

	@Override
	public TreeNode checkForNewChild( int type ) {
		TreeNode addedTreeNode = null;

		// If an activeArea was added
		if( type == Controller.BARRIER ) {
			// Add the last activeArea of the list
			addedTreeNode = new BarrierTreeNode( this, dataControl.getLastBarrier( ) );
			children.add( addedTreeNode );

			// Spread the owner panel to the children
			spreadOwnerPanel( );
		}

		// Return the node created
		return addedTreeNode;
	}

	@Override
	public void checkForDeletedReferences( ) {
		// If some activeArea is missing, delete it
		int i = 0;
		while( i < children.size( ) )
			if( i == dataControl.getBarriers( ).size( ) || !children.get( i ).getDataControl( ).equals( dataControl.getBarriers( ).get( i ) ) )
				children.remove( i );
			else
				i++;

		// Spread the call to the surviving children
		for( TreeNode treeNode : children )
			treeNode.checkForDeletedReferences( );
	}

	@Override
	protected int getNodeType( ) {
		return Controller.BARRIERS_LIST;
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
		return new BarriersListPanel( dataControl );
	}
}
