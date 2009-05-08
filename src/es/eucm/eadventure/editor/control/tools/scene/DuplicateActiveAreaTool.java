package es.eucm.eadventure.editor.control.tools.scene;

import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.data.chapter.elements.ActiveArea;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.scene.ActiveAreaDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ActiveAreasListDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.gui.elementpanels.scene.ActiveAreasTable;
import es.eucm.eadventure.editor.gui.otherpanels.IrregularAreaEditionPanel;

public class DuplicateActiveAreaTool extends Tool {

	private ActiveAreasListDataControl dataControl;
	
	private IrregularAreaEditionPanel iaep;
	
	private ActiveAreasTable table;

	private ActiveAreaDataControl newActiveArea;

	public DuplicateActiveAreaTool(ActiveAreasListDataControl dataControl2,
			IrregularAreaEditionPanel iaep2, ActiveAreasTable table2) {
		this.dataControl = dataControl2;
		this.table = table2;
		this.iaep = iaep2;
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
		if (dataControl.duplicateElement(dataControl.getActiveAreas().get(table.getSelectedRow()))) {
			newActiveArea = dataControl.getLastActiveArea();
			iaep.getScenePreviewEditionPanel().addActiveArea(dataControl.getLastActiveArea());
			iaep.repaint();
			((AbstractTableModel) table.getModel()).fireTableDataChanged();
			table.changeSelection(dataControl.getActiveAreas().size() - 1, dataControl.getActiveAreas().size() - 1, false, false);
			table.editCellAt(dataControl.getActiveAreas().size() - 1, 0);
			if (table.isEditing()) {
				table.getEditorComponent().requestFocusInWindow();
			}
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
