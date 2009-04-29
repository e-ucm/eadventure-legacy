package es.eucm.eadventure.editor.gui.elementpanels.book;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.book.BookPagesListDataControl;

public class PagesTable extends JTable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BookPagesListDataControl dataControl;
	
	public PagesTable (BookPagesListDataControl dControl, BookPagesPanel parentPanel){
		super();
		this.setModel( new ParagraphsTableModel() );
		this.getColumnModel( ).setColumnSelectionAllowed( false );
		this.setDragEnabled( false );

		
		this.getColumnModel().getColumn(0).setCellEditor(new ContentTypeCellRendererEditor(dControl));
		this.getColumnModel().getColumn(0).setCellRenderer(new ContentTypeCellRendererEditor(dControl));
		this.getColumnModel().getColumn(0).setMaxWidth(100);
		this.getColumnModel().getColumn(0).setMinWidth(100);
		
		this.getColumnModel().getColumn(1).setCellEditor(new ResourceCellRendererEditor(dControl, parentPanel));
		this.getColumnModel().getColumn(1).setCellRenderer(new ResourceCellRendererEditor(dControl, parentPanel));

		this.getColumnModel().getColumn(2).setCellEditor(new MarginsCellRendererEditor(dControl, parentPanel));
		this.getColumnModel().getColumn(2).setCellRenderer(new MarginsCellRendererEditor(dControl, parentPanel));
		this.getColumnModel().getColumn(2).setMaxWidth(130);
		this.getColumnModel().getColumn(2).setMinWidth(130);

		this.getSelectionModel( ).setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		this.dataControl = dControl;
		
		this.setRowHeight(22);
		this.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				setRowHeight(22);
				if (getSelectedRow() != -1)
					setRowHeight(getSelectedRow(), 65);
			}
		});
	}
	
	
	private class ParagraphsTableModel extends AbstractTableModel{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public int getColumnCount( ) {
			return 3;
		}

		public int getRowCount( ) {
			return dataControl.getBookPages( ).size( );
		}

		public Object getValueAt( int rowIndex, int columnIndex ) {
			return dataControl.getBookPages( ).get( rowIndex );
		}
		
		@Override
		public String getColumnName(int columnIndex) {
			if (columnIndex==0)
				return TextConstants.getText( "BookPagesList.Type");
			if (columnIndex==1)
				return TextConstants.getText( "BookPagesList.Content" );
			if (columnIndex==2)
				return TextConstants.getText( "BookPagesList.Margins");
			else
				return "";
		}
		
		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return rowIndex == getSelectedRow();
		}
	}
}
