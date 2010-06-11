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
package es.eucm.eadventure.editor.gui.elementpanels.scene;


import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.scene.ExitsListDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.AuxEditCellRendererEditor;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.ExitLooksCellRendererEditor;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.InfoHeaderRenderer;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.NextSceneCellRendererEditor;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.TransitionCellRendererEditor;
import es.eucm.eadventure.editor.gui.otherpanels.IrregularAreaEditionPanel;
import es.eucm.eadventure.editor.gui.otherpanels.ScenePreviewEditionPanel;

public class ExitsTable extends JTable {

    private static final long serialVersionUID = 1L;

    protected ExitsListDataControl dataControl;

    protected IrregularAreaEditionPanel iaep;

    protected ScenePreviewEditionPanel spep;

    public ExitsTable( ExitsListDataControl dControl, IrregularAreaEditionPanel iaep2, JSplitPane previewAuxSplit ) {

        super( );
        this.spep = iaep2.getScenePreviewEditionPanel( );
        this.iaep = iaep2;
        this.dataControl = dControl;

        this.setModel( new ElementsTableModel( ) );
        this.getColumnModel( ).setColumnSelectionAllowed( false );
        this.setDragEnabled( false );
        this.setColumnSelectionAllowed( false );

        String[] ids = Controller.getInstance( ).getIdentifierSummary( ).getGeneralSceneIds( );

        this.getColumnModel( ).getColumn( 0 ).setCellRenderer( new NextSceneCellRendererEditor( ids ) );
        this.getColumnModel( ).getColumn( 0 ).setCellEditor( new NextSceneCellRendererEditor( ids ) );
        this.getColumnModel( ).getColumn( 0 ).setMinWidth( 140 );
        this.getColumnModel( ).getColumn( 1 ).setCellRenderer( new TransitionCellRendererEditor( ) );
        this.getColumnModel( ).getColumn( 1 ).setCellEditor( new TransitionCellRendererEditor( ) );
        this.getColumnModel( ).getColumn( 1 ).setMaxWidth( 110 );
        this.getColumnModel( ).getColumn( 1 ).setMinWidth( 110 );
        this.getColumnModel( ).getColumn( 2 ).setCellRenderer( new ExitLooksCellRendererEditor( ) );
        this.getColumnModel( ).getColumn( 2 ).setCellEditor( new ExitLooksCellRendererEditor( ) );
        this.getColumnModel( ).getColumn( 2 ).setMinWidth( 160 );
        String text = TC.get( "ExitsList.Edit" );
        this.getColumnModel( ).getColumn( 3 ).setCellRenderer( new AuxEditCellRendererEditor( previewAuxSplit, ExitsListPanel.VERTICAL_SPLIT_POSITION, text ) );
        this.getColumnModel( ).getColumn( 3 ).setCellEditor( new AuxEditCellRendererEditor( previewAuxSplit, ExitsListPanel.VERTICAL_SPLIT_POSITION, text ) );
        this.getColumnModel( ).getColumn( 3 ).setMaxWidth( 130 );
        this.getColumnModel( ).getColumn( 3 ).setMinWidth( 130 );

        this.getColumnModel( ).getColumn( 0 ).setHeaderRenderer( new InfoHeaderRenderer( "scenes/Scene_NextScene.html" ) );
        this.getColumnModel( ).getColumn( 1 ).setHeaderRenderer( new InfoHeaderRenderer( "scenes/Scene_Transition.html" ) );
        this.getColumnModel( ).getColumn( 2 ).setHeaderRenderer( new InfoHeaderRenderer( "scenes/Scene_ExitAppearence.html" ) );
        this.getColumnModel( ).getColumn( 3 ).setHeaderRenderer( new InfoHeaderRenderer( ) );

        this.setRowHeight( 25 );
        this.getSelectionModel( ).setSelectionMode( ListSelectionModel.SINGLE_SELECTION );

        this.getSelectionModel( ).addListSelectionListener( new ListSelectionListener( ) {

            public void valueChanged( ListSelectionEvent arg0 ) {

                setRowHeight( 25 );
                if( getSelectedRow( ) != -1 )
                    setRowHeight( getSelectedRow( ), 70 );
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

            return dataControl.getExits( ).size( );
        }

        public Object getValueAt( int rowIndex, int columnIndex ) {

            if( columnIndex == 0 )
                return dataControl.getExits( ).get( rowIndex );
            if( columnIndex == 1 )
                return dataControl.getExits( ).get( rowIndex );
            if( columnIndex == 2 )
                return dataControl.getExits( ).get( rowIndex ).getExitLookDataControl( );
            if( columnIndex == 3 )
                return dataControl.getExits( ).get( rowIndex ).getConditions( );
            return null;
        }

        @Override
        public String getColumnName( int columnIndex ) {

            if( columnIndex == 0 )
                return TC.get( "ExitsList.NextScene" );
            if( columnIndex == 1 )
                return TC.get( "ExitsList.Transition" );
            if( columnIndex == 2 )
                return TC.get( "ExitsList.Appearance" );
            if( columnIndex == 3 )
                return TC.get( "ExitsList.ConditionsAndEffects" );
            return "";
        }

        @Override
        public void setValueAt( Object value, int rowIndex, int columnIndex ) {

            if( columnIndex == 2 ) {
                this.fireTableDataChanged( );
            }
        }

        @Override
        public boolean isCellEditable( int row, int column ) {

            return getSelectedRow( ) == row;
        }
    }
}
