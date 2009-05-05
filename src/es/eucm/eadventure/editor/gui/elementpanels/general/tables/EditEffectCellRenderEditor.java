package es.eucm.eadventure.editor.gui.elementpanels.general.tables;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.EffectsController;

public class EditEffectCellRenderEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

    /**
     * 
     */
	private static final long serialVersionUID = 1L;

	private EffectsController value;

	private JTable table;

	public EditEffectCellRenderEditor(JTable table) {
		this.table = table;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		if (value == null)
			return null;
		this.value = (EffectsController) value;
		return getComponent(isSelected, table);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		if (value == null)
			return null;
		this.value = (EffectsController) value;
		return getComponent(isSelected, table);
	}

	@Override
	public Object getCellEditorValue() {
		return value;
	}
	
	private Component getComponent(boolean isSelected, JTable table) {
		JPanel temp = new JPanel();
		if (isSelected)
			temp.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 0, table.getSelectionBackground()));
		JButton button = new JButton(TextConstants
				.getText("ActionList.EditEffect"));
		button.setFocusable(false);
		button.setEnabled(isSelected);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EditEffectCellRenderEditor.this.value
						.editEffect(EditEffectCellRenderEditor.this.table
								.getSelectedRow());
			}
		});
		temp.setLayout(new BorderLayout());
		temp.add(button, BorderLayout.CENTER);
		return temp;
	}
}
