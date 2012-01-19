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
package es.eucm.eadventure.editor.gui.elementpanels.adaptation;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationProfileDataControl;
import es.eucm.eadventure.editor.control.tools.generic.ChangeStringValueTool;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.StringCellRendererEditor;

public class AdaptationRulesTable extends JTable {

    private static final long serialVersionUID = 647135614707098595L;

    private AdaptationProfileDataControl dataControl;

    public AdaptationRulesTable( AdaptationProfileDataControl dataControl ) {

        this.dataControl = dataControl;
        setModel( new AdaptationRulesTableModel( ) );
        getSelectionModel( ).setSelectionMode( ListSelectionModel.SINGLE_SELECTION );

        getColumnModel( ).getColumn( 0 ).setWidth( 20 );
        getColumnModel( ).getColumn( 0 ).setMaxWidth( 20 );

        getColumnModel( ).getColumn( 1 ).setCellEditor( new StringCellRendererEditor( ) );
        getColumnModel( ).getColumn( 1 ).setCellRenderer( new StringCellRendererEditor( ) );
        getColumnModel( ).getColumn( 2 ).setCellEditor( new InitialSceneCellRendererEditor( ) );
        getColumnModel( ).getColumn( 2 ).setCellRenderer( new InitialSceneCellRendererEditor( ) );

        setRowHeight( 22 );

        getSelectionModel( ).addListSelectionListener( new ListSelectionListener( ) {

            public void valueChanged( ListSelectionEvent arg0 ) {

                setRowHeight( 22 );
                if( getSelectedRow( ) >= 0 )
                    setRowHeight( getSelectedRow( ), 35 );
            }
        } );
    }

    public void fireTableDataChanged( ) {

        ( (AbstractTableModel) getModel( ) ).fireTableDataChanged( );
    }

    /**
     * Table model to display the scenes information.
     */
    public class AdaptationRulesTableModel extends AbstractTableModel {

        /**
         * Required.
         */
        private static final long serialVersionUID = 1L;

        /**
         * Constructor.
         * 
         * @param assRulesInfo
         *            Container array of the information of the scenes
         */
        public AdaptationRulesTableModel( ) {

        }

        public int getColumnCount( ) {

            return 3;
        }

        public int getRowCount( ) {

            return dataControl.getAdaptationRules( ).size( );
        }

        @Override
        public String getColumnName( int columnIndex ) {

            String columnName = "";
            if( columnIndex == 0 )
                columnName = TC.get( "AdaptationRulesList.ColumnHeader0" );
            else if( columnIndex == 1 )
                columnName = TC.get( "AdaptationRule.Description" );
            else if( columnIndex == 2 )
                columnName = TC.get( "AdaptationRule.InitialState.InitialScene" );
            return columnName;
        }

        public Object getValueAt( int rowIndex, int columnIndex ) {

            if( columnIndex == 0 )
                return rowIndex;
            if( columnIndex == 1 )
                return dataControl.getAdaptationRules( ).get( rowIndex ).getDescription( );
            if( columnIndex == 2 )
                return dataControl.getAdaptationRules( ).get( rowIndex );
            return null;
        }

        @Override
        public void setValueAt( Object value, int rowIndex, int columnIndex ) {

            if( columnIndex == 1 ) {
                Controller.getInstance( ).addTool( new ChangeStringValueTool( dataControl.getAdaptationRules( ).get( rowIndex ).getContent( ), (String) value, "getDescription", "setDescription" ) );
            }
        }

        @Override
        public boolean isCellEditable( int rowIndex, int columnIndex ) {

            return rowIndex == getSelectedRow( ) && columnIndex > 0;
        }
    }

}
