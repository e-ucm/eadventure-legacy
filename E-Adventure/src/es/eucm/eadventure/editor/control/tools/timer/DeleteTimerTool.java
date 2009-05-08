package es.eucm.eadventure.editor.control.tools.timer;

import es.eucm.eadventure.common.data.chapter.Timer;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.timer.TimerDataControl;
import es.eucm.eadventure.editor.control.controllers.timer.TimersListDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class DeleteTimerTool extends Tool {

	private TimersListDataControl dataControl;

	private TimerDataControl element;
	
	private int position;
	
	public DeleteTimerTool(TimersListDataControl dataControl2, int selectedRow) {
		this.dataControl = dataControl2;
		this.position = selectedRow;
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
		element = dataControl.getTimers().get(position);
		dataControl.deleteElement(dataControl.getTimers().get(position), true);
		return true;
	}

	@Override
	public boolean redoTool() {
		dataControl.deleteElement(element, true);
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		dataControl.getTimersList().add(position, (Timer) element.getContent());
		dataControl.getTimers().add(position, element);
		Controller.getInstance().updatePanel();
		return true;
	}
}
