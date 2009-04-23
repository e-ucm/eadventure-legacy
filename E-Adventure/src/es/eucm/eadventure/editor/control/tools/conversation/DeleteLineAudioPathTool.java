package es.eucm.eadventure.editor.control.tools.conversation;

import es.eucm.eadventure.common.data.chapter.conversation.line.ConversationLine;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class DeleteLineAudioPathTool extends Tool {

	private ConversationLine line;
	
	private String oldPath;
	
	public DeleteLineAudioPathTool( ConversationLine line ) {
		this.line = line;
		oldPath = line.getAudioPath();
	}

	@Override
	public boolean undoTool(){
		line.setAudioPath(oldPath);
		Controller.getInstance().updatePanel();
		return true;
	}
	
	@Override
	public boolean redoTool(){
		line.setAudioPath(null);
		Controller.getInstance().updatePanel();
		return true;
	}
	
	@Override
	public boolean doTool(){
		line.setAudioPath(null);
		return true;
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
		return true;
	}


}
