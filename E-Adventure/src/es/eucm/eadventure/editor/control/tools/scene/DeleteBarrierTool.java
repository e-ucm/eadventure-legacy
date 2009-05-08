package es.eucm.eadventure.editor.control.tools.scene;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.data.chapter.elements.Barrier;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.scene.BarrierDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.BarriersListDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.gui.otherpanels.ScenePreviewEditionPanel;

public class DeleteBarrierTool extends Tool {

	private BarriersListDataControl dataControl;
	
	private ScenePreviewEditionPanel spep;
	
	private JTable table;

	private BarrierDataControl element;
	
	private int position;
	
	public DeleteBarrierTool(BarriersListDataControl dataControl,
			JTable table2, ScenePreviewEditionPanel spep2) {
		this.dataControl = dataControl;
		this.table = table2;
		this.spep = spep2;
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
		element = dataControl.getBarriers().get(position);
		spep.removeElement(element);
		dataControl.deleteElement(element, true);
		((AbstractTableModel) table.getModel()).fireTableDataChanged();
		return true;
	}

	@Override
	public boolean redoTool() {
		spep.removeElement(element);
		dataControl.deleteElement(element, true);
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		dataControl.getBarriersList().add(position, (Barrier) element.getContent());
		dataControl.getBarriers().add(position, element);
		spep.addBarrier(element);
		Controller.getInstance().updatePanel();
		return true;
	}
}
