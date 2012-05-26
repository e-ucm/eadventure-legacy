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

import java.util.ArrayList;
import java.util.List;

public class AssessmentEffect implements Cloneable {

    /**
     * Text of the effect of the rule, if present (null if not)
     */
    protected String text;

    /**
     * List of properties to be set
     */
    protected List<AssessmentProperty> properties;

    public AssessmentEffect( ) {

        text = null;
        properties = new ArrayList<AssessmentProperty>( );
    }

    /**
     * Sets the text of the rule
     * 
     * @param text
     *            Text of the rule
     */
    public void setText( String text ) {

        this.text = text;
    }

    /**
     * Adds a new assessment property
     * 
     * @param property
     *            Assessment property to be added
     */
    public void addProperty( AssessmentProperty property ) {

        properties.add( property );
    }

    /**
     * Returns the rule's text (if present)
     * 
     * @return Text of the rule if present, null otherwise
     */
    public String getText( ) {

        return text;
    }

    public List<AssessmentProperty> getAssessmentProperties( ) {

        return properties;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        AssessmentEffect ae = (AssessmentEffect) super.clone( );
        if( properties != null ) {
            ae.properties = new ArrayList<AssessmentProperty>( );
            for( AssessmentProperty ap : properties )
                ae.properties.add( (AssessmentProperty) ap.clone( ) );
        }
        ae.text = ( text != null ? new String( text ) : null );
        return ae;

    }

}
