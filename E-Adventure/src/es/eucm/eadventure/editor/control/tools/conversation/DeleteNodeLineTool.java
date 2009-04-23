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
	
	protected int indexInAllConditions;
	protected List<ConditionsController> allConditions;
	protected ConditionsController conditionDeleted;
	
	
	public DeleteNodeLineTool ( ConversationNodeView nodeView, int lineIndex, int indexInAllConditions,List<ConditionsController> allConditions){
		this ( (ConversationNode) nodeView, lineIndex ,indexInAllConditions,allConditions);
	}
	
	public DeleteNodeLineTool ( ConversationNode parent, int lineIndex , int indexInAllConditions,List<ConditionsController> allConditions){
		this.parent = parent;
		this.lineIndex = lineIndex;
		this.indexInAllConditions = indexInAllConditions;
		this.allConditions = allConditions;
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
		conditionDeleted = allConditions.remove(this.indexInAllConditions);
		return true;
	}

	@Override
	public boolean redoTool() {
		parent.removeLine(lineIndex);
		allConditions.remove(this.indexInAllConditions);
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		parent.addLine(lineIndex, lineDeleted);
		allConditions.add(this.indexInAllConditions,conditionDeleted);
		Controller.getInstance().updatePanel();
		return true;
	}

}
