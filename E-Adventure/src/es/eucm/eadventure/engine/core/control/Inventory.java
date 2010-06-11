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
     * 
     * @return Number of items stored in the inventory
     */
    public int getItemCount( ) {

        return storedItems.size( );
    }

    /**
     * Returns a stored item
     * 
     * @param index
     *            Index of the wanted item
     * @return Item in the given position
     */
    public FunctionalItem getItem( int index ) {

        return storedItems.get( index );
    }

    /**
     * Stores an item in the inventory
     * 
     * @param item
     *            Item to store
     */
    public void storeItem( FunctionalItem item ) {

        storedItems.add( item );
    }

    /**
     * Deletes an item from the inventory
     * 
     * @param itemId
     *            Id of the item to be deleted
     */
    public void consumeItem( String itemId ) {

        FunctionalItem item = null;

        for( FunctionalItem currentItem : storedItems )
            if( currentItem.getItem( ).getId( ).equals( itemId ) )
                item = currentItem;

        storedItems.remove( item );
    }
}
