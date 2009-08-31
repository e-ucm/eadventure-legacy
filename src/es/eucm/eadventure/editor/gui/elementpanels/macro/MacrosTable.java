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
package es.eucm.eadventure.editor.gui.elementpanels.macro;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.macro.MacroListDataControl;
import es.eucm.eadventure.editor.control.tools.macro.RenameMacroTool;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.StringCellRendererEditor;

public class MacrosTable extends JTable {

    private static final long serialVersionUID = 1L;

    protected MacroListDataControl dataControl;

    public MacrosTable( MacroListDataControl dControl ) {

        super( );
        this.dataControl = dControl;

        this.setModel( new MacrosTableModel( ) );
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

    private class MacrosTableModel extends AbstractTableModel {

        private static final long serialVersionUID = 1L;

        public int getColumnCount( ) {

            return 1;
        }

        public int getRowCount( ) {

            return dataControl.getMacros( ).size( );
        }

        public Object getValueAt( int rowIndex, int columnIndex ) {

            if( columnIndex == 0 )
                return dataControl.getMacros( ).get( rowIndex ).getId( );
            return null;
        }

        @Override
        public String getColumnName( int columnIndex ) {

            if( columnIndex == 0 )
                return TextConstants.getText( "MacrosList.ID" );
            return "";
        }

        @Override
        public void setValueAt( Object value, int rowIndex, int columnIndex ) {

            if( columnIndex == 0 ) {
                Controller.getInstance( ).addTool( new RenameMacroTool( ( dataControl ).getMacros( ).get( rowIndex ), (String) value ) );
            }
        }

        @Override
        public boolean isCellEditable( int row, int column ) {

            return getSelectedRow( ) == row;
        }
    }
}
