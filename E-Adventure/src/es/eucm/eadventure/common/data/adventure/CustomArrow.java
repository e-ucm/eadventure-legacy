package es.eucm.eadventure.common.data.adventure;

public class CustomArrow implements Cloneable {

    private String type;
    
    private String path;
    
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
    public CustomArrow( String type, String path ) {
    	this.type = type;
        this.path = path;
    }
    
    public boolean equals(Object o) {
    	if (o == null || !(o instanceof CustomArrow))
    		return false;
    	CustomArrow button = (CustomArrow) o;
    	if (button.type.equals(type))
    		return true;
    	return false;
    }
 
    
	public Object clone() throws CloneNotSupportedException {
		CustomArrow cb = (CustomArrow) super.clone();
		cb.path = (path != null ? new String(path) : null);
		cb.type = (type != null ? new String(type) : null);
		return cb;
	}
}
