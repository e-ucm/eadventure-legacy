package es.eucm.eadventure.engine.core.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapterdata.elements.Item;

/**
 * Summary of the items in the adventure 
 */
public class ItemSummary implements Serializable {

    /**
     * Required by Serializable
     */
    private static final long serialVersionUID = 1L;

    /**
     * List of normal items
     */
    private ArrayList<String> normalItems;

    /**
     * List of grabbed items
     */
    private ArrayList<String> grabbedItems;

    /**
     * List of consumed items
     */
    private ArrayList<String> consumedItems;

    /**
     * Default constructor
     * @param items List of items, which will be stored as normal items
     */
    public ItemSummary( List<Item> items ) {
        normalItems = new ArrayList<String>( );
        grabbedItems = new ArrayList<String>( );
        consumedItems = new ArrayList<String>( );

        for( Item item : items ) {
            normalItems.add( item.getId( ) );
        }
    }
    
    /**
     * Returns the list of normal items
     * @return List of normal items
     */
    public ArrayList<String> getNormalItems( ) {
        return normalItems;
    }
    
    /**
     * Returns the list of grabbed items
     * @return List of grabbed items
     */
    public ArrayList<String> getGrabbedItems( ) {
        return grabbedItems;
    }

    /**
     * Returns the list of consumed items
     * @return List of consumed items
     */
    public ArrayList<String> getConsumedItems( ) {
        return consumedItems;
    }

    /**
     * Returns if an item is normal
     * @param itemId Id of the item
     * @return True if the item is normal, false otherwise
     */
    public boolean isItemNormal( String itemId ) {
        return normalItems.contains( itemId );
    }

    /**
     * Returns if an item has been grabbed
     * @param itemId Id of the item
     * @return True if the item has been grabbed, false otherwise
     */
    public boolean isItemGrabbed( String itemId ) {
        return grabbedItems.contains( itemId );
    }

    /**
     * Returns if an item has been consumed
     * @param itemId Id of the item
     * @return True if the item has been consumed, false otherwise
     */
    public boolean isItemConsumed( String itemId ) {
        return consumedItems.contains( itemId );
    }

    /**
     * Mark an item as grabbed, that item must be marked as normal
     * @param itemId Id of the item
     */
    public void grabItem( String itemId ) {
        if( normalItems.contains( itemId ) ) {
            normalItems.remove( itemId );
            grabbedItems.add( itemId );
        }
    }
    
    /**
     * Mark an item as grabbed, that item must be marked as normal
     * @param itemId Id of the item
     */
    public void regenerateItem( String itemId ) {
        if( consumedItems.contains( itemId ) ) {
            consumedItems.remove( itemId );
            normalItems.add( itemId );
        }
    }

    /**
     * Mark an item as consumed, that item must be marked as grabbed
     * @param itemId Id of the item
     */
    public void consumeItem( String itemId ) {
        if( grabbedItems.contains( itemId ) ) {
            grabbedItems.remove( itemId );
            consumedItems.add( itemId );
        }
    }
}
