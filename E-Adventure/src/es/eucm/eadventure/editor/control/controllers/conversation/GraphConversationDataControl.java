package es.eucm.eadventure.editor.control.controllers.conversation;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.conversation.GraphConversation;
import es.eucm.eadventure.common.data.chapter.conversation.line.ConversationLine;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNode;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNodeView;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.EffectsController;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class GraphConversationDataControl extends ConversationDataControl {

	/**
	 * Reference to the graph conversation.
	 */
	private GraphConversation graphConversation;

	/**
	 * Constructor.
	 * 
	 * @param graphConversation
	 *            Contained graph conversation
	 */
	public GraphConversationDataControl( GraphConversation graphConversation ) {
		this.graphConversation = graphConversation;
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
	public int getConversationLineCount( ) {
		int lineCount = 0;

		// Take all the nodes, and add the line count of each one
		List<ConversationNodeView> nodes = getAllNodes( );
		for( ConversationNodeView node : nodes )
			lineCount += node.getLineCount( );

		return lineCount;
	}

	@Override
	public int[] getAddableNodes( ConversationNodeView nodeView ) {
		int[] addableNodes = null;

		// Dialogue nodes can add both dialogue and option nodes
		if( nodeView.getType( ) == ConversationNode.DIALOGUE )
			addableNodes = new int[] { ConversationNode.DIALOGUE, ConversationNode.OPTION };

		// Option nodes can only add dialogue nodes
		else if( nodeView.getType( ) == ConversationNode.OPTION )
			addableNodes = new int[] { ConversationNode.DIALOGUE };

		return addableNodes;
	}

	@Override
	public boolean canAddChild( ConversationNodeView nodeView, int nodeType ) {
		boolean canAddChild = false;

		// A dialogue node only accepts nodes if it is terminal
		if( nodeView.getType( ) == ConversationNode.DIALOGUE && nodeView.isTerminal( ) )
			canAddChild = true;

		// An option node only accepts dialogue nodes
		if( nodeView.getType( ) == ConversationNode.OPTION && nodeType == ConversationNode.DIALOGUE )
			canAddChild = true;

		return canAddChild;
	}

	@Override
	public boolean canLinkNode( ConversationNodeView nodeView ) {
		boolean canLinkNode = false;

		// The node must not be the root
		if( nodeView != graphConversation.getRootNode( ) ) {
			// A dialogue node only can link it it is terminal
			if( nodeView.getType( ) == ConversationNode.DIALOGUE && nodeView.isTerminal( ) )
				canLinkNode = true;

			// An option node can always link to another node
			if( nodeView.getType( ) == ConversationNode.OPTION )
				canLinkNode = true;
		}

		return canLinkNode;
	}

	@Override
	protected boolean canLinkNodeTo( ConversationNodeView fatherView, ConversationNodeView childView ) {
		boolean canLinkNodeTo = false;

		// Check first if the nodes are different
		if( fatherView != childView ) {

			// If the father is a dialogue node, it can link to another if it is terminal
			// Check also that the father is not a child of the child node, to prevent cycles
			if( fatherView.getType( ) == ConversationNode.DIALOGUE && fatherView.isTerminal( ) && !isDirectFather( childView, fatherView ) )
				canLinkNodeTo = true;

			// If the father is an option node, it can only link to a dialogue node
			if( fatherView.getType( ) == ConversationNode.OPTION && childView.getType( ) == ConversationNode.DIALOGUE )
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
	protected boolean canMoveNodeTo( ConversationNodeView nodeView, ConversationNodeView hostNodeView ) {
		// No node moving is allowed in graph conversations
		return false;
	}

	@Override
	public boolean linkNode( ConversationNodeView fatherView, ConversationNodeView childView ) {
		boolean nodeLinked = false;

		// If it is not possible to link the node to the given one, show a message
		if( !canLinkNodeTo( fatherView, childView ) )
			controller.showErrorDialog( TextConstants.getText( "Conversation.OperationLinkNode" ), TextConstants.getText( "Conversation.ErrorLinkNode" ) );

		// If it can be linked
		else {
			boolean linkNode = true;

			// If the node has an effect, ask for confirmation (for the effect will be deleted)
			//if( fatherView.hasEffects( ) )
				//linkNode = controller.showStrictConfirmDialog( TextConstants.getText( "Conversation.OperationLinkNode" ), TextConstants.getText( "Conversation.ErrorLinkNode" ) );

			// If the node must be linked
			if( linkNode ) {
				// Take the complete nodes
				ConversationNode father = (ConversationNode) fatherView;
				ConversationNode child = (ConversationNode) childView;

				// Add the new child
				father.addChild( child );

				// If the father is an option node, add a new line
				if( father.getType( ) == ConversationNode.OPTION )
					father.addLine( new ConversationLine( ConversationLine.PLAYER, TextConstants.getText( "ConversationLine.DefaultText" ) ) );

				// The node was successfully linked
				controller.dataModified( );
				nodeLinked = true;
			}
		}

		return nodeLinked;
	}

	@Override
	public boolean deleteNode( ConversationNodeView nodeView ) {
		boolean nodeDeleted = false;

		// Get the complete node list
		List<ConversationNodeView> nodes = getAllNodes( );

		// For each node
		for( ConversationNodeView currentNodeView : nodes ) {
			int j = 0;

			// Search for the node which is being deleted among each node's children
			while( j < currentNodeView.getChildCount( ) ) {

				// If the current child is the node we want to delete
				if( currentNodeView.getChildView( j ) == nodeView ) {
					// Take the complete current node
					ConversationNode currentNode = (ConversationNode) currentNodeView;

					// Delete the child
					currentNode.removeChild( j );

					// If the current node is an option node, delete the line too
					if( currentNode.getType( ) == ConversationNode.OPTION )
						currentNode.removeLine( j );

					// The node has been deleted
					controller.dataModified( );
					nodeDeleted = true;
				}

				// If it's not, go for the next child
				else
					j++;
			}
		}

		return nodeDeleted;
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
	public List<ConversationNodeView> getAllNodes( ) {
		// Create another list
		List<ConversationNode> nodes = graphConversation.getAllNodes( );
		List<ConversationNodeView> nodeViews = new ArrayList<ConversationNodeView>( );

		// Copy the data
		for( ConversationNode node : nodes )
			nodeViews.add( node );

		return nodeViews;
	}

	/**
	 * Returns if the given father has a direct line of dialogue nodes to get to the child node.
	 * 
	 * @param fatherView
	 *            Father node
	 * @param childView
	 *            Child node
	 * @return True if the father is related to child following only dialogue nodes, false otherwise
	 */
	private boolean isDirectFather( ConversationNodeView fatherView, ConversationNodeView childView ) {
		boolean isDirectFather = false;

		// Check if both nodes are dialogue nodes
		if( fatherView.getType( ) == ConversationNode.DIALOGUE && childView.getType( ) == ConversationNode.DIALOGUE ) {

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
	public String renameElement(String name ) {
		boolean elementRenamed = false;
		String oldConversationId = graphConversation.getId( );
		String references = String.valueOf( controller.countIdentifierReferences( oldConversationId ) );

		// Ask for confirmation
		if(name != null || controller.showStrictConfirmDialog( TextConstants.getText( "Operation.RenameConversationTitle" ), TextConstants.getText( "Operation.RenameElementWarning", new String[] { oldConversationId, references } ) ) ) {

			// Show a dialog asking for the new conversation id
			String newConversationId = name;
			if (name == null)
				newConversationId = controller.showInputDialog( TextConstants.getText( "Operation.RenameConversationTitle" ), TextConstants.getText( "Operation.RenameConversationMessage" ), oldConversationId );

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

		if (elementRenamed)
			return oldConversationId;
		else
			return null;
	}

	@Override
	public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {
		// Check every node on the conversation
		List<ConversationNode> conversationNodes = graphConversation.getAllNodes( );
		for( ConversationNode conversationNode : conversationNodes )
			// Update the summary with the effects, if avalaible
			if( conversationNode.hasEffects( ) )
				EffectsController.updateVarFlagSummary( varFlagSummary, conversationNode.getEffects( ) );
	}

	@Override
	public boolean isValid( String currentPath, List<String> incidences ) {
		return isValidNode ( graphConversation.getRootNode(), currentPath, incidences, new ArrayList<ConversationNode>() );
	}

	@Override
	public int countAssetReferences( String assetPath ) {
		int count = 0;

		// Check every node on the conversation
		List<ConversationNode> conversationNodes = graphConversation.getAllNodes( );
		for( ConversationNode conversationNode : conversationNodes ){
			// Delete the asset references from the effects, if available
			if( conversationNode.hasEffects( ) )
				count += EffectsController.countAssetReferences( assetPath, conversationNode.getEffects( ) );
			
			// Count audio paths
			for (int i=0; i<conversationNode.getLineCount( ); i++){
				if (conversationNode.hasAudioPath( i )){
					String audioPath =conversationNode.getAudioPath( i );
					if (audioPath.equals( assetPath )){
						count++;
					}
				}
			}
			
		}

		return count;
	}

	public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes){
		// Check every node on the conversation
		List<ConversationNode> conversationNodes = graphConversation.getAllNodes( );
		for( ConversationNode conversationNode : conversationNodes ){
			// Delete the asset references from the effects, if avalaible
			if( conversationNode.hasEffects( ) )
				EffectsController.getAssetReferences( assetPaths, assetTypes, conversationNode.getEffects( ) );
			// Count audio paths
			for (int i=0; i<conversationNode.getLineCount( ); i++){
				if (conversationNode.hasAudioPath( i )){
					String audioPath =conversationNode.getAudioPath( i );
					// Search audioPath in the list
					boolean add = true;
					for (String asset: assetPaths){
						if (asset.equals( audioPath )){
							add = false;break;
						}
					}
					if (add){
						int last = assetPaths.size( );
						assetPaths.add( last, audioPath );
						assetTypes.add( last, AssetsController.CATEGORY_AUDIO );
					}
				}
			}
	
		}
	}
	
	@Override
	public void deleteAssetReferences( String assetPath ) {
		// Check every node on the conversation
		List<ConversationNode> conversationNodes = graphConversation.getAllNodes( );
		for( ConversationNode conversationNode : conversationNodes ){
			// Delete the asset references from the effects, if available
			if( conversationNode.hasEffects( ) )
				EffectsController.deleteAssetReferences( assetPath, conversationNode.getEffects( ) );

			// Delete audio paths
			for (int i=0; i<conversationNode.getLineCount( ); i++){
				if (conversationNode.hasAudioPath( i )){
					String audioPath =conversationNode.getAudioPath( i );
					if (audioPath.equals( assetPath )){
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
			if( conversationNode.getType( ) == ConversationNode.DIALOGUE ) {
				// Check all the lines in the node
				for( int i = 0; i < conversationNode.getLineCount( ); i++ ) {
					ConversationLine conversationLine = conversationNode.getLine( i );
					if( conversationLine.getName( ).equals( id ) )
						count++;
				}

				// Add the references from the effects
				if( conversationNode.hasEffects( ) )
					EffectsController.countIdentifierReferences( id, conversationNode.getEffects( ) );
			}
		}

		return count;
	}

	@Override
	public void replaceIdentifierReferences( String oldId, String newId ) {
		// Check every node on the conversation
		List<ConversationNode> conversationNodes = graphConversation.getAllNodes( );
		for( ConversationNode conversationNode : conversationNodes ) {
			// Check only dialogue nodes
			if( conversationNode.getType( ) == ConversationNode.DIALOGUE ) {
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
		}
	}

	@Override
	public void deleteIdentifierReferences( String id ) {
		// Check every node on the conversation
		List<ConversationNode> conversationNodes = graphConversation.getAllNodes( );
		for( ConversationNode conversationNode : conversationNodes ) {
			// Check only dialogue nodes
			if( conversationNode.getType( ) == ConversationNode.DIALOGUE ) {
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
		}
	}

	@Override
	public boolean canBeDuplicated( ) {
		return true;
	}

	@Override
	public void recursiveSearch() {
		check(this.getId(), "ID");
		for (ConversationNodeView cnv : this.getAllNodes()) {
			for (int i = 0; i < cnv.getLineCount(); i++) {
				check(cnv.getLineName(i) , "Line name");
				check(cnv.getLineText(i), "Line text");
			}
		}
	}
}
