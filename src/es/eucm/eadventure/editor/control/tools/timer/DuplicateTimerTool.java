package es.eucm.eadventure.editor.control.tools.timer;

import es.eucm.eadventure.common.data.chapter.Timer;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.timer.TimerDataControl;
import es.eucm.eadventure.editor.control.controllers.timer.TimersListDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class DuplicateTimerTool extends Tool {

	private TimersListDataControl dataControl;
	
	private TimerDataControl newTimer;

	private int selected;
	
	public DuplicateTimerTool(TimersListDataControl dataControl2, int selected) {
		this.dataControl = dataControl2;
		this.selected = selected;
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
		if (dataControl.duplicateElement(dataControl.getTimers().get(selected))) {
			newTimer = dataControl.getLastTimer();
			return true;
		}
		return false;
	}

	@Override
	public boolean redoTool() {
		dataControl.getTimers().add(newTimer);
		dataControl.getTimersList().add((Timer) newTimer.getContent());
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		dataControl.deleteElement(newTimer, false);
		Controller.getInstance().updatePanel();
		return true;
	}
}
