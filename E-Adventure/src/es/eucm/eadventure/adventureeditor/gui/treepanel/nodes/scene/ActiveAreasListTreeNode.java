package es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.scene;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import es.eucm.eadventure.adventureeditor.control.Controller;
import es.eucm.eadventure.adventureeditor.control.controllers.DataControl;
import es.eucm.eadventure.adventureeditor.control.controllers.scene.ActiveAreaDataControl;
import es.eucm.eadventure.adventureeditor.control.controllers.scene.ActiveAreasListDataControl;
import es.eucm.eadventure.adventureeditor.gui.elementpanels.scene.ActiveAreasListPanel;
import es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.TreeNode;

public class ActiveAreasListTreeNode extends TreeNode {

	/**
	 * Contained micro-controller.
	 */
	private ActiveAreasListDataControl dataControl;

	/**
	 * The icon for this node class.
	 */
	private static Icon icon;

	/**
	 * Loads the icon of the node class.
	 */
	public static void loadIcon( ) {
		icon = new ImageIcon( "img/icons/activeAreas.png" );
	}

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            Parent node
	 * @param dataControl
	 *            List of activeAreas
	 */
	public ActiveAreasListTreeNode( TreeNode parent, ActiveAreasListDataControl dataControl ) {
		super( parent );
		this.dataControl = dataControl;

		for( ActiveAreaDataControl activeAreaDataControl : dataControl.getActiveAreas( ) )
			children.add( new ActiveAreaTreeNode( this, activeAreaDataControl ) );
	}

	@Override
	public TreeNode checkForNewChild( int type ) {
		TreeNode addedTreeNode = null;

		// If an activeArea was added
		if( type == Controller.ACTIVE_AREA ) {
			// Add the last activeArea of the list
			addedTreeNode = new ActiveAreaTreeNode( this, dataControl.getLastActiveArea( ) );
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
			if( i == dataControl.getActiveAreas( ).size( ) || !children.get( i ).getDataControl( ).equals( dataControl.getActiveAreas( ).get( i ) ) )
				children.remove( i );
			else
				i++;

		// Spread the call to the surviving children
		for( TreeNode treeNode : children )
			treeNode.checkForDeletedReferences( );
	}

	@Override
	protected int getNodeType( ) {
		return Controller.ACTIVE_AREAS_LIST;
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
		return new ActiveAreasListPanel( dataControl );
	}
}
