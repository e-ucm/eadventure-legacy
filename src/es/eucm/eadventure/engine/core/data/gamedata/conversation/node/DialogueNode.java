package es.eucm.eadventure.engine.core.data.gamedata.conversation.node;

import java.util.Vector;

import es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects.FunctionalEffects;
import es.eucm.eadventure.engine.core.data.gamedata.conversation.util.ConversationLine;

/**
 * This conversational node contains a variable number of dialogue lines, between player characters and non-player characters.
 * This node has a single link to another node, of any kind
 */
public class DialogueNode implements Node {

    /* Attributes */

    /**
     * Conversational line's vector
     */
    private Vector<ConversationLine> dialogue;

    /**
     * Link to the next node
     */
    private Node nextNode;

    /**
     * Indicates if the node is terminal or not
     */
    private boolean terminal;

    /**
     * FunctionalEffect to be triggered when the node has finished (if it's terminal)
     */
    private FunctionalEffects effects;

    private boolean effectConsumed=false;

    /* Methods */

    /**
     * Constructor
     */
    public DialogueNode( ) {
        dialogue = new Vector<ConversationLine>( );
        nextNode = null;
        terminal = true;
        effects = new FunctionalEffects( );
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.conversation.node.Node#addChild(es.eucm.eadventure.engine.engine.data.conversation.node.Node)
     */
    public void addChild( Node child ) {
        if( nextNode != null )
            throw new IndexOutOfBoundsException( );

        nextNode = child;
        terminal = false;
        //effects = null;
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.conversation.node.Node#addChild(int, es.eucm.eadventure.engine.engine.data.conversation.node.Node)
     */
    public void addChild( int index, Node child ) {
        if( index != 0 || nextNode != null )
            throw new IndexOutOfBoundsException( );

        nextNode = child;
        terminal = false;
        //effects = null;
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.conversation.node.Node#setChild(int, es.eucm.eadventure.engine.engine.data.conversation.node.Node)
     */
    public void setChild( int index, Node child ) {
        if( index != 0 || nextNode == null )
            throw new IndexOutOfBoundsException( );

        nextNode = child;
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.conversation.node.Node#removeChild(int)
     */
    public Node removeChild( int index ) {
        if( index != 0 || nextNode == null )
            throw new IndexOutOfBoundsException( );

        Node deletedChild = nextNode;
        nextNode = null;
        terminal = true;
        //effects = new FunctionalEffects( );
        return deletedChild;
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.conversation.node.Node#getChild(int)
     */
    public Node getChild( int index ) {
        if( index != 0 )
            throw new IndexOutOfBoundsException( );

        return nextNode;
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.conversation.node.Node#getChildCount()
     */
    public int getChildCount( ) {
        int childCount = 0;

        if( nextNode != null )
            childCount++;

        return childCount;
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.conversation.node.Node#addLine(es.eucm.eadventure.engine.engine.data.conversation.util.ConversationLine)
     */
    public void addLine( ConversationLine line ) {
        dialogue.add( line );
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.conversation.node.Node#addLine(int, es.eucm.eadventure.engine.engine.data.conversation.util.ConversationLine)
     */
    public void addLine( int index, ConversationLine line ) {
        dialogue.add( index, line );
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.conversation.node.Node#setLine(int, es.eucm.eadventure.engine.engine.data.conversation.util.ConversationLine)
     */
    public void setLine( int index, ConversationLine line ) {
        dialogue.set( index, line );
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.conversation.node.Node#removeLine(int)
     */
    public ConversationLine removeLine( int index ) {
        return dialogue.remove( index );
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.conversation.node.Node#getLine(int)
     */
    public ConversationLine getLine( int index ) {
        return dialogue.get( index );
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.conversation.node.Node#getLineCount()
     */
    public int getLineCount( ) {
        return dialogue.size( );
    }
    
    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.conversation.node.Node#getType()
     */
    public int getType( ) {
        return DIALOGUE;
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.conversation.node.Node#isTerminal()
     */
    public boolean isTerminal( ) {
        return terminal;
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.conversation.node.Node#hasValidEffect()
     */
    public boolean hasValidEffect( ) {
        return effects != null;
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.conversation.node.Node#setEffects(es.eucm.eadventure.engine.engine.data.effects.Effects)
     */
    public void setEffects( FunctionalEffects effects ) {
        this.effects = effects;
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.conversation.node.Node#getEffects()
     */
    public FunctionalEffects getEffects( ) {
        return effects;
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
