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
