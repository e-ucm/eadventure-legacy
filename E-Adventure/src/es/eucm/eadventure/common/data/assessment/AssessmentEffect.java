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
