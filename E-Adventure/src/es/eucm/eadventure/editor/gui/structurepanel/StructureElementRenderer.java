package es.eucm.eadventure.editor.gui.structurepanel;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class StructureElementRenderer extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {

	private static final long serialVersionUID = -2371497952304186775L;

	private StructureElementCell see;
	
	private StructureElement value;
		
	public StructureElementRenderer() {
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean isFocus, int row, int col) {
		this.value = (StructureElement) value;
		see = new StructureElementCell(this.value, table, isSelected);
		return see;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int col) {
		this.value = (StructureElement) value;
		see = new StructureElementCell(this.value, table, true);
		return see;
	}

	@Override
	public Object getCellEditorValue() {
		return value;
	}
}
