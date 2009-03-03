package es.eucm.eadventure.common.data.adaptation;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores an adaptation profile. Each profile contains the path of the xml file where it is stored (relative to the zip file), along with the
 * list of adaptation rules and initial state defined in the profile
 * @author Javier
 *
 */
public class AdaptationProfile  implements Cloneable, ContainsAdaptedState{

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
	 * List of referenced flags
	 */
	private List<String> flags;
	
	/**
	 * List of referenced vars
	 */
	private List<String> vars;
	
	/**
	 * Store if adaptation profile is for scorm2004 
	 */
	private boolean scorm2004;
	
	
	/**
	 * Store if adaptation profile is for scorm 1.2
	 */
	private boolean scorm12;
	
	/**
	 * @param path
	 * @param rules
	 * @param initialState
	 * @param scorm12
	 * @param scorm2004
	 */
	public AdaptationProfile(List<AdaptationRule> rules,
			AdaptedState initialState, String path,boolean scorm12, boolean scorm2004) {
		this.path = path;
		this.rules = rules;
		this.initialState = initialState;
		flags = new ArrayList<String>();
		vars = new ArrayList<String>();
		this.scorm2004 = scorm2004;
		this.scorm12 = scorm12;
	}

	/**
	 * Empty constructor 
	 */
	public AdaptationProfile() {
		path = null;
		rules = new ArrayList<AdaptationRule>();
		scorm2004 = false;
		scorm12 = false;
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
	public AdaptedState getAdaptedState() {
		return initialState;
	}

	/**
	 * @param initialState the initialState to set
	 */
	public void setAdaptedState(AdaptedState initialState) {
		this.initialState = initialState;
	}

	/**
	 * Set all the rules
	 * @param adpRules
	 */
	public void setRules(List<AdaptationRule> adpRules) {
		this.rules = adpRules;
	}
	
	/**
	 * Adds a new flag
	 * @param flag
	 */
	public void addFlag( String flag ){
		if (!flags.contains(flag)){
			flags.add(flag);
		}
	}
	
	/**
	 * Adds a new var
	 * @param var
	 */
	public void addVar( String var ){
		if (!vars.contains(var)){
			vars.add(var);
		}
	}

	/**
	 * @return the flags
	 */
	public List<String> getFlags() {
		return flags;
	}

	/**
	 * @return the vars
	 */
	public List<String> getVars() {
		return vars;
	}

	/**
	 * @param flags the flags to set
	 */
	public void setFlags(List<String> flags) {
		this.flags = flags;
	}

	/**
	 * @param vars the vars to set
	 */
	public void setVars(List<String> vars) {
		this.vars = vars;
	}
	
	public Object clone() throws CloneNotSupportedException {
		AdaptationProfile ap = (AdaptationProfile) super.clone();
		ap.flags = new ArrayList<String>();
		for (String s : flags)
			ap.flags.add((s != null ? new String(s) : null));
		ap.initialState = (AdaptedState) initialState.clone();
		ap.path = (path != null ? new String(path) : null);
		ap.rules = new ArrayList<AdaptationRule>();
		for (AdaptationRule ar : rules)
			ap.rules.add((AdaptationRule) ar.clone());
		ap.vars = new ArrayList<String>();
		for (String s : vars)
			ap.vars.add((s != null ? new String(s) : null));
		return ap; 
	}

	/**
	 * @return the scorm2004
	 */
	public boolean isScorm2004() {
		return scorm2004;
	}

	/**
	 * @param scorm2004 the scorm2004 to set
	 */
	public void setScorm2004(boolean scorm2004) {
		this.scorm2004 = scorm2004;
	}

	/**
	 * @return the scorm12
	 */
	public boolean isScorm12() {
		return scorm12;
	}

	/**
	 * @param scorm12 the scorm12 to set
	 */
	public void setScorm12(boolean scorm12) {
		this.scorm12 = scorm12;
	}

}
