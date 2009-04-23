package es.eucm.eadventure.editor.control.tools.conversation;

import java.util.List;

import es.eucm.eadventure.common.data.chapter.conversation.line.ConversationLine;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNode;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNodeView;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.ConditionsController;
import es.eucm.eadventure.editor.control.tools.Tool;

public class AddNodeLineTool extends Tool {

	protected ConversationNode parent;
	
	protected int lineIndex;
	
	protected ConversationLine lineAdded;
	
	protected String name;
	
	protected int indexInAllConditions;
	protected List<ConditionsController> allConditions;
	
	public AddNodeLineTool ( ConversationNodeView nodeView, int lineIndex, String name, int indexInAllConditions,List<ConditionsController> allConditions){
		this ( (ConversationNode) nodeView, lineIndex, name, indexInAllConditions,allConditions);
	}
	
	public AddNodeLineTool ( ConversationNode parent, int lineIndex, String name ,int indexInAllConditions,List<ConditionsController> allConditions){
		this.parent = parent;
		this.lineIndex = lineIndex;
		this.name = name;
		this.indexInAllConditions = indexInAllConditions;
		this.allConditions = allConditions;
	}

	
	@Override
	public boolean canRedo() {
		return true;
	}

	@Override
	public boolean canUndo() {
		return lineAdded!=null;
	}

	@Override
	public boolean combine(Tool other) {
		return false;
	}

	@Override
	public boolean doTool() {
		lineAdded = new ConversationLine( name, TextConstants.getText( "ConversationLine.DefaultText" ) );
		parent.addLine(lineIndex, lineAdded);
		allConditions.add(this.indexInAllConditions,new ConditionsController(lineAdded.getConditions()));
		return true;
	}

	@Override
	public boolean redoTool() {
		parent.addLine(lineIndex, lineAdded);
		allConditions.add(this.indexInAllConditions,new ConditionsController(lineAdded.getConditions()));
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		parent.removeLine(lineIndex);
		allConditions.remove(this.indexInAllConditions);
		Controller.getInstance().updatePanel();
		return true;
	}

}
