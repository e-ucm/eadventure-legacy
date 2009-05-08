package es.eucm.eadventure.editor.control.tools.structurepanel;

import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.gui.structurepanel.StructureElement;
import es.eucm.eadventure.editor.gui.structurepanel.StructureElementCell;
import es.eucm.eadventure.editor.gui.structurepanel.StructureListElement;

public class DuplicateElementTool extends Tool {

	private StructureElement element;
		
	private JTable table;
	
	private StructureListElement parent;
	
	private StructureElement newElement;
	
	public DuplicateElementTool(StructureElement element, JTable table, StructureListElement parent) {
		this.element = element;
		this.parent = parent;
		this.table = table;
	}

	@Override
	public boolean canUndo() {
		return true;
	}

	@Override
	public boolean doTool() {
		if (parent.getDataControl().duplicateElement(element.getDataControl())) {
			((StructureElement) table.getModel().getValueAt(parent.getChildCount() - 1, 0)).setJustCreated(true);
			((AbstractTableModel) table.getModel()).fireTableDataChanged();
			SwingUtilities.invokeLater(new Runnable()
			{
			    public void run()
			    {
			        if (table.editCellAt(parent.getChildCount() - 1, 0))
			            ((StructureElementCell) table.getEditorComponent()).requestFocusInWindow();        
			    }
			});
			table.changeSelection(parent.getChildCount() - 1, 0, false, false);
			newElement = parent.getChild(parent.getChildCount() - 1);
			return true;
		}
		return false;
	}

	@Override
	public String getToolName() {
		return "Add child";
	}

	@Override
	public boolean undoTool() {
		newElement.delete(false);
		((AbstractTableModel) table.getModel()).fireTableDataChanged();
		table.clearSelection();
		return true;
	}
	
	public boolean canRedo() {
		return false;
	}
	
	@Override
	public boolean redoTool() {
		return false;
	}
	
	@Override
	public boolean combine(Tool other) {
		return false;
	}


}
