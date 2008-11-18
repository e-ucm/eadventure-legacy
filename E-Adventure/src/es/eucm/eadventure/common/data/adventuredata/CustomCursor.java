package es.eucm.eadventure.common.data.adventuredata;

public class CustomCursor {

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
    public CustomCursor( String type, String path ) {
        this.type = type;
        this.path = path;
    }
    
}
