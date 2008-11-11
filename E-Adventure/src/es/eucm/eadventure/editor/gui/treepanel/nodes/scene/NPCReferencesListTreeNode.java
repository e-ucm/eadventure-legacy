package es.eucm.eadventure.editor.gui.treepanel.nodes.scene;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ElementReferenceDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.NPCReferencesListDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.scene.NPCReferencesListPanel;
import es.eucm.eadventure.editor.gui.treepanel.nodes.TreeNode;

public class NPCReferencesListTreeNode extends TreeNode {

	/**
	 * Contained micro-controller.
	 */
	private NPCReferencesListDataControl dataControl;

	/**
	 * The icon for this node class.
	 */
	private static Icon icon;

	/**
	 * Loads the icon of the node class.
	 */
	public static void loadIcon( ) {
		icon = new ImageIcon( "img/icons/npcReferences.png" );
	}

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            Parent node
	 * @param dataControl
	 *            List of NPC references
	 */
	public NPCReferencesListTreeNode( TreeNode parent, NPCReferencesListDataControl dataControl ) {
		super( parent );
		this.dataControl = dataControl;

		for( ElementReferenceDataControl npcReferenceDataControl : dataControl.getNPCReferences( ) )
			children.add( new NPCReferenceTreeNode( this, npcReferenceDataControl ) );
	}

	@Override
	public TreeNode checkForNewChild( int type ) {
		TreeNode addedTreeNode = null;

		// If a NPC reference was added
		if( type == Controller.NPC_REFERENCE ) {
			// Add the last NPC reference of the list
			addedTreeNode = new NPCReferenceTreeNode( this, dataControl.getLastNPCReference( ) );
			children.add( addedTreeNode );

			// Spread the owner panel to the children
			spreadOwnerPanel( );
		}

		// Return the node created
		return addedTreeNode;
	}

	@Override
	public void checkForDeletedReferences( ) {
		// If some character reference is missing, delete it
		int i = 0;
		while( i < children.size( ) )
			if( i == dataControl.getNPCReferences( ).size( ) || !children.get( i ).getDataControl( ).equals( dataControl.getNPCReferences( ).get( i ) ) )
				children.remove( i );
			else
				i++;
	}

	@Override
	protected int getNodeType( ) {
		return Controller.NPC_REFERENCES_LIST;
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
		return new NPCReferencesListPanel( dataControl );
	}
}
