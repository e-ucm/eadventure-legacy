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
package es.eucm.eadventure.editor.gui.elementpanels.globalstate;


import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.globalstate.GlobalStateListDataControl;
import es.eucm.eadventure.editor.control.tools.globalstate.RenameGlobalStateTool;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.StringCellRendererEditor;

public class GlobalStatesTable extends JTable {

    private static final long serialVersionUID = 1L;

    protected GlobalStateListDataControl dataControl;

    public GlobalStatesTable( GlobalStateListDataControl dControl ) {

        super( );
        this.dataControl = dControl;

        this.setModel( new BarriersTableModel( ) );
        this.getColumnModel( ).setColumnSelectionAllowed( false );
        this.setDragEnabled( false );

        putClientProperty( "terminateEditOnFocusLost", Boolean.TRUE );

        this.getColumnModel( ).getColumn( 0 ).setCellEditor( new StringCellRendererEditor( ) );
        this.getColumnModel( ).getColumn( 0 ).setCellRenderer( new StringCellRendererEditor( ) );

        this.getSelectionModel( ).setSelectionMode( ListSelectionModel.SINGLE_SELECTION );

        this.setRowHeight( 20 );
        this.getSelectionModel( ).addListSelectionListener( new ListSelectionListener( ) {

            public void valueChanged( ListSelectionEvent arg0 ) {

                setRowHeight( 20 );
                if( getSelectedRow( ) != -1 )
                    setRowHeight( getSelectedRow( ), 26 );
            }
        } );

        this.setSize( 200, 150 );
    }

    private class BarriersTableModel extends AbstractTableModel {

        private static final long serialVersionUID = 1L;

        public int getColumnCount( ) {

            return 1;
        }

        public int getRowCount( ) {

            return dataControl.getGlobalStates( ).size( );
        }

        public Object getValueAt( int rowIndex, int columnIndex ) {

            if( columnIndex == 0 )
                return dataControl.getGlobalStates( ).get( rowIndex ).getId( );
            return null;
        }

        @Override
        public String getColumnName( int columnIndex ) {

            if( columnIndex == 0 )
                return TC.get( "GlobalStatesList.ID" );
            return "";
        }

        @Override
        public void setValueAt( Object value, int rowIndex, int columnIndex ) {
            if( columnIndex == 0 )
                Controller.getInstance( ).addTool( new RenameGlobalStateTool( ( dataControl ).getGlobalStates( ).get( rowIndex ), (String) value ) );
        }

        @Override
        public boolean isCellEditable( int row, int column ) {
            return getSelectedRow( ) == row;
        }
    }
}
