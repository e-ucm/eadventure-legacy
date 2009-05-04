package es.eucm.eadventure.editor.control.tools.conversation;

import java.util.List;
import java.util.Map;

import es.eucm.eadventure.common.data.chapter.conditions.Conditions;
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
	
	protected List<ConditionsController> node;
	
	
	public AddNodeLineTool ( ConversationNodeView nodeView, int lineIndex, String name,List<ConditionsController> node){
		this ( (ConversationNode) nodeView, lineIndex, name,node);
	}
	
	public AddNodeLineTool ( ConversationNode parent, int lineIndex, String name , List<ConditionsController> node){
		this.parent = parent;
		this.lineIndex = lineIndex;
		this.name = name;
		this.node = node;
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
		node.add(lineIndex,new ConditionsController(lineAdded.getConditions(), 
    			Controller.CONVERSATION_OPTION_LINE, Integer.toString(lineIndex)));
		return true;
	}

	@Override
	public boolean redoTool() {
		parent.addLine(lineIndex, lineAdded);
		node.add(lineIndex,new ConditionsController(lineAdded.getConditions(), 
    			Controller.CONVERSATION_OPTION_LINE, Integer.toString(lineIndex)));
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		parent.removeLine(lineIndex);
		node.remove(lineIndex);
		Controller.getInstance().updatePanel();
		return true;
	}

}
