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
package es.eucm.eadventure.common.data.adaptation;

import java.io.Serializable;

/**
 * LMS property, stores an id and a value, for adaptation purposes
 */
public class UOLProperty implements Serializable, Cloneable {

    /**
     * Required
     */
    private static final long serialVersionUID = 1L;

    /**
     * Id of the property
     */
    private String id;

    /**
     * Value of the property
     */
    private String value;

    /**
     * The type of the comparison operation between LMS value of "id" and
     * attribute "value"
     */
    private String operation;

    /**
     * Default constructor
     * 
     * @param id
     *            Id of the property
     * @param value
     *            Value of the property
     * @param operation
     *            The comparison operation between LMS value of Id and Value
     */
    public UOLProperty( String id, String value, String operation ) {

        this.id = id;
        this.value = value;
        this.operation = operation;
    }

    /**
     * Returns the id of the property
     * 
     * @return Id of the property
     */
    public String getId( ) {

        return id;
    }

    /**
     * Returns the value of the property
     * 
     * @return Value of the property
     */
    public String getValue( ) {

        return value;
    }

    public void setId( String id ) {

        this.id = id;
    }

    public void setValue( String value ) {

        this.value = value;
    }

    /**
     * @return the operation
     */
    public String getOperation( ) {

        return operation;
    }

    /**
     * @param operation
     *            the operation to set
     */
    public void setOperation( String operation ) {

        this.operation = operation;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        UOLProperty uolp = (UOLProperty) super.clone( );
        uolp.id = ( id != null ? new String( id ) : null );
        uolp.value = ( value != null ? new String( value ) : null );
        uolp.operation = ( operation != null ? new String( operation ) : null );
        ;
        return uolp;
    }

}
