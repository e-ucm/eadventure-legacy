/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author López Mañas, E., Pérez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, available at http://e-adventure.e-ucm.es.
 * 
 * <e-Adventure> is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * <e-Adventure>; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */
package es.eucm.eadventure.editor.gui.elementpanels.book;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.gui.TextConstants;
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
        this.getColumnModel( ).getColumn( 0 ).setMaxWidth( 100 );
        this.getColumnModel( ).getColumn( 0 ).setMinWidth( 100 );

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
                return TextConstants.getText( "BookPagesList.Type" );
            if( columnIndex == 1 )
                return TextConstants.getText( "BookPagesList.Content" );
            if( columnIndex == 2 )
                return TextConstants.getText( "BookPagesList.Margins" );
            else
                return "";
        }

        @Override
        public boolean isCellEditable( int rowIndex, int columnIndex ) {

            return rowIndex == getSelectedRow( );
        }
    }
}
