package es.eucm.eadventure.editor.gui.treepanel.nodes.character;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.character.ConversationReferenceDataControl;
import es.eucm.eadventure.editor.control.controllers.character.ConversationReferencesListDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.character.ConversationReferencesListPanel;
import es.eucm.eadventure.editor.gui.treepanel.nodes.TreeNode;

public class ConversationReferencesListTreeNode extends TreeNode {

	/**
	 * Contained micro-controller.
	 */
	private ConversationReferencesListDataControl dataControl;

	/**
	 * The icon for this node class.
	 */
	private static Icon icon;

	/**
	 * Loads the icon of the node class.
	 */
	public static void loadIcon( ) {
		icon = new ImageIcon( "img/icons/conversationReferences.png" );;
	}

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            Parent node
	 * @param dataControl
	 *            List of conversation references
	 */
	public ConversationReferencesListTreeNode( TreeNode parent, ConversationReferencesListDataControl dataControl ) {
		super( parent );
		this.dataControl = dataControl;

		for( ConversationReferenceDataControl conversationReferenceDataControl : dataControl.getConversationReferences( ) )
			children.add( new ConversationReferenceTreeNode( this, conversationReferenceDataControl ) );
	}

	@Override
	public TreeNode checkForNewChild( int type ) {
		TreeNode addedTreeNode = null;

		// If a conversation reference was added
		if( type == Controller.CONVERSATION_REFERENCE ) {
			// Add the last item reference of the list
			addedTreeNode = new ConversationReferenceTreeNode( this, dataControl.getLastConversationReference( ) );
			children.add( addedTreeNode );

			// Spread the owner panel to the children
			spreadOwnerPanel( );
		}

		// Return the node created
		return addedTreeNode;
	}

	@Override
	public void checkForDeletedReferences( ) {
		// If some conversation is missing, delete it
		int i = 0;
		while( i < children.size( ) )
			if( i == dataControl.getConversationReferences( ).size( ) || !children.get( i ).getDataControl( ).equals( dataControl.getConversationReferences( ).get( i ) ) )
				children.remove( i );
			else
				i++;
	}

	@Override
	protected int getNodeType( ) {
		return Controller.CONVERSATION_REFERENCES_LIST;
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
		return new ConversationReferencesListPanel( dataControl );
	}
	
	@Override
	public TreeNode isObjectTreeNode(Object object) {
		if (dataControl == object)
			return this;
		return null;
	}

	@Override
	public TreeNode isObjectContentTreeNode(Object object) {
		if (dataControl.getContent() == object)
			return this;
		return null;
	}

}
