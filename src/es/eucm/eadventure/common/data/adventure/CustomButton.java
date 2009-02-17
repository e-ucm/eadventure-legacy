package es.eucm.eadventure.common.data.adventure;

public class CustomButton implements Cloneable {

    private String type;
    
    private String path;
    
    private String action;

    /**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
     * @return the type
     */
    public String getType( ) {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType( String type ) {
        this.type = type;
    }

    /**
     * @return the path
     */
    public String getPath( ) {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath( String path ) {
        this.path = path;
    }

    /**
     * @param type
     * @param path
     */
    public CustomButton( String action, String type, String path ) {
        this.action = action;
    	this.type = type;
        this.path = path;
    }
 
    
	public Object clone() throws CloneNotSupportedException {
		CustomButton cb = (CustomButton) super.clone();
		cb.action = (action != null ? new String(action) : null);
		cb.path = (path != null ? new String(path) : null);
		cb.type = (type != null ? new String(type) : null);
		
		return cb;
	}
}
