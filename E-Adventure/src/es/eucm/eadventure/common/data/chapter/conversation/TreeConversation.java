/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *          research group.
 *   
 *    Copyright 2005-2012 <e-UCM> research group.
 *  
 *     <e-UCM> is a research group of the Department of Software Engineering
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
 * This file is part of <e-Adventure>, version 1.4.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       <e-Adventure> is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      <e-Adventure> is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.common.data.chapter.conversation;

import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNode;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNodeView;
import es.eucm.eadventure.common.data.chapter.conversation.node.DialogueConversationNode;

public class TreeConversation extends Conversation {

    /**
     * Tree conversation constructor.
     * 
     * @param conversationName
     *            Name of the conversation
     */
    public TreeConversation( String conversationName ) {

        super( Conversation.TREE, conversationName, new DialogueConversationNode( ) );
    }

    /**
     * Tree conversation constructor.
     * 
     * @param conversationName
     *            Name of the conversation
     * @param root
     *            Root of the conversation
     */
    public TreeConversation( String conversationName, ConversationNode root ) {

        super( Conversation.TREE, conversationName, root );
    }

    /**
     * Checks if there is a "go-back" tag in the given node. This is, if the
     * node is a DialogueNode, and is linked to the OptionNode from which came
     * from
     * 
     * @param node
     *            Node (must be a DialogueNode) to check
     * @return True if the node has a "go-back" tag, false otherwise
     */
    public static boolean thereIsGoBackTag( ConversationNodeView node ) {

        boolean goBackTag = false;

        // Perform the check only if the node is a DialogueNode and it has a child
        if( node.getType( ) == ConversationNodeView.DIALOGUE && node.getChildCount( ) > 0 ) {
            ConversationNodeView possibleFather = node.getChildView( 0 );

            // For each child of the possible father node, check if it match with the possible child
            for( int i = 0; i < possibleFather.getChildCount( ); i++ )
                if( possibleFather.getChildView( i ) == node )
                    goBackTag = true;
        }

        return goBackTag;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        TreeConversation tc = (TreeConversation) super.clone( );
        return tc;
    }
}
