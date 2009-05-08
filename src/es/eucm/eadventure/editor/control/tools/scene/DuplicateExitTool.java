package es.eucm.eadventure.editor.control.tools.scene;

import es.eucm.eadventure.common.data.chapter.Exit;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.scene.ExitDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ExitsListDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.gui.otherpanels.IrregularAreaEditionPanel;

public class DuplicateExitTool extends Tool {

	private ExitsListDataControl dataControl;
	
	private IrregularAreaEditionPanel iaep;
	
	private ExitDataControl newExit;

	private int selected;
	
	public DuplicateExitTool(ExitsListDataControl dataControl2,
			IrregularAreaEditionPanel iaep, int selected) {
		this.dataControl = dataControl2;
		this.iaep = iaep;
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
		if (dataControl.duplicateElement(dataControl.getExits().get(selected))) {
			newExit = dataControl.getLastExit();
			iaep.getScenePreviewEditionPanel().addExit(dataControl.getLastExit());
			iaep.repaint();
			return true;
		}
		return false;
	}

	@Override
	public boolean redoTool() {
		dataControl.getExits().add(newExit);
		dataControl.getExitsList().add((Exit) newExit.getContent());
		iaep.getScenePreviewEditionPanel().addExit(newExit);
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		dataControl.deleteElement(newExit, false);
		iaep.getScenePreviewEditionPanel().removeElement(newExit);
		Controller.getInstance().updatePanel();
		return true;
	}
}
