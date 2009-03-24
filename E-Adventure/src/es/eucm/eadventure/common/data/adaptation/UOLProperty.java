package es.eucm.eadventure.common.data.adaptation;

import java.io.Serializable;

/**
 * Assessment property, stores an id and a value
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
     * The type of the comparison operation between LMS value of "id" and attribute "value"
     */
    private String operation;

    /**
     * Default constructor
     * @param id Id of the property
     * @param value Value of the property
     * @param operation The comparison operation between LMS value of Id and Value
     */
    public UOLProperty( String id, String value, String operation ) {
        this.id = id;
        this.value = value;
        this.operation = operation;
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
	
    /**
     * @return the operation
     */
    public String getOperation() {
	return operation;
    }

    /**
     * @param operation the operation to set
     */
    public void setOperation(String operation) {
	this.operation = operation;
    }
	
    public Object clone() throws CloneNotSupportedException {
	UOLProperty uolp = (UOLProperty) super.clone();
	uolp.id = (id != null ? new String(id) : null);
	uolp.value = (value != null ? new String(value) : null);
	uolp.operation = (operation != null ? new String(operation) : null);;
	return uolp;
    }	

	
}
