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
package es.eucm.eadventure.editor.control.tools.conversation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import es.eucm.eadventure.common.data.chapter.conditions.Conditions;
import es.eucm.eadventure.common.data.chapter.conversation.line.ConversationLine;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNode;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNodeView;
import es.eucm.eadventure.common.data.chapter.conversation.node.DialogueConversationNode;
import es.eucm.eadventure.common.data.chapter.conversation.node.OptionConversationNode;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.ConditionsController;
import es.eucm.eadventure.editor.control.tools.Tool;

/**
 * Edition tool for adding a child to a node in a conversation
 * 
 * @author Javier
 * 
 */
public class AddConversationNodeTool extends Tool {

    protected ConversationNode newChild;

    protected ConversationNode parent;

    protected int index;

    protected int nodeType;

    protected Map<ConversationNodeView, List<ConditionsController>> allConditions;

    public AddConversationNodeTool( ConversationNode parent, int nodeType, Map<ConversationNodeView, List<ConditionsController>> allConditions ) {

        this.parent = parent;
        this.nodeType = nodeType;
        this.allConditions = allConditions;
    }

    public AddConversationNodeTool( ConversationNodeView nodeView, int nodeType, Map<ConversationNodeView, List<ConditionsController>> allConditions ) {

        this.parent = (ConversationNode) nodeView;
        this.nodeType = nodeType;
        this.allConditions = allConditions;
    }

    @Override
    public boolean canRedo( ) {

        return true;
    }

    @Override
    public boolean canUndo( ) {

        return index != -1 && newChild != null;
    }

    @Override
    public boolean combine( Tool other ) {

        return false;
    }

    @Override
    public boolean doTool( ) {

        // By default, add the child
        boolean addChild = true;

        // If it's sure to add the child
        if( addChild ) {

            // Create the requested node (only accept dialogue and option node)
            if( nodeType == ConversationNodeView.DIALOGUE )
                newChild = new DialogueConversationNode( );
            if( nodeType == ConversationNodeView.OPTION )
                newChild = new OptionConversationNode( );

            // If a child has been created
            if( newChild != null ) {

                // Add the child to the given node
                parent.addChild( newChild );

                // Add to Conditions controller
                allConditions.put( newChild, new ArrayList<ConditionsController>( ) );

                // If the node was an option node, add a new line
                if( parent.getType( ) == ConversationNodeView.OPTION ) {
                    parent.addLine( new ConversationLine( ConversationLine.PLAYER, TC.get( "ConversationLine.NewOption" ) ) );
                    allConditions.get( parent ).add( new ConditionsController( new Conditions( ), Controller.CONVERSATION_OPTION_LINE, Integer.toString( 0 ) ) );
                }
                // Save the index of the newChild
                index = -1;
                for( int i = 0; i < parent.getChildCount( ); i++ ) {
                    if( parent.getChild( i ) == newChild ) {
                        index = i;
                        break;
                    }
                }

            }
        }

        return newChild != null;
    }

    @Override
    public boolean redoTool( ) {

        parent.addChild( index, newChild );
        // Add to Conditions controller
        allConditions.put( newChild, new ArrayList<ConditionsController>( ) );
        // If the node was an option node, add a new line
        if( parent.getType( ) == ConversationNodeView.OPTION ) {
            parent.addLine( index, new ConversationLine( ConversationLine.PLAYER, TC.get( "ConversationLine.NewOption" ) ) );
            allConditions.get( parent ).add( new ConditionsController( new Conditions( ), Controller.CONVERSATION_OPTION_LINE, Integer.toString( 0 ) ) );
        }
        Controller.getInstance( ).updatePanel( );
        return true;
    }

    @Override
    public boolean undoTool( ) {

        parent.removeChild( index );
        allConditions.remove( newChild );
        if( parent.getType( ) == ConversationNodeView.OPTION ) {
            parent.removeLine( index );
            allConditions.get( parent ).remove( index );

        }
        Controller.getInstance( ).updatePanel( );
        return true;
    }

}
