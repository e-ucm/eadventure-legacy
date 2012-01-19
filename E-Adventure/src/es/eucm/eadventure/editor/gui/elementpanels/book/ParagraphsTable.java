/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *          research group.
 *   
 *    Copyright 2005-2012 <e-UCM> research group.
 *  
 *     <e-UCM> is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *  
 *          C Profesor Jose Garcia Santesmases sn,
 *          28040 Madrid (Madrid), Spain.
 *  
 *          For more info please visit:  <http://e-adventure.e-ucm.es> or
 *          <http://www.e-ucm.es>
 *  
 *  ****************************************************************************
 * This file is part of <e-Adventure>, version 1.4.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       <e-Adventure> is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      <e-Adventure> is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
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

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.book.BookParagraphDataControl;
import es.eucm.eadventure.editor.control.controllers.book.BookParagraphsListDataControl;
import es.eucm.eadventure.editor.gui.otherpanels.bookpanels.BookParagraphPreviewPanel;

public class ParagraphsTable extends JTable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private BookParagraphsListDataControl dataControl;

    public ParagraphsTable( BookParagraphsListDataControl dControl, BookParagraphPreviewPanel previewPanel2 ) {

        super( );
        this.setModel( new ParagraphsTableModel( ) );
        this.getColumnModel( ).setColumnSelectionAllowed( false );
        this.setDragEnabled( false );
        this.getColumnModel( ).getColumn( 0 ).setMaxWidth( 130 );
        this.getColumnModel( ).getColumn( 0 ).setMinWidth( 130 );
        this.getColumnModel( ).getColumn( 0 ).setCellRenderer( new ParagraphsTableCellRenderer( ) );

        this.getColumnModel( ).getColumn( 1 ).setCellEditor( new ParagraphCellRendererEditor( previewPanel2 ) );
        this.getColumnModel( ).getColumn( 1 ).setCellRenderer( new ParagraphCellRendererEditor( previewPanel2 ) );

        this.getSelectionModel( ).setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
        this.dataControl = dControl;

        setRowHeight( 22 );
        getSelectionModel( ).addListSelectionListener( new ListSelectionListener( ) {

            public void valueChanged( ListSelectionEvent arg0 ) {

                setRowHeight( 22 );
                if( getSelectedRow( ) != -1 )
                    setRowHeight( getSelectedRow( ), 50 );
            }
        } );
    }

    private class ParagraphsTableModel extends AbstractTableModel {

        private static final long serialVersionUID = 1L;

        public int getColumnCount( ) {

            return 2;
        }

        public int getRowCount( ) {

            return dataControl.getBookParagraphs( ).size( );
        }

        public Object getValueAt( int rowIndex, int columnIndex ) {

            if( columnIndex == 0 ) {
                List<BookParagraphDataControl> paragraphs = dataControl.getBookParagraphs( );
                return paragraphs.get( rowIndex );
            }
            if( columnIndex == 1 ) {
                return dataControl.getBookParagraphs( ).get( rowIndex );
            }
            return null;
        }

        @Override
        public String getColumnName( int columnIndex ) {

            if( columnIndex == 0 )
                return TC.get( "BookParagraphsList.ParagraphType" );
            if( columnIndex == 1 )
                return TC.get( "BookParagraphsList.Content" );
            else
                return "";
        }

        @Override
        public boolean isCellEditable( int rowIndex, int columnIndex ) {

            return rowIndex == getSelectedRow( ) && columnIndex > 0;
        }
    }

    private class ParagraphsTableCellRenderer extends DefaultTableCellRenderer {

        private static final long serialVersionUID = 1L;

        @Override
        public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {

            if( value instanceof BookParagraphDataControl ) {
                String elementName = TC.getElement( ( (BookParagraphDataControl) value ).getType( ) );
                if( ( (BookParagraphDataControl) value ).getType( ) == Controller.BOOK_TITLE_PARAGRAPH ) {
                    return new IconTextPanel( "img/icons/titleBookParagraph.png", elementName, isSelected );
                }
                else if( ( (BookParagraphDataControl) value ).getType( ) == Controller.BOOK_BULLET_PARAGRAPH ) {
                    return new IconTextPanel( "img/icons/bulletBookParagraph.png", elementName, isSelected );
                }
                else if( ( (BookParagraphDataControl) value ).getType( ) == Controller.BOOK_TEXT_PARAGRAPH ) {
                    return new IconTextPanel( "img/icons/textBookParagraph.png", elementName, isSelected );
                }
                else if( ( (BookParagraphDataControl) value ).getType( ) == Controller.BOOK_IMAGE_PARAGRAPH ) {
                    return new IconTextPanel( "img/icons/imageBookParagraph.png", elementName, isSelected );
                }
                else
                    return null;
            }
            else {
                return new JLabel( value.toString( ) );
            }
        }

    }

}
