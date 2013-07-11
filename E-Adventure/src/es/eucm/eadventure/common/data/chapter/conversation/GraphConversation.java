/*******************************************************************************
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the e-UCM
 *          research group.
 *   
 *    Copyright 2005-2012 e-UCM research group.
 *  
 *     e-UCM is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *  
 *          C Profesor Jose Garcia Santesmases sn,
 *          28040 Madrid (Madrid), Spain.
 *  
 *          For more info please visit:  <http://e-adventure.e-ucm.es> or
 *          <http://www.e-ucm.es>
 *  
 *  ****************************************************************************
 * This file is part of eAdventure, version 1.5.
 * 
 *   You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with Adventure.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.common.data.chapter.conversation;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNode;
import es.eucm.eadventure.common.data.chapter.conversation.node.DialogueConversationNode;

public class GraphConversation extends Conversation {

    private String vignetteId = null;
    
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
    public GraphConversation( String conversationName, String vignetteId, ConversationNode root ) {

        super( Conversation.GRAPH, conversationName, root );
        this.vignetteId = vignetteId;
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

    
    public String getVignetteId( ) {
    
        return vignetteId;
    }

    
    public void setVignetteId( String vignetteId ) {
    
        this.vignetteId = vignetteId;
    }
}
