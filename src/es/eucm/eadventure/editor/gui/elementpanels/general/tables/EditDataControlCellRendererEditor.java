package es.eucm.eadventure.editor.gui.elementpanels.general.tables;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.gui.structurepanel.StructureControl;

public class EditDataControlCellRendererEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

	private static final long serialVersionUID = 8128260157985286632L;
	
	protected DataControl value;
	
	public Object getCellEditorValue() {
		return value;
	}
	
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int col) {
		this.value = (DataControl) value;
		JButton button = new JButton(TextConstants.getText( "GeneralText.Edit" ));
		button.setFocusable(false);
		button.setEnabled(isSelected);
		button.addActionListener(new EditButtonActionListener(this.value));
		return button;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JButton button = new JButton(TextConstants.getText( "GeneralText.Edit" ));
		button.setFocusable(false);
		button.setEnabled(isSelected);
		return button;
	}
	
	private class EditButtonActionListener implements ActionListener {

		private DataControl dataControl;
		
		public EditButtonActionListener(DataControl dataControl) {
			this.dataControl = dataControl;
		}
		
		public void actionPerformed(ActionEvent e) {
			StructureControl.getInstance().changeDataControl(dataControl);
		}
	}

}
