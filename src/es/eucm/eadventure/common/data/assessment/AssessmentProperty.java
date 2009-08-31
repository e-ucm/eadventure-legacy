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
package es.eucm.eadventure.common.data.assessment;

import java.io.Serializable;

import es.eucm.eadventure.common.data.HasId;

/**
 * Assessment property, stores an id and a value
 */
public class AssessmentProperty implements Serializable, Cloneable, HasId {

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
     * Default constructor
     * 
     * @param id
     *            Id of the property
     * @param value
     *            Value of the property
     */
    public AssessmentProperty( String id, String value ) {

        this.id = id;
        this.value = value;

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

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        AssessmentProperty ap = (AssessmentProperty) super.clone( );
        ap.id = ( id != null ? new String( id ) : null );
        ap.value = ( value != null ? new String( value ) : null );
        return ap;
    }
}
