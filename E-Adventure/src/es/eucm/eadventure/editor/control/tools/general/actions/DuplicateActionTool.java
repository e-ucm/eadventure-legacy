package es.eucm.eadventure.editor.control.tools.general.actions;

import es.eucm.eadventure.common.data.chapter.Action;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.general.ActionDataControl;
import es.eucm.eadventure.editor.control.controllers.general.ActionsListDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class DuplicateActionTool extends Tool {

	private ActionsListDataControl dataControl;
	
	private int position;
	
	private ActionDataControl newAction;

	public DuplicateActionTool(ActionsListDataControl dataControl2,
			int type) {
		this.dataControl = dataControl2;
		this.position = type;
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
		if (dataControl.duplicateElement(dataControl.getActions().get(position))) {
			newAction = dataControl.getLastAction();
			return true;
		}
		return false;
	}

	@Override
	public boolean redoTool() {
		dataControl.getActions().add(newAction);
		dataControl.getActionsList().add((Action) newAction.getContent());
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		dataControl.deleteElement(newAction, false);
		Controller.getInstance().updatePanel();
		return true;
	}
}
