package es.eucm.eadventure.editor.gui.treepanel.nodes.conversation;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.conversation.ConversationDataControl;
import es.eucm.eadventure.editor.control.controllers.conversation.ConversationsListDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.conversation.ConversationsListPanel;
import es.eucm.eadventure.editor.gui.treepanel.nodes.TreeNode;

public class ConversationsListTreeNode extends TreeNode {

	/**
	 * Contained micro-controller.
	 */
	private ConversationsListDataControl dataControl;

	/**
	 * The icon for this node class.
	 */
	private static Icon icon;

	/**
	 * Loads the icon of the node class.
	 */
	public static void loadIcon( ) {
		icon = new ImageIcon( "img/icons/conversations.png" );;
	}

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            Parent node
	 * @param dataControl
	 *            List of conversations
	 */
	public ConversationsListTreeNode( TreeNode parent, ConversationsListDataControl dataControl ) {
		super( parent );
		this.dataControl = dataControl;

		for( ConversationDataControl conversationDataControl : dataControl.getConversations( ) ) {
			if( conversationDataControl.getType( ) == Controller.CONVERSATION_TREE )
				children.add( new TreeConversationTreeNode( this, conversationDataControl ) );
			if( conversationDataControl.getType( ) == Controller.CONVERSATION_GRAPH )
				children.add( new GraphConversationTreeNode( this, conversationDataControl ) );
		}
	}

	@Override
	public TreeNode checkForNewChild( int type ) {
		TreeNode addedTreeNode = null;

		// Add the last book of the list
		ConversationDataControl lastConversationDataControl = dataControl.getLastConversation( );

		if( lastConversationDataControl.getType( ) == Controller.CONVERSATION_TREE ) {
			addedTreeNode = new TreeConversationTreeNode( this, lastConversationDataControl );
			children.add( addedTreeNode );
		} else if( lastConversationDataControl.getType( ) == Controller.CONVERSATION_GRAPH ) {
			addedTreeNode = new GraphConversationTreeNode( this, lastConversationDataControl );
			children.add( addedTreeNode );
		}

		// Spread the owner panel to the children
		spreadOwnerPanel( );

		// Return the node created
		return addedTreeNode;
	}

	@Override
	public void checkForDeletedReferences( ) {
	// Do nothing
	}

	@Override
	protected int getNodeType( ) {
		return Controller.CONVERSATIONS_LIST;
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
		return new ConversationsListPanel( dataControl );
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
