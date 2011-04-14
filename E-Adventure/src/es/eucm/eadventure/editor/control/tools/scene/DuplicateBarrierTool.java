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
package es.eucm.eadventure.editor.control.tools.scene;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.data.chapter.elements.Barrier;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.scene.BarrierDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.BarriersListDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.gui.otherpanels.ScenePreviewEditionPanel;

public class DuplicateBarrierTool extends Tool {

    private BarriersListDataControl dataControl;

    private ScenePreviewEditionPanel spep;

    private JTable table;

    private BarrierDataControl newBarrier;

    public DuplicateBarrierTool( BarriersListDataControl dataControl2, ScenePreviewEditionPanel spep2, JTable table2 ) {

        this.dataControl = dataControl2;
        this.table = table2;
        this.spep = spep2;
    }

    @Override
    public boolean canRedo( ) {

        return true;
    }

    @Override
    public boolean canUndo( ) {

        return true;
    }

    @Override
    public boolean combine( Tool other ) {

        return false;
    }

    @Override
    public boolean doTool( ) {

        
        if( dataControl.duplicateElement( dataControl.getBarriers( ).get( table.getSelectedRow( ) ) ) ) {
            newBarrier = dataControl.getLastBarrier( );
            spep.addBarrier( dataControl.getLastBarrier( ) );
            spep.repaint( );
            ( (AbstractTableModel) table.getModel( ) ).fireTableDataChanged( );
            table.changeSelection( dataControl.getBarriers( ).size( ) - 1, dataControl.getBarriers( ).size( ) - 1, false, false );
           //table.editCellAt( dataControl.getActiveAreas( ).size( ) - 1, 0 );
          //  if( table.isEditing( ) ) {
          //      table.getEditorComponent( ).requestFocusInWindow( );
          //  }
            return true;
        }
        return false;
    }

    @Override
    public boolean redoTool( ) {

        dataControl.getBarriers( ).add( newBarrier );
        dataControl.getBarriersList( ).add( (Barrier) newBarrier.getContent( ) );
        spep.addBarrier( dataControl.getLastBarrier( ) );
        Controller.getInstance( ).getIdentifierSummary( ).addActiveAreaId( newBarrier.getId( ) );
        Controller.getInstance( ).updatePanel( );
        return true;
    }

    @Override
    public boolean undoTool( ) {

        dataControl.deleteElement( newBarrier, false );
        spep.removeElement( newBarrier );
        Controller.getInstance( ).updatePanel( );
        return true;
    }
}