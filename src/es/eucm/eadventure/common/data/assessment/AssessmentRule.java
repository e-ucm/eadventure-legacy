package es.eucm.eadventure.common.data.assessment;

import java.util.List;

import es.eucm.eadventure.common.data.chapter.conditions.*;

/**
 * Rule for the assesment engine
 */
public class AssessmentRule implements Cloneable {
    
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
    public static final String IMPORTANCE_VALUES[] = {
        "verylow",
        "low",
        "normal",
        "high",
        "veryhigh"
    };
    

    
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
     * @param id Id of the rule
     * @param importance Importance of the rule
     */
    public AssessmentRule( String id, int importance) {
        this.id = id;
        this.importance = importance;
        concept = null;
        conditions = new Conditions();
        effect = new AssessmentEffect();
    }
    
    /**
     * Sets the concept of the rule
     * @param concept Concept of the rule
     */
    public void setConcept( String concept ) {
        this.concept = concept;
    }

    /**
     * Sets the conditions of the rule
     * @param conditions Conditions of the rule
     */
    public void setConditions( Conditions conditions ) {
        this.conditions = conditions;
    }

    /**
     * Sets the text of the rule
     * @param text Text of the rule
     */
    public void setText( String text ) {
        effect.setText( text );
    }
    
    /**
     * Adds a new assessment property
     * @param property Assessment property to be added
     */
    public void addProperty( AssessmentProperty property ) {
        effect.getAssessmentProperties( ).add( property );
    }
    
    /**
     * Return the rule's id
     * @return Id of the rule
     */
    public String getId( ) {
        return id;
    }
    
    /**
     * Return the rule's importance
     * @return Importance of the rule
     */
    public int getImportance( ) {
        return importance;
    }

    /**
     * Returns the rule's concept
     * @return Concept of the rule
     */
    public String getConcept( ) {
        return concept;
    }
    
    /**
     * Returns the rule's text (if present)
     * @return Text of the rule if present, null otherwise
     */
    public String getText( ) {
        return effect.getText( );
    }


    public List<AssessmentProperty> getAssessmentProperties( ) {
        return effect.getAssessmentProperties( );
    }

	/**
	 * @param importance the importance to set
	 */
	public void setImportance( int importance ) {
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
	
	public Object clone() throws CloneNotSupportedException {
		AssessmentRule ar = (AssessmentRule) super.clone();
		ar.concept = (concept != null ? new String(concept) : null);
		if (conditions != null)
			ar.conditions = (Conditions) conditions.clone();
		if (effect != null)
			ar.effect = (AssessmentEffect) effect.clone();
		ar.id = (id != null ? new String(id) : null);
		ar.importance = importance;
		return ar;
	}
}
