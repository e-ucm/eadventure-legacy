package es.eucm.eadventure.common.data.adaptation;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores an adaptation profile. Each profile contains the path of the xml file where it is stored (relative to the zip file), along with the
 * list of adaptation rules and initial state defined in the profile
 * @author Javier
 *
 */
public class AdaptationProfile  implements Cloneable{

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
	 * @param path
	 * @param rules
	 * @param initialState
	 */
	public AdaptationProfile(List<AdaptationRule> rules,
			AdaptedState initialState, String path) {
		this.path = path;
		this.rules = rules;
		this.initialState = initialState;
		flags = new ArrayList<String>();
		vars = new ArrayList<String>();
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
}
