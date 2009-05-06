package es.eucm.eadventure.editor.gui.elementpanels.book;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import es.eucm.eadventure.common.data.chapter.book.BookPage;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.book.BookPagesListDataControl;

public class ContentTypeCellRendererEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

	private static final long serialVersionUID = 8128260157985286632L;
	
	private BookPage value;
	
	private int row;
	
	private JComboBox typeCombo;
		
	private BookPagesListDataControl control;
	
	public ContentTypeCellRendererEditor(BookPagesListDataControl control) {
		this.control = control;
	}

	public Object getCellEditorValue() {
		return value;
	}
	
	public Component getTableCellEditorComponent(JTable table, Object value2, boolean isSelected, int row, int col) {
		this.value = (BookPage) value2;
		this.row = row;
		return createComponent(isSelected, table.getSelectionBackground(), table);
	}

	public Component getTableCellRendererComponent(JTable table, Object value2, boolean isSelected, boolean hasFocus, int row, int column) {
		this.value = (BookPage) value2;
		this.row = row;
		if (table.getSelectedRow() == row) {
			return createComponent(isSelected, table.getSelectionBackground(), table);
		}
		
		if( value.getType( ) == BookPage.TYPE_RESOURCE )
			return new JLabel(TextConstants.getText( "BookPageType.Resource" ));
		else if( value.getType( ) == BookPage.TYPE_URL )
			return new JLabel(TextConstants.getText( "BookPageType.URL" ));
		else if ( value.getType() == BookPage.TYPE_IMAGE )
			return new JLabel(TextConstants.getText( "BookPageType.Image" ));
		return new JLabel("");
	}

	private Component createComponent(boolean isSelected, Color color, JTable table) {
		JPanel temp = new JPanel();
		if (isSelected)
			temp.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 0, color));

		String[] types = new String[] {
			TextConstants.getText( "BookPageType.Resource" ),
			TextConstants.getText( "BookPageType.URL" ),
			TextConstants.getText( "BookPageType.Image" )
		};
		typeCombo = new JComboBox(types);
		if( value.getType( ) == BookPage.TYPE_RESOURCE )
			typeCombo.setSelectedIndex(0);
		else if( value.getType( ) == BookPage.TYPE_URL )
			typeCombo.setSelectedIndex(1);
		else if ( value.getType() == BookPage.TYPE_IMAGE )
			typeCombo.setSelectedIndex(2);
		
		typeCombo.addActionListener(new OptionChangedListener(table));

		temp.add(typeCombo);
		
		return temp;
	}	
	
	private class OptionChangedListener implements ActionListener {
		private JTable table;
		
		public OptionChangedListener(JTable table) {
			this.table = table;
		}

		public void actionPerformed( ActionEvent e ) {
			if( typeCombo.getSelectedIndex() == 0 ){
				control.setType(BookPage.TYPE_RESOURCE);
			}else if( typeCombo.getSelectedIndex() == 1 ){
				control.setType(BookPage.TYPE_URL);
			} else if ( typeCombo.getSelectedIndex() == 2 ) {
				control.setType(BookPage.TYPE_IMAGE);
			}
			((AbstractTableModel) table.getModel()).fireTableDataChanged();
			table.changeSelection(row, 0, false, false);
		}
	}

}
