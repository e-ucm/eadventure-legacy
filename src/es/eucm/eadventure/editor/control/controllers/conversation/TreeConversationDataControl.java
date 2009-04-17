package es.eucm.eadventure.editor.control.controllers.conversation;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.conversation.Conversation;
import es.eucm.eadventure.common.data.chapter.conversation.TreeConversation;
import es.eucm.eadventure.common.data.chapter.conversation.line.ConversationLine;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNode;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNodeView;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.EffectsController;
import es.eucm.eadventure.editor.control.tools.conversation.DeleteConversationNodeTool;
import es.eucm.eadventure.editor.control.tools.conversation.MoveConversationNodeTool;
import es.eucm.eadventure.editor.control.tools.conversation.ToggleGoBackTagTool;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

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
	public boolean canLinkNodeTo( ConversationNodeView fatherView, ConversationNodeView childView ) {
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
	public boolean canMoveNodeTo( ConversationNodeView nodeView, ConversationNodeView hostNodeView ) {
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
		return controller.addTool(new DeleteConversationNodeTool(DeleteConversationNodeTool.MODE_TREE, nodeView, getConversation()));
	}

	@Override
	public boolean moveNode( ConversationNodeView nodeView, ConversationNodeView hostNodeView ) {
		return controller.addTool( new MoveConversationNodeTool( this, nodeView, hostNodeView ) );
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
		return controller.addTool( new ToggleGoBackTagTool(this, nodeView));
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
	public boolean isChildOf( ConversationNodeView child, ConversationNodeView father ) {
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
	public ConversationNode searchForFather( ConversationNode possibleFather, ConversationNode childNode ) {
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

	@Override
	public Object getContent( ) {
		return treeConversation;
	}

	@Override
	public String renameElement( String name ) {
		boolean elementRenamed = false;
		String oldConversationId = treeConversation.getId( );
		String references = String.valueOf( controller.countIdentifierReferences( oldConversationId ) );

		// Ask for confirmation
		if(name != null || controller.showStrictConfirmDialog( TextConstants.getText( "Operation.RenameConversationTitle" ), TextConstants.getText( "Operation.RenameElementWarning", new String[] { oldConversationId, references } ) ) ) {

			// Show a dialog asking for the new conversation id
			String newConversationId = name;
			if (name == null)
				newConversationId = controller.showInputDialog( TextConstants.getText( "Operation.RenameConversationTitle" ), TextConstants.getText( "Operation.RenameConversationMessage" ), oldConversationId );

			// If some value was typed and the identifiers are different
			if( newConversationId != null && !newConversationId.equals( oldConversationId ) && controller.isElementIdValid( newConversationId ) ) {
				treeConversation.setId( newConversationId );
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
		updateFlagSummaryInNode( varFlagSummary, treeConversation.getRootNode( ) );
	}

	/**
	 * Updates the given flag summary, adding the flag references contained in the given node. This method works
	 * recursively.
	 * 
	 * @param varFlagSummary
	 *            Flag summary to update
	 * @param conversationNode
	 *            Node in which the identifier must be searched
	 */
	public void updateFlagSummaryInNode( VarFlagSummary varFlagSummary, ConversationNode conversationNode ) {
		// Update the summary with the effects
		if( conversationNode.hasEffects( ) )
			EffectsController.updateVarFlagSummary( varFlagSummary, conversationNode.getEffects( ) );

		// Spread the call to the children (if the node hasn't got a go-back tag)
		if( !TreeConversation.thereIsGoBackTag( conversationNode ) )
			for( int i = 0; i < conversationNode.getChildCount( ); i++ )
				updateFlagSummaryInNode( varFlagSummary, conversationNode.getChild( i ) );
	}

	@Override
	public boolean isValid( String currentPath, List<String> incidences ) {
		return isValidNode ( treeConversation.getRootNode(), currentPath, incidences, new ArrayList<ConversationNode>() );
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

	@Override
	public void recursiveSearch() {
		check(this.getId(), "ID");
		List<ConversationNodeView> list = new ArrayList<ConversationNodeView>();
		list.add(this.getRootNode());
		int j = 0;
		while(j < list.size()) {
			ConversationNodeView temp = list.get(j);
			j++;
			for (int i = 0; i < temp.getChildCount(); i++)
				if (!list.contains(temp.getChildView(i)))
					list.add(temp.getChildView(i));
			for (int i = 0; i < temp.getLineCount(); i ++) {
				check(temp.getLineName(i), TextConstants.getText("Search.LineName"));
				check(temp.getLineText(i), TextConstants.getText("Search.LineText"));
			}
		}
	}

	@Override
	public Conversation getConversation() {
		return treeConversation;
	}

	@Override
	public void setConversation(Conversation conversation) {
		if ( conversation instanceof TreeConversation){
			this.treeConversation = (TreeConversation)conversation;
		}
	}
	
	@Override
	public List<DataControl> getPathToDataControl(DataControl dataControl) {
		return null;
	}

}
