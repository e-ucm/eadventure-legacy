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
package es.eucm.eadventure.editor.control.controllers.item;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.elements.Item;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.DataControlWithResources;
import es.eucm.eadventure.editor.control.controllers.Searchable;
import es.eucm.eadventure.editor.control.controllers.general.ActionsListDataControl;
import es.eucm.eadventure.editor.control.controllers.general.ResourcesDataControl;
import es.eucm.eadventure.editor.control.tools.general.assets.AddResourcesBlockTool;
import es.eucm.eadventure.editor.control.tools.general.commontext.ChangeDescriptionTool;
import es.eucm.eadventure.editor.control.tools.general.commontext.ChangeDetailedDescriptionTool;
import es.eucm.eadventure.editor.control.tools.general.commontext.ChangeDocumentationTool;
import es.eucm.eadventure.editor.control.tools.general.commontext.ChangeNameTool;
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

    /**
     * Returns the name of the item.
     * 
     * @return Item's name
     */
    public String getName( ) {

        return item.getName( );
    }

    /**
     * Returns the brief description of the item.
     * 
     * @return Item's description
     */
    public String getBriefDescription( ) {

        return item.getDescription( );
    }

    /**
     * Returns the detailed description of the item.
     * 
     * @return Item's detailed description
     */
    public String getDetailedDescription( ) {

        return item.getDetailedDescription( );
    }

    /**
     * Sets the new documentation of the item.
     * 
     * @param documentation
     *            Documentation of the item
     */
    public void setDocumentation( String documentation ) {

        controller.addTool( new ChangeDocumentationTool( item, documentation ) );
    }

    /**
     * Sets the new name of the item.
     * 
     * @param name
     *            Name of the item
     */
    public void setName( String name ) {

        controller.addTool( new ChangeNameTool( item, name ) );
    }

    /**
     * Sets the new brief description of the item.
     * 
     * @param description
     *            Description of the item
     */
    public void setBriefDescription( String description ) {

        controller.addTool( new ChangeDescriptionTool( item, description ) );
    }

    /**
     * Sets the new detailed description of the item.
     * 
     * @param detailedDescription
     *            Detailed description of the item
     */
    public void setDetailedDescription( String detailedDescription ) {

        controller.addTool( new ChangeDetailedDescriptionTool( item, detailedDescription ) );
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

        return count;
    }

    @Override
    public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {

        // Iterate through the resources
        for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
            resourcesDataControl.getAssetReferences( assetPaths, assetTypes );

        // Add the references in the actions
        actionsListDataControl.getAssetReferences( assetPaths, assetTypes );
    }

    @Override
    public void deleteAssetReferences( String assetPath ) {

        // Iterate through the resources
        for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
            resourcesDataControl.deleteAssetReferences( assetPath );

        // Delete the references from the actions
        actionsListDataControl.deleteAssetReferences( assetPath );
    }

    @Override
    public int countIdentifierReferences( String id ) {

        return actionsListDataControl.countIdentifierReferences( id );
    }

    @Override
    public void replaceIdentifierReferences( String oldId, String newId ) {

        actionsListDataControl.replaceIdentifierReferences( oldId, newId );
    }

    @Override
    public void deleteIdentifierReferences( String id ) {

        actionsListDataControl.deleteIdentifierReferences( id );
    }

    @Override
    public boolean canBeDuplicated( ) {

        return true;
    }

    @Override
    public void recursiveSearch( ) {

        check( this.getBriefDescription( ), TC.get( "Search.BriefDescription" ) );
        check( this.getDetailedDescription( ), TC.get( "Search.DetailedDescription" ) );
        check( this.getDocumentation( ), TC.get( "Search.Documentation" ) );
        check( this.getId( ), "ID" );
        check( this.getName( ), TC.get( "Search.Name" ) );
        check( this.getPreviewImage( ), TC.get( "Search.PreviewImage" ) );
        this.getActionsList( ).recursiveSearch( );
    }

    @Override
    public List<Searchable> getPathToDataControl( Searchable dataControl ) {

        List<Searchable> path = getPathFromChild( dataControl, resourcesDataControlList );
        if( path != null )
            return path;
        path = getPathFromChild( dataControl, actionsListDataControl );
        return path;
    }

}
