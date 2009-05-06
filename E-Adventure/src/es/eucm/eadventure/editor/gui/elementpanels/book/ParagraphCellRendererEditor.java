package es.eucm.eadventure.editor.gui.elementpanels.book;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.book.BookParagraphDataControl;
import es.eucm.eadventure.editor.gui.otherpanels.imagepanels.BookImagePanel;

public class ParagraphCellRendererEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

	private static final long serialVersionUID = 8128260157985286632L;
	
	private BookParagraphDataControl value;
	
	private JTextPane textPane;
	
	private JTextField textField;
	
	private BookImagePanel previewPanel;
	
	public ParagraphCellRendererEditor(BookImagePanel previewPanel2) {
		this.previewPanel = previewPanel2;
	}

	public Object getCellEditorValue() {
		return value;
	}
	
	public Component getTableCellEditorComponent(JTable table, Object value2, boolean isSelected, int row, int col) {
		this.value = (BookParagraphDataControl) value2;
		return createComponent(isSelected, table.getSelectionBackground());
	}

	public Component getTableCellRendererComponent(JTable table, Object value2, boolean isSelected, boolean hasFocus, int row, int column) {
		this.value = (BookParagraphDataControl) value2;
		if (table.getSelectedRow() == row) {
			return createComponent(isSelected, table.getSelectionBackground());
		}

		textPane = new JTextPane();
		textPane.setEditable(false);
		
		String text = "";
		if (value.getType() == Controller.BOOK_TITLE_PARAGRAPH || value.getType() == Controller.BOOK_TEXT_PARAGRAPH || value.getType() == Controller.BOOK_BULLET_PARAGRAPH)
			text = value.getParagraphContent();
		if (value.getType() == Controller.BOOK_IMAGE_PARAGRAPH)
			return new JLabel(value.getParagraphContent());

	
		text.replace('\n', '\\');
		return new JLabel(text);
//		textField.setText(text);
//		return textField;
	}

	private Component createComponent(boolean isSelected, Color color) {
		JPanel temp = new JPanel();
		if (isSelected)
			temp.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 0, color));
		

		if (value.getType() == Controller.BOOK_TITLE_PARAGRAPH || value.getType() == Controller.BOOK_TEXT_PARAGRAPH || value.getType() == Controller.BOOK_BULLET_PARAGRAPH)
			createTextPanel(temp);
		if (value.getType() == Controller.BOOK_IMAGE_PARAGRAPH)
			createImagePanel(temp);
		
		return temp;
	}	
	
	private void createTextPanel(JPanel panel) {
		textPane = new JTextPane();
		textPane.setText(value.getParagraphContent());
		textPane.setAutoscrolls(true);
		
		textPane.getDocument().addDocumentListener(new TextAreaChangesListener( ));

		JScrollPane scrollPane = new JScrollPane(textPane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		scrollPane.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent arg0) {
				SwingUtilities.invokeLater(new Runnable()
				{
				    public void run()
				    {
				    	textPane.requestFocusInWindow();
				    }
				});
			}
			public void focusLost(FocusEvent arg0) {
				
			}
		});

		panel.setLayout(new BorderLayout());
		panel.add(scrollPane, BorderLayout.CENTER);
		textPane.requestFocusInWindow();
	}
	
	/**
	 * Listener for the text fields. It checks the values from the fields and updates the data.
	 */
	private class TextAreaChangesListener implements DocumentListener {

		public void changedUpdate( DocumentEvent arg0 ) {
		}

		public void insertUpdate( DocumentEvent arg0 ) {
			value.setParagraphTextContent( textPane.getText( ) );
			previewPanel.updatePreview();
		}

		public void removeUpdate( DocumentEvent arg0 ) {
			value.setParagraphTextContent( textPane.getText( ) );
			previewPanel.updatePreview();
		}
	}
	
	private void createImagePanel(JPanel panel) {
		Icon deleteContentIcon = new ImageIcon( "img/icons/deleteContent.png" );

		panel.setLayout( new GridBagLayout( ) );
		
		GridBagConstraints c2 = new GridBagConstraints( );
		c2.insets = new Insets( 4, 4, 4, 4 );
		c2.fill = GridBagConstraints.NONE;
		c2.weightx = 0;
		c2.weighty = 0;

		JButton deleteContentButton = new JButton( deleteContentIcon );
		deleteContentButton.addActionListener( new DeleteContentButtonListener( ) );
		deleteContentButton.setPreferredSize( new Dimension( 20, 20 ) );
		deleteContentButton.setMaximumSize(new Dimension(20,20));
		deleteContentButton.setToolTipText( TextConstants.getText( "Resources.DeleteAsset" ) );
		panel.add( deleteContentButton, c2 );

		c2.gridx = 1;
		c2.fill = GridBagConstraints.HORIZONTAL;
		c2.weightx = 1;
		textField = new JTextField( value.getParagraphContent( ) );
		textField.setEditable( false );
		panel.add( textField, c2 );

		// Create the "Select" button and insert it
		c2.gridx = 2;
		c2.fill = GridBagConstraints.NONE;
		c2.weightx = 0;
		JButton selectButton = new JButton( TextConstants.getText( "Resources.Select" ) );
		selectButton.addActionListener( new ExamineButtonListener( ) );
		panel.add( selectButton, c2 );	
	}

	/**
	 * Listener for the delete content button.
	 */
	private class DeleteContentButtonListener implements ActionListener {
		public void actionPerformed( ActionEvent e ) {
			textField.setText( null );
			value.deleteImageParagraphContent( );
			previewPanel.updatePreview();
		}
	}

	/**
	 * Listener for the examine button.
	 */
	private class ExamineButtonListener implements ActionListener {
		public void actionPerformed( ActionEvent e ) {
			value.editImagePath( );
			textField.setText( value.getParagraphContent( ) );
			previewPanel.updatePreview();
		}
	}

}
