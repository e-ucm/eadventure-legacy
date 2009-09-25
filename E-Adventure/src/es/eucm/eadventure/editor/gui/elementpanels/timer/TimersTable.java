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
package es.eucm.eadventure.editor.gui.elementpanels.timer;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.timer.TimersListDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.BooleanCellRendererEditor;

public class TimersTable extends JTable {

    private static final long serialVersionUID = 1L;

    protected TimersListDataControl dataControl;

    private TimersListPanel panel;

    public TimersTable( TimersListDataControl dControl, TimersListPanel panel ) {

        super( );
        this.dataControl = dControl;
        this.panel = panel;

        this.setModel( new BarriersTableModel( ) );
        this.getColumnModel( ).setColumnSelectionAllowed( false );
        this.setDragEnabled( false );

        this.getColumnModel( ).getColumn( 1 ).setCellEditor( new TimerTimeCellRendererEditor( ) );
        this.getColumnModel( ).getColumn( 1 ).setCellRenderer( new TimerTimeCellRendererEditor( ) );
        this.getColumnModel( ).getColumn( 2 ).setCellEditor( new BooleanCellRendererEditor( ) );
        this.getColumnModel( ).getColumn( 2 ).setCellRenderer( new BooleanCellRendererEditor( ) );

        this.setRowHeight( 20 );
        this.getSelectionModel( ).addListSelectionListener( new ListSelectionListener( ) {

            public void valueChanged( ListSelectionEvent arg0 ) {

                setRowHeight( 20 );
                if( getSelectedRow( ) != -1 ) {
                    setRowHeight( getSelectedRow( ), 30 );
                }
            }
        } );

        this.getSelectionModel( ).setSelectionMode( ListSelectionModel.SINGLE_SELECTION );

        this.setSize( 200, 150 );
    }

    private class BarriersTableModel extends AbstractTableModel {

        private static final long serialVersionUID = 1L;

        public int getColumnCount( ) {

            return 3;
        }

        public int getRowCount( ) {

            return dataControl.getTimers( ).size( );
        }

        public Object getValueAt( int rowIndex, int columnIndex ) {

            if( columnIndex == 0 )
                return TC.getElement( Controller.TIMER ) + ":#" + ( rowIndex + 1 );
            if( columnIndex == 1 )
                return dataControl.getTimers( ).get( rowIndex );
            if( columnIndex == 2 )
                return new Boolean( dataControl.getTimers( ).get( rowIndex ).isShowTime( ) );
            return null;
        }

        @Override
        public String getColumnName( int columnIndex ) {

            if( columnIndex == 0 )
                return TC.get( "TimersList.Timer" );
            if( columnIndex == 1 )
                return TC.get( "TimersList.Time" );
            if( columnIndex == 2 )
                return TC.get( "TimersList.Display" );
            return "";
        }

        @Override
        public void setValueAt( Object value, int rowIndex, int columnIndex ) {

            if( columnIndex == 2 ) {
                Boolean show = (Boolean) value;
                dataControl.getTimers( ).get( rowIndex ).setShowTime( show.booleanValue( ) );
                panel.updateInfoPanel( rowIndex );
            }
        }

        @Override
        public boolean isCellEditable( int row, int column ) {

            return getSelectedRow( ) == row && column != 0;
        }
    }
}
