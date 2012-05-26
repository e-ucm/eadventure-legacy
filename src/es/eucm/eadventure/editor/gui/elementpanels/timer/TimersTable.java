/*******************************************************************************
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the e-UCM
 *          research group.
 *   
 *    Copyright 2005-2012 e-UCM research group.
 *  
 *     e-UCM is a research group of the Department of Software Engineering
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
 * This file is part of eAdventure, version 1.5.
 * 
 *   You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with Adventure.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
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
