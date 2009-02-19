package es.eucm.eadventure.editor.control.tools.general;

import es.eucm.eadventure.common.data.chapter.ExitLook;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class InvalidExitCursorTool extends Tool{

	protected String oldCursorPath;
	
	protected ExitLook exitLook;
	
	public InvalidExitCursorTool (ExitLook exitLook){
		this.exitLook = exitLook;
		this.oldCursorPath = exitLook.getCursorPath();
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
		if (exitLook.getCursorPath()!=null){
			exitLook.setCursorPath(null);
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean redoTool() {
		exitLook.setCursorPath(null);
		Controller.getInstance().reloadPanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		exitLook.setCursorPath(oldCursorPath);
		Controller.getInstance().reloadPanel();
		return true;
	}
	
}
