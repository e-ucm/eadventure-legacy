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
