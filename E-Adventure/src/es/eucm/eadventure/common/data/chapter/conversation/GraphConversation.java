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

	public GraphConversation(TreeConversation conversation) {
		super( Conversation.GRAPH, conversation.getId(), conversation.getRootNode());
	}

	/**
	 * Returns a list with all the nodes in the conversation.
	 * 
	 * @return List with the nodes of the conversation
	 */
	public List<ConversationNode> getAllNodes( ) {
		List<ConversationNode> nodes = new ArrayList<ConversationNode>();
		
		nodes.add( getRootNode() );
		int i = 0;
		while (i < nodes.size()) {
			ConversationNode temp = nodes.get(i);
			i++;
			for (int j = 0; j < temp.getChildCount(); j++) {
				ConversationNode temp2 = temp.getChild(j);
				if (!nodes.contains(temp2))
					nodes.add(temp2);
			}
		}
		
		return nodes;
	}
	
	public Object clone() throws CloneNotSupportedException {
		GraphConversation gc = (GraphConversation) super.clone();
		return gc;
	}
}
