package es.eucm.eadventure.editor.control.tools.timer;

import es.eucm.eadventure.common.data.chapter.Timer;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.timer.TimerDataControl;
import es.eucm.eadventure.editor.control.controllers.timer.TimersListDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class AddTimerTool extends Tool {

	private TimersListDataControl dataControl;
	
	private TimerDataControl newTimer;

	public AddTimerTool(TimersListDataControl dataControl2) {
		this.dataControl = dataControl2;
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
		if (dataControl.addElement(dataControl.getAddableElements()[0], null)) {
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
