package es.eucm.eadventure.editor.control.controllers.conversation;

import java.util.List;

import es.eucm.eadventure.common.data.chapterdata.conversation.TreeConversation;
import es.eucm.eadventure.common.data.chapterdata.conversation.line.ConversationLine;
import es.eucm.eadventure.common.data.chapterdata.conversation.node.ConversationNode;
import es.eucm.eadventure.common.data.chapterdata.conversation.node.ConversationNodeView;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.EffectsController;
import es.eucm.eadventure.editor.data.supportdata.FlagSummary;
import es.eucm.eadventure.editor.gui.TextConstants;

public class TreeConversationDataControl extends ConversationDataControl {

	/**
	 * Reference to the tree conversation.
	 */
	private TreeConversation treeConversation;

	/**
	 * Constructor.
	 * 
	 * @param treeConversation
	 *            Contained tree conversation
	 */
	public TreeConversationDataControl( TreeConversation treeConversation ) {
		this.treeConversation = treeConversation;
	}

	@Override
	public int getType( ) {
		return Controller.CONVERSATION_TREE;
	}

	@Override
	public String getId( ) {
		return treeConversation.getId( );
	}

	@Override
	public ConversationNodeView getRootNode( ) {
		return treeConversation.getRootNode( );
	}

	@Override
	public int getConversationLineCount( ) {
		return getRecursiveNodeLineCount( getRootNode( ) );
	}

	/**
	 * Returns the line count of the given node and all its children.
	 * 
	 * @param node
	 *            Node to extract the line count
	 * @return Line count of the node and all its children
	 */
	private int getRecursiveNodeLineCount( ConversationNodeView node ) {
		// Take the line count of the node
		int lineCount = node.getLineCount( );

		// Spread the call to the children (if the node hasn't got a go-back tag)
		if( !TreeConversation.thereIsGoBackTag( node ) )
			for( int i = 0; i < node.getChildCount( ); i++ )
				lineCount += getRecursiveNodeLineCount( node.getChildView( i ) );

		return lineCount;
	}

	@Override
	public int[] getAddableNodes( ConversationNodeView nodeView ) {
		int[] addableNodes = null;

		// Dialogue nodes can only add option nodes
		if( nodeView.getType( ) == ConversationNode.DIALOGUE )
			addableNodes = new int[] { ConversationNode.OPTION };

		// Option nodes can only add dialogue nodes
		else if( nodeView.getType( ) == ConversationNode.OPTION )
			addableNodes = new int[] { ConversationNode.DIALOGUE };

		return addableNodes;
	}

	@Override
	public boolean canAddChild( ConversationNodeView nodeView, int nodeType ) {
		boolean canAddChild = false;

		// A dialogue node only accepts option nodes, if it is terminal
		if( nodeView.getType( ) == ConversationNode.DIALOGUE && nodeView.isTerminal( ) && nodeType == ConversationNode.OPTION )
			canAddChild = true;

		// An option node only accepts dialogue nodes
		if( nodeView.getType( ) == ConversationNode.OPTION && nodeType == ConversationNode.DIALOGUE )
			canAddChild = true;

		return canAddChild;
	}

	@Override
	public boolean canLinkNode( ConversationNodeView nodeView ) {
		// No links are allowed in trees, only add and delete operations
		return false;
	}

	@Override
	protected boolean canLinkNodeTo( ConversationNodeView fatherView, ConversationNodeView childView ) {
		// No links are allowed in trees, only add and delete operations
		return false;
	}

	@Override
	public boolean canDeleteNode( ConversationNodeView nodeView ) {
		// Any node can be deleted, if it is not the root
		return nodeView != treeConversation.getRootNode( );
	}

	@Override
	public boolean canMoveNode( ConversationNodeView nodeView ) {
		// A node can be moved if it is not the root and doesn't have a go-back tag
		return nodeView != treeConversation.getRootNode( ) && !thereIsGoBackTag( nodeView );
	}

	@Override
	protected boolean canMoveNodeTo( ConversationNodeView nodeView, ConversationNodeView hostNodeView ) {
		boolean possibleMove = false;

		// First check that both nodes are not the root, and they're not the same node
		if( nodeView != null && hostNodeView != null && nodeView != treeConversation.getRootNode( ) && hostNodeView != treeConversation.getRootNode( ) && nodeView != hostNodeView && !isChildOf( hostNodeView, nodeView ) ) {
			// If the first node is a dialogue node, can be moved to a option node
			if( nodeView.getType( ) == ConversationNode.DIALOGUE && hostNodeView.getType( ) == ConversationNode.OPTION )
				possibleMove = true;

			// If the first node is a option node, can be moved to a terminal dialogue node
			if( nodeView.getType( ) == ConversationNode.OPTION && hostNodeView.getType( ) == ConversationNode.DIALOGUE && hostNodeView.isTerminal( ) )
				possibleMove = true;
		}

		return possibleMove;
	}

	@Override
	public boolean linkNode( ConversationNodeView fatherView, ConversationNodeView childView ) {
		// No links are allowed in a tree, return false always
		return false;
	}

	@Override
	public boolean deleteNode( ConversationNodeView nodeView ) {
		boolean nodeDeleted = false;

		// Ask for confirmation
		if( controller.showStrictConfirmDialog( TextConstants.getText( "Conversation.OperationDeleteNode" ), TextConstants.getText( "Conversation.ConfirmDeleteNode" ) ) ) {
			// Take the complete node
			ConversationNode node = (ConversationNode) nodeView;

			// If the node was deleted
			if( recursiveDeleteNode( treeConversation.getRootNode( ), node ) ) {
				// Set the data as modified
				controller.dataModified( );
				nodeDeleted = true;
			}
		}

		return nodeDeleted;
	}

	@Override
	public boolean moveNode( ConversationNodeView nodeView, ConversationNodeView hostNodeView ) {
		boolean nodeMoved = false;

		// If it is not possible to move the node to the given position, show a message
		if( !canMoveNodeTo( nodeView, hostNodeView ) )
			controller.showErrorDialog( TextConstants.getText( "Conversation.OperationModeNode" ), TextConstants.getText( "Conversation.ErrorMoveNode" ) );

		// If it can be moved, try to move the node
		else {

			// First we check that is possible to move, and that hostNode is not a child of node, because that would
			// make a cycle
			if( !isChildOf( hostNodeView, nodeView ) ) {
				// Take the full conversation node
				ConversationNode node = (ConversationNode) nodeView;
				ConversationNode hostNode = (ConversationNode) hostNodeView;

				// First obtain the father of node, to delete the link
				ConversationNode fatherOfNode = searchForFather( treeConversation.getRootNode( ), node );

				int i = 0;
				// For each child of the father node
				while( i < fatherOfNode.getChildCount( ) ) {
					// If the current child is the node to be moved, remove it
					if( fatherOfNode.getChild( i ) == node ) {
						fatherOfNode.removeChild( i );

						// Remove the line too if it is an option node
						if( fatherOfNode.getType( ) == ConversationNode.OPTION )
							fatherOfNode.removeLine( i );
					}

					// If it is not, increase i
					else
						i++;
				}

				// Add the moving node to the host node
				hostNode.addChild( node );

				// If the host node is an option node, add a new line
				if( hostNode.getType( ) == ConversationNode.OPTION )
					hostNode.addLine( new ConversationLine( ConversationLine.PLAYER, TextConstants.getText( "ConversationLine.DefaultText" ) ) );

				// Set the data as modified
				controller.dataModified( );
				nodeMoved = true;
			}
		}

		return nodeMoved;
	}

	/**
	 * Checks if there is a "go-back" tag in the given node. This is, if the node is a DialogueNode, and is linked to
	 * the OptionNode from which came from
	 * 
	 * @param nodeView
	 *            Node (must be a DialogueNode) to check
	 * @return True if the node has a "go-back" tag, false otherwise
	 */
	public static boolean thereIsGoBackTag( ConversationNodeView nodeView ) {
		return TreeConversation.thereIsGoBackTag( nodeView );
	}

	/**
	 * Returns if it possible to toggle a go back tag to a node.
	 * 
	 * @param nodeView
	 *            Node in which we want to add the tag
	 * @return True if the tag can be added or removed, false otherwise
	 */
	public boolean canToggleGoBackTag( ConversationNodeView nodeView ) {
		boolean canToggleGoBackTag = false;

		// A node can have a go back tag if it is a dialogue node and is not the root
		if( nodeView.getType( ) == ConversationNode.DIALOGUE && nodeView != treeConversation.getRootNode( ) ) {
			// It must be terminal or contain a go back tag already
			if( nodeView.isTerminal( ) || thereIsGoBackTag( nodeView ) )
				canToggleGoBackTag = true;
		}

		return canToggleGoBackTag;
	}

	/**
	 * Adds or removes a go back tag on a node.
	 * 
	 * @param nodeView
	 *            Node to add or remove the tag
	 * @return True if the tag was added, false otherwise
	 */
	public boolean toggleGoBackTag( ConversationNodeView nodeView ) {
		ConversationNode node = (ConversationNode) nodeView;
		boolean goBackTagAdded = false;

		// If there is no "go-back" tag, add it
		if( !TreeConversation.thereIsGoBackTag( node ) ) {

			boolean addGoBackTag = true;

			// If the node has an effect, ask for confirmation (for the effect will be deleted)
			if( nodeView.hasEffects( ) )
				addGoBackTag = controller.showStrictConfirmDialog( TextConstants.getText( "Conversation.OperationAddGoBackTag" ), TextConstants.getText( "Conversation.ConfirmationAddGoBackTag" ) );

			// Add the go-back tag
			if( addGoBackTag ) {

				// First we must search for the father of the node
				ConversationNode father = searchForFather( treeConversation.getRootNode( ), node );

				// Attach then the node to the father
				node.addChild( father );

				// Tag attached
				controller.dataModified( );
				goBackTagAdded = true;
			}
		}

		// It there is a "go-back" tag, delete it
		else {
			// We remove the only child of the node
			node.removeChild( 0 );

			// Tag deleted
			controller.dataModified( );
			goBackTagAdded = true;
		}

		return goBackTagAdded;
	}

	/**
	 * Recursive function that if the given child is really child of the father node.
	 * 
	 * @param child
	 *            Supposedly child node
	 * @param father
	 *            Supossedly father node
	 * @return True if child is really a child (direct or indirect) of father, false otherwise
	 */
	private boolean isChildOf( ConversationNodeView child, ConversationNodeView father ) {
		boolean isChild = false;

		// If the father node is a dialogue node, with a valid child
		if( father.getType( ) == ConversationNode.DIALOGUE && !father.isTerminal( ) && !TreeConversation.thereIsGoBackTag( father ) ) {
			// If the child of father equals the given child, return true
			if( father.getChildView( 0 ) == child )
				isChild = true;

			// If not, make a recursive call
			else
				isChild = isChildOf( child, father.getChildView( 0 ) );
		}

		// If the father node is a option node
		else if( father.getType( ) == ConversationNode.OPTION ) {
			// For each child, and while no relation has been found
			for( int i = 0; i < father.getChildCount( ) && !isChild; i++ ) {
				// If the current child equals the given child, return true
				if( father.getChildView( i ) == child )
					isChild = true;

				// If not, make a recursive call
				else
					isChild = isChildOf( child, father.getChildView( i ) );
			}
		}

		return isChild;
	}

	/**
	 * Recursive function that searchs and returns the father node of a given node
	 * 
	 * @param possibleFather
	 *            Node in which we will search for the given node
	 * @param childNode
	 *            Node whose father we want to find
	 */
	private ConversationNode searchForFather( ConversationNode possibleFather, ConversationNode childNode ) {
		ConversationNode father = null;

		// If it is a dialogue node and it has a valid child, do another call to the function
		if( possibleFather.getType( ) == ConversationNode.DIALOGUE && !possibleFather.isTerminal( ) && !TreeConversation.thereIsGoBackTag( possibleFather ) )
			if( possibleFather.getChild( 0 ) == childNode )
				father = possibleFather;
			else
				father = searchForFather( possibleFather.getChild( 0 ), childNode );

		// If the possible father is a option node, check every child to see if some of them is the given child node
		else if( possibleFather.getType( ) == ConversationNode.OPTION ) {
			// While there are children left, and we have not found the real father node
			for( int i = 0; i < possibleFather.getChildCount( ) && father == null; i++ ) {

				// If the child of the possible father and the given child node are equal, we have found the father
				if( possibleFather.getChild( i ) == childNode )
					father = possibleFather;

				// If not, do the call with the child node
				else
					father = searchForFather( possibleFather.getChild( i ), childNode );
			}
		}

		return father;
	}

	/**
	 * Recursive function that deletes the references of nodeToDelete in node and its children.
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
		if( node.getType( ) == ConversationNode.DIALOGUE ) {
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
		else if( node.getType( ) == ConversationNode.OPTION ) {
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

	@Override
	public Object getContent( ) {
		return treeConversation;
	}

	@Override
	public boolean renameElement( ) {
		boolean elementRenamed = false;
		String oldConversationId = treeConversation.getId( );
		String references = String.valueOf( controller.countIdentifierReferences( oldConversationId ) );

		// Ask for confirmation
		if( controller.showStrictConfirmDialog( TextConstants.getText( "Operation.RenameConversationTitle" ), TextConstants.getText( "Operation.RenameElementWarning", new String[] { oldConversationId, references } ) ) ) {

			// Show a dialog asking for the new conversation id
			String newConversationId = controller.showInputDialog( TextConstants.getText( "Operation.RenameConversationTitle" ), TextConstants.getText( "Operation.RenameConversationMessage" ), oldConversationId );

			// If some value was typed and the identifiers are different
			if( newConversationId != null && !newConversationId.equals( oldConversationId ) && controller.isElementIdValid( newConversationId ) ) {
				treeConversation.setId( newConversationId );
				controller.replaceIdentifierReferences( oldConversationId, newConversationId );
				controller.getIdentifierSummary( ).deleteConversationId( oldConversationId );
				controller.getIdentifierSummary( ).addConversationId( newConversationId );
				controller.dataModified( );
				elementRenamed = true;
			}
		}

		return elementRenamed;
	}

	@Override
	public void updateFlagSummary( FlagSummary flagSummary ) {
		updateFlagSummaryInNode( flagSummary, treeConversation.getRootNode( ) );
	}

	/**
	 * Updates the given flag summary, adding the flag references contained in the given node. This method works
	 * recursively.
	 * 
	 * @param flagSummary
	 *            Flag summary to update
	 * @param conversationNode
	 *            Node in which the identifier must be searched
	 */
	public void updateFlagSummaryInNode( FlagSummary flagSummary, ConversationNode conversationNode ) {
		// Update the summary with the effects
		if( conversationNode.hasEffects( ) )
			EffectsController.updateFlagSummary( flagSummary, conversationNode.getEffects( ) );

		// Spread the call to the children (if the node hasn't got a go-back tag)
		if( !TreeConversation.thereIsGoBackTag( conversationNode ) )
			for( int i = 0; i < conversationNode.getChildCount( ); i++ )
				updateFlagSummaryInNode( flagSummary, conversationNode.getChild( i ) );
	}

	@Override
	public boolean isValid( String currentPath, List<String> incidences ) {
		return true;
	}

	@Override
	public int countAssetReferences( String assetPath ) {
		return countAssetReferencesInNode( assetPath, treeConversation.getRootNode( ) );
	}

	/**
	 * Counts all the references to a given asset in the given node. This method works recursively.
	 * 
	 * @param assetPath
	 *            Path to the asset (relative to the ZIP), without suffix in case of an animation or set of slides
	 * @param conversationNode
	 *            Node in which the asset must be searched
	 * @return Number of references to the given asset
	 */
	public int countAssetReferencesInNode( String assetPath, ConversationNode conversationNode ) {
		int count = 0;

		// Count the asset references in the effects
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

		// Spread the call to the children (if the node hasn't got a go-back tag)
		if( !TreeConversation.thereIsGoBackTag( conversationNode ) )
			for( int i = 0; i < conversationNode.getChildCount( ); i++ )
				count += countAssetReferencesInNode( assetPath, conversationNode.getChild( i ) );

		return count;
	}
	
	public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes){
		// Check every node on the conversation
		getAssetReferencesInNode ( assetPaths, assetTypes, treeConversation.getRootNode( ));
	}
	
	public void getAssetReferencesInNode( List<String> assetPaths, List<Integer> assetTypes, ConversationNode conversationNode ){
		// Delete the asset references from the effects, if available
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
	

	@Override
	public void deleteAssetReferences( String assetPath ) {
		deleteAssetReferencesInNode( assetPath, treeConversation.getRootNode( ) );
	}

	/**
	 * Searchs all the references to a given asset in a given node, and deletes them. This method works recursively.
	 * 
	 * @param assetPath
	 *            Path to the asset (relative to the ZIP), without suffix in case of an animation or set of slides
	 * @param conversationNode
	 *            Node in which the asset must be searched
	 */
	public void deleteAssetReferencesInNode( String assetPath, ConversationNode conversationNode ) {
		// Delete the asset references in the effects
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
		
		// Spread the call to the children (if the node hasn't got a go-back tag)
		if( !TreeConversation.thereIsGoBackTag( conversationNode ) )
			for( int i = 0; i < conversationNode.getChildCount( ); i++ )
				deleteAssetReferencesInNode( assetPath, conversationNode.getChild( i ) );
		
	}

	@Override
	public int countIdentifierReferences( String id ) {
		return countIdentifierReferencesInNode( id, treeConversation.getRootNode( ) );
	}

	/**
	 * Counts all the references to a given identifier in a given node. This method works recursively.
	 * 
	 * @param id
	 *            Identifier to which the references must be found
	 * @param conversationNode
	 *            Node in which the identifier must be searched
	 * @return Number of references to the given identifier
	 */
	public int countIdentifierReferencesInNode( String id, ConversationNode conversationNode ) {
		int count = 0;

		// Search in the lines only in dialogue nodes
		if( conversationNode.getType( ) == ConversationNode.DIALOGUE ) {
			// Check every line
			for( int i = 0; i < conversationNode.getLineCount( ); i++ ) {
				if( conversationNode.getLine( i ).getName( ).equals( id ) )
					count++;
			}
		}

		// Count the effects references too
		if( conversationNode.hasEffects( ) )
			count += EffectsController.countIdentifierReferences( id, conversationNode.getEffects( ) );

		// Spread the call to the children (if the node hasn't got a go-back tag)
		if( !TreeConversation.thereIsGoBackTag( conversationNode ) )
			for( int i = 0; i < conversationNode.getChildCount( ); i++ )
				countIdentifierReferencesInNode( id, conversationNode.getChild( i ) );

		return count;
	}

	@Override
	public void replaceIdentifierReferences( String oldId, String newId ) {
		replaceIdentifierReferencesInNode( oldId, newId, treeConversation.getRootNode( ) );
	}

	/**
	 * Searchs all the references to a given identifier in a given node, and replaces them with another one. This method
	 * works recursively.
	 * 
	 * @param oldId
	 *            Identifier to be replaced
	 * @param newId
	 *            Identifier to replace the old one
	 * @param conversationNode
	 *            Node in which the identifier must be searched
	 */
	public void replaceIdentifierReferencesInNode( String oldId, String newId, ConversationNode conversationNode ) {
		// Replace the lines only in dialogue nodes
		if( conversationNode.getType( ) == ConversationNode.DIALOGUE ) {
			// Check every line
			for( int i = 0; i < conversationNode.getLineCount( ); i++ ) {
				ConversationLine conversationLine = conversationNode.getLine( i );
				if( conversationLine.getName( ).equals( oldId ) )
					conversationLine.setName( newId );
			}
		}

		// Count the effects references too
		if( conversationNode.hasEffects( ) )
			EffectsController.replaceIdentifierReferences( oldId, newId, conversationNode.getEffects( ) );

		// Spread the call to the children (if the node hasn't got a go-back tag)
		if( !TreeConversation.thereIsGoBackTag( conversationNode ) )
			for( int i = 0; i < conversationNode.getChildCount( ); i++ )
				replaceIdentifierReferencesInNode( oldId, newId, conversationNode.getChild( i ) );
	}

	@Override
	public void deleteIdentifierReferences( String id ) {
		deleteIdentifierReferencesInNode( id, treeConversation.getRootNode( ) );
	}

	/**
	 * Searchs all the references to a given identifier in a given node, and deletes them. This method works
	 * recursively.
	 * 
	 * @param id
	 *            Identifier to be deleted
	 * @param conversationNode
	 *            Node in which the identifier must be searched
	 */
	public void deleteIdentifierReferencesInNode( String id, ConversationNode conversationNode ) {
		// Delete the lines only in dialogue nodes
		if( conversationNode.getType( ) == ConversationNode.DIALOGUE ) {
			// Check every line
			int i = 0;
			while( i < conversationNode.getLineCount( ) ) {
				if( conversationNode.getLine( i ).getName( ).equals( id ) )
					conversationNode.removeLine( i );
				else
					i++;
			}
		}

		// Delete the effects references too
		if( conversationNode.hasEffects( ) )
			EffectsController.deleteIdentifierReferences( id, conversationNode.getEffects( ) );

		// Spread the call to the children (if the node hasn't got a go-back tag)
		if( !TreeConversation.thereIsGoBackTag( conversationNode ) )
			for( int i = 0; i < conversationNode.getChildCount( ); i++ )
				deleteIdentifierReferencesInNode( id, conversationNode.getChild( i ) );
	}

	@Override
	public boolean canBeDuplicated( ) {
		return true;
	}
}
