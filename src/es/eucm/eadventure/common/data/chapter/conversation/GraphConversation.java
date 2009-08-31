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
import java.util.List;

import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNode;
import es.eucm.eadventure.common.data.chapter.conversation.node.DialogueConversationNode;

public class GraphConversation extends Conversation {

    /**
     * Graph conversation constructor.
     * 
     * @param conversationName
     *            Name of the conversation
     */
    public GraphConversation( String conversationName ) {

        super( Conversation.GRAPH, conversationName, new DialogueConversationNode( ) );
    }

    /**
     * Graph conversation constructor.
     * 
     * @param conversationName
     *            Name of the conversation
     * @param root
     *            Root of the conversation
     */
    public GraphConversation( String conversationName, ConversationNode root ) {

        super( Conversation.GRAPH, conversationName, root );
    }

    public GraphConversation( TreeConversation conversation ) {

        super( Conversation.GRAPH, conversation.getId( ), conversation.getRootNode( ) );
    }

    /**
     * Returns a list with all the nodes in the conversation.
     * 
     * @return List with the nodes of the conversation
     */
    @Override
    public List<ConversationNode> getAllNodes( ) {

        List<ConversationNode> nodes = new ArrayList<ConversationNode>( );

        nodes.add( getRootNode( ) );
        int i = 0;
        while( i < nodes.size( ) ) {
            ConversationNode temp = nodes.get( i );
            i++;
            for( int j = 0; j < temp.getChildCount( ); j++ ) {
                ConversationNode temp2 = temp.getChild( j );
                if( !nodes.contains( temp2 ) )
                    nodes.add( temp2 );
            }
        }

        return nodes;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        GraphConversation gc = (GraphConversation) super.clone( );
        return gc;
    }
}
