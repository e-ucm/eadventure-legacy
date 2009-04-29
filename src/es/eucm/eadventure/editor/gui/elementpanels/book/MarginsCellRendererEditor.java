package es.eucm.eadventure.editor.gui.elementpanels.book;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import es.eucm.eadventure.common.data.chapter.book.BookPage;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.book.BookPagesListDataControl;
import es.eucm.eadventure.editor.gui.editdialogs.ChangePageMarginsDialog;

public class MarginsCellRendererEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

	private static final long serialVersionUID = 8128260157985286632L;
	
	private BookPage value;
		
	private BookPagesListDataControl control;
	
	private BookPagesPanel parentPanel;
	
	public MarginsCellRendererEditor(BookPagesListDataControl control, BookPagesPanel parentPanel) {
		this.control = control;
		this.parentPanel = parentPanel;
	}

	@Override
	public Object getCellEditorValue() {
		return value;
	}
	
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value2, boolean isSelected, int row, int col) {
		this.value = (BookPage) value2;
		return createComponent(isSelected, table.getSelectionBackground());
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value2, boolean isSelected, boolean hasFocus, int row, int column) {
		this.value = (BookPage) value2;
		if (table.getSelectedRow() == row) {
			return createComponent(isSelected, table.getSelectionBackground());
		}
		
		JButton button = new JButton(TextConstants.getText("BookPage.Margin"));
		button.setEnabled(false);
		return button;
	}

	private Component createComponent(boolean isSelected, Color color) {
		JPanel temp = new JPanel();
		if (isSelected)
			temp.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 0, color));
		
		JButton changeMargins = new JButton(TextConstants.getText("BookPage.Margin"));
		changeMargins.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String backgroundPath =  parentPanel.getDataControl().getPreviewImage();
		        Image background;
		        if (backgroundPath!=null && backgroundPath.length( )>0)
		        	background = AssetsController.getImage( backgroundPath );
		        else
		        	background = null;
				new ChangePageMarginsDialog(control, background);
				parentPanel.updatePreview( );
			}
		});
		changeMargins.setEnabled(value!=null && (value.getType() == BookPage.TYPE_IMAGE || value.getType() == BookPage.TYPE_RESOURCE) );
		temp.add( changeMargins );

		return temp;
	}	
}
