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
package es.eucm.eadventure.editor.control.tools.scene;

import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.data.chapter.elements.ActiveArea;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.scene.ActiveAreaDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ActiveAreasListDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.gui.elementpanels.scene.ActiveAreasTable;
import es.eucm.eadventure.editor.gui.otherpanels.IrregularAreaEditionPanel;

public class DuplicateActiveAreaTool extends Tool {

    private ActiveAreasListDataControl dataControl;

    private IrregularAreaEditionPanel iaep;

    private ActiveAreasTable table;

    private ActiveAreaDataControl newActiveArea;

    public DuplicateActiveAreaTool( ActiveAreasListDataControl dataControl2, IrregularAreaEditionPanel iaep2, ActiveAreasTable table2 ) {

        this.dataControl = dataControl2;
        this.table = table2;
        this.iaep = iaep2;
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

        if( dataControl.duplicateElement( dataControl.getActiveAreas( ).get( table.getSelectedRow( ) ) ) ) {
            newActiveArea = dataControl.getLastActiveArea( );
            iaep.getScenePreviewEditionPanel( ).addActiveArea( dataControl.getLastActiveArea( ) );
            iaep.repaint( );
            ( (AbstractTableModel) table.getModel( ) ).fireTableDataChanged( );
            table.changeSelection( dataControl.getActiveAreas( ).size( ) - 1, dataControl.getActiveAreas( ).size( ) - 1, false, false );
            table.editCellAt( dataControl.getActiveAreas( ).size( ) - 1, 0 );
            if( table.isEditing( ) ) {
                table.getEditorComponent( ).requestFocusInWindow( );
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean redoTool( ) {

        dataControl.getActiveAreas( ).add( newActiveArea );
        dataControl.getActiveAreasList( ).add( (ActiveArea) newActiveArea.getContent( ) );
        iaep.getScenePreviewEditionPanel( ).addActiveArea( dataControl.getLastActiveArea( ) );
        Controller.getInstance( ).getIdentifierSummary( ).addActiveAreaId( newActiveArea.getId( ) );
        Controller.getInstance( ).updatePanel( );
        return true;
    }

    @Override
    public boolean undoTool( ) {

        dataControl.deleteElement( newActiveArea, false );
        iaep.getScenePreviewEditionPanel( ).removeElement( newActiveArea );
        Controller.getInstance( ).updatePanel( );
        return true;
    }
}
