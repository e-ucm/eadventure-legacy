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
