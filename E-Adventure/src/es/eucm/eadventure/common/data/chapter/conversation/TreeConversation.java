package es.eucm.eadventure.common.data.chapter.conversation;

import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNode;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNodeView;
import es.eucm.eadventure.common.data.chapter.conversation.node.DialogueConversationNode;

public class TreeConversation extends Conversation {

	/**
	 * Tree conversation constructor.
	 * 
	 * @param conversationName
	 *            Name of the conversation
	 */
	public TreeConversation( String conversationName ) {
		super( Conversation.TREE, conversationName, new DialogueConversationNode( ) );
	}

	/**
	 * Tree conversation constructor.
	 * 
	 * @param conversationName
	 *            Name of the conversation
	 * @param root
	 *            Root of the conversation
	 */
	public TreeConversation( String conversationName, ConversationNode root ) {
		super( Conversation.TREE, conversationName, root );
	}

	/**
	 * Checks if there is a "go-back" tag in the given node. This is, if the node is a DialogueNode, and is linked to
	 * the OptionNode from which came from
	 * 
	 * @param node
	 *            Node (must be a DialogueNode) to check
	 * @return True if the node has a "go-back" tag, false otherwise
	 */
	public static boolean thereIsGoBackTag( ConversationNodeView node ) {
		boolean goBackTag = false;

		// Perform the check only if the node is a DialogueNode and it has a child
		if( node.getType( ) == ConversationNode.DIALOGUE && node.getChildCount( ) > 0 ) {
			ConversationNodeView possibleFather = node.getChildView( 0 );

			// For each child of the possible father node, check if it match with the possible child
			for( int i = 0; i < possibleFather.getChildCount( ); i++ )
				if( possibleFather.getChildView( i ) == node )
					goBackTag = true;
		}

		return goBackTag;
	}
}
