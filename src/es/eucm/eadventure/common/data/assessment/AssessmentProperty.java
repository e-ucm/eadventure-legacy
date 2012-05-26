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
     * If this property dependent on var/flag value, this attribute store its name 
     */
    private String varName;
    
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
        this.varName = null;

    }

    /**
     * Constructor for properties dependent on in-game values
     * 
     * @param id
     * @param value
     * @param varName
     */
    public AssessmentProperty( String id, String value, String varName ) {

        this.id = id;
        this.value = value;
        this.varName = varName;

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
        ap.varName = (varName != null ? new String (varName) : null);
        return ap;
    }

    
    public String getVarName( ) {
    
        return varName;
    }

    
    public void setVarName( String varName ) {
    
        this.varName = varName;
    }
}
