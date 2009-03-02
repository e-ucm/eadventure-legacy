package es.eucm.eadventure.editor.control.tools.conversation;

import es.eucm.eadventure.common.data.chapter.conversation.TreeConversation;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNode;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNodeView;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.conversation.TreeConversationDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class ToggleGoBackTagTool extends Tool{

	private Controller controller;
	
	private ConversationNodeView nodeView;
	private TreeConversation treeConversation;
	
	private TreeConversationDataControl dataControl;
	private ConversationNode father;
	private ConversationNode oldNode;
	
	private boolean thereWasGBTag;
	
	public ToggleGoBackTagTool ( TreeConversationDataControl dataControl, ConversationNodeView nodeView ){
		this.dataControl = dataControl;
		this.nodeView = nodeView;
		this.treeConversation = (TreeConversation)dataControl.getConversation();
		this.controller = Controller.getInstance();
	}
	
	@Override
	public boolean canRedo() {
		return true;
	}

	@Override
	public boolean canUndo() {
		return true;
	}

	@Override
	public boolean combine(Tool other) {
		return false;
	}

	@Override
	public boolean doTool() {
		ConversationNode node = (ConversationNode) nodeView;
		boolean goBackTagAdded = false;

		// If there is no "go-back" tag, add it
		if( !TreeConversation.thereIsGoBackTag( node ) ) {
			thereWasGBTag = true;

			boolean addGoBackTag = true;

			// If the node has an effect, ask for confirmation (for the effect will be deleted)
			if( nodeView.hasEffects( ) )
				addGoBackTag = controller.showStrictConfirmDialog( TextConstants.getText( "Conversation.OperationAddGoBackTag" ), TextConstants.getText( "Conversation.ConfirmationAddGoBackTag" ) );

			// Add the go-back tag
			if( addGoBackTag ) {

				// First we must search for the father of the node
				father = dataControl.searchForFather( treeConversation.getRootNode( ), node );

				// Attach then the node to the father
				node.addChild( father );

				goBackTagAdded = true;
			}
		}

		// It there is a "go-back" tag, delete it
		else {
			thereWasGBTag = false;
			// We remove the only child of the node
			oldNode = node.removeChild( 0 );
			goBackTagAdded = true;
		}

		return goBackTagAdded;
	}

	@Override
	public boolean redoTool() {
		ConversationNode node = (ConversationNode) nodeView;
		if (thereWasGBTag)
			node.addChild( father );
		else
			node.removeChild(0);
		return true;
	}

	@Override
	public boolean undoTool() {
		ConversationNode node = (ConversationNode) nodeView;
		if (thereWasGBTag){
			for ( int i=0; i<node.getChildCount(); i++ ){
				if ( node.getChild( i )== father){
					node.removeChild(i);
				}
			}
		}else
			node.addChild(0, oldNode);
		return true;
	}

}
