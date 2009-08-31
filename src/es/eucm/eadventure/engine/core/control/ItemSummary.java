/**
 * <e-Adventure> is an <e-UCM> research project.
 * <e-UCM>, Department of Software Engineering and Artificial Intelligence.
 * Faculty of Informatics, Complutense University of Madrid (Spain).
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J.
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009
 * Web-site: http://e-adventure.e-ucm.es
 */

/*
    Copyright (C) 2004-2009 <e-UCM> research group

    This file is part of <e-Adventure> project, an educational game & game-like 
    simulation authoring tool, availabe at http://e-adventure.e-ucm.es. 

    <e-Adventure> is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    <e-Adventure> is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with <e-Adventure>; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

*/
package es.eucm.eadventure.engine.core.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.elements.Item;

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
