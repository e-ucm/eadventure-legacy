package es.eucm.eadventure.editor.control.tools.conversation;

import es.eucm.eadventure.common.data.chapter.conversation.line.ConversationLine;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNode;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNodeView;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class AddNodeLineTool extends Tool {

	protected ConversationNode parent;
	
	protected int lineIndex;
	
	protected ConversationLine lineAdded;
	
	protected String name;
	
	public AddNodeLineTool ( ConversationNodeView nodeView, int lineIndex, String name){
		this ( (ConversationNode) nodeView, lineIndex, name );
	}
	
	public AddNodeLineTool ( ConversationNode parent, int lineIndex, String name){
		this.parent = parent;
		this.lineIndex = lineIndex;
		this.name = name;
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
		return true;
	}

	@Override
	public boolean redoTool() {
		parent.addLine(lineIndex, lineAdded);
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		parent.removeLine(lineIndex);
		Controller.getInstance().updatePanel();
		return true;
	}

}
