package es.eucm.eadventure.engine.core.data.gamedata.conversation.node;

import es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects.Effects;
import es.eucm.eadventure.engine.core.data.gamedata.conversation.util.ConversationLine;

/**
 * Interface that comprises all the possible nodes for a conversation. Initially, two classes implement this
 * interface: DialogueNode and OptionNode
 */
public interface Node {
    
    /* Atributes */
    
    /**
     * Constant for dialogue node
     */
    public static final int DIALOGUE = 0;
    
    /**
     * Constant for option node
     */
    public static final int OPTION = 1;

    /* Operations with children */

    /**
     * Adds a new child to the node, in the last position
     * @param child Node for insertion
     */
    public void addChild( Node child );

    /**
     * Adds a new child to the node, in the specified position
     * @param index Index for insertion
     * @param child Node for insertion
     */
    public void addChild( int index, Node child );

    /**
     * Replaces the specified child with a given node 
     * @param index Index for replacement
     * @param child Node to be stored at the specified position
     */
    public void setChild( int index, Node child );

    /**
     * Removes the child in the specified position
     * @param index Index for removal
     * @return Reference to the removed child
     */
    public Node removeChild( int index );

    /**
     * Returns the child in the specified position
     * @param index Index for extraction
     */
    public Node getChild( int index );

    /**
     * Returns the children's number of the node
     */
    public int getChildCount( );

    /* Operations with conversational lines */

    /**
     * Adds a new line to the node, in the last position
     * @param line Line for insertion
     */
    public void addLine( ConversationLine line );

    /**
     * Adds a new line to the node, in the specified position
     * @param index Index for insertion
     * @param line Line for insertion
     */
    public void addLine( int index, ConversationLine line );

    /**
     * Replaces the specified line with a given one
     * @param index Index for replacement
     * @param line Line to be stored at the specified position
     */
    public void setLine( int index, ConversationLine line );

    /**
     * Removes the line in the specified position
     * @param index Index for removal
     * @return Reference to the removed line
     */
    public ConversationLine removeLine( int index );

    /**
     * Returns the line in the specified position
     * @param index Index for extraction
     */
    public ConversationLine getLine( int index );

    /**
     * Returns the lines' number of the node
     */
    public int getLineCount( );

    /* Node information */
    
    /**
     * Returns the type of the current node
     * @return DIALOGUE if dialogue node, OPTION if option node
     */
    public int getType( );

    /**
     * Returns if the node is terminal (has no children)
     * @return True if the node is terminal, false otherwise
     */
    public boolean isTerminal( );

    /**
     * Returns if the node has a valid effect set
     * @return True if the node has an effect (even if empty), false otherwise
     */
    public boolean hasValidEffect( );
    
    public void consumeEffect();
    
    public void resetEffect();
    
    public boolean isEffectConsumed();

    /**
     * Sets the effects triggered when the conversation is finished (only terminal nodes accept effects)
     * @param effects New effects
     */
    public void setEffects( Effects effects );

    /**
     * Returns the effect triggered when the conversation is finished
     * @return The effect held by the node if it is terminal, null otherwise
     */
    public Effects getEffects( );

}
