package es.eucm.eadventure.engine.core.control;

import java.util.ArrayList;

import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalItem;

/**
 * This class stores all the items of the inventory
 */
public class Inventory {

    /**
     * List of items stored in the inventory
     */
    private ArrayList<FunctionalItem> storedItems;

    /**
     * Empty constructor
     */
    public Inventory( ) {
        storedItems = new ArrayList<FunctionalItem>( );
    }

    /**
     * Returns the number of items
     * @return Number of items stored in the inventory
     */
    public int getItemCount( ) {
        return storedItems.size( );
    }

    /**
     * Returns a stored item
     * @param index Index of the wanted item
     * @return Item in the given position
     */
    public FunctionalItem getItem( int index ) {
        return storedItems.get( index );
    }

    /**
     * Stores an item in the inventory
     * @param item Item to store
     */
    public void storeItem( FunctionalItem item ) {
        storedItems.add( item );
    }

    /**
     * Deletes an item from the inventory
     * @param itemId Id of the item to be deleted
     */
    public void consumeItem( String itemId ) {
        FunctionalItem item = null;

        for( FunctionalItem currentItem : storedItems )
            if( currentItem.getItem( ).getId( ).equals( itemId ) )
                item = currentItem;

        storedItems.remove( item );
    }
}
