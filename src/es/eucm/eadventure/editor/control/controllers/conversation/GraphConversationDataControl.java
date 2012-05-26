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
package es.eucm.eadventure.editor.control.controllers.conversation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.eucm.eadventure.common.auxiliar.AssetsConstants;
import es.eucm.eadventure.common.data.chapter.conversation.Conversation;
import es.eucm.eadventure.common.data.chapter.conversation.GraphConversation;
import es.eucm.eadventure.common.data.chapter.conversation.line.ConversationLine;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNode;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNodeView;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.ConditionsController;
import es.eucm.eadventure.editor.control.controllers.EffectsController;
import es.eucm.eadventure.editor.control.controllers.Searchable;
import es.eucm.eadventure.editor.control.tools.conversation.DeleteConversationNodeTool;
import es.eucm.eadventure.editor.control.tools.conversation.LinkConversationNodeTool;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class GraphConversationDataControl extends ConversationDataControl {

    /**
     * Reference to the graph conversation.
     */
    private GraphConversation graphConversation;

    /**
     * A list with each conversation line conditions controller
     */
    private Map<ConversationNodeView, List<ConditionsController>> allConditions;

    /**
     * Constructor.
     * 
     * @param graphConversation
     *            Contained graph conversation
     */
    public GraphConversationDataControl( GraphConversation graphConversation ) {

        this.graphConversation = graphConversation;
        storeAllConditions( );
    }

    @Override
    public int getType( ) {

        return Controller.CONVERSATION_GRAPH;
    }

    @Override
    public String getId( ) {

        return graphConversation.getId( );
    }

    @Override
    public ConversationNodeView getRootNode( ) {

        return graphConversation.getRootNode( );
    }

    @Override
    public void updateAllConditions( ) {

        allConditions.clear( );
        List<ConversationNodeView> nodes = getAllNodesViews( );
        for( ConversationNodeView node : nodes ) {
            ArrayList<ConditionsController> nodeConditions = new ArrayList<ConditionsController>( );
            // add each condition for each conversation line
            for( int i = 0; i < node.getLineCount( ); i++ ) {
                nodeConditions.add( new ConditionsController( node.getLineConditions( i ), ( node.getType( ) == ConversationNodeView.DIALOGUE ? Controller.CONVERSATION_DIALOGUE_LINE : Controller.CONVERSATION_OPTION_LINE ), Integer.toString( i ) ) );
            }
            allConditions.put( node, nodeConditions );
        }
    }

    /**
     * Store all line conditions in allConditions
     */
    private void storeAllConditions( ) {

        allConditions = new HashMap<ConversationNodeView, List<ConditionsController>>( );
        updateAllConditions( );
    }

    /**
     * Returns the conditions controller associated to the given conversation
     * line
     * 
     * @param convLine
     * @return Conditions controller
     * 
     */
    public ConditionsController getLineConditionController( ConversationNodeView node, int line ) {

        return ( allConditions.get( node ) ).get( line );
    }

    @Override
    public int getConversationLineCount( ) {

        int lineCount = 0;

        // Take all the nodes, and add the line count of each one
        List<ConversationNodeView> nodes = getAllNodesViews( );
        for( ConversationNodeView node : nodes )
            lineCount += node.getLineCount( );

        return lineCount;
    }

    @Override
    public int[] getAddableNodes( ConversationNodeView nodeView ) {

        int[] addableNodes = null;

        // Dialogue nodes can add both dialogue and option nodes
        if( nodeView.getType( ) == ConversationNodeView.DIALOGUE )
            addableNodes = new int[] { ConversationNodeView.DIALOGUE, ConversationNodeView.OPTION };

        // Option nodes can only add dialogue nodes
        else if( nodeView.getType( ) == ConversationNodeView.OPTION )
            addableNodes = new int[] { ConversationNodeView.DIALOGUE };

        return addableNodes;
    }

    @Override
    public boolean canAddChild( ConversationNodeView nodeView, int nodeType ) {

        boolean canAddChild = false;

        // A dialogue node only accepts nodes if it is terminal
        if( nodeView.getType( ) == ConversationNodeView.DIALOGUE && nodeView.isTerminal( ) )
            canAddChild = true;

        // An option node only accepts dialogue nodes
        if( nodeView.getType( ) == ConversationNodeView.OPTION && nodeType == ConversationNodeView.DIALOGUE )
            canAddChild = true;

        return canAddChild;
    }

    @Override
    public boolean canLinkNode( ConversationNodeView nodeView ) {

        boolean canLinkNode = false;

        // The node must not be the root
        if( nodeView != graphConversation.getRootNode( ) ) {
            // A dialogue node only can link it it is terminal
            if( nodeView.getType( ) == ConversationNodeView.DIALOGUE && nodeView.isTerminal( ) )
                canLinkNode = true;

            // An option node can always link to another node
            if( nodeView.getType( ) == ConversationNodeView.OPTION )
                canLinkNode = true;
        }

        return canLinkNode;
    }

    @Override
    public boolean canDeleteLink( ConversationNodeView nodeView ) {

        boolean canLinkNode = false;

        // The node must not be the root
        if( nodeView != graphConversation.getRootNode( ) ) {
            // A dialogue node only can link it it is terminal
            if( nodeView.getType( ) == ConversationNodeView.DIALOGUE && nodeView.isTerminal( ) )
                canLinkNode = true;

            // An option node can always link to another node
            if( nodeView.getType( ) == ConversationNodeView.OPTION )
                canLinkNode = true;
        }

        return !canLinkNode && this.getAllNodesViews( ).size( ) > 1;
    }

    @Override
    public boolean canLinkNodeTo( ConversationNodeView fatherView, ConversationNodeView childView ) {

        boolean canLinkNodeTo = false;

        // Check first if the nodes are different
        if( fatherView != childView ) {

            // If the father is a dialogue node, it can link to another if it is terminal
            // Check also that the father is not a child of the child node, to prevent cycles
            if( fatherView.getType( ) == ConversationNodeView.DIALOGUE && fatherView.isTerminal( ) && !isDirectFather( childView, fatherView ) )
                canLinkNodeTo = true;

            // If the father is an option node, it can only link to a dialogue node
            if( fatherView.getType( ) == ConversationNodeView.OPTION && childView.getType( ) == ConversationNodeView.DIALOGUE )
                canLinkNodeTo = true;
        }

        return canLinkNodeTo;
    }

    @Override
    public boolean canDeleteNode( ConversationNodeView nodeView ) {

        // Any node can be deleted, if it is not the start node
        return nodeView != graphConversation.getRootNode( );
    }

    @Override
    public boolean canMoveNode( ConversationNodeView nodeView ) {

        // No node moving is allowed in graph conversations
        return false;
    }

    @Override
    public boolean canMoveNodeTo( ConversationNodeView nodeView, ConversationNodeView hostNodeView ) {

        // No node moving is allowed in graph conversations
        return false;
    }

    @Override
    public boolean linkNode( ConversationNodeView fatherView, ConversationNodeView childView ) {

        return controller.addTool( new LinkConversationNodeTool( this, fatherView, childView ) );
    }

    @Override
    public boolean deleteNode( ConversationNodeView nodeView ) {

        return controller.addTool( new DeleteConversationNodeTool( nodeView, (GraphConversation) getConversation( ), allConditions ) );
    }

    @Override
    public boolean moveNode( ConversationNodeView nodeView, ConversationNodeView hostNodeView ) {

        // No node moving is allowed in graph conversations
        return false;
    }

    /**
     * Returns a list with all the nodes in the conversation.
     * 
     * @return List with the nodes of the conversation
     */
    public List<ConversationNodeView> getAllNodesViews( ) {

        // Create another list
        List<ConversationNode> nodes = graphConversation.getAllNodes( );
        List<ConversationNodeView> nodeViews = new ArrayList<ConversationNodeView>( );

        // Copy the data
        for( ConversationNode node : nodes )
            nodeViews.add( node );

        return nodeViews;
    }

    /**
     * Returns a list with all the nodes in the conversation.
     * 
     * @return List with the nodes of the conversation
     */
    public List<SearchableNode> getAllSearchableNodes( ) {

        // Create another list
        List<ConversationNode> nodes = graphConversation.getAllNodes( );
        List<SearchableNode> nodeViews = new ArrayList<SearchableNode>( );

        // Copy the data
        for( ConversationNode node : nodes )
            nodeViews.add( new SearchableNode( node ) );

        return nodeViews;
    }

    /**
     * Returns if the given father has a direct line of dialogue nodes to get to
     * the child node.
     * 
     * @param fatherView
     *            Father node
     * @param childView
     *            Child node
     * @return True if the father is related to child following only dialogue
     *         nodes, false otherwise
     */
    private boolean isDirectFather( ConversationNodeView fatherView, ConversationNodeView childView ) {

        boolean isDirectFather = false;

        // Check if both nodes are dialogue nodes
        if( fatherView.getType( ) == ConversationNodeView.DIALOGUE && childView.getType( ) == ConversationNodeView.DIALOGUE ) {

            // Check if the father is not a terminal node
            if( !fatherView.isTerminal( ) ) {

                // If the only child of the father equals the child, there is a direct line
                if( fatherView.getChildView( 0 ) == childView )
                    isDirectFather = true;

                // If not, keep searching with the only child of the father
                else
                    isDirectFather = isDirectFather( fatherView.getChildView( 0 ), childView );
            }
        }

        return isDirectFather;
    }

    @Override
    public Object getContent( ) {

        return graphConversation;
    }

    @Override
    public String renameElement( String name ) {

        boolean elementRenamed = false;
        String oldConversationId = graphConversation.getId( );
        String references = String.valueOf( controller.countIdentifierReferences( oldConversationId ) );

        // Ask for confirmation
        if( name != null || controller.showStrictConfirmDialog( TC.get( "Operation.RenameConversationTitle" ), TC.get( "Operation.RenameElementWarning", new String[] { oldConversationId, references } ) ) ) {

            // Show a dialog asking for the new conversation id
            String newConversationId = name;
            if( name == null )
                newConversationId = controller.showInputDialog( TC.get( "Operation.RenameConversationTitle" ), TC.get( "Operation.RenameConversationMessage" ), oldConversationId );

            // If some value was typed and the identifiers are different
            if( newConversationId != null && !newConversationId.equals( oldConversationId ) && controller.isElementIdValid( newConversationId ) ) {
                graphConversation.setId( newConversationId );
                controller.replaceIdentifierReferences( oldConversationId, newConversationId );
                controller.getIdentifierSummary( ).deleteConversationId( oldConversationId );
                controller.getIdentifierSummary( ).addConversationId( newConversationId );
                //controller.dataModified( );
                elementRenamed = true;
            }
        }

        if( elementRenamed )
            return oldConversationId;
        else
            return null;
    }

    @Override
    public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {

        // Check every node on the conversation
        List<ConversationNode> conversationNodes = graphConversation.getAllNodes( );
        for( ConversationNode conversationNode : conversationNodes ) {
            // Update the summary with the effects, if avalaible
            if( conversationNode.hasEffects( ) )
                EffectsController.updateVarFlagSummary( varFlagSummary, conversationNode.getEffects( ) );

            // Update the summary with the conditions of the lines
            for( int i = 0; i < conversationNode.getLineCount( ); i++ ) {
                ConditionsController.updateVarFlagSummary( varFlagSummary, conversationNode.getLineConditions( i ) );
            }
        }
    }

    @Override
    public boolean isValid( String currentPath, List<String> incidences ) {

        return isValidNode( graphConversation.getRootNode( ), currentPath, incidences, new ArrayList<ConversationNode>( ) );
    }

    @Override
    public int countAssetReferences( String assetPath ) {

        int count = 0;

        // Check every node on the conversation
        List<ConversationNode> conversationNodes = graphConversation.getAllNodes( );
        for( ConversationNode conversationNode : conversationNodes ) {
            // Delete the asset references from the effects, if available
            if( conversationNode.hasEffects( ) )
                count += EffectsController.countAssetReferences( assetPath, conversationNode.getEffects( ) );

            // Count audio paths
            for( int i = 0; i < conversationNode.getLineCount( ); i++ ) {
                if( conversationNode.hasAudioPath( i ) ) {
                    String audioPath = conversationNode.getAudioPath( i );
                    if( audioPath.equals( assetPath ) ) {
                        count++;
                    }
                }
            }

        }

        return count;
    }

    @Override
    public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {

        // Check every node on the conversation
        List<ConversationNode> conversationNodes = graphConversation.getAllNodes( );
        for( ConversationNode conversationNode : conversationNodes ) {
            // Delete the asset references from the effects, if avalaible
            if( conversationNode.hasEffects( ) )
                EffectsController.getAssetReferences( assetPaths, assetTypes, conversationNode.getEffects( ) );
            // Count audio paths
            for( int i = 0; i < conversationNode.getLineCount( ); i++ ) {
                if( conversationNode.hasAudioPath( i ) ) {
                    String audioPath = conversationNode.getAudioPath( i );
                    // Search audioPath in the list
                    boolean add = true;
                    for( String asset : assetPaths ) {
                        if( asset.equals( audioPath ) ) {
                            add = false;
                            break;
                        }
                    }
                    if( add ) {
                        int last = assetPaths.size( );
                        assetPaths.add( last, audioPath );
                        assetTypes.add( last, AssetsConstants.CATEGORY_AUDIO );
                    }
                }
            }

        }
    }

    @Override
    public void deleteAssetReferences( String assetPath ) {

        // Check every node on the conversation
        List<ConversationNode> conversationNodes = graphConversation.getAllNodes( );
        for( ConversationNode conversationNode : conversationNodes ) {
            // Delete the asset references from the effects, if available
            if( conversationNode.hasEffects( ) )
                EffectsController.deleteAssetReferences( assetPath, conversationNode.getEffects( ) );

            // Delete audio paths
            for( int i = 0; i < conversationNode.getLineCount( ); i++ ) {
                if( conversationNode.hasAudioPath( i ) ) {
                    String audioPath = conversationNode.getAudioPath( i );
                    if( audioPath.equals( assetPath ) ) {
                        conversationNode.getLine( i ).setAudioPath( null );
                    }
                }
            }

        }
    }

    @Override
    public int countIdentifierReferences( String id ) {

        int count = 0;

        // Check every node on the conversation
        List<ConversationNode> conversationNodes = graphConversation.getAllNodes( );
        for( ConversationNode conversationNode : conversationNodes ) {
            // Check only dialogue nodes
            if( conversationNode.getType( ) == ConversationNodeView.DIALOGUE ) {
                // Check all the lines in the node
                for( int i = 0; i < conversationNode.getLineCount( ); i++ ) {
                    ConversationLine conversationLine = conversationNode.getLine( i );
                    if( conversationLine.getName( ).equals( id ) )
                        count++;
                }

                // Add the references from the effects
                if( conversationNode.hasEffects( ) )
                    count += EffectsController.countIdentifierReferences( id, conversationNode.getEffects( ) );

            }
        }

        // add conditions references
        for( List<ConditionsController> conditions : allConditions.values( ) )
            for( ConditionsController condition : conditions )
                count += condition.countIdentifierReferences( id );

        return count;
    }

    @Override
    public void replaceIdentifierReferences( String oldId, String newId ) {

        // Check every node on the conversation
        List<ConversationNode> conversationNodes = graphConversation.getAllNodes( );
        for( ConversationNode conversationNode : conversationNodes ) {
            // Check only dialogue nodes
            if( conversationNode.getType( ) == ConversationNodeView.DIALOGUE ) {
                // Check all the lines in the node, and replace the identifier if necessary
                for( int i = 0; i < conversationNode.getLineCount( ); i++ ) {
                    ConversationLine conversationLine = conversationNode.getLine( i );
                    if( conversationLine.getName( ).equals( oldId ) )
                        conversationLine.setName( newId );
                }

                // Replace the references from the effects
                if( conversationNode.hasEffects( ) )
                    EffectsController.replaceIdentifierReferences( oldId, newId, conversationNode.getEffects( ) );
            }

            // add conditions references
            for( List<ConditionsController> conditions : allConditions.values( ) )
                for( ConditionsController condition : conditions )
                    condition.replaceIdentifierReferences( oldId, newId );
        }
    }

    @Override
    public void deleteIdentifierReferences( String id ) {

        // Check every node on the conversation
        List<ConversationNode> conversationNodes = graphConversation.getAllNodes( );
        for( ConversationNode conversationNode : conversationNodes ) {
            // Check only dialogue nodes
            if( conversationNode.getType( ) == ConversationNodeView.DIALOGUE ) {
                // Check all the lines in the node, and replace the identifier if necessary
                int i = 0;
                while( i < conversationNode.getLineCount( ) ) {
                    if( conversationNode.getLine( i ).getName( ).equals( id ) )
                        conversationNode.removeLine( i );
                    else
                        i++;
                }

                // Replace the references from the effects
                if( conversationNode.hasEffects( ) )
                    EffectsController.deleteIdentifierReferences( id, conversationNode.getEffects( ) );
            }

            // add conditions references
            for( List<ConditionsController> conditions : allConditions.values( ) )
                for( ConditionsController condition : conditions )
                    condition.deleteIdentifierReferences( id );
        }
    }

    @Override
    public boolean canBeDuplicated( ) {

        return true;
    }

    @Override
    public void recursiveSearch( ) {

        check( this.getId( ), "ID" );
        for( SearchableNode cnv : this.getAllSearchableNodes( ) ) {
            cnv.recursiveSearch( );
        }
    }

    @Override
    public Conversation getConversation( ) {

        return graphConversation;
    }

    @Override
    public void setConversation( Conversation conversation ) {

        if( conversation instanceof GraphConversation ) {
            graphConversation = (GraphConversation) conversation;
        }
    }

    @Override
    public List<Searchable> getPathToDataControl( Searchable dataControl ) {

        List<Searchable> path = getPathFromChild( dataControl, this.getAllSearchableNodes( ) );
        if( path != null )
            return path;
        if( dataControl == this ) {
            path = new ArrayList<Searchable>( );
            path.add( this );
            return path;
        }
        return null;
    }

    /**
     * @return the allConditions
     */
    public Map<ConversationNodeView, List<ConditionsController>> getAllConditions( ) {

        return allConditions;
    }

    @Override
    public List<ConversationNodeView> getAllNodes( ) {

        return this.getAllNodesViews( );
    }

}
