package es.eucm.eadventure.engine.assessment;

import java.io.Serializable;

/**
 * Assessment property, stores an id and a value
 */
public class AssessmentProperty implements Serializable {

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
    private int value; 

    /**
     * Default constructor
     * @param id Id of the property
     * @param value Value of the property
     */
    public AssessmentProperty( String id, int value ) {
        this.id = id;
        this.value = value;
    }

    /**
     * Returns the id of the property
     * @return Id of the property
     */
    public String getId( ) {
        return id;
    }

    /**
     * Returns the value of the property
     * @return Value of the property
     */
    public int getValue( ) {
        return value;
    }
}
