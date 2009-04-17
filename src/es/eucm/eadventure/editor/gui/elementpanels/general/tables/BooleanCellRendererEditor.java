package es.eucm.eadventure.editor.gui.elementpanels.general.tables;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class BooleanCellRendererEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

	private static final long serialVersionUID = 8128260157985286632L;
	
	private Boolean value;
	
	@Override
	public Object getCellEditorValue() {
		return value;
	}
	
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int col) {
		this.value = (Boolean) value;
		JCheckBox checkBox = new JCheckBox();
		checkBox.setFocusable(false);
		checkBox.setSelected(this.value.booleanValue());
		checkBox.setEnabled(isSelected);
		checkBox.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				boolean selected = ((JCheckBox)arg0.getSource()).isSelected();
				if (selected != BooleanCellRendererEditor.this.value.booleanValue()) {
					 BooleanCellRendererEditor.this.value = new Boolean(selected);
				}
			}
		});
		return checkBox;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		if (value instanceof Boolean) { 
			JCheckBox checkBox = new JCheckBox();
			checkBox.setFocusable(false);
			checkBox.setEnabled(isSelected);
			checkBox.setSelected(((Boolean) value).booleanValue());
			return checkBox;
	    }
		return null;
	}

}
