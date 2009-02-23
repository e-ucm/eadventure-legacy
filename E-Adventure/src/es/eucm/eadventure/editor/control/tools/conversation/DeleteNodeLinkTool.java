package es.eucm.eadventure.editor.control.tools.conversation;

import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNode;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNodeView;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class DeleteNodeLinkTool extends Tool {

	protected ConversationNode parent;
	
	protected int linkIndex;
	
	protected ConversationNode linkDeleted;
	
	protected String confirmTitle;
	
	protected String confirmText;
	
	public DeleteNodeLinkTool ( ConversationNodeView nodeView ){
		this ( (ConversationNode) nodeView );
	}
	
	public DeleteNodeLinkTool ( ConversationNode parent ){
		this.parent = parent;
		this.linkIndex = 0;
		this.confirmTitle = TextConstants.getText( "Conversation.OperationDeleteLink" );
		this.confirmText = TextConstants.getText( "Conversation.ConfirmationDeleteLink" );
	}

	
	@Override
	public boolean canRedo() {
		return true;
	}

	@Override
	public boolean canUndo() {
		return linkDeleted!=null;
	}

	@Override
	public boolean combine(Tool other) {
		return false;
	}

	@Override
	public boolean doTool() {
		// Ask for confirmation
		if( Controller.getInstance().showStrictConfirmDialog( confirmTitle, confirmText ) ) {
			linkDeleted = parent.getChild(linkIndex);
			parent.removeChild(linkIndex);
			return true;
		}
		return false;
	}

	@Override
	public boolean redoTool() {
		parent.removeChild(linkIndex);
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		parent.addChild(linkIndex, linkDeleted);
		Controller.getInstance().updatePanel();
		return true;
	}

}
