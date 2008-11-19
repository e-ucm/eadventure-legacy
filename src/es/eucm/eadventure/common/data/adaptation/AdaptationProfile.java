package es.eucm.eadventure.common.data.adaptation;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores an adaptation profile. Each profile contains the path of the xml file where it is stored (relative to the zip file), along with the
 * list of adaptation rules and initial state defined in the profile
 * @author Javier
 *
 */
public class AdaptationProfile {

	/**
	 * Relative path of the file containing the adaptation rules
	 */
	private String path;
	
	/**
	 * The list of adaptation rules
	 */
	private List<AdaptationRule> rules;
	
	/**
	 * Initial state defined in the profile
	 */
	private AdaptedState initialState;
	
	/**
	 * @param path
	 * @param rules
	 * @param initialState
	 */
	public AdaptationProfile(List<AdaptationRule> rules,
			AdaptedState initialState, String path) {
		this.path = path;
		this.rules = rules;
		this.initialState = initialState;
	}

	/**
	 * Empty constructor 
	 */
	public AdaptationProfile() {
		path = null;
		rules = new ArrayList<AdaptationRule>();
		
	}
	
	/**
	 * @param path
	 */
	public AdaptationProfile(String path) {
		this();
		this.path = path;
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
	public List<AdaptationRule> getRules() {
		return rules;
	}

	/**
	 * Adds a new rule to the structure
	 */
	public void addRule ( AdaptationRule rule ){
		this.rules.add(rule);
	}

	/**
	 * @return the initialState
	 */
	public AdaptedState getInitialState() {
		return initialState;
	}

	/**
	 * @param initialState the initialState to set
	 */
	public void setInitialState(AdaptedState initialState) {
		this.initialState = initialState;
	}

	/**
	 * Set all the rules
	 * @param adpRules
	 */
	public void setRules(List<AdaptationRule> adpRules) {
		this.rules = adpRules;
	}
}
