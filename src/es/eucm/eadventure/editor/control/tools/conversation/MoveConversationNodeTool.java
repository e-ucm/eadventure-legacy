package es.eucm.eadventure.editor.control.tools.conversation;

import es.eucm.eadventure.common.data.chapter.conversation.TreeConversation;
import es.eucm.eadventure.common.data.chapter.conversation.line.ConversationLine;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNode;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNodeView;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.conversation.TreeConversationDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class MoveConversationNodeTool extends Tool {

	private Controller controller;
	private ConversationNodeView nodeView; 
	
	private ConversationNodeView hostNodeView;
	private int newIndex;
	
	private ConversationNodeView oldHostNodeView;
	private int oldIndex;
	private ConversationLine oldLine;
	
	private TreeConversationDataControl dataControl;
	private TreeConversation treeConversation;
	
	public MoveConversationNodeTool ( TreeConversationDataControl dataControl, ConversationNodeView nodeView, ConversationNodeView hostNodeView){
		this.dataControl = dataControl;
		this.nodeView = nodeView;
		this.hostNodeView = hostNodeView;
		this.controller = Controller.getInstance();
		this.treeConversation = (TreeConversation)dataControl.getConversation();
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
		boolean nodeMoved = false;

		// If it is not possible to move the node to the given position, show a message
		if( !dataControl.canMoveNodeTo( nodeView, hostNodeView ) )
			controller.showErrorDialog( TextConstants.getText( "Conversation.OperationModeNode" ), TextConstants.getText( "Conversation.ErrorMoveNode" ) );

		// If it can be moved, try to move the node
		else {

			// First we check that is possible to move, and that hostNode is not a child of node, because that would
			// make a cycle
			if( !dataControl.isChildOf( hostNodeView, nodeView ) ) {
				// Take the full conversation node
				ConversationNode node = (ConversationNode) nodeView;
				ConversationNode hostNode = (ConversationNode) hostNodeView;

				// First obtain the father of node, to delete the link
				ConversationNode fatherOfNode = dataControl.searchForFather( treeConversation.getRootNode( ), node );

				int i = 0;
				// For each child of the father node
				while( i < fatherOfNode.getChildCount( ) ) {
					// If the current child is the node to be moved, remove it
					if( fatherOfNode.getChild( i ) == node ) {
						oldHostNodeView = fatherOfNode;
						oldIndex = i;
						fatherOfNode.removeChild( i );

						// Remove the line too if it is an option node
						if( fatherOfNode.getType( ) == ConversationNode.OPTION )
							oldLine = fatherOfNode.removeLine( i );
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

				nodeMoved = true;
			}
		}

		return nodeMoved;
	}

	@Override
	public boolean redoTool() {
		ConversationNode newHost = (ConversationNode)hostNodeView;
		ConversationNode oldHost = (ConversationNode)oldHostNodeView;
		ConversationNode node = (ConversationNode)nodeView;
		
		// Remove from old host
		for (int i=0; i<oldHost.getChildCount(); i++){
			if ( oldHost.getChild( i ) == node ){
				oldHost.removeChild( i );
				if (oldHost.getType() == ConversationNode.OPTION)
					oldHost.removeLine( i );
				break;
			}
		}
		
		// Now add it to the new host
		newHost.addChild( newIndex, node );
		if (newHost.getType() == ConversationNode.OPTION){
			newHost.addLine( newIndex, new ConversationLine( ConversationLine.PLAYER, TextConstants.getText( "ConversationLine.DefaultText" ) ));
		}
		return true;
	}

	@Override
	public boolean undoTool() {
		ConversationNode newHost = (ConversationNode)hostNodeView;
		ConversationNode oldHost = (ConversationNode)oldHostNodeView;
		ConversationNode node = (ConversationNode)nodeView;
		
		// Remove from new host
		for (int i=0; i<newHost.getChildCount(); i++){
			if ( newHost.getChild( i ) == node ){
				newHost.removeChild( i );
				newIndex = i;
				if (newHost.getType() == ConversationNode.OPTION)
					newHost.removeLine( i );
				break;
			}
		}
		
		// Now add it to old host
		oldHost.addChild( oldIndex, node );
		if (oldHost.getType() == ConversationNode.OPTION){
			oldHost.addLine( oldIndex, oldLine);
		}
		return true;
	}

}
