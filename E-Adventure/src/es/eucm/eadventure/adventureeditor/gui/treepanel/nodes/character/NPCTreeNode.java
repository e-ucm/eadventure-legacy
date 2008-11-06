package es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.character;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import es.eucm.eadventure.adventureeditor.control.Controller;
import es.eucm.eadventure.adventureeditor.control.controllers.DataControl;
import es.eucm.eadventure.adventureeditor.control.controllers.character.NPCDataControl;
import es.eucm.eadventure.adventureeditor.control.controllers.general.ResourcesDataControl;
import es.eucm.eadventure.adventureeditor.gui.elementpanels.character.NPCPanel;
import es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.TreeNode;

//import es.eucm.eadventure.adventureeditor.gui.treepanel.nodes.general.ResourcesTreeNode;

public class NPCTreeNode extends TreeNode {

	/**
	 * Contained micro-controller.
	 */
	private NPCDataControl dataControl;

	/**
	 * The icon for this node class.
	 */
	private static Icon icon;

	/**
	 * Loads the icon of the node class.
	 */
	public static void loadIcon( ) {
		icon = new ImageIcon( "img/icons/npc.png" );;
	}

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            Parent node
	 * @param dataControl
	 *            Character to be contained
	 */
	public NPCTreeNode( TreeNode parent, NPCDataControl dataControl ) {
		super( parent );
		this.dataControl = dataControl;

		//for( ResourcesDataControl resourcesDataControl : dataControl.getResources( ) )
		//	children.add( new ResourcesTreeNode( this, resourcesDataControl ) );

		children.add( new ConversationReferencesListTreeNode( this, dataControl.getConversationReferencesList( ) ) );
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
		return Controller.NPC;
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
		return new NPCPanel( dataControl );
	}

	@Override
	public String toString( ) {
		return dataControl.getId( );
	}
}