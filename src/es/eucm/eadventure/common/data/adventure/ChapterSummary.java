package es.eucm.eadventure.common.data.adventure;

import es.eucm.eadventure.common.data.Described;
import es.eucm.eadventure.common.data.Titled;

/**
 * Basically this class represents a chapter entry in the descriptor file.
 * It may contain the following data:
 * path, title, description, metadata (optionally), adaptation-configuration (optionally), assessment-configuration (optionally)
 * @author Javier
 *
 */
public class ChapterSummary implements Cloneable, Titled, Described {
	
	/**
	 * Chapter's title.
	 */
	private String title;

	/**
	 * Chapter's description.
	 */
	private String description;

	/**
	 * Adaptation file's path, if there is any.
	 */
	private String adaptationPath;

	/**
	 * Assessment file's path, if there is any.
	 */
	private String assessmentPath;
	
	/**
	 * Relative path to the zip where it was contained. Used for replacing 
	 */
	private String name;
	
	/**
	 * Empty constructor. Sets values to null or blank
	 */
	public ChapterSummary( ) {
		title = null;
		description = "";
		adaptationPath = "";
		assessmentPath = "";
	}
	
	/**
	 * Constructor with title for the chapter. Sets empty values..
	 * 
	 * @param title
	 *            Title for the chapter
	 */
	public ChapterSummary( String title ) {
		this();
		this.title = title;
	}
	
	/**
	 * Returns the title of the chapter
	 * 
	 * @return Chapter's title
	 */
	public String getTitle( ) {
		return title;
	}

	/**
	 * Returns the description of the chapter.
	 * 
	 * @return Chapter's description
	 */
	public String getDescription( ) {
		return description;
	}

	/**
	 * Returns the path of the adaptation file.
	 * 
	 * @return the path of the adaptation file
	 */
	public String getAdaptationPath( ) {
		return adaptationPath;
	}

	/**
	 * Returns the path of the assessment file.
	 * 
	 * @return the path of the assessment file
	 */
	public String getAssessmentPath( ) {
		return assessmentPath;
	}
	
	/**
	 * Sets the title of the chapter.
	 * 
	 * @param title
	 *            New title for the chapter
	 */
	public void setTitle( String title ) {
		this.title = title;
	}

	/**
	 * Sets the description of the chapter.
	 * 
	 * @param description
	 *            New description for the chapter
	 */
	public void setDescription( String description ) {
		this.description = description;
	}

	/**
	 * Changes the path of the adaptation file.
	 * 
	 * @param adaptationPath
	 *            the new path of the adaptation file
	 */
	public void setAdaptationPath( String adaptationPath ) {
		this.adaptationPath = adaptationPath;
	}

	/**
	 * Changes the path of the assessment file.
	 * 
	 * @param assessmentPath
	 *            the new path of the assessment file
	 */
	public void setAssessmentPath( String assessmentPath ) {
		this.assessmentPath = assessmentPath;
	}
	
	/**
	 * @return the name
	 */
	public String getName( ) {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName( String name ) {
		this.name = name;
	}

	/**
	 * Returns true if an assessment profile has been defined for the chapter
	 * @return
	 */
	public boolean hasAssessmentProfile() {
		return this.assessmentPath!=null && !this.assessmentPath.equals("");
	}
	
	/**
	 * Returns true if an adaptation profile has been defined for the chapter
	 * @return
	 */
	public boolean hasAdaptationProfile() {
		return this.adaptationPath!=null && !this.adaptationPath.equals("");
	}
	
	public Object clone() throws CloneNotSupportedException {
		ChapterSummary cs = (ChapterSummary) super.clone();
		cs.adaptationPath = (adaptationPath != null ? new String(adaptationPath) : null);
		cs.assessmentPath = (assessmentPath != null ? new String(assessmentPath) : null);
		cs.description = (description != null ? new String(description) : null);
		cs.name = (name != null ? new String(name) : null);
		cs.title = (title != null ? new String(title) : null);
		
		return cs;
	}
}
