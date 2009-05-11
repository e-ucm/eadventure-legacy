package es.eucm.eadventure.editor.gui.structurepanel;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 * Renderer for the StructureElement in the table
 * 
 * @author Eugenio Marchiori
 */
public class StructureElementRenderer extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {

	private static final long serialVersionUID = -2371497952304186775L;

	private StructureElementCell see;
	
	private StructureElement value;
	
	private StructureListElement parent;
		
	public StructureElementRenderer(StructureListElement parent) {
		this.parent = parent;
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean isFocus, int row, int col) {
		this.value = (StructureElement) value;
		see = new StructureElementCell(this.value, table, isSelected, parent);
		return see;
	}

	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int col) {
		this.value = (StructureElement) value;
		see = new StructureElementCell(this.value, table, true, parent);
		return see;
	}

	public Object getCellEditorValue() {
		return value;
	}
}
