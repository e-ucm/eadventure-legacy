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
package es.eucm.eadventure.editor.control.tools.conversation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import es.eucm.eadventure.common.data.chapter.conversation.Conversation;
import es.eucm.eadventure.common.data.chapter.conversation.GraphConversation;
import es.eucm.eadventure.common.data.chapter.conversation.TreeConversation;
import es.eucm.eadventure.common.data.chapter.conversation.line.ConversationLine;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNode;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNodeView;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.ConditionsController;
import es.eucm.eadventure.editor.control.tools.Tool;

public class DeleteConversationNodeTool extends Tool {

    public static final int MODE_GRAPH = 1;

    public static final int MODE_TREE = 2;

    private boolean showConfirmation;

    private int mode;

    private Controller controller;

    private ConversationNodeView nodeView;

    private ConversationNode rootNode;

    private Conversation conversation;

    //// DATA TO BE GENERATED DURING DOTOOL
    private ConversationLine deletedLine;

    private ConversationNode deletedNode;

    private ConversationNode parentNode;

    /**
     * The condition controllers associated to the deleted node
     */
    private List<ConditionsController> deletedConditions;

    /**
     * The condition associated to deleted conversation line (if deleted node is
     * "option node")
     */
    private ConditionsController deletedCondition;

    private int index;

    private Map<ConversationNodeView, List<ConditionsController>> allConditions;

    public DeleteConversationNodeTool( int mode, ConversationNodeView _nodeView, Conversation conversation, Map<ConversationNodeView, List<ConditionsController>> allConditions ) {

        this.mode = mode;
        this.showConfirmation = ( mode == MODE_TREE );
        this.controller = Controller.getInstance( );
        this.nodeView = _nodeView;
        this.conversation = conversation;
        this.rootNode = conversation.getRootNode( );
        this.allConditions = allConditions;
    }

    public DeleteConversationNodeTool( ConversationNodeView _nodeView, GraphConversation conversation, Map<ConversationNodeView, List<ConditionsController>> allConditions ) {

        this( MODE_GRAPH, _nodeView, conversation, allConditions );
    }

    @Override
    public boolean canRedo( ) {

        return true;
    }

    @Override
    public boolean canUndo( ) {

        return deletedNode != null && mode != MODE_TREE;
    }

    @Override
    public boolean combine( Tool other ) {

        return false;
    }

    @Override
    public boolean doTool( ) {

        boolean nodeDeleted = false;

        // Ask for confirmation
        if( !showConfirmation || controller.showStrictConfirmDialog( TC.get( "Conversation.OperationDeleteNode" ), TC.get( "Conversation.ConfirmDeleteNode" ) ) ) {
            // Take the complete node
            ConversationNode node = (ConversationNode) nodeView;

            // If the node was deleted
            if( mode == MODE_TREE ) {
                if( recursiveDeleteNode( rootNode/*treeConversation.getRootNode( )*/, node ) ) {
                    // Set the data as modified
                    nodeDeleted = true;
                }
            }
            else if( mode == MODE_GRAPH ) {
                // Get the complete node list
                List<ConversationNodeView> nodes = getAllNodes( conversation );

                // For each node
                for( ConversationNodeView currentNodeView : nodes ) {
                    int j = 0;

                    // Search for the node which is being deleted among each node's children
                    while( j < currentNodeView.getChildCount( ) ) {

                        // If the current child is the node we want to delete
                        if( currentNodeView.getChildView( j ) == nodeView ) {
                            // Take the complete current node
                            parentNode = (ConversationNode) currentNodeView;

                            // Delete the child
                            deletedNode = parentNode.removeChild( j );
                            index = j;

                            // remove the conditions associated to removed node
                            deletedConditions = allConditions.remove( deletedNode );

                            // If the current node is an option node, delete the line too
                            if( parentNode.getType( ) == ConversationNodeView.OPTION ) {
                                deletedLine = parentNode.removeLine( j );
                                // delete the associated condition data control
                                deletedCondition = allConditions.get( parentNode ).remove( j );
                            }

                            // The node has been deleted
                            nodeDeleted = true;
                        }

                        // If it's not, go for the next child
                        else
                            j++;
                    }
                }
            }
        }

        return nodeDeleted;
    }

    @Override
    public boolean redoTool( ) {

        parentNode.removeChild( index );
        allConditions.remove( deletedNode );
        // If the current node is an option node, delete the line too
        if( parentNode.getType( ) == ConversationNodeView.OPTION ) {
            parentNode.removeLine( index );
            allConditions.get( parentNode ).remove( index );
        }

        controller.updatePanel( );
        return true;

    }

    @Override
    public boolean undoTool( ) {

        parentNode.addChild( index, deletedNode );
        allConditions.put( deletedNode, deletedConditions );
        // If the current node is an option node, delete the line too
        if( parentNode.getType( ) == ConversationNodeView.OPTION ) {
            parentNode.addLine( index, deletedLine );
            allConditions.get( deletedNode ).add( index, deletedCondition );
        }

        controller.reloadPanel( );
        return true;
    }

    /**
     * Recursive function that deletes the references of nodeToDelete in node
     * and its children.
     * 
     * @param node
     *            Node to check for references to the node being deleted
     * @param nodeToDelete
     *            Reference to the node that is being deleted
     * @return True if the node to delete was found and deleted, false otherwise
     */
    private boolean recursiveDeleteNode( ConversationNode node, ConversationNode nodeToDelete ) {

        boolean isDeleted = false;

        // If it is a dialogue node
        if( node.getType( ) == ConversationNodeView.DIALOGUE ) {
            // If the node has a valid child
            if( !node.isTerminal( ) && !TreeConversation.thereIsGoBackTag( node ) ) {
                // If the child equals the node to be deleted, delete the child
                if( node.getChild( 0 ) == nodeToDelete ) {
                    node.removeChild( 0 );
                    isDeleted = true;
                }

                // If not, call the function with the child of the current node
                else
                    isDeleted = recursiveDeleteNode( node.getChild( 0 ), nodeToDelete );
            }
        }

        // If the node is a option node
        else if( node.getType( ) == ConversationNodeView.OPTION ) {
            int i = 0;

            // For each child
            while( i < node.getChildCount( ) ) {
                // If the child equals the node to be deleted, delete the child and its line
                if( node.getChild( i ) == nodeToDelete ) {
                    node.removeChild( i );
                    node.removeLine( i );
                    isDeleted = true;
                }

                // If not, make a recursive call with the current child, and increase i
                else {
                    isDeleted = isDeleted || recursiveDeleteNode( node.getChild( i ), nodeToDelete );
                    i++;
                }
            }
        }

        return isDeleted;
    }

    /**
     * Returns a list with all the nodes in the conversation.
     * 
     * @return List with the nodes of the conversation
     */
    public List<ConversationNodeView> getAllNodes( Conversation conversation ) {

        // Create another list
        List<ConversationNode> nodes = conversation.getAllNodes( );
        List<ConversationNodeView> nodeViews = new ArrayList<ConversationNodeView>( );

        // Copy the data
        for( ConversationNode node : nodes )
            nodeViews.add( node );

        return nodeViews;
    }
}
