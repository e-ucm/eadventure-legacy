package es.eucm.eadventure.editor.control.tools.globalstate;

import es.eucm.eadventure.common.data.chapter.conditions.GlobalState;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.globalstate.GlobalStateDataControl;
import es.eucm.eadventure.editor.control.controllers.globalstate.GlobalStateListDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class DeleteGlobalStateTool extends Tool {

	private GlobalStateListDataControl dataControl;

	private GlobalStateDataControl element;
	
	private int position;
	
	public DeleteGlobalStateTool(GlobalStateListDataControl dataControl2, int selectedRow) {
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
		element = dataControl.getGlobalStates().get(position);
		dataControl.deleteElement(dataControl.getGlobalStates().get(position), true);
		return true;
	}

	@Override
	public boolean redoTool() {
		dataControl.deleteElement(element, true);
		Controller.getInstance().getIdentifierSummary().deleteGlobalStateId(element.getId());
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		dataControl.getGlobalStatesList().add(position, (GlobalState) element.getContent());
		dataControl.getGlobalStates().add(position, element);
		Controller.getInstance().getIdentifierSummary().addGlobalStateId(element.getId());
		Controller.getInstance().updatePanel();
		return true;
	}
}
