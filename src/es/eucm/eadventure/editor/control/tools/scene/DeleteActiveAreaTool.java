package es.eucm.eadventure.editor.control.tools.scene;

import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.data.chapter.elements.ActiveArea;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.scene.ActiveAreaDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ActiveAreasListDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.gui.elementpanels.scene.ActiveAreasTable;
import es.eucm.eadventure.editor.gui.otherpanels.IrregularAreaEditionPanel;
import es.eucm.eadventure.editor.gui.otherpanels.imageelements.ImageElement;

public class DeleteActiveAreaTool extends Tool {

	private ActiveAreasListDataControl dataControl;
	
	private IrregularAreaEditionPanel iaep;
	
	private ActiveAreasTable table;

	private ActiveAreaDataControl element;
	
	private int position;
	
	public DeleteActiveAreaTool(ActiveAreasListDataControl dataControl2,
			IrregularAreaEditionPanel iaep, ActiveAreasTable table2) {
		this.dataControl = dataControl2;
		this.table = table2;
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
		position = table.getSelectedRow();
		element = dataControl.getActiveAreas().get(position);
		iaep.getScenePreviewEditionPanel().removeElement(element);
		iaep.getScenePreviewEditionPanel().setSelectedElement((ImageElement) null);
		dataControl.deleteElement(element, true);
		((AbstractTableModel) table.getModel()).fireTableDataChanged();
		return true;
	}

	@Override
	public boolean redoTool() {
		iaep.getScenePreviewEditionPanel().removeElement(element);
		dataControl.deleteElement(element, true);
		Controller.getInstance().getIdentifierSummary().deleteActiveAreaId(element.getId());
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		dataControl.getActiveAreas().add(position, element);
		dataControl.getActiveAreasList().add(position, (ActiveArea) element.getContent());
		iaep.getScenePreviewEditionPanel().removeElement(element);
		Controller.getInstance().getIdentifierSummary().addActiveAreaId(element.getId());
		Controller.getInstance().updatePanel();
		return true;
	}
}
