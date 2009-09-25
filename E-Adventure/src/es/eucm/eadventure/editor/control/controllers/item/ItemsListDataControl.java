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
package es.eucm.eadventure.editor.control.controllers.item;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.data.chapter.elements.Item;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.Searchable;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class ItemsListDataControl extends DataControl {

    /**
     * List of items.
     */
    private List<Item> itemsList;

    /**
     * List of item controllers.
     */
    private List<ItemDataControl> itemsDataControlList;

    /**
     * Constructor.
     * 
     * @param itemsList
     *            List of items
     */
    public ItemsListDataControl( List<Item> itemsList ) {

        this.itemsList = itemsList;

        // Create subcontrollers
        itemsDataControlList = new ArrayList<ItemDataControl>( );
        for( Item item : itemsList )
            itemsDataControlList.add( new ItemDataControl( item ) );
    }

    /**
     * Returns the list of item controllers.
     * 
     * @return Item controllers
     */
    public List<ItemDataControl> getItems( ) {

        return itemsDataControlList;
    }

    /**
     * Returns the last item controller from the list.
     * 
     * @return Last item controller
     */
    public ItemDataControl getLastItem( ) {

        return itemsDataControlList.get( itemsDataControlList.size( ) - 1 );
    }

    /**
     * Returns the info of the items contained in the list.
     * 
     * @return Array with the information of the items. It contains the
     *         identifier of each item, and the number of actions
     */
    public String[][] getItemsInfo( ) {

        String[][] itemsInfo = null;

        // Create the list for the items
        itemsInfo = new String[ itemsList.size( ) ][ 2 ];

        // Fill the array with the info
        for( int i = 0; i < itemsList.size( ); i++ ) {
            Item item = itemsList.get( i );
            itemsInfo[i][0] = item.getId( );
            itemsInfo[i][1] = TC.get( "ItemsList.ActionsNumber", String.valueOf( item.getActions( ).size( ) ) );
        }

        return itemsInfo;
    }

    @Override
    public Object getContent( ) {

        return itemsList;
    }

    @Override
    public int[] getAddableElements( ) {

        return new int[] { Controller.ITEM };
    }

    @Override
    public boolean canAddElement( int type ) {

        // It can always add new items
        return type == Controller.ITEM;
    }

    @Override
    public boolean canBeDeleted( ) {

        return false;
    }

    @Override
    public boolean canBeMoved( ) {

        return false;
    }

    @Override
    public boolean canBeRenamed( ) {

        return false;
    }

    @Override
    public boolean addElement( int type, String itemId ) {

        boolean elementAdded = false;

        if( type == Controller.ITEM ) {

            if( itemId == null )
                itemId = controller.showInputDialog( TC.get( "Operation.AddItemTitle" ), TC.get( "Operation.AddItemMessage" ), TC.get( "Operation.AddItemDefaultValue" ) );

            if( itemId != null && controller.isElementIdValid( itemId ) ) {
                Item newItem = new Item( itemId );
                itemsList.add( newItem );
                itemsDataControlList.add( new ItemDataControl( newItem ) );
                controller.getIdentifierSummary( ).addItemId( itemId );
                elementAdded = true;
            }
        }

        return elementAdded;
    }

    @Override
    public boolean duplicateElement( DataControl dataControl ) {

        if( !( dataControl instanceof ItemDataControl ) )
            return false;

        try {
            Item newElement = (Item) ( ( (Item) ( dataControl.getContent( ) ) ).clone( ) );
            String id = newElement.getId( );
            int i = 1;
            do {
                id = newElement.getId( ) + i;
                i++;
            } while( !controller.isElementIdValid( id, false ) );
            newElement.setId( id );
            itemsList.add( newElement );
            itemsDataControlList.add( new ItemDataControl( newElement ) );
            controller.getIdentifierSummary( ).addItemId( id );
            return true;
        }
        catch( CloneNotSupportedException e ) {
            ReportDialog.GenerateErrorReport( e, true, "Could not clone item" );
            return false;
        }
    }

    @Override
    public String getDefaultId( int type ) {

        return TC.get( "Operation.AddItemDefaultValue" );
    }

    @Override
    public boolean deleteElement( DataControl dataControl, boolean askConfirmation ) {

        boolean elementDeleted = false;
        String itemId = ( (ItemDataControl) dataControl ).getId( );
        String references = String.valueOf( controller.countIdentifierReferences( itemId ) );

        // Ask for confirmation
        if( !askConfirmation || controller.showStrictConfirmDialog( TC.get( "Operation.DeleteElementTitle" ), TC.get( "Operation.DeleteElementWarning", new String[] { itemId, references } ) ) ) {
            if( itemsList.remove( dataControl.getContent( ) ) ) {
                itemsDataControlList.remove( dataControl );
                controller.deleteIdentifierReferences( itemId );
                controller.getIdentifierSummary( ).deleteItemId( itemId );
                //controller.dataModified( );
                elementDeleted = true;
            }
        }

        return elementDeleted;
    }

    @Override
    public boolean moveElementUp( DataControl dataControl ) {

        boolean elementMoved = false;
        int elementIndex = itemsList.indexOf( dataControl.getContent( ) );

        if( elementIndex > 0 ) {
            itemsList.add( elementIndex - 1, itemsList.remove( elementIndex ) );
            itemsDataControlList.add( elementIndex - 1, itemsDataControlList.remove( elementIndex ) );
            //controller.dataModified( );
            elementMoved = true;
        }

        return elementMoved;
    }

    @Override
    public boolean moveElementDown( DataControl dataControl ) {

        boolean elementMoved = false;
        int elementIndex = itemsList.indexOf( dataControl.getContent( ) );

        if( elementIndex < itemsList.size( ) - 1 ) {
            itemsList.add( elementIndex + 1, itemsList.remove( elementIndex ) );
            itemsDataControlList.add( elementIndex + 1, itemsDataControlList.remove( elementIndex ) );
            //controller.dataModified( );
            elementMoved = true;
        }

        return elementMoved;
    }

    @Override
    public String renameElement( String name ) {

        return null;
    }

    @Override
    public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {

        // Iterate through each item
        for( ItemDataControl itemDataControl : itemsDataControlList )
            itemDataControl.updateVarFlagSummary( varFlagSummary );
    }

    @Override
    public boolean isValid( String currentPath, List<String> incidences ) {

        boolean valid = true;

        // Update the current path
        currentPath += " >> " + TC.getElement( Controller.ITEMS_LIST );

        // Iterate through the items
        for( ItemDataControl itemDataControl : itemsDataControlList ) {
            String itemPath = currentPath + " >> " + itemDataControl.getId( );
            valid &= itemDataControl.isValid( itemPath, incidences );
        }

        return valid;
    }

    @Override
    public int countAssetReferences( String assetPath ) {

        int count = 0;

        // Iterate through each item
        for( ItemDataControl itemDataControl : itemsDataControlList )
            count += itemDataControl.countAssetReferences( assetPath );

        return count;
    }

    @Override
    public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {

        // Iterate through each item
        for( ItemDataControl itemDataControl : itemsDataControlList )
            itemDataControl.getAssetReferences( assetPaths, assetTypes );
    }

    @Override
    public void deleteAssetReferences( String assetPath ) {

        // Iterate through each item
        for( ItemDataControl itemDataControl : itemsDataControlList )
            itemDataControl.deleteAssetReferences( assetPath );
    }

    @Override
    public int countIdentifierReferences( String id ) {

        int count = 0;

        // Iterate through each item
        for( ItemDataControl itemDataControl : itemsDataControlList )
            count += itemDataControl.countIdentifierReferences( id );

        return count;
    }

    @Override
    public void replaceIdentifierReferences( String oldId, String newId ) {

        // Iterate through each item
        for( ItemDataControl itemDataControl : itemsDataControlList )
            itemDataControl.replaceIdentifierReferences( oldId, newId );
    }

    @Override
    public void deleteIdentifierReferences( String id ) {

        // Spread the call to every item
        for( ItemDataControl itemDataControl : itemsDataControlList )
            itemDataControl.deleteIdentifierReferences( id );
    }

    @Override
    public boolean canBeDuplicated( ) {

        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void recursiveSearch( ) {

        for( DataControl dc : this.itemsDataControlList )
            dc.recursiveSearch( );
    }

    @Override
    public List<Searchable> getPathToDataControl( Searchable dataControl ) {

        return getPathFromChild( dataControl, itemsDataControlList );
    }

}
