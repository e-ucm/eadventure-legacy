package es.eucm.eadventure.adventureeditor.data.adaptation;

import java.io.Serializable;

/**
 * Assessment property, stores an id and a value
 */
public class UOLProperty implements Serializable {

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
     * @param id Id of the property
     * @param value Value of the property
     */
    public UOLProperty( String id, String value ) {
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
    public String getValue( ) {
        return value;
    }

	public void setId( String id ) {
		this.id=id;
	}

	public void setValue( String value ) {
		this.value = value;
	}
}
