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
package es.eucm.eadventure.editor.gui.elementpanels.scene;

import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.scene.ActiveAreasListDataControl;
import es.eucm.eadventure.editor.control.tools.scene.RenameActiveAreaTool;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.AuxEditCellRendererEditor;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.ConditionsCellRendererEditor;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.DocumentationCellRendererEditor;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.InfoHeaderRenderer;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.StringCellRendererEditor;
import es.eucm.eadventure.editor.gui.otherpanels.IrregularAreaEditionPanel;
import es.eucm.eadventure.editor.gui.otherpanels.ScenePreviewEditionPanel;

public class ActiveAreasTable extends JTable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    protected ActiveAreasListDataControl dataControl;

    protected IrregularAreaEditionPanel iaep;

    protected ScenePreviewEditionPanel spep;

    public ActiveAreasTable( ActiveAreasListDataControl dControl, IrregularAreaEditionPanel iaep2, JSplitPane previewAuxSplit ) {

        super( );
        this.spep = iaep2.getScenePreviewEditionPanel( );
        this.iaep = iaep2;
        this.dataControl = dControl;

        putClientProperty( "terminateEditOnFocusLost", Boolean.TRUE );

        this.setModel( new ElementsTableModel( ) );
        this.getColumnModel( ).setColumnSelectionAllowed( false );
        this.setDragEnabled( false );

        this.getSelectionModel( ).addListSelectionListener( new ListSelectionListener( ) {

            public void valueChanged( ListSelectionEvent arg0 ) {

                if( getSelectedRow( ) >= 0 ) {
                    iaep.setRectangular( dataControl.getActiveAreas( ).get( getSelectedRow( ) ) );
                    iaep.repaint( );
                }
            }
        } );

        this.getColumnModel( ).getColumn( 0 ).setHeaderRenderer( new InfoHeaderRenderer( ) );
        this.getColumnModel( ).getColumn( 1 ).setHeaderRenderer( new InfoHeaderRenderer( "general/Conditions.html" ) );
        this.getColumnModel( ).getColumn( 2 ).setHeaderRenderer( new InfoHeaderRenderer( "scenes/Scene_ActiveAreaActions.html" ) );
        this.getColumnModel( ).getColumn( 3 ).setHeaderRenderer( new InfoHeaderRenderer( ) );

        this.getColumnModel( ).getColumn( 0 ).setCellEditor( new StringCellRendererEditor( ) );
        this.getColumnModel( ).getColumn( 0 ).setCellRenderer( new StringCellRendererEditor( ) );

        this.getColumnModel( ).getColumn( 1 ).setCellRenderer( new ConditionsCellRendererEditor( ) );
        this.getColumnModel( ).getColumn( 1 ).setCellEditor( new ConditionsCellRendererEditor( ) );
        this.getColumnModel( ).getColumn( 1 ).setMaxWidth( 120 );
        this.getColumnModel( ).getColumn( 1 ).setMinWidth( 120 );

        String text = TC.get( "ActiveAreasList.EditActions" );
        this.getColumnModel( ).getColumn( 2 ).setCellRenderer( new AuxEditCellRendererEditor( previewAuxSplit, ActiveAreasListPanel.VERTICAL_SPLIT_POSITION, text ) );
        this.getColumnModel( ).getColumn( 2 ).setCellEditor( new AuxEditCellRendererEditor( previewAuxSplit, ActiveAreasListPanel.VERTICAL_SPLIT_POSITION, text ) );
        this.getColumnModel( ).getColumn( 2 ).setMaxWidth( 105 );
        this.getColumnModel( ).getColumn( 2 ).setMinWidth( 105 );

        this.getColumnModel( ).getColumn( 3 ).setCellRenderer( new DocumentationCellRendererEditor( ) );
        this.getColumnModel( ).getColumn( 3 ).setCellEditor( new DocumentationCellRendererEditor( ) );
        this.getColumnModel( ).getColumn( 3 ).setMaxWidth( 140 );
        this.getColumnModel( ).getColumn( 3 ).setMinWidth( 140 );

        this.getSelectionModel( ).setSelectionMode( ListSelectionModel.SINGLE_SELECTION );

        this.setRowHeight( 22 );
        this.getSelectionModel( ).addListSelectionListener( new ListSelectionListener( ) {

            public void valueChanged( ListSelectionEvent arg0 ) {

                setRowHeight( 22 );
                if( getSelectedRow( ) != -1 )
                    setRowHeight( getSelectedRow( ), 26 );
            }
        } );

        this.setSize( 200, 150 );
    }

    private class ElementsTableModel extends AbstractTableModel {

        private static final long serialVersionUID = 1L;

        public int getColumnCount( ) {

            return 4;
        }

        public int getRowCount( ) {

            return dataControl.getActiveAreas( ).size( );
        }

        public Object getValueAt( int rowIndex, int columnIndex ) {

            if( columnIndex == 0 )
                return dataControl.getActiveAreas( ).get( rowIndex ).getId( );
            if( columnIndex == 1 )
                return dataControl.getActiveAreas( ).get( rowIndex ).getConditions( );
            if( columnIndex == 3 )
                return dataControl.getActiveAreas( ).get( rowIndex ).getDescriptionsController( );
            return null;
        }

        @Override
        public String getColumnName( int columnIndex ) {

            if( columnIndex == 0 )
                return TC.get( "ActiveAreasList.Id" );
            if( columnIndex == 1 )
                return TC.get( "ActiveAreasList.Conditions" );
            if( columnIndex == 2 )
                return TC.get( "ActiveAreasList.Actions" );
            if( columnIndex == 3 )
                return TC.get( "ActiveAreasList.Documentation" );
            return "";
        }

        @Override
        public void setValueAt( Object value, int rowIndex, int columnIndex ) {

            if( columnIndex == 0 ) {
                if( dataControl.getActiveAreas( ).size( ) > rowIndex )
                    Controller.getInstance( ).addTool( new RenameActiveAreaTool( dataControl.getActiveAreas( ).get( rowIndex ), (String) value ) );
            }
        }

        @Override
        public boolean isCellEditable( int row, int column ) {

            return getSelectedRow( ) == row;
        }
    }
}
