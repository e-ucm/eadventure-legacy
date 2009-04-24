package es.eucm.eadventure.editor.gui.elementpanels.general.tables;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.AbstractCellEditor;
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
		JPanel temp = new JPanel();
		
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
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		if (isSelected)
			return new JTextField((String) value);
		else
			return new JLabel((String) value);
	}

}
