package es.eucm.eadventure.editor.control.tools.scene;

import es.eucm.eadventure.common.data.chapter.elements.ActiveArea;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.scene.ActiveAreaDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ActiveAreasListDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.gui.otherpanels.IrregularAreaEditionPanel;

public class AddActiveAreaTool extends Tool {

	private ActiveAreasListDataControl dataControl;
	
	private IrregularAreaEditionPanel iaep;
	
	private String id;
	
	private ActiveAreaDataControl newActiveArea;

	public AddActiveAreaTool(ActiveAreasListDataControl dataControl2,
			String id, IrregularAreaEditionPanel iaep) {
		this.dataControl = dataControl2;
		this.id = id;
		this.iaep = iaep;
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
		if (dataControl.addElement(dataControl.getAddableElements()[0], id)) {
			this.newActiveArea = dataControl.getLastActiveArea();
			iaep.getScenePreviewEditionPanel().addActiveArea(dataControl.getLastActiveArea());
			iaep.repaint();
			return true;
		}
		return false;
	}

	@Override
	public boolean redoTool() {
		dataControl.getActiveAreas().add(newActiveArea);
		dataControl.getActiveAreasList().add((ActiveArea) newActiveArea.getContent());
		iaep.getScenePreviewEditionPanel().addActiveArea(dataControl.getLastActiveArea());
		Controller.getInstance().getIdentifierSummary( ).addActiveAreaId( newActiveArea.getId() );
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		dataControl.deleteElement(newActiveArea, false);
		iaep.getScenePreviewEditionPanel().removeElement(newActiveArea);
		Controller.getInstance().updatePanel();
		return true;
	}
}
