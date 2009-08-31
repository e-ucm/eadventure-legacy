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
package es.eucm.eadventure.common.data.chapter.conditions;

import java.util.ArrayList;
import java.util.List;

/**
 * This class holds a list of conditions
 */
public class Conditions implements Cloneable {

    /**
     * The list of conditions
     */
    private List<List<Condition>> conditionsList;

    /**
     * Create a new Conditions
     */
    public Conditions( ) {

        conditionsList = new ArrayList<List<Condition>>( );
    }

    /**
     * Adds a new condition to the list
     * 
     * @param condition
     *            the condition to add
     */
    public void add( int index, Condition condition ) {

        List<Condition> newBlock = new ArrayList<Condition>( );
        newBlock.add( condition );
        conditionsList.add( index, newBlock );
    }

    /**
     * Adds a new condition to the list
     * 
     * @param condition
     *            the condition to add
     */
    public void add( Condition condition ) {

        List<Condition> newBlock = new ArrayList<Condition>( );
        newBlock.add( condition );
        conditionsList.add( newBlock );
    }

    /**
     * Adds a list of conditions, such that at least one of these must be ok
     * 
     * @param conditions
     *            the conditions to add
     */
    public void add( Conditions conditions ) {

        conditionsList.add( conditions.getSimpleConditions( ) );
    }

    /**
     * Inserts a list of conditions in the given position
     * 
     * @param conditions
     *            the conditions to add
     * @param index
     *            the index where conditions must be inserted
     */
    public void add( int index, Conditions conditions ) {

        conditionsList.add( index, conditions.getSimpleConditions( ) );
    }

    /**
     * Inserts a list of conditions in the given position
     * 
     * @param conditions
     *            the conditions to add
     * @param index
     *            the index where conditions must be inserted
     */
    public void add( int index, List<Condition> conditions ) {

        conditionsList.add( index, conditions );
    }

    /**
     * Inserts a list of conditions in the given position
     * 
     * @param conditions
     *            the conditions to add
     * @param index
     *            the index where conditions must be inserted
     */
    public void add( List<Condition> conditions ) {

        conditionsList.add( conditions );
    }

    /**
     * Returns whether the conditions block is empty or not.
     * 
     * @return True if the block has no conditions, false otherwise
     */
    public boolean isEmpty( ) {

        return conditionsList.isEmpty( );
    }

    /**
     * Deletes the given either conditions block.
     * 
     * @param index
     *            Index of the either conditions block
     */
    public List<Condition> delete( int index ) {

        return conditionsList.remove( index );
    }

    /**
     * Returns a list with all the simple conditions of the block. All these
     * conditions must be evaluated with AND.
     * 
     * @return List of conditions
     */
    public List<Condition> getSimpleConditions( ) {

        List<Condition> conditions = new ArrayList<Condition>( );
        for( List<Condition> wrapper : conditionsList ) {
            if( wrapper.size( ) == 1 ) {
                conditions.add( wrapper.get( 0 ) );
            }
        }
        return conditions;
    }

    /**
     * Returns a list with all the either condition blocks. This method is only
     * held for past compatibility
     * 
     * @return List of conditions
     */
    private List<Conditions> getEitherConditions( ) {

        List<Conditions> conditions = new ArrayList<Conditions>( );
        for( List<Condition> wrapper : conditionsList ) {
            if( wrapper.size( ) > 1 ) {
                Conditions eitherBlock = new Conditions( );
                for( Condition condition : wrapper ) {
                    eitherBlock.add( condition );
                }
                conditions.add( eitherBlock );
            }
        }
        return conditions;
    }

    /**
     * @return the conditionsList
     */
    public List<List<Condition>> getConditionsList( ) {

        return conditionsList;
    }

    /**
     * Return all global state ids for this controller
     * 
     * @return
     */
    public List<String> getGloblaStateIds( ) {

        List<String> conditions = new ArrayList<String>( );
        for( List<Condition> wrapper : conditionsList ) {
            for( Condition condition : wrapper ) {
                if( condition.getType( ) == Condition.GLOBAL_STATE_CONDITION )
                    conditions.add( condition.getId( ) );
            }
        }
        return conditions;
    }

    /**
     * Returns the number of either conditions blocks present.
     * 
     * @return Count of either conditions blocks
     */
    public int getEitherConditionsBlockCount( ) {

        return getEitherConditions( ).size( );
    }

    /**
     * Returns the either block of conditions specified.
     * 
     * @param index
     *            Index of the either block of conditions
     * @return List of conditions
     */
    public List<Condition> getEitherConditions( int index ) {

        return getEitherConditions( ).get( index ).getSimpleConditions( );
    }

    /**
     * Returns the either block of conditions specified.
     * 
     * @param index
     *            Index of the either block of conditions
     * @return List of conditions
     */
    public Conditions getEitherBlock( int index ) {

        return getEitherConditions( ).get( index );
    }

    public List<Condition> get( int index ) {

        if( index >= 0 && index < this.conditionsList.size( ) ) {
            return conditionsList.get( index );
        }
        return null;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @Override
    public Object clone( ) throws CloneNotSupportedException {

        Conditions clone = (Conditions) super.clone( );
        clone.conditionsList = new ArrayList<List<Condition>>( );
        for( List<Condition> wrapper : this.conditionsList ) {
            List<Condition> wrapperClone = new ArrayList<Condition>( );
            clone.add( wrapperClone );
            for( Condition condition : wrapper ) {
                wrapperClone.add( (Condition) condition.clone( ) );
            }
        }
        return clone;
    }

    public int size( ) {

        return conditionsList.size( );
    }

}
