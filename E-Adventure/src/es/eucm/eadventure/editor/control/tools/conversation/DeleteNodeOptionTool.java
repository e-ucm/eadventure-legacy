package es.eucm.eadventure.editor.control.tools.conversation;

import es.eucm.eadventure.common.data.chapter.conversation.line.ConversationLine;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNode;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNodeView;
import es.eucm.eadventure.common.gui.TextConstants;

public class DeleteNodeOptionTool extends DeleteNodeLinkTool{

	protected ConversationLine deletedLine;
	
	public DeleteNodeOptionTool(ConversationNode parent, int optionIndex) {
		super(parent);
		this.confirmText = TextConstants.getText( "Conversation.ConfirmationDeleteOption" );
		this.confirmTitle = TextConstants.getText( "Conversation.OperationDeleteOption" );
		this.linkIndex = optionIndex;
	}
	
	public DeleteNodeOptionTool(ConversationNodeView parent, int optionIndex) {
		super(parent);
		this.confirmText = TextConstants.getText( "Conversation.ConfirmationDeleteOption" );
		this.confirmTitle = TextConstants.getText( "Conversation.OperationDeleteOption" );
		this.linkIndex = optionIndex;
	}
	
	public boolean doTool(){
		boolean done = super.doTool();
		if (done){
			deletedLine = parent.getLine(linkIndex);
			parent.removeLine( linkIndex );
		}
		return done;
	}
	
	public boolean undoTool(){
		boolean done = super.undoTool();
		if (done){
			parent.addLine( linkIndex, deletedLine );
		}
		return done;
	}

	public boolean redoTool(){
		boolean done = super.redoTool();
		if (done){
			parent.removeLine( linkIndex );
		}
		return done;
	}

}
