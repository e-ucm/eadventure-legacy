/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author López Mañas, E., Pérez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, available at http://e-adventure.e-ucm.es.
 * 
 * <e-Adventure> is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * <e-Adventure>; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */
package es.eucm.eadventure.common.data.chapter.conversation.node;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.conditions.Conditions;
import es.eucm.eadventure.common.data.chapter.conversation.line.ConversationLine;
import es.eucm.eadventure.common.data.chapter.effects.Effects;

/**
 * This conversational node contains a variable number of dialogue lines,
 * between player characters and non-player characters. This node has a single
 * link to another node, of any kind
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

    private boolean effectConsumed = false;

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
     * @see es.eucm.eadventure.common.data.chapterdata.conversation.node.ConversationNodeView#getType()
     */
    public int getType( ) {

        return DIALOGUE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see es.eucm.eadventure.common.data.chapterdata.conversation.node.ConversationNodeView#isTerminal()
     */
    public boolean isTerminal( ) {

        return terminal;
    }

    /*
     * (non-Javadoc)
     * 
     * @see es.eucm.eadventure.common.data.chapterdata.conversation.node.ConversationNodeView#getChildCount()
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
     * @see es.eucm.eadventure.common.data.chapterdata.conversation.node.ConversationNodeView#getLineCount()
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
     * @see es.eucm.eadventure.common.data.chapterdata.conversation.node.ConversationNodeView#hasEffects()
     */
    public boolean hasEffects( ) {

        return hasValidEffect( ) && !effects.isEmpty( );
    }

    @Override
    public void setEffects( Effects effects ) {

        this.effects = effects;
    }

    @Override
    public Effects getEffects( ) {

        return effects;
    }

    @Override
    public void consumeEffect( ) {

        effectConsumed = true;
    }

    @Override
    public boolean isEffectConsumed( ) {

        return effectConsumed;
    }

    @Override
    public void resetEffect( ) {

        effectConsumed = false;
    }

    @Override
    public boolean hasValidEffect( ) {

        return effects != null;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        DialogueConversationNode dcn = (DialogueConversationNode) super.clone( );
        if( dialogue != null ) {
            dcn.dialogue = new ArrayList<ConversationLine>( );
            for( ConversationLine cl : dialogue )
                dcn.dialogue.add( (ConversationLine) cl.clone( ) );
        }
        dcn.effectConsumed = effectConsumed;
        dcn.effects = ( effects != null ? (Effects) effects.clone( ) : null );
        //dcn.nextNode = (nextNode != null ? (ConversationNode) nextNode.clone() : null);
        dcn.nextNode = null;
        dcn.terminal = terminal;
        return dcn;
    }

    public Conditions getLineConditions( int index ) {

        return dialogue.get( index ).getConditions( );
    }

    public ConversationLine getConversationLine( int index ) {

        return dialogue.get( index );
    }

}
