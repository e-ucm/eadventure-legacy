package es.eucm.eadventure.editor.control.tools.structurepanel;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.general.ChapterDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.gui.structurepanel.StructureElement;

public class RemoveElementTool extends Tool {

	private StructureElement element;
	
	private ChapterDataControl chapterDataControl;
	
	private Chapter chapter;
	
	private JTable table;
	
	public RemoveElementTool(JTable table, StructureElement element) {
		this.element = element;
		this.table = table;
		chapterDataControl = Controller.getInstance().getSelectedChapterDataControl();
		try {
			chapter = (Chapter) (((Chapter) chapterDataControl.getContent()).clone());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean canRedo() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canUndo() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean combine(Tool other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doTool() {
		if (element.delete(true)) {
//			table.updateUI();
//			int index = table.getSelectedRow();
//			table.getSelectionModel().clearSelection();
//			((AbstractTableModel) table.getModel()).fireTableRowsDeleted(index, index);
//			((AbstractTableModel) table.getModel()).fireTableDataChanged();
			table.clearSelection();
			Controller.getInstance().updateTree();
			
			return true;
		}
		return false;
	}

	@Override
	public boolean redoTool() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean undoTool() {
		// TODO Auto-generated method stub
		return false;
	}

}
