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
package es.eucm.eadventure.editor.gui.elementpanels.scene;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.scene.BarriersListDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.ConditionsCellRendererEditor;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.InfoHeaderRenderer;

public class BarriersTable extends JTable {

    private static final long serialVersionUID = 1L;

    protected BarriersListDataControl dataControl;

    public BarriersTable( BarriersListDataControl dControl ) {

        super( );
        this.dataControl = dControl;

        this.setModel( new BarriersTableModel( ) );
        this.getColumnModel( ).setColumnSelectionAllowed( false );
        this.setDragEnabled( false );

        this.getColumnModel( ).getColumn( 0 ).setHeaderRenderer( new InfoHeaderRenderer( ) );
        this.getColumnModel( ).getColumn( 1 ).setHeaderRenderer( new InfoHeaderRenderer( "general/Conditions.html" ) );

        this.getColumnModel( ).getColumn( 1 ).setCellRenderer( new ConditionsCellRendererEditor( ) );
        this.getColumnModel( ).getColumn( 1 ).setCellEditor( new ConditionsCellRendererEditor( ) );

        this.setRowHeight( 25 );
        this.getSelectionModel( ).setSelectionMode( ListSelectionModel.SINGLE_SELECTION );

        this.setSize( 200, 150 );
    }

    private class BarriersTableModel extends AbstractTableModel {

        private static final long serialVersionUID = 1L;

        public int getColumnCount( ) {

            return 2;
        }

        public int getRowCount( ) {

            return dataControl.getBarriers( ).size( );
        }

        public Object getValueAt( int rowIndex, int columnIndex ) {

            if( columnIndex == 0 )
                return TextConstants.getText( "BarriersList.BarrierNumber" ) + ( rowIndex + 1 );
            if( columnIndex == 1 )
                return dataControl.getBarriers( ).get( rowIndex ).getConditions( );
            return null;
        }

        @Override
        public String getColumnName( int columnIndex ) {

            if( columnIndex == 0 )
                return TextConstants.getText( "BarriersList.Barrier" );
            if( columnIndex == 1 )
                return TextConstants.getText( "BarriersList.Conditions" );
            return "";
        }

        @Override
        public void setValueAt( Object value, int rowIndex, int columnIndex ) {

        }

        @Override
        public boolean isCellEditable( int row, int column ) {

            return getSelectedRow( ) == row && column == 1;
        }
    }
}
