package es.eucm.eadventure.editor.control.tools.general.actions;

import es.eucm.eadventure.common.data.chapter.Action;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.general.ActionDataControl;
import es.eucm.eadventure.editor.control.controllers.general.ActionsListDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class DeleteActionTool extends Tool {

	private ActionsListDataControl dataControl;
	
	private ActionDataControl element;
	
	private int position;
	
	public DeleteActionTool(ActionsListDataControl dataControl2, int selectedRow) {
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
		element = dataControl.getActions().get(position);
		dataControl.deleteElement(element, false);
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
		dataControl.getActions().add(position, element);
		dataControl.getActionsList().add(position, (Action) element.getContent());
		Controller.getInstance().updatePanel();
		return true;
	}
}
