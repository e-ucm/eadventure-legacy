package es.eucm.eadventure.common.data.chapterdata.conversation;

import es.eucm.eadventure.common.data.chapterdata.conversation.node.ConversationNode;

/**
 * Implements Tree and Graph conversations
 */
public abstract class Conversation {

	/**
	 * Constant for tree conversations.
	 */
	public static final int TREE = 0;

	/**
	 * Constant for graph conversations.
	 */
	public static final int GRAPH = 1;

	/* Attributes */

	/**
	 * Type of the conversation.
	 */
	private int conversationType;

	/**
	 * Reference name of the conversation
	 */
	private String conversationId;

	/**
	 * Root of the conversation
	 */
	private ConversationNode root;

	/* Methods */

	/**
	 * Constructor
	 * 
	 * @param conversationType
	 *            Type of the conversation
	 * @param conversationId
	 *            Identifier of the conversation
	 * @param root
	 *            Root node (start) of the conversation
	 */
	protected Conversation( int conversationType, String conversationId, ConversationNode root ) {
		this.conversationType = conversationType;
		this.conversationId = conversationId;
		this.root = root;
	}

	/**
	 * Returns the type of the conversation.
	 * 
	 * @return Conversation's type
	 */
	public int getType( ) {
		return conversationType;
	}

	/**
	 * Returns the name of the conversation.
	 * 
	 * @return Conversation's name
	 */
	public String getId( ) {
		return conversationId;
	}

	/**
	 * Returns the initial node of the conversation, the one which starts the conversation.
	 * 
	 * @return First node of the conversation
	 */
	public ConversationNode getRootNode( ) {
		return root;
	}

	/**
	 * Sets the a new identifier for the conversation.
	 * 
	 * @param id
	 *            New identifier
	 */
	public void setId( String id ) {
		this.conversationId = id;
	}
}
