package es.eucm.eadventure.common.data.chapter.conversation.node;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.conversation.line.ConversationLine;
import es.eucm.eadventure.common.data.chapter.effects.Effects;

/**
 * This conversational node contains a set of lines, which represent the possible options that the player can choose in
 * a certain point of the conversation. For it's correct use, there must be the same number of lines and children, for
 * each line represents an option, linked with the path the conversation will follow if the option is choosed. Only
 * DialogueNode can be linked with this kind of node
 */
public class OptionConversationNode extends ConversationNode {

	/* Attributes */

	/**
	 * Conversational line's vector
	 */
	private List<ConversationLine> options;

	/**
	 * Links to the path to follow for each option
	 */
	private List<ConversationNode> optionNodes;
	
    private boolean effectConsumed=false;

	/* Methods */

	/**
	 * Constructor
	 */
	public OptionConversationNode( ) {
		options = new ArrayList<ConversationLine>( );
		optionNodes = new ArrayList<ConversationNode>( );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.eucm.eadventure.engine.engine.data.conversation.node.Node#getType()
	 */
	public int getType( ) {
		return OPTION;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.eucm.eadventure.engine.engine.data.conversation.node.Node#isTerminal()
	 */
	public boolean isTerminal( ) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.eucm.eadventure.common.data.chapterdata.conversation.node.ConversationNodeView#getChildCount()
	 */
	public int getChildCount( ) {
		return optionNodes.size( );
	}

	@Override
	public ConversationNode getChild( int index ) {
		return optionNodes.get( index );
	}

	@Override
	public void addChild( ConversationNode child ) {
		optionNodes.add( child );
	}

	@Override
	public void addChild( int index, ConversationNode child ) {
		optionNodes.add( index, child );
	}

	@Override
	public ConversationNode removeChild( int index ) {
		return optionNodes.remove( index );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.eucm.eadventure.common.data.chapterdata.conversation.node.ConversationNodeView#getLineCount()
	 */
	public int getLineCount( ) {
		return options.size( );
	}

	@Override
	public ConversationLine getLine( int index ) {
		return options.get( index );
	}

	@Override
	public void addLine( ConversationLine line ) {
		options.add( line );
	}

	@Override
	public void addLine( int index, ConversationLine line ) {
		options.add( index, line );
	}

	@Override
	public ConversationLine removeLine( int index ) {
		return options.remove( index );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.eucm.eadventure.engine.engine.data.conversation.node.Node#hasValidEffect()
	 */
	public boolean hasEffects( ) {
		return false;
	}

	@Override
	public void setEffects( Effects effects ) {
	// Empty, cannot set an effect into a option node, for it cannot be terminal
	}

	@Override
	public Effects getEffects( ) {
		return null;
	}
	
    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.conversation.node.Node#hasValidEffect()
     */
    public boolean hasValidEffect( ) {
        return false;
    }

    public void consumeEffect( ) {
        effectConsumed=true;
    }

    public boolean isEffectConsumed( ) {
        return effectConsumed;
    }
    public void resetEffect( ) {
        effectConsumed = false;
     }

}