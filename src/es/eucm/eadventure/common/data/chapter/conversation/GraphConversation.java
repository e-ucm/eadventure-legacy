package es.eucm.eadventure.common.data.chapter.conversation;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNode;
import es.eucm.eadventure.common.data.chapter.conversation.node.DialogueConversationNode;

public class GraphConversation extends Conversation {

	/**
	 * Graph conversation constructor.
	 * 
	 * @param conversationName
	 *            Name of the conversation
	 */
	public GraphConversation( String conversationName ) {
		super( Conversation.GRAPH, conversationName, new DialogueConversationNode( ) );
	}

	/**
	 * Graph conversation constructor.
	 * 
	 * @param conversationName
	 *            Name of the conversation
	 * @param root
	 *            Root of the conversation
	 */
	public GraphConversation( String conversationName, ConversationNode root ) {
		super( Conversation.GRAPH, conversationName, root );
	}

	/**
	 * Returns a list with all the nodes in the conversation.
	 * 
	 * @return List with the nodes of the conversation
	 */
	public List<ConversationNode> getAllNodes( ) {
		// Create two vectors, one for the already visited nodes, and one for the nodes we have to visit
		List<ConversationNode> visited = new ArrayList<ConversationNode>( );
		List<ConversationNode> notVisited = new ArrayList<ConversationNode>( );

		// Add the start node into the not yet visited nodes
		notVisited.add( getRootNode( ) );

		// While there is nodes to visit
		while( !notVisited.isEmpty( ) ) {
			// Remove the first node, and add it into the visited vector
			ConversationNode currentNode = notVisited.remove( 0 );
			visited.add( currentNode );

			// For every child of the current node
			for( int i = 0; i < currentNode.getChildCount( ); i++ )
				// If the child isn't in the visited vector nor into the not visited vector, add it to the not visited
				// vector
				if( !visited.contains( currentNode.getChild( i ) ) && !notVisited.contains( currentNode.getChild( i ) ) )
					notVisited.add( currentNode.getChild( i ) );
		}

		// Return the visited vector
		return visited;
	}
	
	public Object clone() throws CloneNotSupportedException {
		GraphConversation gc = (GraphConversation) super.clone();
		return gc;
	}
}
