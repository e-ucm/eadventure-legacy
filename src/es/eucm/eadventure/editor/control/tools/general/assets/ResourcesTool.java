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

import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.data.AssetInformation;

/**
 * Abstract class for Resources modification. It contains the common data that
 * tools EditResourceTool, SetResourceTool and DeleteResourceTool will use.
 * 
 * @author Javier
 * 
 */
public abstract class ResourcesTool extends Tool {

    /**
     * Controller
     */
    protected Controller controller;

    /**
     * Contained resources. This field is kept updated all the time.
     */
    protected Resources resources;

    /**
     * Old resources. This is a backup copy that is done when the tool is built
     * (for undo)
     */
    protected Resources oldResources;

    /**
     * The assets information of the resources.
     */
    protected AssetInformation[] assetsInformation;

    /**
     * indicates if the resource block belongs to a NPC, the player or other
     * element
     */
    protected int resourcesType;

    /**
     * The index of the resource to be modified
     */
    protected int index;

    /**
     * Default constructor
     * 
     * @throws CloneNotSupportedException
     */
    public ResourcesTool( Resources resources, AssetInformation[] assetsInformation, int resourcesType, int index ) throws CloneNotSupportedException {

        this.resources = resources;
        this.assetsInformation = assetsInformation;
        this.resourcesType = resourcesType;
        this.controller = Controller.getInstance( );
        this.index = index;
        this.oldResources = (Resources) ( resources.clone( ) );
    }

    @Override
    public boolean undoTool( ) {

        // Restores the resources object with the information stored in oldResoures
        try {
            Resources temp = (Resources) ( resources.clone( ) );
            resources.clearAssets( );
            String[] oldResourceTypes = oldResources.getAssetTypes( );
            for( String type : oldResourceTypes ) {
                resources.addAsset( type, oldResources.getAssetPath( type ) );
            }

            // Update older data
            oldResources.clearAssets( );
            oldResourceTypes = temp.getAssetTypes( );
            for( String type : oldResourceTypes ) {
                oldResources.addAsset( type, temp.getAssetPath( type ) );
            }
            //			controller.reloadPanel();
            return true;
        }
        catch( CloneNotSupportedException e ) {
            e.printStackTrace( );
            return false;
        }
    }

    @Override
    public boolean redoTool( ) {

        return undoTool( );
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

}
