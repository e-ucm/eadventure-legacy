package es.eucm.eadventure.editor.control.tools.animation;

import es.eucm.eadventure.common.data.animation.Frame;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class ChangeWaitForClick extends Tool {

	private Frame frame;
	
	private boolean waitForClick;
	
	private boolean oldWaitForClick;
	
	public ChangeWaitForClick(Frame frame, boolean waitForClick) {
		this.frame = frame;
		this.waitForClick = waitForClick;
		this.oldWaitForClick = frame.isWaitforclick();
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
		if (waitForClick == oldWaitForClick)
			return false;
		frame.setWaitforclick(waitForClick);
		return true;
	}

	@Override
	public boolean redoTool() {
		frame.setWaitforclick(waitForClick);
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		frame.setWaitforclick(oldWaitForClick);
		Controller.getInstance().updatePanel();
		return true;
	}

}
