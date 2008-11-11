package es.eucm.eadventure.engine.gamelauncher.gameentry;

/**
 * Contains the title and the story of an e-Adventure game 
 */
public class GameEntry {
    
    /**
     * Filename of the adventure
     */
    private String filename;
    
    /**
     * Title of the adventure
     */
    private String title;    
    
    /**
     * Description of the adventure
     */
    private String description;
    
    /**
     * True if the adventure is valid, false otherwise.
     */
    private boolean valid;
    
    /**
     * Default constructor
     */
    public GameEntry( ) {
        filename = null;
        title = "No title avalaible";
        description = "No description avalaible";
        valid = true;
    }
    
    /**
     * Sets the filename of the adventure
     * @param filename Filename of the adventure
     */
    public void setFilename( String filename ) {
        this.filename = filename;
    }
    
    /**
     * Sets the title of the adventure
     * @param title Title of the adventure
     */
    public void setTitle( String title ) {
        this.title = (title.equals( "" )? "No title avalaible" : title );
    }
    
    /**
     * Sets the description of the adventure
     * @param description description of the adventure
     */
    public void setDescription( String description ) {
        this.description = (description.equals( "" )? "No description avalaible" : description );
    }
    
    /**
     * Sets if the adventure is valid or not
     * @param valid True if the adventure is valid, false otherwise
     */
    public void setValid( boolean valid ) {
        this.valid = valid;
    }
    
    /**
     * Appends the filename of the adventure to the title
     * @param filename Filename of the adventure
     */
    public void appendFilenameToTitle( String filename ) {
        title = title + " (" + filename + ")";
    }
    
    /**
     * Returns the filename of the adventure
     * @return Filename of the adventure
     */
    public String getFilename( ) {
        return filename;
    }
    
    /**
     * Returns the title of the adventure
     * @return Title of the adventure
     */
    public String getTitle( ) {
        return title;
    }

    /**
     * Returns the description of the adventure
     * @return Description of the adventure
     */
    public String getDescription( ) {
        return description;
    }
    
    /**
     * Returns whether the adventure is valid or not
     * @return True if the adventure is valid, false otherwise
     */
    public boolean isValid( ) {
        return valid;
    }
    
    /*
     *  (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString( ) {
       return title; 
    }
}
