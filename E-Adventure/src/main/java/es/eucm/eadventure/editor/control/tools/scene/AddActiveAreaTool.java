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

import es.eucm.eadventure.common.data.chapter.elements.ActiveArea;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.scene.ActiveAreaDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ActiveAreasListDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.gui.otherpanels.IrregularAreaEditionPanel;

public class AddActiveAreaTool extends Tool {

    private ActiveAreasListDataControl dataControl;

    private IrregularAreaEditionPanel iaep;

    private String id;

    private ActiveAreaDataControl newActiveArea;

    public AddActiveAreaTool( ActiveAreasListDataControl dataControl2, String id, IrregularAreaEditionPanel iaep ) {

        this.dataControl = dataControl2;
        this.id = id;
        this.iaep = iaep;
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

        if( dataControl.addElement( dataControl.getAddableElements( )[0], id ) ) {
            this.newActiveArea = dataControl.getLastActiveArea( );
            iaep.getScenePreviewEditionPanel( ).addActiveArea( dataControl.getLastActiveArea( ) );
            iaep.repaint( );
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
