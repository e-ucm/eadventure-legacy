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
 * Specific class for a Var-based Condition
 * 
 * @author Javier
 * 
 */
public class VarCondition extends Condition {

    /**
     * Constant for greater-than var.
     */
    public static final int VAR_GREATER_THAN = 2;

    /**
     * Constant for greater-than or equals var.
     */
    public static final int VAR_GREATER_EQUALS_THAN = 3;

    /**
     * Constant for equals var.
     */
    public static final int VAR_EQUALS = 4;

    /**
     * Constant for less than or equals var.
     */
    public static final int VAR_LESS_EQUALS_THAN = 5;

    /**
     * Constant for less-than var.
     */
    public static final int VAR_LESS_THAN = 6;

    /**
     * MIN VALUE
     */
    public static final int MIN_VALUE = 0;

    /**
     * MAX VALUE
     */
    public static final int MAX_VALUE = Integer.MAX_VALUE;

    /**
     * The value of the var-condition
     */
    private int value;

    /**
     * Constructor
     * 
     * @param flagVar
     * @param state
     */

    public VarCondition( String flagVar, int state, int value ) {

        super( VAR_CONDITION, flagVar, state );
        this.value = value;
    }

    /**
     * @return the value
     */
    public Integer getValue( ) {

        return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue( Integer value ) {

        this.value = value;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        VarCondition vc = (VarCondition) super.clone( );
        vc.id = ( id != null ? new String( id ) : null );
        vc.state = state;
        vc.type = type;
        vc.value = value;
        return vc;
    }

}
