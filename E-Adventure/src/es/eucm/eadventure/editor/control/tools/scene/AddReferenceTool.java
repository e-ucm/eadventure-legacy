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

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.scene.ElementContainer;
import es.eucm.eadventure.editor.control.controllers.scene.ReferencesListDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.gui.elementpanels.scene.ElementReferencesTable;
import es.eucm.eadventure.editor.gui.otherpanels.ScenePreviewEditionPanel;

public class AddReferenceTool extends Tool {

    private ReferencesListDataControl referencesListDataControl;

    private int type;

    private ScenePreviewEditionPanel spep;

    private ElementReferencesTable table;

    private ElementContainer newElement;

    public AddReferenceTool( ReferencesListDataControl referencesListDataControl, int type, ScenePreviewEditionPanel spep, ElementReferencesTable table ) {

        this.referencesListDataControl = referencesListDataControl;
        this.type = type;
        this.spep = spep;
        this.table = table;
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

        int category;
        if( referencesListDataControl.addElement( type, null ) ) {
            category = ReferencesListDataControl.transformType( type );
            if( category != 0 && referencesListDataControl.getLastElementContainer( ) != null ) {
                // it is not necessary to check if it is an player element container because never a player will be added
                newElement = referencesListDataControl.getLastElementContainer( );
                spep.addElement( category, newElement.getErdc( ) );
                spep.setSelectedElement( newElement.getErdc( ) );
                spep.repaint( );
                int layer = newElement.getErdc( ).getElementReference( ).getLayer( );
                table.getSelectionModel( ).setSelectionInterval( layer, layer );
                table.updateUI( );
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean redoTool( ) {

        referencesListDataControl.addElement( newElement );
        int category = ReferencesListDataControl.transformType( type );
        spep.addElement( category, newElement.getErdc( ) );
        Controller.getInstance( ).updatePanel( );
        return true;
    }

    @Override
    public boolean undoTool( ) {

        referencesListDataControl.deleteElement( newElement.getErdc( ), false );
        int category = ReferencesListDataControl.transformType( type );
        spep.removeElement( category, newElement.getErdc( ) );
        Controller.getInstance( ).updatePanel( );
        return true;
    }
}
