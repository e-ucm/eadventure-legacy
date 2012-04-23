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
package es.eucm.eadventure.editor.control.controllers.item;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.elements.Item;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.DataControlWithResources;
import es.eucm.eadventure.editor.control.controllers.DescriptionsController;
import es.eucm.eadventure.editor.control.controllers.Searchable;
import es.eucm.eadventure.editor.control.controllers.general.ActionsListDataControl;
import es.eucm.eadventure.editor.control.controllers.general.ResourcesDataControl;
import es.eucm.eadventure.editor.control.tools.general.assets.AddResourcesBlockTool;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class ItemDataControl extends DataControlWithResources {

    /**
     * Contained item.
     */
    private Item item;

    /**
     * Actions list controller.
     */
    private ActionsListDataControl actionsListDataControl;
    
    /**
     * Controller for descriptions
     */
    private DescriptionsController descriptionController;

    /**
     * Constructor.
     * 
     * @param item
     *            Contained item
     */
    public ItemDataControl( Item item ) {

        this.item = item;
        this.resourcesList = item.getResources( );

        selectedResources = 0;

        // Add a new resource if the list is empty
        if( resourcesList.size( ) == 0 )
            resourcesList.add( new Resources( ) );

        // Create the subcontrollers
        resourcesDataControlList = new ArrayList<ResourcesDataControl>( );
        for( Resources resources : resourcesList )
            resourcesDataControlList.add( new ResourcesDataControl( resources, Controller.ITEM ) );

        actionsListDataControl = new ActionsListDataControl( item.getActions( ), this );
        
        descriptionController = new DescriptionsController(item.getDescriptions( ));
    }

    /**
     * Returns the actions list controller.
     * 
     * @return Actions list controller
     */
    public ActionsListDataControl getActionsList( ) {

        return actionsListDataControl;
    }

    /**
     * Returns the path to the selected preview image.
     * 
     * @return Path to the image, null if not present
     */
    public String getPreviewImage( ) {

        return resourcesDataControlList.get( selectedResources ).getAssetPath( "image" );
    }

    /**
     * Returns the id of the item.
     * 
     * @return Item's id
     */
    public String getId( ) {

        return item.getId( );
    }

    /**
     * Returns the documentation of the item.
     * 
     * @return Item's documentation
     */
    public String getDocumentation( ) {

        return item.getDocumentation( );
    }


    public void setReturnsWhenDragged(Boolean returnsWhenDragged) {
        item.setReturnsWhenDragged( returnsWhenDragged );
    }
    
    public Boolean isReturnsWhenDragged() {
        return item.isReturnsWhenDragged( );
    }

    @Override
    public Object getContent( ) {

        return item;
    }

    @Override
    public int[] getAddableElements( ) {

        //return new int[] { Controller.RESOURCES };
        return new int[] {};
    }

    @Override
    public boolean canAddElement( int type ) {

        // It can always add new resources
        //return type == Controller.RESOURCES;
        return false;
    }

    @Override
    public boolean canBeDeleted( ) {

        return true;
    }

    @Override
    public boolean canBeMoved( ) {

        return true;
    }

    @Override
    public boolean canBeRenamed( ) {

        return true;
    }

    @Override
    public boolean addElement( int type, String id ) {

        boolean elementAdded = false;

        if( type == Controller.RESOURCES ) {
            elementAdded = Controller.getInstance( ).addTool( new AddResourcesBlockTool( resourcesList, resourcesDataControlList, Controller.ITEM, this ) );
        }

        return elementAdded;
    }

    @Override
    public boolean moveElementUp( DataControl dataControl ) {

        boolean elementMoved = false;
        int elementIndex = resourcesList.indexOf( dataControl.getContent( ) );

        if( elementIndex > 0 ) {
            resourcesList.add( elementIndex - 1, resourcesList.remove( elementIndex ) );
            resourcesDataControlList.add( elementIndex - 1, resourcesDataControlList.remove( elementIndex ) );
            //controller.dataModified( );
            elementMoved = true;
        }

        return elementMoved;
    }

    @Override
    public boolean moveElementDown( DataControl dataControl ) {

        boolean elementMoved = false;
        int elementIndex = resourcesList.indexOf( dataControl.getContent( ) );

        if( elementIndex < resourcesList.size( ) - 1 ) {
            resourcesList.add( elementIndex + 1, resourcesList.remove( elementIndex ) );
            resourcesDataControlList.add( elementIndex + 1, resourcesDataControlList.remove( elementIndex ) );
            //controller.dataModified( );
            elementMoved = true;
        }

        return elementMoved;
    }

    @Override
    public String renameElement( String name ) {

        boolean elementRenamed = false;
        String oldItemId = item.getId( );
        String references = String.valueOf( controller.countIdentifierReferences( oldItemId ) );

        // Ask for confirmation
        if( name != null || controller.showStrictConfirmDialog( TC.get( "Operation.RenameItemTitle" ), TC.get( "Operation.RenameElementWarning", new String[] { oldItemId, references } ) ) ) {

            // Show a dialog asking for the new item id
            String newItemId = name;
            if( name == null )
                newItemId = controller.showInputDialog( TC.get( "Operation.RenameItemTitle" ), TC.get( "Operation.RenameItemMessage" ), oldItemId );

            // If some value was typed and the identifiers are different
            if( newItemId != null && !newItemId.equals( oldItemId ) && controller.isElementIdValid( newItemId ) ) {
                item.setId( newItemId );
                controller.replaceIdentifierReferences( oldItemId, newItemId );
                controller.getIdentifierSummary( ).deleteItemId( oldItemId );
                controller.getIdentifierSummary( ).addItemId( newItemId );
                //controller.dataModified( );
                elementRenamed = true;
            }
        }

        if( elementRenamed )
            return oldItemId;
        return null;
    }

    @Override
    public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {

        actionsListDataControl.updateVarFlagSummary( varFlagSummary );
        
        //1.4
        descriptionController.updateVarFlagSummary( varFlagSummary );
        
        // Iterate through the resources
        for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
            resourcesDataControl.updateVarFlagSummary( varFlagSummary );
    }

    @Override
    public boolean isValid( String currentPath, List<String> incidences ) {

        boolean valid = true;

        // Iterate through the resources
        for( int i = 0; i < resourcesDataControlList.size( ); i++ ) {
            String resourcesPath = currentPath + " >> " + TC.getElement( Controller.RESOURCES ) + " #" + ( i + 1 );
            valid &= resourcesDataControlList.get( i ).isValid( resourcesPath, incidences );
        }

        // Spread the call to the actions
        valid &= actionsListDataControl.isValid( currentPath, incidences );
        
        //1.4
        valid &=descriptionController.isValid( currentPath, incidences );

        return valid;
    }

    @Override
    public int countAssetReferences( String assetPath ) {

        int count = 0;

        // Iterate through the resources
        for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
            count += resourcesDataControl.countAssetReferences( assetPath );

        // Add the references in the actions
        count += actionsListDataControl.countAssetReferences( assetPath );
        
        //v1.4
        count+=this.descriptionController.countAssetReferences( assetPath ); 

        return count;
    }

    @Override
    public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {

        // Iterate through the resources
        for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
            resourcesDataControl.getAssetReferences( assetPaths, assetTypes );

        // Add the references in the actions
        actionsListDataControl.getAssetReferences( assetPaths, assetTypes );
        
        //v1.4
        this.descriptionController.getAssetReferences( assetPaths, assetTypes );
    }

    @Override
    public void deleteAssetReferences( String assetPath ) {

        // Iterate through the resources
        for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
            resourcesDataControl.deleteAssetReferences( assetPath );

        // Delete the references from the actions
        actionsListDataControl.deleteAssetReferences( assetPath );
        
        //1.4
        this.descriptionController.deleteAssetReferences( assetPath );
    }

    @Override
    public int countIdentifierReferences( String id ) {

        return actionsListDataControl.countIdentifierReferences( id ) + this.descriptionController.countIdentifierReferences( id );
    }

    @Override
    public void replaceIdentifierReferences( String oldId, String newId ) {

        actionsListDataControl.replaceIdentifierReferences( oldId, newId );
        
        //1.4
        descriptionController.replaceIdentifierReferences( oldId, newId );
    }

    @Override
    public void deleteIdentifierReferences( String id ) {

        actionsListDataControl.deleteIdentifierReferences( id );
        
        //1.4
        this.descriptionController.deleteIdentifierReferences( id );
    }

    @Override
    public boolean canBeDuplicated( ) {

        return true;
    }

    @Override
    public void recursiveSearch( ) {
        check( this.getDocumentation( ), TC.get( "Search.Documentation" ) );
        check( this.getId( ), "ID" );
        check( this.getPreviewImage( ), TC.get( "Search.PreviewImage" ) );
        this.descriptionController.recursiveSearch( );
        this.getActionsList( ).recursiveSearch( );
        for (ResourcesDataControl r:resourcesDataControlList){
            r.recursiveSearch( );
        }
    }

    @Override
    public List<Searchable> getPathToDataControl( Searchable dataControl ) {

        List<Searchable> path = getPathFromChild( dataControl, resourcesDataControlList );
        if( path != null )
            return path;
        path = getPathFromChild( dataControl, this.descriptionController );
        if( path != null )
            return path;
        path = getPathFromChild( dataControl, actionsListDataControl );
        return path;
    }

    
    public DescriptionsController getDescriptionController( ) {
    
        return descriptionController;
    }

}
