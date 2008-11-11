package es.eucm.eadventure.engine.core.data.gamedata.conversation.node;

import java.util.Vector;

import es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects.Effects;
import es.eucm.eadventure.engine.core.data.gamedata.conversation.util.ConversationLine;

/**
 * This conversational node contains a set of lines, which represent the possible options that the player can choose in
 * a certain point of the conversation. For it's correct use, there must be the same number of lines and children, for each
 * line represents an option, linked with the path the conversation will follow if the option is choosed. Only DialogueNode
 * can be linked with this kind of node
 */
public class OptionNode implements Node {

    /* Attributes */

    /**
     * Conversational line's vector
     */
    private Vector<ConversationLine> options;

    /**
     * Links to the path to follow for each option
     */
    private Vector<Node> optionNodes;

    private boolean effectConsumed=false;

    /* Methods */

    /**
     * Constructor
     */
    public OptionNode( ) {
        options = new Vector<ConversationLine>( );
        optionNodes = new Vector<Node>( );
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.conversation.node.Node#addChild(es.eucm.eadventure.engine.engine.data.conversation.node.Node)
     */
    public void addChild( Node child ) {
        optionNodes.add( child );
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.conversation.node.Node#addChild(int, es.eucm.eadventure.engine.engine.data.conversation.node.Node)
     */
    public void addChild( int index, Node child ) {
        optionNodes.add( index, child );
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.conversation.node.Node#setChild(int, es.eucm.eadventure.engine.engine.data.conversation.node.Node)
     */
    public void setChild( int index, Node child ) {
        optionNodes.set( index, child );
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.conversation.node.Node#removeChild(int)
     */
    public Node removeChild( int index ) {
        return optionNodes.remove( index );
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.conversation.node.Node#getChild(int)
     */
    public Node getChild( int index ) {
        return optionNodes.get( index );
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.conversation.node.Node#getChildCount()
     */
    public int getChildCount( ) {
        return optionNodes.size( );
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.conversation.node.Node#addLine(es.eucm.eadventure.engine.engine.data.conversation.util.ConversationLine)
     */
    public void addLine( ConversationLine line ) {
        options.add( line );
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.conversation.node.Node#addLine(int, es.eucm.eadventure.engine.engine.data.conversation.util.ConversationLine)
     */
    public void addLine( int index, ConversationLine line ) {
        options.add( index, line );
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.conversation.node.Node#setLine(int, es.eucm.eadventure.engine.engine.data.conversation.util.ConversationLine)
     */
    public void setLine( int index, ConversationLine line ) {
        options.set( index, line );
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.conversation.node.Node#removeLine(int)
     */
    public ConversationLine removeLine( int index ) {
        return options.remove( index );
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.conversation.node.Node#getLine(int)
     */
    public ConversationLine getLine( int index ) {
        return options.get( index );
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.conversation.node.Node#getLineCount()
     */
    public int getLineCount( ) {
        return options.size( );
    }
    
    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.conversation.node.Node#getType()
     */
    public int getType( ) {
        return OPTION;
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.conversation.node.Node#isTerminal()
     */
    public boolean isTerminal( ) {
        return false;
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.conversation.node.Node#hasValidEffect()
     */
    public boolean hasValidEffect( ) {
        return false;
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.conversation.node.Node#setEffects(es.eucm.eadventure.engine.engine.data.effects.Effects)
     */
    public void setEffects( Effects effects ) {
        // Empty, cannot set an effect into a option node, for it cannot be terminal
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.conversation.node.Node#getEffects()
     */
    public Effects getEffects( ) {
        return null;
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