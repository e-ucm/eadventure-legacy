package es.eucm.eadventure.editor.control.tools.animation;

import es.eucm.eadventure.common.data.animation.Timed;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class ChangeTimeTool extends Tool {

	private Timed timed;
	
	private long newTime;
	
	private long oldTime;
	
	public ChangeTimeTool(Timed frame, long newTime) {
		this.timed = frame;
		this.newTime = newTime;
		this.oldTime = frame.getTime();
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
		if (other instanceof ChangeTimeTool) {
			ChangeTimeTool ctt = (ChangeTimeTool) other;
			if (ctt.timed == timed) {
				newTime = ctt.newTime;
				timeStamp = ctt.timeStamp;
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean doTool() {
		if (newTime == oldTime)
			return false;
		timed.setTime(newTime);
		return true;
	}

	@Override
	public boolean redoTool() {
		timed.setTime(newTime);
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		timed.setTime(oldTime);
		Controller.getInstance().updatePanel();
		return true;
	}

}
