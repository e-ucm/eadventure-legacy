package es.eucm.eadventure.editor.control.tools.conversation;

import es.eucm.eadventure.common.data.chapter.conversation.line.ConversationLine;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNode;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNodeView;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class DeleteNodeLineTool extends Tool {

	protected ConversationNode parent;
	
	protected int lineIndex;
	
	protected ConversationLine lineDeleted;
	
	public DeleteNodeLineTool ( ConversationNodeView nodeView, int lineIndex ){
		this ( (ConversationNode) nodeView, lineIndex );
	}
	
	public DeleteNodeLineTool ( ConversationNode parent, int lineIndex ){
		this.parent = parent;
		this.lineIndex = lineIndex;
	}

	
	@Override
	public boolean canRedo() {
		return true;
	}

	@Override
	public boolean canUndo() {
		return lineDeleted!=null;
	}

	@Override
	public boolean combine(Tool other) {
		return false;
	}

	@Override
	public boolean doTool() {
		lineDeleted = parent.getLine(lineIndex);
		parent.removeLine(lineIndex);
		return true;
	}

	@Override
	public boolean redoTool() {
		parent.removeLine(lineIndex);
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		parent.addLine(lineIndex, lineDeleted);
		Controller.getInstance().updatePanel();
		return true;
	}

}
