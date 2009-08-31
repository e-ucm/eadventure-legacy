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
