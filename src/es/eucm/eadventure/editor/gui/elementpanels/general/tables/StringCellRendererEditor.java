package es.eucm.eadventure.editor.gui.elementpanels.general.tables;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class StringCellRendererEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

	private static final long serialVersionUID = 8128260157985286632L;
	
	private String value;
	
	private JTextField textField;
	
	@Override
	public Object getCellEditorValue() {
		return value;
	}

	@Override
	public Component getTableCellEditorComponent(final JTable table, Object value2, boolean isSelected, final int row, final int col) {
		this.value = (String) value2;
		return createPanel(isSelected, table);
	}

	private Component createPanel(boolean isSelected, JTable table) {
		JPanel temp = new JPanel();
		if (isSelected)
			temp.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 0, table.getSelectionBackground()));
		
		textField = new JTextField(this.value);
		temp.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				SwingUtilities.invokeLater(new Runnable()
				{
				    public void run()
				    {
				        if (!textField.hasFocus()) {
				        	textField.selectAll();
				        	textField.requestFocusInWindow();
				        }
				    }
				});
			}
			
			public void focusLost(FocusEvent e) {
			}
		});
		textField.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					value = textField.getText();
					stopCellEditing();
				}
		});
		temp.setLayout(new BorderLayout());
		temp.add(textField, BorderLayout.CENTER);
		return temp;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value2, boolean isSelected, boolean hasFocus, int row, int column) {
		if (isSelected) {
			this.value = (String) value2;
			return createPanel(isSelected, table);
		} else
			return new JLabel((String) value);
	}

}
