/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *         research group.
 *  
 *   Copyright 2005-2010 <e-UCM> research group.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *         http://e-adventure.e-ucm.es/contributors
 * 
 *   <e-UCM> is a research group of the Department of Software Engineering
 *         and Artificial Intelligence at the Complutense University of Madrid
 *         (School of Computer Science).
 * 
 *         C Profesor Jose Garcia Santesmases sn,
 *         28040 Madrid (Madrid), Spain.
 * 
 *         For more info please visit:  <http://e-adventure.e-ucm.es> or
 *         <http://www.e-ucm.es>
 * 
 * ****************************************************************************
 * 
 * This file is part of <e-Adventure>, version 1.2.
 * 
 *     <e-Adventure> is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     <e-Adventure> is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 * 
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.gui.elementpanels.book;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.book.BookPagesListDataControl;

public class PagesTable extends JTable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private BookPagesListDataControl dataControl;

    public PagesTable( BookPagesListDataControl dControl, BookPagesPanel parentPanel ) {

        super( );
        this.setModel( new ParagraphsTableModel( ) );
        this.getColumnModel( ).setColumnSelectionAllowed( false );
        this.setDragEnabled( false );

        this.getColumnModel( ).getColumn( 0 ).setCellEditor( new ContentTypeCellRendererEditor( dControl ) );
        this.getColumnModel( ).getColumn( 0 ).setCellRenderer( new ContentTypeCellRendererEditor( dControl ) );
        this.getColumnModel( ).getColumn( 0 ).setMaxWidth( 160 );
        this.getColumnModel( ).getColumn( 0 ).setMinWidth( 140 );

        this.getColumnModel( ).getColumn( 1 ).setCellEditor( new ResourceCellRendererEditor( dControl, parentPanel ) );
        this.getColumnModel( ).getColumn( 1 ).setCellRenderer( new ResourceCellRendererEditor( dControl, parentPanel ) );

        this.getColumnModel( ).getColumn( 2 ).setCellEditor( new MarginsCellRendererEditor( dControl, parentPanel ) );
        this.getColumnModel( ).getColumn( 2 ).setCellRenderer( new MarginsCellRendererEditor( dControl, parentPanel ) );
        this.getColumnModel( ).getColumn( 2 ).setMaxWidth( 130 );
        this.getColumnModel( ).getColumn( 2 ).setMinWidth( 130 );

        this.getSelectionModel( ).setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
        this.dataControl = dControl;

        this.setRowHeight( 22 );
        this.getSelectionModel( ).addListSelectionListener( new ListSelectionListener( ) {

            public void valueChanged( ListSelectionEvent arg0 ) {

                setRowHeight( 22 );
                if( getSelectedRow( ) != -1 )
                    setRowHeight( getSelectedRow( ), 65 );
            }
        } );
    }

    private class ParagraphsTableModel extends AbstractTableModel {

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
        public String getColumnName( int columnIndex ) {

            if( columnIndex == 0 )
                return TC.get( "BookPagesList.Type" );
            if( columnIndex == 1 )
                return TC.get( "BookPagesList.Content" );
            if( columnIndex == 2 )
                return TC.get( "BookPagesList.Margins" );
            else
                return "";
        }

        @Override
        public boolean isCellEditable( int rowIndex, int columnIndex ) {

            return rowIndex == getSelectedRow( );
        }
    }
}
