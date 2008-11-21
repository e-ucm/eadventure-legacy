package es.eucm.eadventure.editor.control.controllers.conversation;

import es.eucm.eadventure.common.data.chapter.conversation.line.ConversationLine;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNode;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNodeView;
import es.eucm.eadventure.common.data.chapter.conversation.node.DialogueConversationNode;
import es.eucm.eadventure.common.data.chapter.conversation.node.OptionConversationNode;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.EffectsController;
import es.eucm.eadventure.editor.gui.assetchooser.AssetChooser;
import es.eucm.eadventure.editor.gui.editdialogs.EffectsDialog;

public abstract class ConversationDataControl extends DataControl {

	/**
	 * Returns the type of the contained conversation.
	 * 
	 * @return Type of the contained conversation
	 */
	public abstract int getType( );

	/**
	 * Returns the id of the contained conversation.
	 * 
	 * @return Id of the contained conversation
	 */
	public abstract String getId( );

	/**
	 * Returns the root node of the conversation.
	 * 
	 * @return Root node
	 */
	public abstract ConversationNodeView getRootNode( );

	/**
	 * Returns the number of lines that has the conversation.
	 * 
	 * @return Number of lines of the conversation
	 */
	public abstract int getConversationLineCount( );

	/**
	 * Returns the types of nodes which can be added to the given node
	 * 
	 * @param nodeView
	 *            Node which we want to know what kind of node can be added
	 * @return Array of node types that can be added
	 */
	public abstract int[] getAddableNodes( ConversationNodeView nodeView );

	/**
	 * Returns if it is possible to add a child to the given node
	 * 
	 * @param nodeView
	 *            Node which we want to know if a child is addable
	 * @param nodeType
	 *            The type of node that we want to add
	 * @return True if a child can be added (get NodeTypes with getAddeableNodes( ConversationalNode )), false otherwise
	 */
	public abstract boolean canAddChild( ConversationNodeView nodeView, int nodeType );

	/**
	 * Returns if it is possible to link the given node
	 * 
	 * @param nodeView
	 *            Node to be linked
	 * @return True if the node initially can be linked to another one, false otherwise
	 */
	public abstract boolean canLinkNode( ConversationNodeView nodeView );

	/**
	 * Returns if it is possible to link a given node with another one
	 * 
	 * @param fatherView
	 *            Node to act as the father
	 * @param childView
	 *            Node to act as the child
	 * @return True if node2 can be attached to node1, false otherwise
	 */
	protected abstract boolean canLinkNodeTo( ConversationNodeView fatherView, ConversationNodeView childView );

	/**
	 * Returns if it is possible to delete the given node
	 * 
	 * @param nodeView
	 *            Node to be deleted
	 * @return True if the node can be deleted, false otherwise
	 */
	public abstract boolean canDeleteNode( ConversationNodeView nodeView );

	/**
	 * Returns if it is possible to move the given node
	 * 
	 * @param nodeView
	 *            Node to be moved
	 * @return True if the node initially can be moved, false otherwise
	 */
	public abstract boolean canMoveNode( ConversationNodeView nodeView );

	/**
	 * Returns if it is possible to move the given node to a child position of the given host node
	 * 
	 * @param nodeView
	 *            Node to be moved
	 * @param hostNodeView
	 *            Node that will act as host
	 * @return True if node can be moved as a child of host node, false otherwise
	 */
	protected abstract boolean canMoveNodeTo( ConversationNodeView nodeView, ConversationNodeView hostNodeView );

	/**
	 * Adds a new child of the indicated type, to the given node
	 * 
	 * @param nodeView
	 *            Node in which the child will be placed
	 * @param nodeType
	 *            Type of node to be added
	 * @return True if a node was added, false otherwise
	 */
	public boolean addChild( ConversationNodeView nodeView, int nodeType ) {
		ConversationNode newChild = null;

		// By default, add the child
		boolean addChild = true;

		// If the node has effects, ask for confirmation (for the effect will be deleted)
		//if( nodeView.hasEffects( ) )
		//	addChild = controller.showStrictConfirmDialog( TextConstants.getText( "Conversation.OperationAddChild" ), TextConstants.getText( "Conversation.ConfirmationAddChildToNodeWithEffects" ) );

		// If it's sure to add the child
		if( addChild ) {

			// Create the requested node (only accept dialogue and option node)
			if( nodeType == ConversationNode.DIALOGUE )
				newChild = new DialogueConversationNode( );
			if( nodeType == ConversationNode.OPTION )
				newChild = new OptionConversationNode( );

			// If a child has been created
			if( newChild != null ) {
				// Take the full conversation node
				ConversationNode node = (ConversationNode) nodeView;

				// Add the child to the given node
				node.addChild( newChild );

				// If the node was an option node, add a new line
				if( node.getType( ) == ConversationNode.OPTION )
					node.addLine( new ConversationLine( ConversationLine.PLAYER, TextConstants.getText( "ConversationLine.NewOption" ) ) );

				// Set the data as modified
				controller.dataModified( );
			}
		}

		return newChild != null;
	}

	/**
	 * Links the two given nodes, as father and child
	 * 
	 * @param fatherView
	 *            Father node (first selected node)
	 * @param childView
	 *            Child node (second selected node)
	 * @return True if the nodes had been successfully linked, false otherwise
	 */
	public abstract boolean linkNode( ConversationNodeView fatherView, ConversationNodeView childView );

	/**
	 * Deletes the given node in the conversation
	 * 
	 * @param nodeView
	 *            Node to be deleted
	 * @return True if the node was successfully deleted, false otherwise
	 */
	public abstract boolean deleteNode( ConversationNodeView nodeView );

	/**
	 * Moves the given node to a child position of the given host node
	 * 
	 * @param nodeView
	 *            Node to be moved
	 * @param hostNodeView
	 *            Node that will act as host
	 * @return True if the node was succesfully moved, false otherwise
	 */
	public abstract boolean moveNode( ConversationNodeView nodeView, ConversationNodeView hostNodeView );

	/**
	 * Adds a line in the given node, with the given name and a default text.
	 * 
	 * @param nodeView
	 *            Node in which the line must be placed
	 * @param lineIndex
	 *            Index in which the line will be placed
	 * @param name
	 *            Name of the line
	 */
	public void addNodeLine( ConversationNodeView nodeView, int lineIndex, String name ) {
		// Insert the dialogue line in the selected position
		( (ConversationNode) nodeView ).addLine( lineIndex, new ConversationLine( name, TextConstants.getText( "ConversationLine.DefaultText" ) ) );

		// Set the data as modified
		controller.dataModified( );
	}

	/**
	 * Sets a new name in the given line of the node.
	 * 
	 * @param nodeView
	 *            Node in which the line is placed
	 * @param lineIndex
	 *            Index of the line to modify
	 * @param name
	 *            New name for the line
	 */
	public void setNodeLineName( ConversationNodeView nodeView, int lineIndex, String name ) {
		// Take the complete node
		ConversationNode node = (ConversationNode) nodeView;

		// Set the new name for the line if the value has changed
		if( !node.getLineName( lineIndex ).equals( name ) ) {
			node.getLine( lineIndex ).setName( name );

			// Set the data as modified
			controller.dataModified( );
		}
	}

	/**
	 * Sets a new text in the given line of the node.
	 * 
	 * @param nodeView
	 *            Node in which the line is placed
	 * @param lineIndex
	 *            Index of the line to modify
	 * @param text
	 *            New text for the line
	 */
	public void setNodeLineText( ConversationNodeView nodeView, int lineIndex, String text ) {
		// Take the complete node
		ConversationNode node = (ConversationNode) nodeView;

		// Set the new text for the line if the value has changed
		if( !node.getLineName( lineIndex ).equals( text ) ) {
			node.getLine( lineIndex ).setText( text );

			// Set the data as modified
			controller.dataModified( );
		}
	}
	
	/**
	 * Sets a new audio path in the given line of the node.
	 * 
	 * @param nodeView
	 *            Node in which the line is placed
	 * @param lineIndex
	 *            Index of the line to modify
	 * @param audioPath
	 *            New audio path for the line
	 */
	public void setNodeLineAudioPath( ConversationNodeView nodeView, int lineIndex, String audioPath ) {
		// Take the complete node
		ConversationNode node = (ConversationNode) nodeView;

		// Set the new text for the line if the value has changed
		if( !node.hasAudioPath( lineIndex ) || !node.getAudioPath( lineIndex ).equals( audioPath ) ) {
			node.getLine( lineIndex ).setAudioPath( audioPath );

			// Set the data as modified
			controller.dataModified( );
		}
	}


	/**
	 * Moves a line up in the list of the given node.
	 * 
	 * @param nodeView
	 *            Node which holds the line
	 * @param lineIndex
	 *            Index of the line to move
	 * @return True if the line was moved, false otherwise
	 */
	public boolean moveNodeLineUp( ConversationNodeView nodeView, int lineIndex ) {
		boolean lineMoved = false;

		// Take the full node
		ConversationNode node = (ConversationNode) nodeView;

		// Cannot move the line up if it is in the first position
		if( lineIndex > 0 ) {
			// Remove the line and insert it in the upper position
			node.addLine( lineIndex - 1, node.removeLine( lineIndex ) );

			// If the node is a OptionNode, move the respective child along the line
			if( node.getType( ) == ConversationNode.OPTION ) {
				ConversationNode nodeMoved = node.removeChild( lineIndex );
				node.addChild( lineIndex - 1, nodeMoved );
			}

			// The line was successfully moved
			controller.dataModified( );
			lineMoved = true;
		}

		return lineMoved;
	}

	/**
	 * Moves a line down in the list of the given node.
	 * 
	 * @param nodeView
	 *            Node which holds the line
	 * @param lineIndex
	 *            Index of the line to move
	 * @return True if the line was moved, false otherwise
	 */
	public boolean moveNodeLineDown( ConversationNodeView nodeView, int lineIndex ) {
		boolean lineMoved = false;

		// Take the full node
		ConversationNode node = (ConversationNode) nodeView;

		// Cannot move the line down if it is the last position
		if( lineIndex < node.getLineCount( ) - 1 ) {
			// Remove the line and insert it in the lower position
			node.addLine( lineIndex + 1, node.removeLine( lineIndex ) );

			// If the node is a OptionNode, move the respective child along the line
			if( node.getType( ) == ConversationNodeView.OPTION ) {
				ConversationNode nodeMoved = node.removeChild( lineIndex );
				node.addChild( lineIndex + 1, nodeMoved );
			}

			// The line was successfully moved
			controller.dataModified( );
			lineMoved = true;
		}

		return lineMoved;
	}

	/**
	 * Deletes a line in the given node.
	 * 
	 * @param nodeView
	 *            Node in which the line will be deleted
	 * @param lineIndex
	 *            Index of the line to delete
	 */
	public void deleteNodeLine( ConversationNodeView nodeView, int lineIndex ) {
		// Delete the line
		( (ConversationNode) nodeView ).removeLine( lineIndex );

		// Set the data as modified
		controller.dataModified( );
	}

	/**
	 * Deletes the link with the child node. This method should only be used with dialogue nodes (to delete the link
	 * with their only child). For option nodes, the method <i>deleteNodeOption</i> should be used instead.
	 * 
	 * @param nodeView
	 *            Dialogue node to delete the link
	 * @return True if the link was deleted, false otherwise
	 */
	public boolean deleteNodeLink( ConversationNodeView nodeView ) {
		boolean linkDeleted = false;

		// Ask for confirmation
		if( controller.showStrictConfirmDialog( TextConstants.getText( "Conversation.OperationDeleteLink" ), TextConstants.getText( "Conversation.ConfirmationDeleteLink" ) ) ) {
			// Delete the link of the node
			( (ConversationNode) nodeView ).removeChild( 0 );

			// Set the data as modified
			controller.dataModified( );
			linkDeleted = true;
		}

		return linkDeleted;
	}

	/**
	 * Deletes the given option in the node. This method should only be used with option nodes, for it deletes the child
	 * node along the option. To delete the links on dialogue nodes, use the method <i>deleteNodeLink</i> instead.
	 * 
	 * @param nodeView
	 *            Option node to delete the option
	 * @param optionIndex
	 *            Index of the option to be deleted
	 * @return True if the option was deleted, false otherwise
	 */
	public boolean deleteNodeOption( ConversationNodeView nodeView, int optionIndex ) {
		boolean optionDeleted = false;

		// Ask for confirmation
		if( controller.showStrictConfirmDialog( TextConstants.getText( "Conversation.OperationDeleteOption" ), TextConstants.getText( "Conversation.ConfirmationDeleteOption" ) ) ) {
			// Take the complete node
			ConversationNode node = (ConversationNode) nodeView;

			// Delete the selected option along with the line
			node.removeLine( optionIndex );
			node.removeChild( optionIndex );

			// Set the data as modified
			controller.dataModified( );
			optionDeleted = true;
		}

		return optionDeleted;
	}

	/**
	 * Shows the GUI to edit the effects of the node.
	 * 
	 * @param nodeView
	 *            Node whose effects we want to modify
	 */
	public void editNodeEffects( ConversationNodeView nodeView ) {
		// Take the complete node
		ConversationNode node = (ConversationNode) nodeView;

		// Show a edit effects dialog with a new effects controller
		new EffectsDialog( new EffectsController( node.getEffects( ) ) );
	}

	@Override
	public int[] getAddableElements( ) {
		return new int[] {};
	}

	@Override
	public boolean canAddElement( int type ) {
		return false;
	}

	@Override
	public boolean canBeDeleted( ) {
		return true;
	}

	@Override
	public boolean canBeMoved( ) {
		return true;
	}

	@Override
	public boolean canBeRenamed( ) {
		return true;
	}

	@Override
	public boolean addElement( int type ) {
		return false;
	}

	@Override
	public boolean deleteElement( DataControl dataControl ) {
		return false;
	}

	@Override
	public boolean moveElementUp( DataControl dataControl ) {
		return false;
	}

	@Override
	public boolean moveElementDown( DataControl dataControl ) {
		return false;
	}

	public boolean editLineAudioPath( ConversationNodeView selectedNode, int selectedRow ) {
		boolean edited=false;
		String selectedAsset = null;
		AssetChooser chooser = AssetsController.getAssetChooser( AssetsController.CATEGORY_AUDIO, AssetsController.FILTER_NONE );
		int option = chooser.showAssetChooser( controller.peekWindow( ) );
		//In case the asset was selected from the zip file
		if( option == AssetChooser.ASSET_FROM_ZIP ) {
			selectedAsset = chooser.getSelectedAsset( );
		}

		//In case the asset was not in the zip file: first add it
		else if( option == AssetChooser.ASSET_FROM_OUTSIDE ) {
			boolean added = AssetsController.addSingleAsset( AssetsController.CATEGORY_AUDIO, chooser.getSelectedFile( ).getAbsolutePath( ) );
			if( added ) {
				selectedAsset = chooser.getSelectedFile( ).getName( );
			}
		}

		// If a file was selected
		if( selectedAsset != null ) {
			// Take the index of the selected asset
			String[] assetFilenames = AssetsController.getAssetFilenames( AssetsController.CATEGORY_AUDIO );
			String[] assetPaths = AssetsController.getAssetsList( AssetsController.CATEGORY_AUDIO );
			int assetIndex = -1;
			for( int i = 0; i < assetFilenames.length; i++ )
				if( assetFilenames[i].equals( selectedAsset ) )
					assetIndex = i;

			// Store the data in the resources block (removing the suffix if necessary)
			this.setNodeLineAudioPath( selectedNode, selectedRow, assetPaths[assetIndex] );
			edited=true;
			controller.dataModified( );
		}

		return edited;
	}
	
	
	 /**
	  * Change the randomly in the selected node.
	  * 
	  * @param selectedNode
	  * 			The node in which will be the actions
	  * 
	  */
	public void setRandomlyOptions(ConversationNodeView selectedNode){
		ConversationNode node = (ConversationNode) selectedNode;
		// Set the data as modified
		controller.dataModified( );
		//Change the randomly of showing of options
			((OptionConversationNode)node).changeRandomly();
			
		
	}
	
	public boolean isRandomActivate(ConversationNodeView selectedNode){
		ConversationNode node = (ConversationNode) selectedNode;
		return ((OptionConversationNode)node).isRandom();
	}
}
