package es.eucm.eadventure.editor.control.tools.conversation;

import java.util.List;

import es.eucm.eadventure.common.data.chapter.conversation.line.ConversationLine;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNode;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNodeView;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.ConditionsController;
import es.eucm.eadventure.editor.control.tools.Tool;

public class DeleteNodeLineTool extends Tool {

	protected ConversationNode parent;
	
	protected int lineIndex;
	
	protected ConversationLine lineDeleted;
	
	protected List<ConditionsController> node;
	
	protected ConditionsController conditionDeleted;
	
	
	public DeleteNodeLineTool ( ConversationNodeView nodeView, int lineIndex,List<ConditionsController> node){
		this ( (ConversationNode) nodeView, lineIndex ,node);
	}
	
	public DeleteNodeLineTool ( ConversationNode parent, int lineIndex ,List<ConditionsController> node){
		this.parent = parent;
		this.lineIndex = lineIndex;
		this.node = node;
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
		conditionDeleted = node.remove(lineIndex);
		return true;
	}

	@Override
	public boolean redoTool() {
		parent.removeLine(lineIndex);
		node.remove(lineIndex);
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		parent.addLine(lineIndex, lineDeleted);
		node.add(lineIndex,conditionDeleted);
		Controller.getInstance().updatePanel();
		return true;
	}

}
