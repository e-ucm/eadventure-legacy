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
package es.eucm.eadventure.common.data.chapter.conversation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.eucm.eadventure.common.data.HasId;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNode;

/**
 * Implements Tree and Graph conversations
 */
public abstract class Conversation implements Cloneable, HasId {

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
     * Returns the initial node of the conversation, the one which starts the
     * conversation.
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

    public List<ConversationNode> getAllNodes( ) {

        List<ConversationNode> nodes = new ArrayList<ConversationNode>( );
        getAllNodes( root, nodes );
        return nodes;
    }

    private void getAllNodes( ConversationNode firstNode, List<ConversationNode> nodes ) {

        for( int i = -1; i < firstNode.getChildCount( ); i++ ) {
            ConversationNode child = null;
            if( i == -1 )
                child = firstNode;
            else
                child = firstNode.getChild( i );
            // Check the child is not in the list yet
            boolean isInList = false;
            for( ConversationNode aNode : nodes ) {
                if( aNode == child ) {
                    isInList = true;
                    break;
                }

            }
            if( !isInList ) {
                nodes.add( child );
                getAllNodes( child, nodes );
            }
        }
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        Conversation c = (Conversation) super.clone( );
        c.conversationId = ( conversationId != null ? new String( conversationId ) : null );
        c.conversationType = conversationType;

        HashMap<ConversationNode, ConversationNode> clonedNodes = new HashMap<ConversationNode, ConversationNode>( );

        c.root = ( root != null ? (ConversationNode) root.clone( ) : null );

        clonedNodes.put( root, c.root );
        List<ConversationNode> nodes = new ArrayList<ConversationNode>( );
        List<ConversationNode> visited = new ArrayList<ConversationNode>( );
        nodes.add( root );

        while( !nodes.isEmpty( ) ) {
            ConversationNode temp = nodes.get( 0 );
            ConversationNode cloned = clonedNodes.get( temp );
            nodes.remove( 0 );
            visited.add( temp );

            for( int i = 0; i < temp.getChildCount( ); i++ ) {
                ConversationNode tempCloned = clonedNodes.get( temp.getChild( i ) );
                if( tempCloned == null ) {
                    tempCloned = (ConversationNode) temp.getChild( i ).clone( );
                    clonedNodes.put( temp.getChild( i ), tempCloned );
                }
                cloned.addChild( tempCloned );

                if( !visited.contains( temp.getChild( i ) ) && !nodes.contains( temp.getChild( i ) ) )
                    nodes.add( temp.getChild( i ) );
            }
        }
        return c;
    }
}
