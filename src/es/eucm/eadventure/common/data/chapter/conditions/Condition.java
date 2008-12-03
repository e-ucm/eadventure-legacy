package es.eucm.eadventure.common.data.chapter.conditions;

/**
 * This class manages a condition in eAdventure
 */
public class Condition {

	/**
	 * Constant for state not set.
	 */
	public static final int NO_STATE = -1;
	
	/**
	 * Constant for active flag.
	 */
	public static final int FLAG_ACTIVE = 0;

	/**
	 * Constant for inactive flag.
	 */
	public static final int FLAG_INACTIVE = 1;
	
	/**
	 * Constant for greater-than var.
	 */
	public static final int VAR_GREATER_THAN = 2;
	
	/**
	 * Constant for greater-than or equals var.
	 */
	public static final int VAR_GREATER_EQUALS_THAN = 3;

	/**
	 * Constant for equals var.
	 */
	public static final int VAR_EQUALS = 4;
	
	/**
	 * Constant for less than or equals var.
	 */
	public static final int VAR_LESS_EQUALS_THAN = 5;

	/**
	 * Constant for less-than var.
	 */
	public static final int VAR_LESS_THAN = 6;

	/**
	 * Condition based on var
	 */
	public static final int VAR_CONDITION = 0;
	
	/**
	 * Condition based on flag
	 */
	public static final int FLAG_CONDITION = 1;
	
	/**
	 * Condition based on condition group
	 */
	public static final int GROUP_CONDITION = 2;


	/**
	 * Name of the flag to be checked
	 */
	protected String flagVar;

	/**
	 * Stores if the flag must be active or inactive
	 */
	protected int state;

	/**
	 * Type of the condition ({@link #VAR_CONDITION}, {@link #FLAG_CONDITION} or {@link #GROUP_CONDITION}
	 */
	protected int type;

	/**
	 * Creates a new condition
	 * 
	 * @param flagVar
	 *            Flag/Var of the condition
	 * @param state
	 *            Determines the state: {@link #FLAG_ACTIVE} {@link #FLAG_INACTIVE} {@link #NO_STATE} {@link #VAR_EQUALS}
	 * 					 {@link #VAR_GREATER_EQUALS_THAN} {@link #VAR_GREATER_THAN} {@link #VAR_LESS_EQUALS_THAN}
	 *                   {@link #VAR_LESS_THAN} 
	 */
	public Condition( String flagVar, int state ) {
		this.type = FLAG_CONDITION;
		this.flagVar = flagVar;
		this.state = state;
	}

	/**
	 * Returns the flag/Var of the condition
	 * 
	 * @return The flag/Var of the condition
	 */
	public String getFlagVar( ) {
		return flagVar;
	}

	/**
	 * Returns whether the flag/Var must be activated or deactivated for this condition to be satisfied
	 * 
	 * @return the state {@link #FLAG_ACTIVE} {@link #FLAG_INACTIVE} {@link #NO_STATE} {@link #VAR_EQUALS}
	 * 					 {@link #VAR_GREATER_EQUALS_THAN} {@link #VAR_GREATER_THAN} {@link #VAR_LESS_EQUALS_THAN}
	 *                   {@link #VAR_LESS_THAN}
	 */
	public int getState( ) {
		return state;
	}

	/**
	 * Returns true if the state is FLAG_ACTIVE
	 * @return
	 */
	public boolean isActiveState ( ){
		return state == FLAG_ACTIVE;
	}

	/**
	 * Returns true if the state is FLAG_INACTIVE
	 * @return
	 */
	public boolean isInactiveState ( ){
		return state == FLAG_INACTIVE;
	}

	
	/**
	 * Sets a new flag for this condition
	 * 
	 * @param flagVar
	 *            New condition flag/Var
	 */
	public void setFlagVar( String flagVar ) {
		this.flagVar = flagVar;
	}

	/**
	 * Sets a new active or inactive state for the flag/Var.
	 * 
	 * @param state
	 *            New state {@link #FLAG_ACTIVE} {@link #FLAG_INACTIVE} {@link #NO_STATE} {@link #VAR_EQUALS}
	 * 					 {@link #VAR_GREATER_EQUALS_THAN} {@link #VAR_GREATER_THAN} {@link #VAR_LESS_EQUALS_THAN}
	 *                   {@link #VAR_LESS_THAN}
	 */
	public void setState( int state ) {
		this.state = state;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}
}
