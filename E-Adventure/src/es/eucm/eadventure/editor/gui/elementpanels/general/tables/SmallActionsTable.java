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
package es.eucm.eadventure.editor.gui.elementpanels.general.tables;

import java.util.List;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.general.ActionDataControl;
import es.eucm.eadventure.editor.control.controllers.general.ActionsListDataControl;

public class SmallActionsTable extends JTable {

    private static final long serialVersionUID = -777111416961485368L;

    private ActionsListDataControl dataControl;

    public SmallActionsTable( ActionsListDataControl dControl ) {

        super( );
        this.dataControl = dControl;

        this.setModel( new ActionsTableModel( ) );
        this.getColumnModel( ).setColumnSelectionAllowed( false );
        this.setDragEnabled( false );

        this.getColumnModel( ).getColumn( 0 ).setCellRenderer( new SmallActionCellRendererEditor( ) );
        this.getColumnModel( ).getColumn( 0 ).setCellEditor( new SmallActionCellRendererEditor( ) );

        this.getSelectionModel( ).setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
        this.setRowHeight( 20 );
        this.getSelectionModel( ).addListSelectionListener( new ListSelectionListener( ) {

            public void valueChanged( ListSelectionEvent arg0 ) {

                setRowHeight( 20 );
                if( getSelectedRow( ) != -1 ) {
                    if( dataControl.getActions( ).get( getSelectedRow( ) ).getType( ) == Controller.ACTION_CUSTOM || dataControl.getActions( ).get( getSelectedRow( ) ).getType( ) == Controller.ACTION_CUSTOM_INTERACT )
                        setRowHeight( getSelectedRow( ), 250 );
                    else
                        setRowHeight( getSelectedRow( ), 180 );
                }
            }
        } );

        this.setSize( 200, 150 );
    }

    private class ActionsTableModel extends AbstractTableModel {

        private static final long serialVersionUID = -243535410363608581L;

        public int getColumnCount( ) {

            return 1;
        }

        public int getRowCount( ) {

            return dataControl.getActions( ).size( );
        }

        public Object getValueAt( int rowIndex, int columnIndex ) {

            List<ActionDataControl> actions = dataControl.getActions( );
            if( columnIndex == 0 )
                return actions.get( rowIndex );
            return null;
        }

        @Override
        public String getColumnName( int columnIndex ) {

            if( columnIndex == 0 )
                return TC.get( "SmallActionList.Action" );
            return "";
        }

        @Override
        public void setValueAt( Object value, int rowIndex, int columnIndex ) {

        }

        @Override
        public boolean isCellEditable( int row, int column ) {

            return row == getSelectedRow( );
        }
    }
}
