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
package es.eucm.eadventure.editor.control.controllers.general;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.CustomAction;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.Searchable;
import es.eucm.eadventure.editor.control.tools.general.assets.AddResourcesBlockTool;
import es.eucm.eadventure.editor.control.tools.general.assets.DeleteResourcesBlockTool;
import es.eucm.eadventure.editor.control.tools.general.commontext.ChangeNameTool;

public class CustomActionDataControl extends ActionDataControl {

    /**
     * Contained customAction structure
     */
    private CustomAction customAction;

    /**
     * Default constructor
     * 
     * @param action
     *            the custom Action
     */
    public CustomActionDataControl( CustomAction action ) {

        super( action );
        customAction = action;

        this.resourcesList = customAction.getResources( );
        if( this.resourcesList.size( ) == 0 )
            this.resourcesList.add( new Resources( ) );
        selectedResources = 0;

        resourcesDataControlList = new ArrayList<ResourcesDataControl>( );
        for( Resources resources : resourcesList ) {
            resourcesDataControlList.add( new ResourcesDataControl( resources, Controller.ACTION_CUSTOM ) );
        }

    }

    /**
     * @param name
     *            the name to set
     */
    public void setName( String name ) {

        controller.addTool( new ChangeNameTool( customAction, name ) );
    }

    @Override
    public int countAssetReferences( String assetPath ) {

        int count = super.countAssetReferences( assetPath );

        for( ResourcesDataControl resources : resourcesDataControlList )
            count += resources.countAssetReferences( assetPath );

        return count;
    }

    /**
     * @return the value of name
     */
    public String getName( ) {

        return customAction.getName( );
    }

    @Override
    public void recursiveSearch( ) {

        super.recursiveSearch( );
        check( this.getName( ), TC.get( "Search.Name" ) );
    }

    @Override
    public List<Searchable> getPathToDataControl( Searchable dataControl ) {

        return getPathFromChild( dataControl, resourcesDataControlList );
    }
    
    
    @Override
    public boolean addElement( int type, String id ) {
        boolean elementAdded = false;

        if( type == Controller.RESOURCES ) {
            elementAdded = Controller.getInstance( ).addTool( new AddResourcesBlockTool( resourcesList, resourcesDataControlList, Controller.ACTION_CUSTOM, this ) );
        }

        return elementAdded;

    }
    
    @Override
    public boolean deleteElement( DataControl dataControl, boolean askConfirmation ) {

        return controller.addTool( new DeleteResourcesBlockTool( resourcesList, resourcesDataControlList, dataControl, this ) );
    }
    
    

}
