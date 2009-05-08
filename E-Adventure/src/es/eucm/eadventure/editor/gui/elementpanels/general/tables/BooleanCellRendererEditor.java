package es.eucm.eadventure.editor.gui.elementpanels.general.tables;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class BooleanCellRendererEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

	private static final long serialVersionUID = 8128260157985286632L;
	
	private Boolean value;
	
	public Object getCellEditorValue() {
		return value;
	}
	
	public Component getTableCellEditorComponent(final JTable table, Object value, boolean isSelected, final int row, final int col) {
		this.value = (Boolean) value;
		return createPanel(table, isSelected);
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		this.value = (Boolean) value;
		return createPanel(table, isSelected);
	}

	private JPanel createPanel(JTable table, boolean isSelected) {
		JCheckBox checkBox = new JCheckBox();
		checkBox.setFocusable(false);
		checkBox.setSelected(value.booleanValue());
		checkBox.setEnabled(isSelected);
		checkBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean selected = ((JCheckBox)arg0.getSource()).isSelected();
				if (selected != value.booleanValue()) {
					 value = new Boolean(selected);
					 stopCellEditing();
				}
			}
		});
		
		JPanel panel = new JPanel();
		panel.add(checkBox);
		if (isSelected) {
			panel.setBackground(table.getSelectionBackground());
			checkBox.setBackground(table.getSelectionBackground());
		}
		return panel;
	}
}
