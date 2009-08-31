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
package es.eucm.eadventure.editor.control.tools.general;

import es.eucm.eadventure.editor.control.controllers.scene.ElementContainer;
import es.eucm.eadventure.editor.control.controllers.scene.ReferencesListDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.gui.elementpanels.scene.ElementReferencesTable;

/**
 * That tool edit the changes in player layer because of the fact of player's
 * movements in ScenePanel table
 * 
 */
public class MovePlayerLayerInTableTool extends Tool {

    private ElementReferencesTable table;

    private ReferencesListDataControl referencesListDataControl;

    private boolean moveUp;

    private ElementContainer element;

    private int selectedRow;

    public MovePlayerLayerInTableTool( ReferencesListDataControl rldc, ElementReferencesTable table2, boolean isMoveUp ) {

        this.referencesListDataControl = rldc;
        this.table = table2;
        this.moveUp = isMoveUp;
        selectedRow = table.getSelectedRow( );
        element = referencesListDataControl.getAllReferencesDataControl( ).get( selectedRow );
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

        action( moveUp );
        return true;
    }

    private void action( boolean up ) {

        // do moveDown 
        if( !up && referencesListDataControl.moveElementDown( element.getErdc( ) ) ) {
            table.getSelectionModel( ).setSelectionInterval( selectedRow + 1, selectedRow + 1 );
            table.updateUI( );
        }
        //do moveUp
        if( up && referencesListDataControl.moveElementUp( element.getErdc( ) ) ) {
            table.getSelectionModel( ).setSelectionInterval( selectedRow - 1, selectedRow - 1 );
            table.updateUI( );
        }
        moveUp = !moveUp;
    }

    @Override
    public boolean redoTool( ) {

        action( moveUp );
        return true;
    }

    @Override
    public boolean undoTool( ) {

        action( moveUp );
        return true;
    }

}
