package es.eucm.eadventure.engine.core.data.gamedescriptor;

/**
 * Stores the data for a eAdventure's game chapter
 */
public class Chapter {

    /**
     * The path of the chapter's files
     */
    private String path;
    
    /**
     * Chapter's title
     */
    private String title;
    
    /**
     * Chapter's description
     */
    private String description;
    
    /**
     * Adaptation file's path, if any
     */
    private String adaptationPath;
    
    /**
     * Assessment file's path, if any
     */
    private String assessmentPath;
    
    /**
     * Constructor
     */
    public Chapter() {
        adaptationPath = null;
        assessmentPath = null;
    }
    
    /**
     * Returns the path of the chapter's file
     * @return the path of the chapter's file
     */
    public String getPath() {
        return path;
    }
    
    /**
     * Changes the path of the chapter's file
     * @param path the new path of the chapter's file
     */
    public void setPath( String path ) {
        this.path = path;
    }
    
    /**
     * Returns the title of the chapter
     * @return Chapter's title
     */
    public String getTitle( ) {
        return title;
    }

    /**
     * Sets the title of the chapter
     * @param title New title for the chapter
     */
    public void setTitle( String title ) {
        this.title = title;
    }
    
    /**
     * Returns the description of the chapter
     * @return Chapter's description
     */
    public String getDescription( ) {
        return description;
    }

    /**
     * Sets the description of the chapter
     * @param description New description for the chapter
     */
    public void setDescription( String description ) {
        this.description = description;
    }
    
    /**
     * Returns the path of the adaptation file
     * @return the path of the adaptation file
     */
    public String getAdaptationPath() {
        return adaptationPath;
    }
    
    /**
     * Changes the path of the adaptation file
     * @param adaptationPath the new path of the adaptation file
     */
    public void setAdaptationPath( String adaptationPath ) {
        this.adaptationPath = adaptationPath;
    }
    
    /**
     * Returns the path of the assessment file
     * @return the path of the assessment file
     */
    public String getAssessmentPath() {
        return assessmentPath;
    }
    
    /**
     * Changes the path of the assessment file
     * @param assessmentPath the new path of the assessment file
     */
    public void setAssessmentPath( String assessmentPath ) {
        this.assessmentPath = assessmentPath;
    }
    
    /**
     * Returns whether the chapter has an assessment profile
     * @return true if the chapter has an assessment profile, false otherwise
     */
    public boolean hasAssessmentProfile() {
        return (assessmentPath!=null && !assessmentPath.equals( "" ));
    }
    
    /**
     * Returns whether the chapter has an adaptation profile
     * @return true if the chapter has an adaptation profile, false otherwise
     */
    public boolean hasAdaptationProfile() {
        return (adaptationPath!=null && !adaptationPath.equals( "" ));
    }
    
}
