package es.eucm.eadventure.editor.gui.elementpanels.conversation;

import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class TextLineCellRendererEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

	private static final long serialVersionUID = 8128260157985286632L;
	
	private String value;
	
	private JTextPane textField;
	
	private LinesPanel linesPanel;
	
	public TextLineCellRendererEditor(LinesPanel linesPanel) {
		this.linesPanel = linesPanel;
	}

	@Override
	public Object getCellEditorValue() {
		return value;
	}
	
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value2, boolean isSelected, final int row, final int col) {
		this.value = (String) value2;
		textField = new JTextPane();
		textField.setText(this.value);
		textField.setAutoscrolls(true);
		textField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent arg0) {
				value = textField.getText();
			}
			public void insertUpdate(DocumentEvent arg0) {
				value = textField.getText();
			}
			public void removeUpdate(DocumentEvent arg0) {
				value = textField.getText();
			}
		});
		textField.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					value = textField.getText();
					stopCellEditing();
					linesPanel.editNextLine();
				}
			}
			public void keyReleased(KeyEvent arg0) {
			}
			public void keyTyped(KeyEvent arg0) {
			}
		});
		JScrollPane scrollPane = new JScrollPane(textField, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		scrollPane.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent arg0) {
				SwingUtilities.invokeLater(new Runnable()
				{
				    public void run()
				    {
				    	textField.requestFocusInWindow();
				    }
				});
			}
			public void focusLost(FocusEvent arg0) {
				
			}
		});

		return scrollPane;
		
	}
	

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		if (isSelected) {
			JTextPane textPane = new JTextPane();
			textPane.setText((String) value);
			textPane.setAutoscrolls(true);
			return textPane;
		} else
			return new JLabel((String) value);
	}

}
