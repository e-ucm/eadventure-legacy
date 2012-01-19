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
package es.eucm.eadventure.common.data.chapter.conditions;

/**
 * A condition based on a reference to a global state
 * 
 * @author Javier
 * 
 */
public class FlagCondition extends Condition {

    /**
     * Constant for active flag.
     */
    public static final int FLAG_ACTIVE = 0;

    /**
     * Constant for inactive flag.
     */
    public static final int FLAG_INACTIVE = 1;

    /**
     * Constructor
     * 
     * @param flagVar
     * @param state
     */
    public FlagCondition( String id ) {

        super( Condition.FLAG_CONDITION, id, FlagCondition.FLAG_ACTIVE );
    }

    /**
     * Constructor
     * 
     * @param flagVar
     * @param state
     */
    public FlagCondition( String id, int state ) {

        super( Condition.FLAG_CONDITION, id, state );
        if( state != FLAG_ACTIVE && state != FLAG_INACTIVE ) {
            state = FLAG_ACTIVE;
        }
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        FlagCondition gsr = (FlagCondition) super.clone( );
        return gsr;
    }

    /**
     * Returns true if the state is FLAG_ACTIVE
     * 
     * @return
     */
    public boolean isActiveState( ) {

        return state == FLAG_ACTIVE;
    }

    /**
     * Returns true if the state is FLAG_INACTIVE
     * 
     * @return
     */
    public boolean isInactiveState( ) {

        return state == FLAG_INACTIVE;
    }

}
