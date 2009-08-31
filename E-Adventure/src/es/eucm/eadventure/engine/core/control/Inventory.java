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
