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
package es.eucm.eadventure.editor.gui.elementpanels.general.tables;

import java.util.List;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.general.ActionDataControl;
import es.eucm.eadventure.editor.control.controllers.general.ActionsListDataControl;

public class ActionsTable extends JTable {

    private static final long serialVersionUID = -777111416961485368L;

    private ActionsListDataControl dataControl;

    public ActionsTable( ActionsListDataControl dControl ) {

        super( );
        this.dataControl = dControl;

        this.setModel( new ActionsTableModel( ) );
        this.getColumnModel( ).setColumnSelectionAllowed( false );
        this.getColumnModel( ).getColumn( 0 ).setHeaderRenderer( new InfoHeaderRenderer( ) );
        this.getColumnModel( ).getColumn( 1 ).setHeaderRenderer( new InfoHeaderRenderer( "actions/NeedsGetTo.html" ) );
        this.getColumnModel( ).getColumn( 2 ).setHeaderRenderer( new InfoHeaderRenderer( "general/Conditions.html" ) );
        this.setDragEnabled( false );

        this.getColumnModel( ).getColumn( 0 ).setCellRenderer( new ActionCellRendererEditor( ) );
        this.getColumnModel( ).getColumn( 0 ).setCellEditor( new ActionCellRendererEditor( ) );
        this.getColumnModel( ).getColumn( 1 ).setCellRenderer( new ActionDetailsCellRendererEditor( ) );
        this.getColumnModel( ).getColumn( 1 ).setCellEditor( new ActionDetailsCellRendererEditor( ) );
        this.getColumnModel( ).getColumn( 2 ).setCellRenderer( new ConditionsCellRendererEditor( ) );
        this.getColumnModel( ).getColumn( 2 ).setCellEditor( new ConditionsCellRendererEditor( ) );
        this.getColumnModel( ).getColumn( 2 ).setMaxWidth( 120 );
        this.getColumnModel( ).getColumn( 2 ).setMinWidth( 120 );
        this.getColumnModel( ).getColumn( 2 ).setWidth( 120 );

        this.getSelectionModel( ).setSelectionMode( ListSelectionModel.SINGLE_SELECTION );

        this.setRowHeight( 20 );
        this.getSelectionModel( ).addListSelectionListener( new ListSelectionListener( ) {

            public void valueChanged( ListSelectionEvent arg0 ) {

                setRowHeight( 20 );
                setRowHeight( getSelectedRow( ), 44 );
            }
        } );
        this.setSize( 200, 150 );
    }

    private class ActionsTableModel extends AbstractTableModel {

        private static final long serialVersionUID = -243535410363608581L;

        public int getColumnCount( ) {

            return 3;
        }

        public int getRowCount( ) {

            return dataControl.getActions( ).size( );
        }

        public Object getValueAt( int rowIndex, int columnIndex ) {

            List<ActionDataControl> actions = dataControl.getActions( );
            if( columnIndex == 0 )
                return actions.get( rowIndex );
            if( columnIndex == 1 )
                return actions.get( rowIndex );
            if( columnIndex == 2 )
                return actions.get( rowIndex ).getConditions( );
            return null;
        }

        @Override
        public String getColumnName( int columnIndex ) {

            if( columnIndex == 0 )
                return TC.get( "ActionsList.ActionName" );
            if( columnIndex == 1 )
                return TC.get( "ActionsList.NeedsGoTo" );
            if( columnIndex == 2 )
                return TC.get( "ActionsList.Conditions" );
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
