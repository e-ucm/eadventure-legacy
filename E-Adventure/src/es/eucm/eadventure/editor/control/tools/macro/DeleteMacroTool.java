package es.eucm.eadventure.editor.control.tools.macro;

import es.eucm.eadventure.common.data.chapter.effects.Macro;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.macro.MacroDataControl;
import es.eucm.eadventure.editor.control.controllers.macro.MacroListDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class DeleteMacroTool extends Tool {

	private MacroListDataControl dataControl;

	private MacroDataControl element;
	
	private int position;
	
	public DeleteMacroTool(MacroListDataControl dataControl2, int selectedRow) {
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
		element = dataControl.getMacros().get(position);
		dataControl.deleteElement(dataControl.getMacros().get(position), true);
		return true;
	}

	@Override
	public boolean redoTool() {
		dataControl.deleteElement(element, true);
		Controller.getInstance().getIdentifierSummary().deleteMacroId(element.getId());
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		dataControl.getMacrosList().add(position, (Macro) element.getContent());
		dataControl.getMacros().add(position, element);
		Controller.getInstance().getIdentifierSummary().addMacroId(element.getId());
		Controller.getInstance().updatePanel();
		return true;
	}
}
