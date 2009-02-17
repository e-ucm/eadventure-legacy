package es.eucm.eadventure.common.data.chapter.conversation.node;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    
    /**
	 * Effect to be triggered when the node has finished 
	 */
	private Effects effects;
    
    /**
     * Show the options randomly
     */
    private boolean random;
    
	/* Methods */

	public boolean isRandom() {
		return random;
	}

	public void changeRandomly() {
		this.random = !this.random;
		
	}

	/**
	 * Constructor
	 */
	public OptionConversationNode(boolean random ) {
		options = new ArrayList<ConversationLine>( );
		optionNodes = new ArrayList<ConversationNode>( );
		this.random = random;
		effects = new Effects( );
	}
	
	/**
	 * Constructor
	 */
	public OptionConversationNode( ) {
		this ( false );
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
	 * @see es.eucm.eadventure.common.data.chapterdata.conversation.node.ConversationNodeView#hasEffects()
	 */
	public boolean hasEffects( ) {
		return hasValidEffect()&&!effects.isEmpty( );
	}

	@Override
	public void setEffects( Effects effects ) {
		this.effects = effects;
	}

	@Override
	public Effects getEffects( ) {
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

	@Override
	public boolean hasValidEffect() {
		return effects != null;
	}

	

    /**
     * Change randomly the position of the options.
     */
    public void doRandom(){
    	// If option of randomly are activated
    	if (random && getLineCount()>0 ){
    		int cont = getLineCount();
    		Random rnd = new Random();
    		int pos;
    		ArrayList<ConversationLine> op = new ArrayList<ConversationLine>();
    		ArrayList<ConversationNode> opNode = new ArrayList<ConversationNode>();
    		// Iterate the array and change randomly the position
    		while (cont>1){
    			pos = rnd.nextInt(cont);
    			op.add(options.get(pos));
    			opNode.add(optionNodes.get(pos));
    			options.remove(pos);
    			optionNodes.remove(pos);
    			cont--;
    			
    		}
    		// It must be out of loop 
    		op.add(options.get(0));
			opNode.add(optionNodes.get(0));
			
    		options = op;
    		optionNodes = opNode;
    	}
    }
    
	public Object clone() throws CloneNotSupportedException {
		OptionConversationNode ocn = (OptionConversationNode) super.clone();
		ocn.effectConsumed = effectConsumed;
		ocn.effects = (effects != null ? (Effects) effects.clone() : null);
		if (optionNodes != null) {
			ocn.optionNodes = new ArrayList<ConversationNode>();
			for (ConversationNode cn : optionNodes)
				ocn.optionNodes.add((ConversationNode) cn.clone());
		}
		if (options != null) {
			ocn.options = new ArrayList<ConversationLine>();
			for (ConversationLine cl : options)
				ocn.options.add((ConversationLine) cl.clone());
		}
		ocn.random = random;
		return ocn;
	}
}