/*******************************************************************************
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the e-UCM
 *          research group.
 *   
 *    Copyright 2005-2012 e-UCM research group.
 *  
 *     e-UCM is a research group of the Department of Software Engineering
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
 * This file is part of eAdventure, version 1.5.
 * 
 *   You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with Adventure.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
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
     * Constant for not-equals var.
     */
    public static final int VAR_NOT_EQUALS = 7;

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
