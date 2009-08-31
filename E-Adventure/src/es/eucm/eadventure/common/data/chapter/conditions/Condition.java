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

import es.eucm.eadventure.common.data.HasId;

/**
 * This class manages a condition in eAdventure
 */
public abstract class Condition implements Cloneable, HasId {

    /**
     * Constant for state not set.
     */
    public static final int NO_STATE = -1;

    /**
     * Condition based on var
     */
    public static final int VAR_CONDITION = 0;

    /**
     * Condition based on flag
     */
    public static final int FLAG_CONDITION = 1;

    /**
     * Condition based on condition group
     */
    public static final int GLOBAL_STATE_CONDITION = 2;

    /**
     * Name of the flag to be checked
     */
    protected String id;

    /**
     * Stores if the flag must be active or inactive
     */
    protected int state;

    /**
     * Type of the condition ({@link #VAR_CONDITION}, {@link #FLAG_CONDITION}
     * or {@link #GLOBAL_STATE_CONDITION}
     */
    protected int type;

    /**
     * Creates a new condition
     * 
     * @param flagVar
     *            Flag/Var of the condition
     * @param state
     *            Determines the state: {@link #FLAG_ACTIVE}
     *            {@link #FLAG_INACTIVE} {@link #NO_STATE} {@link #VAR_EQUALS}
     *            {@link #VAR_GREATER_EQUALS_THAN} {@link #VAR_GREATER_THAN}
     *            {@link #VAR_LESS_EQUALS_THAN} {@link #VAR_LESS_THAN}
     */
    public Condition( int type, String flagVar, int state ) {

        this.type = type;
        this.id = flagVar;
        this.state = state;
    }

    /**
     * Returns the flag/Var of the condition
     * 
     * @return The flag/Var of the condition
     */
    public String getId( ) {

        return id;
    }

    /**
     * Returns whether the flag/Var must be activated or deactivated for this
     * condition to be satisfied
     * 
     * @return the state {@link #FLAG_ACTIVE} {@link #FLAG_INACTIVE}
     *         {@link #NO_STATE} {@link #VAR_EQUALS}
     *         {@link #VAR_GREATER_EQUALS_THAN} {@link #VAR_GREATER_THAN}
     *         {@link #VAR_LESS_EQUALS_THAN} {@link #VAR_LESS_THAN}
     */
    public Integer getState( ) {

        return state;
    }

    /**
     * Sets a new flag for this condition
     * 
     * @param flagVar
     *            New condition flag/Var
     */
    public void setId( String flagVar ) {

        this.id = flagVar;
    }

    /**
     * Sets a new active or inactive state for the flag/Var.
     * 
     * @param state
     *            New state {@link #FLAG_ACTIVE} {@link #FLAG_INACTIVE}
     *            {@link #NO_STATE} {@link #VAR_EQUALS}
     *            {@link #VAR_GREATER_EQUALS_THAN} {@link #VAR_GREATER_THAN}
     *            {@link #VAR_LESS_EQUALS_THAN} {@link #VAR_LESS_THAN}
     */
    public void setState( Integer state ) {

        this.state = state;
    }

    /**
     * @return the type
     */
    public int getType( ) {

        return type;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        Condition c = (Condition) super.clone( );
        c.id = ( id != null ? new String( id ) : null );
        c.state = state;
        c.type = type;
        return c;
    }
}
