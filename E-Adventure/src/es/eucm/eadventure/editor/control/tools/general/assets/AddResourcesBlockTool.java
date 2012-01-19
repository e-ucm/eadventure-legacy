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
package es.eucm.eadventure.editor.control.tools.general.assets;

import java.util.List;

import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControlWithResources;
import es.eucm.eadventure.editor.control.controllers.general.ResourcesDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class AddResourcesBlockTool extends Tool {

    /**
     * Arguments
     */
    private List<Resources> resourcesList;

    private List<ResourcesDataControl> resourcesDataControlList;

    private int resourcesType;

    private DataControlWithResources parent;

    /*
     * Temporal data for undo/redo
     */
    private Resources newResources;

    private ResourcesDataControl newResourcesDataControl;

    public AddResourcesBlockTool( List<Resources> resourcesList, List<ResourcesDataControl> resourcesDataControlList, int resourcesType, DataControlWithResources parent ) {

        this.resourcesList = resourcesList;
        this.resourcesDataControlList = resourcesDataControlList;
        this.resourcesType = resourcesType;
        this.parent = parent;
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

        newResources = new Resources( );
        newResourcesDataControl = new ResourcesDataControl( newResources, resourcesType );
        resourcesList.add( newResources );
        resourcesDataControlList.add( newResourcesDataControl );
        return true;
    }

    @Override
    public boolean redoTool( ) {

        resourcesList.add( newResources );
        resourcesDataControlList.add( newResourcesDataControl );
        parent.setSelectedResources( resourcesList.size( ) - 1 );
        Controller.getInstance( ).updatePanel( );
        return true;
    }

    @Override
    public boolean undoTool( ) {

        boolean undone = resourcesList.remove( newResources ) && resourcesDataControlList.remove( newResourcesDataControl );

        if( undone ) {
            parent.setSelectedResources( resourcesList.size( ) - 1 );
            Controller.getInstance( ).updatePanel( );
        }
        return undone;
    }

}
