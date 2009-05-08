package es.eucm.eadventure.editor.control.tools.scene;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.data.chapter.Exit;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.scene.ExitDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ExitsListDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.gui.otherpanels.IrregularAreaEditionPanel;

public class DeleteExitTool extends Tool {

	private ExitsListDataControl dataControl;
	
	private IrregularAreaEditionPanel iaep;
	
	private JTable table;

	private ExitDataControl element;
	
	private int position;
	
	public DeleteExitTool(ExitsListDataControl dataControl2,
			JTable table2, IrregularAreaEditionPanel iaep) {
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
		int position = table.getSelectedRow();
		element = dataControl.getExits().get(position);
		iaep.getScenePreviewEditionPanel().removeElement(element);
		dataControl.deleteElement(element, true);
		((AbstractTableModel) table.getModel()).fireTableDataChanged();
		return true;
	}

	@Override
	public boolean redoTool() {
		iaep.getScenePreviewEditionPanel().removeElement(element);
		dataControl.deleteElement(element, true);
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		dataControl.getExitsList().add(position, (Exit) element.getContent());
		dataControl.getExits().add(position, element);
		iaep.getScenePreviewEditionPanel().addExit(element);
		Controller.getInstance().updatePanel();
		return true;
	}
}
