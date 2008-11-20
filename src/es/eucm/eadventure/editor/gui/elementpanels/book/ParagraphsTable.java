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
import es.eucm.eadventure.editor.control.controllers.book.BookParagraphDataControl;
import es.eucm.eadventure.editor.control.controllers.book.BookParagraphsListDataControl;

public class ParagraphsTable extends JTable{

	private BookParagraphsListDataControl dataControl;
	
	public ParagraphsTable (BookParagraphsListDataControl dControl){
		super();
		this.setModel( new ParagraphsTableModel() );
		this.getColumnModel( ).setColumnSelectionAllowed( false );
		this.setDragEnabled( false );
		this.getColumnModel().getColumn(0).setMaxWidth( 6 );
		this.getColumnModel().getColumn(1).setCellRenderer(
				new ParagraphsTableCellRenderer());
		this.getSelectionModel( ).setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		this.dataControl = dControl;
	}
	
	
	private class ParagraphsTableModel extends AbstractTableModel{

		public int getColumnCount( ) {
			return 2;
		}

		public int getRowCount( ) {
			return dataControl.getBookParagraphs( ).size( );
		}

		public Object getValueAt( int rowIndex, int columnIndex ) {
			if (columnIndex ==1 ){
				List<BookParagraphDataControl> paragraphs = dataControl.getBookParagraphs( );
				return paragraphs.get( rowIndex );
			} else {
				return Integer.toString( rowIndex );
			}
			
		}
		
		@Override
		public String getColumnName(int columnIndex) {
			if (columnIndex==1)
				return TextConstants.getText( "BookParagraphsList.Title" );
			else
				return "";
		}
	}
	
	private class ParagraphsTableCellRenderer extends DefaultTableCellRenderer {

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable,
		 *      java.lang.Object, boolean, boolean, int, int)
		 */
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
