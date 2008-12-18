package es.eucm.eadventure.editor.gui.treepanel.nodes.character;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.character.PlayerDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.character.PlayerPanel;
import es.eucm.eadventure.editor.gui.treepanel.nodes.TreeNode;

//import es.eucm.eadventure.editor.gui.treepanel.nodes.general.ResourcesTreeNode;

public class PlayerTreeNode extends TreeNode {

	/**
	 * Contained micro-controller.
	 */
	private PlayerDataControl dataControl;

	/**
	 * The icon for this node class.
	 */
	private static Icon icon;

	/**
	 * Loads the icon of the node class.
	 */
	public static void loadIcon( ) {
		icon = new ImageIcon( "img/icons/player.png" );;
	}

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            Parent node
	 * @param dataControl
	 *            Player to be contained
	 */
	public PlayerTreeNode( TreeNode parent, PlayerDataControl dataControl ) {
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
			children.add( addedTreeNode );

			// Spread the owner panel to the children
			spreadOwnerPanel( );
		}*/

		// Return the node created
		return addedTreeNode;
	}

	@Override
	public void checkForDeletedReferences( ) {
	// Do nothing
	}

	@Override
	protected int getNodeType( ) {
		return Controller.PLAYER;
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
		return new PlayerPanel( dataControl );
	}
}