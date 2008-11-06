package es.eucm.eadventure.engine.assessment;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.engine.core.gui.TextConstantsEngine;

import es.eucm.eadventure.engine.core.data.gamedata.conditions.Conditions;

/**
 * Rule for the assesment engine
 */
public class AssessmentRule {
    
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
     * String values for the different importance values (for printing)
     */
    public static final String[] IMPORTANCE_VALUES_PRINT = {
        TextConstantsEngine.getText( "Report.Importance.VeryLow" ),
        TextConstantsEngine.getText( "Report.Importance.Low" ),
        TextConstantsEngine.getText( "Report.Importance.Normal" ),
        TextConstantsEngine.getText( "Report.Importance.High" ),
        TextConstantsEngine.getText( "Report.Importance.VeryHigh" )
    };
    
    /**
     * Id of the rule
     */
    private String id;
    
    /**
     * Importance of the rule, stored as an integer
     */
    private int importance;
    
    /**
     * Concept of the rule
     */
    private String concept;
    
    /**
     * Conditions for the rule to trigger
     */
    protected Conditions conditions;
    
    /**
     * Text of the effect of the rule, if present (null if not)
     */   
    protected String text;
    
    /**
     * List of properties to be set
     */
    protected List<AssessmentProperty> properties;
    
    /**
     * Default constructor
     * @param id Id of the rule
     * @param importance Importance of the rule
     */
    public AssessmentRule( String id, int importance) {
        this.id = id;
        this.importance = importance;
        concept = null;
        conditions = null;
        text = null;
        properties = new ArrayList<AssessmentProperty>( );
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
        this.text = text;
    }
    
    /**
     * Adds a new assessment property
     * @param property Assessment property to be added
     */
    public void addProperty( AssessmentProperty property ) {
        properties.add( property );
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
        return text;
    }

    /**
     * Returns true if the rule is active
     * @return True if the rule is active, false otherwise
     */
    public boolean isActive( ) {
        return conditions.allConditionsOk( );
    }

    public List<AssessmentProperty> getAssessmentProperties( ) {
        return properties;
    }
}
