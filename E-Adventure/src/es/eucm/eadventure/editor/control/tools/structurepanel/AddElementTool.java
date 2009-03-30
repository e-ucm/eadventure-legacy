package es.eucm.eadventure.editor.control.tools.structurepanel;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.gui.structurepanel.StructureListElement;

public class AddElementTool extends Tool {

	private int type;
	
	private JPanel panel;
	
	private StructureListElement element;
		
	private JTable table;
	
	public AddElementTool(JPanel panel, StructureListElement element, JTable table) {
		this.panel = panel;
		this.type = element.getDataControl().getAddableElements()[0];
		this.element = element;
		this.table = table;
	}

	@Override
	public boolean canUndo() {
		return false;
	}

	@Override
	public boolean doTool() {
		if( element.getDataControl( ).canAddElement( type ) && element.getDataControl( ).addElement( type ) ) {
			((AbstractTableModel) table.getModel()).fireTableDataChanged();
			table.changeSelection(element.getChildCount() - 1, element.getChildCount() - 1, false, false);
			table.editCellAt(element.getChildCount() - 1, 0);
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
		return false;
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
