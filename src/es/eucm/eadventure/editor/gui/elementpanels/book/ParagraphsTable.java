package es.eucm.eadventure.editor.gui.elementpanels.book;

import java.awt.Component;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.book.BookParagraphDataControl;
import es.eucm.eadventure.editor.control.controllers.book.BookParagraphsListDataControl;
import es.eucm.eadventure.editor.gui.otherpanels.imagepanels.BookImagePanel;

public class ParagraphsTable extends JTable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BookParagraphsListDataControl dataControl;
	
	public ParagraphsTable (BookParagraphsListDataControl dControl, BookImagePanel previewPanel2){
		super();
		this.setModel( new ParagraphsTableModel() );
		this.getColumnModel( ).setColumnSelectionAllowed( false );
		this.setDragEnabled( false );
		this.getColumnModel().getColumn(0).setMaxWidth(130);
		this.getColumnModel().getColumn(0).setMinWidth(130);
		this.getColumnModel().getColumn(0).setCellRenderer(new ParagraphsTableCellRenderer());

		this.getColumnModel().getColumn(1).setCellEditor(new ParagraphCellRendererEditor(previewPanel2));
		this.getColumnModel().getColumn(1).setCellRenderer(new ParagraphCellRendererEditor(previewPanel2));
		
		this.getSelectionModel( ).setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		this.dataControl = dControl;
		
		setRowHeight(22);
		getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				setRowHeight(22);
				if (getSelectedRow() != -1)
					setRowHeight(getSelectedRow(), 50);
			}
		});
	}
	
	private class ParagraphsTableModel extends AbstractTableModel{

		private static final long serialVersionUID = 1L;

		public int getColumnCount( ) {
			return 2;
		}

		public int getRowCount( ) {
			return dataControl.getBookParagraphs( ).size( );
		}

		public Object getValueAt( int rowIndex, int columnIndex ) {
			if (columnIndex == 0) {
				List<BookParagraphDataControl> paragraphs = dataControl.getBookParagraphs( );
				return paragraphs.get( rowIndex );
			}
			if (columnIndex == 1) {
				return dataControl.getBookParagraphs().get(rowIndex);
			}
			return null;
		}
		
		@Override
		public String getColumnName(int columnIndex) {
			if (columnIndex==0)
				return TextConstants.getText( "BookParagraphsList.ParagraphType" );
			if (columnIndex==1)
				return TextConstants.getText( "BookParagraphsList.Content");
			else
				return "";
		}
		
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return rowIndex == getSelectedRow() && columnIndex > 0;
		}
	}
	
	private class ParagraphsTableCellRenderer extends DefaultTableCellRenderer {

		private static final long serialVersionUID = 1L;

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			
			if (value instanceof BookParagraphDataControl){
				String elementName = TextConstants.getElementName( ((BookParagraphDataControl)value).getType( ) );
				if (((BookParagraphDataControl)value).getType( ) == Controller.BOOK_TITLE_PARAGRAPH){
					return new IconTextPanel("img/icons/titleBookParagraph.png", elementName, isSelected);				
				} else if (((BookParagraphDataControl)value).getType( ) == Controller.BOOK_BULLET_PARAGRAPH){
					return new IconTextPanel("img/icons/bulletBookParagraph.png", elementName,isSelected);
				} else if (((BookParagraphDataControl)value).getType( ) == Controller.BOOK_TEXT_PARAGRAPH){
					return new IconTextPanel("img/icons/textBookParagraph.png", elementName,isSelected);
				} else if (((BookParagraphDataControl)value).getType( ) == Controller.BOOK_IMAGE_PARAGRAPH){
					return new IconTextPanel("img/icons/imageBookParagraph.png", elementName,isSelected);
				} else
					return null;
			} else {
				return new JLabel(value.toString( ));
			}
		}

	}

}