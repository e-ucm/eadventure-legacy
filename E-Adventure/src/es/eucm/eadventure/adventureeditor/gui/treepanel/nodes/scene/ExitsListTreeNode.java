package es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.scene;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import es.eucm.eadventure.adventureeditor.control.Controller;
import es.eucm.eadventure.adventureeditor.control.controllers.DataControl;
import es.eucm.eadventure.adventureeditor.control.controllers.scene.ExitDataControl;
import es.eucm.eadventure.adventureeditor.control.controllers.scene.ExitsListDataControl;
import es.eucm.eadventure.adventureeditor.gui.elementpanels.scene.ExitsListPanel;
import es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.TreeNode;

public class ExitsListTreeNode extends TreeNode {

	/**
	 * Contained micro-controller.
	 */
	private ExitsListDataControl dataControl;

	/**
	 * The icon for this node class.
	 */
	private static Icon icon;

	/**
	 * Loads the icon of the node class.
	 */
	public static void loadIcon( ) {
		icon = new ImageIcon( "img/icons/exits.png" );
	}

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            Parent node
	 * @param dataControl
	 *            List of exits
	 */
	public ExitsListTreeNode( TreeNode parent, ExitsListDataControl dataControl ) {
		super( parent );
		this.dataControl = dataControl;

		for( ExitDataControl exitDataControl : dataControl.getExits( ) )
			children.add( new ExitTreeNode( this, exitDataControl ) );
	}

	@Override
	public TreeNode checkForNewChild( int type ) {
		TreeNode addedTreeNode = null;

		// If an exit was added
		if( type == Controller.EXIT ) {
			// Add the last exit of the list
			addedTreeNode = new ExitTreeNode( this, dataControl.getLastExit( ) );
			children.add( addedTreeNode );

			// Spread the owner panel to the children
			spreadOwnerPanel( );
		}

		// Return the node created
		return addedTreeNode;
	}

	@Override
	public void checkForDeletedReferences( ) {
		// If some exit is missing, delete it
		int i = 0;
		while( i < children.size( ) )
			if( i == dataControl.getExits( ).size( ) || !children.get( i ).getDataControl( ).equals( dataControl.getExits( ).get( i ) ) )
				children.remove( i );
			else
				i++;

		// Spread the call to the surviving children
		for( TreeNode treeNode : children )
			treeNode.checkForDeletedReferences( );
	}

	@Override
	protected int getNodeType( ) {
		return Controller.EXITS_LIST;
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
		return new ExitsListPanel( dataControl );
	}
}
