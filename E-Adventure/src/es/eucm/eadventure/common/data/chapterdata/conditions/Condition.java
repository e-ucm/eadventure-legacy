package es.eucm.eadventure.common.data.chapterdata.conditions;

/**
 * This class manages a condition in eAdventure
 */
public class Condition {

	/**
	 * Constant for active flag.
	 */
	public static final boolean FLAG_ACTIVE = true;

	/**
	 * Constant for inactive flag.
	 */
	public static final boolean FLAG_INACTIVE = false;

	/**
	 * Name of the flag to be checked
	 */
	private String flag;

	/**
	 * Stores if the flag must be active or inactive
	 */
	private boolean state;

	/**
	 * Creates a new condition
	 * 
	 * @param flag
	 *            Flag of the condition
	 * @param state
	 *            Determines whether the flag must be activated or deactivated for this condition to be satisfied
	 */
	public Condition( String flag, boolean state ) {
		this.flag = flag;
		this.state = state;
	}

	/**
	 * Returns the flag of the condition
	 * 
	 * @return The flag of the condition
	 */
	public String getFlag( ) {
		return flag;
	}

	/**
	 * Returns whether the flag must be activated or deactivated for this condition to be satisfied
	 * 
	 * @return true if the flag must be activated for this condition to be satisfied, false if it has to be deactivated
	 */
	public boolean getState( ) {
		return state;
	}

	/**
	 * Sets a new flag for this condition
	 * 
	 * @param flag
	 *            New condition flag
	 */
	public void setFlag( String flag ) {
		this.flag = flag;
	}

	/**
	 * Sets a new active or inactive state for the flag.
	 * 
	 * @param state
	 *            New state for the flag, must be FLAG_ACTIVE or FLAG_INACTIVE
	 */
	public void setState( boolean state ) {
		this.state = state;
	}
}
