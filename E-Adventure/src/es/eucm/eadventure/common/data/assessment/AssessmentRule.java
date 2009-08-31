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

import java.util.List;

import es.eucm.eadventure.common.data.HasId;
import es.eucm.eadventure.common.data.chapter.conditions.*;

/**
 * Rule for the assesment engine
 */
public class AssessmentRule implements Cloneable, HasId {

    /**
     * Number of different importance values
     */
    public static final int N_IMPORTANCE_VALUES = 5;

    /**
     * Importance value for very low
     */
    public static final int IMPORTANCE_VERY_LOW = 0;

    /**
     * Importance value for low
     */
    public static final int IMPORTANCE_LOW = 1;

    /**
     * Importance value for normal
     */
    public static final int IMPORTANCE_NORMAL = 2;

    /**
     * Importance value for high
     */
    public static final int IMPORTANCE_HIGH = 3;

    /**
     * Importance value for very high
     */
    public static final int IMPORTANCE_VERY_HIGH = 4;

    /**
     * String values for the different importance values
     */
    public static final String IMPORTANCE_VALUES[] = { "verylow", "low", "normal", "high", "veryhigh" };

    /**
     * Id of the rule
     */
    protected String id;

    /**
     * Importance of the rule, stored as an integer
     */
    protected int importance;

    /**
     * Concept of the rule
     */
    protected String concept;

    /**
     * Conditions for the rule to trigger
     */
    protected Conditions conditions;

    /**
     * The effect of the rule
     */
    protected AssessmentEffect effect;

    /**
     * Default constructor
     * 
     * @param id
     *            Id of the rule
     * @param importance
     *            Importance of the rule
     */
    public AssessmentRule( String id, int importance ) {

        this.id = id;
        this.importance = importance;
        concept = null;
        conditions = new Conditions( );
        effect = new AssessmentEffect( );
    }

    /**
     * Sets the concept of the rule
     * 
     * @param concept
     *            Concept of the rule
     */
    public void setConcept( String concept ) {

        this.concept = concept;
    }

    /**
     * Sets the conditions of the rule
     * 
     * @param conditions
     *            Conditions of the rule
     */
    public void setConditions( Conditions conditions ) {

        this.conditions = conditions;
    }

    /**
     * Sets the text of the rule
     * 
     * @param text
     *            Text of the rule
     */
    public void setText( String text ) {

        effect.setText( text );
    }

    /**
     * Adds a new assessment property
     * 
     * @param property
     *            Assessment property to be added
     */
    public void addProperty( AssessmentProperty property ) {

        effect.getAssessmentProperties( ).add( property );
    }

    /**
     * Return the rule's id
     * 
     * @return Id of the rule
     */
    public String getId( ) {

        return id;
    }

    /**
     * Return the rule's importance
     * 
     * @return Importance of the rule
     */
    public Integer getImportance( ) {

        return importance;
    }

    /**
     * Returns the rule's concept
     * 
     * @return Concept of the rule
     */
    public String getConcept( ) {

        return concept;
    }

    /**
     * Returns the rule's text (if present)
     * 
     * @return Text of the rule if present, null otherwise
     */
    public String getText( ) {

        return effect.getText( );
    }

    public List<AssessmentProperty> getAssessmentProperties( ) {

        return effect.getAssessmentProperties( );
    }

    /**
     * @param importance
     *            the importance to set
     */
    public void setImportance( Integer importance ) {

        this.importance = importance;
    }

    /**
     * @return the conditions
     */
    public Conditions getConditions( ) {

        return conditions;
    }

    public void setId( String assRuleId ) {

        this.id = assRuleId;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        AssessmentRule ar = (AssessmentRule) super.clone( );
        ar.concept = ( concept != null ? new String( concept ) : null );
        if( conditions != null )
            ar.conditions = (Conditions) conditions.clone( );
        if( effect != null )
            ar.effect = (AssessmentEffect) effect.clone( );
        ar.id = ( id != null ? new String( id ) : null );
        ar.importance = importance;
        return ar;
    }
}
