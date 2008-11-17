package es.eucm.eadventure.common.data.chapterdata.conversation.node;

import es.eucm.eadventure.common.data.chapterdata.conversation.line.ConversationLine;
import es.eucm.eadventure.common.data.chapterdata.effects.Effects;

/**
 * Abstract class that comprises all the possible nodes for a conversation. Initially, two classes implement this
 * interface: DialogueNode and OptionNode
 */
public abstract class ConversationNode implements ConversationNodeView {

	/**
	 * Returns the child in the specified position
	 * 
	 * @param index
	 *            Index for extraction
	 * @return The child conversation node selected
	 */
	public abstract ConversationNode getChild( int index );

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.eucm.eadventure.common.data.chapterdata.conversation.node.ConversationNodeView#getChildView(int)
	 */
	public ConversationNodeView getChildView( int index ) {
		return getChild( index );
	}

	/**
	 * Adds a new child to the node, in the last position
	 * 
	 * @param child
	 *            Node for insertion
	 */
	public abstract void addChild( ConversationNode child );

	/**
	 * Adds a new child to the node, in the specified position
	 * 
	 * @param index
	 *            Index for insertion
	 * @param child
	 *            Node for insertion
	 */
	public abstract void addChild( int index, ConversationNode child );

	/**
	 * Removes the child in the specified position
	 * 
	 * @param index
	 *            Index for removal
	 * @return Reference to the removed child
	 */
	public abstract ConversationNode removeChild( int index );

	/**
	 * Returns the line in the specified position.
	 * 
	 * @param index
	 *            Index for extraction
	 * @return The conversation line selected
	 */
	public abstract ConversationLine getLine( int index );

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.eucm.eadventure.common.data.chapterdata.conversation.node.ConversationNodeView#isPlayerLine(int)
	 */
	public boolean isPlayerLine( int index ) {
		return getLine( index ).isPlayerLine( );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.eucm.eadventure.common.data.chapterdata.conversation.node.ConversationNodeView#getLineName(int)
	 */
	public String getLineName( int index ) {
		return getLine( index ).getName( );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.eucm.eadventure.common.data.chapterdata.conversation.node.ConversationNodeView#getLineText(int)
	 */
	public String getLineText( int index ) {
		return getLine( index ).getText( );
	}
	
	public String getAudioPath( int index ) {
		return getLine ( index ).getAudioPath( );
	}
	
	public boolean hasAudioPath( int index ) {
		return getLine ( index ).isValidAudio( );
	}



	/**
	 * Adds a new line to the node, in the last position
	 * 
	 * @param line
	 *            Line for insertion
	 */
	public abstract void addLine( ConversationLine line );

	/**
	 * Adds a new line to the node, in the specified position
	 * 
	 * @param index
	 *            Index for insertion
	 * @param line
	 *            Line for insertion
	 */
	public abstract void addLine( int index, ConversationLine line );

	/**
	 * Removes the line in the specified position
	 * 
	 * @param index
	 *            Index for removal
	 * @return Reference to the removed line
	 */
	public abstract ConversationLine removeLine( int index );

	/**
	 * Sets the effects triggered when the conversation is finished (only terminal nodes accept effects)
	 * 
	 * @param effects
	 *            New effects
	 */
	public abstract void setEffects( Effects effects );

	/**
	 * Returns the effect triggered when the conversation is finished
	 * 
	 * @return The effect held by the node if it is terminal, null otherwise
	 */
	public abstract Effects getEffects( );
	
    /**
     * Returns if the node has a valid effect set
     * @return True if the node has an effect (even if empty), false otherwise
     */
    public abstract boolean hasValidEffect( );
    
    public abstract void consumeEffect();
    
    public abstract void resetEffect();
    
    public abstract boolean isEffectConsumed();

}
