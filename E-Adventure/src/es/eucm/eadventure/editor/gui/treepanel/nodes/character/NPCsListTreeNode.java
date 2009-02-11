package es.eucm.eadventure.editor.gui.treepanel.nodes.character;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.character.NPCDataControl;
import es.eucm.eadventure.editor.control.controllers.character.NPCsListDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.character.NPCsListPanel;
import es.eucm.eadventure.editor.gui.treepanel.nodes.TreeNode;

public class NPCsListTreeNode extends TreeNode {

	/**
	 * Contained micro-controller.
	 */
	private NPCsListDataControl dataControl;

	/**
	 * The icon for this node class.
	 */
	private static Icon icon;

	/**
	 * Loads the icon of the node class.
	 */
	public static void loadIcon( ) {
		icon = new ImageIcon( "img/icons/npcs.png" );;
	}

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            Parent node
	 * @param dataControl
	 *            List of NPCs
	 */
	public NPCsListTreeNode( TreeNode parent, NPCsListDataControl dataControl ) {
		super( parent );
		this.dataControl = dataControl;

		for( NPCDataControl npcDataControl : dataControl.getNPCs( ) )
			children.add( new NPCTreeNode( this, npcDataControl ) );
	}

	@Override
	public TreeNode checkForNewChild( int type ) {
		TreeNode addedTreeNode = null;

		// If a NPC was added
		if( type == Controller.NPC ) {
			// Add the last scene of the list
			addedTreeNode = new NPCTreeNode( this, dataControl.getLastNPC( ) );
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
		return Controller.NPCS_LIST;
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
		return new NPCsListPanel( dataControl );
	}
	
	@Override
	public TreeNode isObjectTreeNode(Object object) {
		if (dataControl == object)
			return this;
		return null;
	}
}
