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
package es.eucm.eadventure.editor.gui.elementpanels.general.tables;


import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControlWithResources;
import es.eucm.eadventure.editor.control.tools.structurepanel.RenameElementTool;
import es.eucm.eadventure.editor.gui.elementpanels.general.LooksPanel;

public class ResourcesTable extends JTable {

    private static final long serialVersionUID = 1L;

    protected DataControlWithResources dataControl;

    protected LooksPanel looksPanel;

    public ResourcesTable( DataControlWithResources dControl, LooksPanel looksPanel2 ) {

        super( );
        this.looksPanel = looksPanel2;
        this.dataControl = dControl;

        this.setModel( new ElementsTableModel( ) );
        this.getColumnModel( ).setColumnSelectionAllowed( false );
        this.setDragEnabled( false );

        this.getColumnModel( ).getColumn( 0 ).setHeaderRenderer( new InfoHeaderRenderer( "general/Appearence.html" ) );
        this.getColumnModel( ).getColumn( 1 ).setHeaderRenderer( new InfoHeaderRenderer( "general/Conditions.html" ) );

        this.getColumnModel( ).getColumn( 1 ).setCellRenderer( new ConditionsCellRendererEditor( ) );
        this.getColumnModel( ).getColumn( 1 ).setCellEditor( new ConditionsCellRendererEditor( ) );

        this.getSelectionModel( ).setSelectionMode( ListSelectionModel.SINGLE_SELECTION );

        this.setSize( 200, 150 );
    }

    public void resetModel( ) {

        this.setModel( new ElementsTableModel( ) );
        this.getColumnModel( ).getColumn( 0 ).setHeaderRenderer( new InfoHeaderRenderer( "general/Appearence.html" ) );
        this.getColumnModel( ).getColumn( 1 ).setHeaderRenderer( new InfoHeaderRenderer( "general/Conditions.html" ) );

        this.getColumnModel( ).getColumn( 1 ).setCellRenderer( new ConditionsCellRendererEditor( ) );
        this.getColumnModel( ).getColumn( 1 ).setCellEditor( new ConditionsCellRendererEditor( ) );

        this.getSelectionModel( ).setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
        ( (AbstractTableModel) this.getModel( ) ).fireTableDataChanged( );
    }

    public int getSelectedIndex( ) {

        return getSelectedRow( );
    }

    public void setSelectedIndex( int index ) {

        getSelectionModel( ).setSelectionInterval( index, index );
    }

    private class ElementsTableModel extends AbstractTableModel {

        private static final long serialVersionUID = 1L;

        public int getColumnCount( ) {

            return 2;
        }

        public int getRowCount( ) {

            return dataControl.getResourcesCount( );
        }

        public Object getValueAt( int rowIndex, int columnIndex ) {

            if( columnIndex == 0) {
                String name = dataControl.getResources( ).get( rowIndex ).getName( );
                if (name == null || name.equals( "" ))
                    name = "No name";
                if(getSelectedRow() != rowIndex)
                    return TC.get( "ResourcesList.ResourcesBlockNumber" ) + ( rowIndex + 1 ) + ": " + name;
                else
                    return name;
            }
            if( columnIndex == 1 ) {
                if( dataControl.getResources( ).size( ) == 1 )
                    return null;
                return dataControl.getResources( ).get( rowIndex ).getConditions( );
            }
            return null;
        }

        @Override
        public String getColumnName( int columnIndex ) {

            if( columnIndex == 0 )
                return TC.get( "ResourcesList.ResourcesBlock" );
            if( columnIndex == 1 )
                return TC.get( "ResourcesList.Conditions" );
            return "";
        }

        @Override
        public void setValueAt( Object value, int rowIndex, int columnIndex ) {
            if (columnIndex == 0) {
                Controller.getInstance( ).addTool( new RenameElementTool(dataControl.getResources( ).get( rowIndex ), (String) value));
            }
        }

        @Override
        public boolean isCellEditable( int row, int column ) {

            return getSelectedRow( ) == row;
        }
    }
}
