/*******************************************************************************
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the e-UCM
 *          research group.
 *   
 *    Copyright 2005-2012 e-UCM research group.
 *  
 *     e-UCM is a research group of the Department of Software Engineering
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
 * This file is part of eAdventure, version 1.5.
 * 
 *   You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with Adventure.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.control.tools.scene;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.scene.ElementContainer;
import es.eucm.eadventure.editor.control.controllers.scene.ReferencesListDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.gui.elementpanels.scene.ElementReferencesTable;
import es.eucm.eadventure.editor.gui.otherpanels.ScenePreviewEditionPanel;

public class DeleteReferenceTool extends Tool {

    private ReferencesListDataControl referencesListDataControl;

    private ScenePreviewEditionPanel spep;

    private ElementReferencesTable table;

    private ElementContainer element;

    private int selectedRow;

    public DeleteReferenceTool( ReferencesListDataControl referencesListDataControl, ElementReferencesTable table, ScenePreviewEditionPanel spep ) {

        this.table = table;
        this.referencesListDataControl = referencesListDataControl;
        this.spep = spep;
        this.selectedRow = table.getSelectedRow( );
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

        element = referencesListDataControl.getAllReferencesDataControl( ).get( selectedRow );
        if( referencesListDataControl.deleteElement( element.getErdc( ), true ) ) {
            if( !element.isPlayer( ) ) {
                spep.removeElement( ReferencesListDataControl.transformType( element.getErdc( ).getType( ) ), element.getErdc( ) );
                table.clearSelection( );
                table.changeSelection( 0, 1, false, false );
                table.updateUI( );
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean redoTool( ) {

        ElementContainer element = referencesListDataControl.getAllReferencesDataControl( ).get( selectedRow );
        if( referencesListDataControl.deleteElement( element.getErdc( ), true ) ) {
            if( !element.isPlayer( ) ) {
                spep.removeElement( ReferencesListDataControl.transformType( element.getErdc( ).getType( ) ), element.getErdc( ) );
                table.clearSelection( );
                table.changeSelection( 0, 1, false, false );
                table.updateUI( );
            }
            Controller.getInstance( ).updatePanel( );
            return true;
        }
        return false;
    }

    @Override
    public boolean undoTool( ) {

        referencesListDataControl.addElement( element );
        spep.addElement( element.getErdc( ).getType( ), element.getErdc( ) );
        table.clearSelection( );
        table.changeSelection( selectedRow, 1, false, false );
        table.updateUI( );
        Controller.getInstance( ).updatePanel( );
        return true;
    }
}
