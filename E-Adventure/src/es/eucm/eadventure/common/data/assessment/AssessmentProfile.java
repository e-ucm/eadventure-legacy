package es.eucm.eadventure.common.data.assessment;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores an assessment profile. Each profile contains the path of the xml file where it is stored (relative to the zip file), along with the
 * list of assessment rules defined in the profile
 * @author Javier
 *
 */
public class AssessmentProfile {

	/**
	 * Relative path of the file containing the asssessment rules
	 */
	private String path;
	
	/**
	 * The list of assessment rules
	 */
	private List<AssessmentRule> rules;
	
	/**
	 * Empty constructor 
	 */
	public AssessmentProfile() {
		this( new ArrayList<AssessmentRule>(), null );
		
	}
	
	/**
	 * @param path
	 */
	public AssessmentProfile(String path) {
		this( new ArrayList<AssessmentRule>(), path );
	}

	public AssessmentProfile(List<AssessmentRule> assessmentRules, String path2) {
		rules = assessmentRules;
		this.path = path2;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the rules
	 */
	public List<AssessmentRule> getRules() {
		return rules;
	}

	/**
	 * Adds a new rule to the structure
	 */
	public void addRule ( AssessmentRule rule ){
		this.rules.add(rule);
	}

	/**
	 * Set all the rules
	 * @param assessmentRules
	 */
	public void setRules(List<AssessmentRule> assessmentRules) {
		this.rules = assessmentRules;
	}
}
