package es.eucm.eadventure.editor.control.tools.conversation;

import es.eucm.eadventure.common.data.chapter.conversation.line.ConversationLine;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNode;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNodeView;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.conversation.ConversationDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class LinkConversationNodeTool extends Tool{

	private ConversationNodeView fatherView; 
	
	private ConversationNodeView childView;
	
	private Controller controller;
	
	private ConversationDataControl dataControl;
	
	public LinkConversationNodeTool ( ConversationDataControl _dataControl, ConversationNodeView _fatherView, ConversationNodeView _childView ){
		this.fatherView = _fatherView;
		this.childView = _childView;
		this.dataControl = _dataControl;
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
		boolean nodeLinked = false;

		// If it is not possible to link the node to the given one, show a message
		if( !dataControl.canLinkNodeTo( fatherView, childView ) )
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
				nodeLinked = true;
			}
		}

		return nodeLinked;
	}

	@Override
	public boolean redoTool() {
		// Take the complete nodes
		ConversationNode father = (ConversationNode) fatherView;
		ConversationNode child = (ConversationNode) childView;
		// Add the new child
		father.addChild( child );
		// If the father is an option node, add a new line
		if( father.getType( ) == ConversationNode.OPTION )
			father.addLine( new ConversationLine( ConversationLine.PLAYER, TextConstants.getText( "ConversationLine.DefaultText" ) ) );
		controller.updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		// Take the complete nodes
		ConversationNode father = (ConversationNode) fatherView;
		ConversationNode child = (ConversationNode) childView;
		
		// Add the new child
		int index = -1;
		for ( int i=0; i<father.getChildCount(); i++){
			if ( father.getChild( i ) == child){
				index = i;
				father.removeChild( i );
				break;
			}
		}
		
		// If the father is an option node, add a new line
		if( father.getType( ) == ConversationNode.OPTION && index!=-1)
			father.removeLine( index );
		controller.updatePanel();
		return true;
	}
	
}
