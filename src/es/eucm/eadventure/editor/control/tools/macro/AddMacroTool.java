package es.eucm.eadventure.editor.control.tools.macro;

import es.eucm.eadventure.common.data.chapter.effects.Macro;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.macro.MacroDataControl;
import es.eucm.eadventure.editor.control.controllers.macro.MacroListDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class AddMacroTool extends Tool {

	private MacroListDataControl dataControl;
	
	private MacroDataControl newMacro;

	public AddMacroTool(MacroListDataControl dataControl2) {
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
			newMacro = dataControl.getLastMacro();
			return true;
		}
		return false;
	}

	@Override
	public boolean redoTool() {
		dataControl.getMacros().add(newMacro);
		dataControl.getMacrosList().add((Macro) newMacro.getContent());
		Controller.getInstance().getIdentifierSummary( ).addMacroId( newMacro.getId() );
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		dataControl.deleteElement(newMacro, false);
		Controller.getInstance().getIdentifierSummary( ).deleteMacroId( newMacro.getId() );
		Controller.getInstance().updatePanel();
		return true;
	}
}
