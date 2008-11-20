package es.eucm.eadventure.editor.gui.elementpanels.book;

import java.awt.Component;
import java.awt.dnd.DropTarget;
import java.util.List;

import javax.swing.DropMode;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.book.BookPagesListDataControl;
import es.eucm.eadventure.editor.control.controllers.book.BookParagraphDataControl;
import es.eucm.eadventure.editor.control.controllers.book.BookParagraphsListDataControl;

public class PagesTable extends JTable{

	private BookPagesListDataControl dataControl;
	
	public PagesTable (BookPagesListDataControl dControl){
		super();
		this.setModel( new ParagraphsTableModel() );
		this.getColumnModel( ).setColumnSelectionAllowed( false );
		this.setDragEnabled( false );
		this.getColumnModel().getColumn(0).setMaxWidth( 6 );
		this.getSelectionModel( ).setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		this.dataControl = dControl;
	}
	
	
	private class ParagraphsTableModel extends AbstractTableModel{

		public int getColumnCount( ) {
			return 2;
		}

		public int getRowCount( ) {
			return dataControl.getBookPages( ).size( );
		}

		public Object getValueAt( int rowIndex, int columnIndex ) {
			if (columnIndex ==1 ){
				return dataControl.getBookPages( ).get( rowIndex ).getUri( );
			} else {
				return Integer.toString( rowIndex+1 );
			}
			
		}
		
		@Override
		public String getColumnName(int columnIndex) {
			if (columnIndex==1)
				return TextConstants.getText( "BookPagesList.Title" );
			else
				return "";
		}
	}
}
