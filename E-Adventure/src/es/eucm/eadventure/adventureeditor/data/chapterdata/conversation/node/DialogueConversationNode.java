package es.eucm.eadventure.adventureeditor.data.chapterdata.conversation.node;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.adventureeditor.data.chapterdata.conversation.line.ConversationLine;
import es.eucm.eadventure.adventureeditor.data.chapterdata.effects.Effects;

/**
 * This conversational node contains a variable number of dialogue lines, between player characters and non-player
 * characters. This node has a single link to another node, of any kind
 */
public class DialogueConversationNode extends ConversationNode {

	/* Attributes */

	/**
	 * Conversational line's vector
	 */
	private List<ConversationLine> dialogue;

	/**
	 * Link to the next node
	 */
	private ConversationNode nextNode;

	/**
	 * Indicates if the node is terminal or not
	 */
	private boolean terminal;

	/**
	 * Effect to be triggered when the node has finished (if it's terminal)
	 */
	private Effects effects;

	/* Methods */

	/**
	 * Constructor
	 */
	public DialogueConversationNode( ) {
		dialogue = new ArrayList<ConversationLine>( );
		nextNode = null;
		terminal = true;
		effects = new Effects( );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.eucm.eadventure.adventureeditor.data.chapterdata.conversation.node.ConversationNodeView#getType()
	 */
	public int getType( ) {
		return DIALOGUE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.eucm.eadventure.adventureeditor.data.chapterdata.conversation.node.ConversationNodeView#isTerminal()
	 */
	public boolean isTerminal( ) {
		return terminal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.eucm.eadventure.adventureeditor.data.chapterdata.conversation.node.ConversationNodeView#getChildCount()
	 */
	public int getChildCount( ) {
		int childCount = 0;

		if( nextNode != null )
			childCount++;

		return childCount;
	}

	@Override
	public ConversationNode getChild( int index ) {
		if( index != 0 )
			throw new IndexOutOfBoundsException( );

		return nextNode;
	}

	@Override
	public void addChild( ConversationNode child ) {
		if( nextNode != null )
			throw new IndexOutOfBoundsException( );

		nextNode = child;
		terminal = false;
		//TODO MODIFIED
		//effects.clear( );
	}

	@Override
	public void addChild( int index, ConversationNode child ) {
		if( index != 0 || nextNode != null )
			throw new IndexOutOfBoundsException( );

		nextNode = child;
		terminal = false;
		//TODO MODIFIED
		//effects.clear( );
	}

	@Override
	public ConversationNode removeChild( int index ) {
		if( index != 0 || nextNode == null )
			throw new IndexOutOfBoundsException( );

		ConversationNode deletedChild = nextNode;
		nextNode = null;
		terminal = true;
		return deletedChild;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.eucm.eadventure.adventureeditor.data.chapterdata.conversation.node.ConversationNodeView#getLineCount()
	 */
	public int getLineCount( ) {
		return dialogue.size( );
	}

	@Override
	public ConversationLine getLine( int index ) {
		return dialogue.get( index );
	}

	@Override
	public void addLine( ConversationLine line ) {
		dialogue.add( line );
	}

	@Override
	public void addLine( int index, ConversationLine line ) {
		dialogue.add( index, line );
	}

	@Override
	public ConversationLine removeLine( int index ) {
		return dialogue.remove( index );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.eucm.eadventure.adventureeditor.data.chapterdata.conversation.node.ConversationNodeView#hasEffects()
	 */
	public boolean hasEffects( ) {
		return !effects.isEmpty( );
	}

	@Override
	public void setEffects( Effects effects ) {
		this.effects = effects;
	}

	@Override
	public Effects getEffects( ) {
		return effects;
	}

}
