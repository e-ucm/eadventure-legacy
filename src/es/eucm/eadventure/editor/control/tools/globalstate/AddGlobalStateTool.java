package es.eucm.eadventure.editor.control.tools.globalstate;

import es.eucm.eadventure.common.data.chapter.conditions.GlobalState;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.globalstate.GlobalStateDataControl;
import es.eucm.eadventure.editor.control.controllers.globalstate.GlobalStateListDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class AddGlobalStateTool extends Tool {

	private GlobalStateListDataControl dataControl;
	
	private GlobalStateDataControl newGlobalState;

	public AddGlobalStateTool(GlobalStateListDataControl dataControl2) {
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
			newGlobalState = dataControl.getLastGlobalState();
			return true;
		}
		return false;
	}

	@Override
	public boolean redoTool() {
		dataControl.getGlobalStates().add(newGlobalState);
		dataControl.getGlobalStatesList().add((GlobalState) newGlobalState.getContent());
		Controller.getInstance().getIdentifierSummary( ).addGlobalStateId( newGlobalState.getId() );
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		dataControl.deleteElement(newGlobalState, false);
		Controller.getInstance().getIdentifierSummary( ).deleteGlobalStateId( newGlobalState.getId() );
		Controller.getInstance().updatePanel();
		return true;
	}
}
