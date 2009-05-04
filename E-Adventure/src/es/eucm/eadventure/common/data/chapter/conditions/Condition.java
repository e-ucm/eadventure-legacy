package es.eucm.eadventure.common.data.chapter.conditions;

import es.eucm.eadventure.common.data.HasId;

/**
 * This class manages a condition in eAdventure
 */
public abstract class Condition implements Cloneable, HasId {

	/**
	 * Constant for state not set.
	 */
	public static final int NO_STATE = -1;
	

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
	public static final int GLOBAL_STATE_CONDITION = 2;


	/**
	 * Name of the flag to be checked
	 */
	protected String id;

	/**
	 * Stores if the flag must be active or inactive
	 */
	protected int state;

	/**
	 * Type of the condition ({@link #VAR_CONDITION}, {@link #FLAG_CONDITION} or {@link #GLOBAL_STATE_CONDITION}
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
	public Condition( int type, String flagVar, int state ) {
		this.type = type;
		this.id = flagVar;
		this.state = state;
	}

	/**
	 * Returns the flag/Var of the condition
	 * 
	 * @return The flag/Var of the condition
	 */
	public String getId( ) {
		return id;
	}

	/**
	 * Returns whether the flag/Var must be activated or deactivated for this condition to be satisfied
	 * 
	 * @return the state {@link #FLAG_ACTIVE} {@link #FLAG_INACTIVE} {@link #NO_STATE} {@link #VAR_EQUALS}
	 * 					 {@link #VAR_GREATER_EQUALS_THAN} {@link #VAR_GREATER_THAN} {@link #VAR_LESS_EQUALS_THAN}
	 *                   {@link #VAR_LESS_THAN}
	 */
	public Integer getState( ) {
		return state;
	}

	
	/**
	 * Sets a new flag for this condition
	 * 
	 * @param flagVar
	 *            New condition flag/Var
	 */
	public void setId( String flagVar ) {
		this.id = flagVar;
	}

	/**
	 * Sets a new active or inactive state for the flag/Var.
	 * 
	 * @param state
	 *            New state {@link #FLAG_ACTIVE} {@link #FLAG_INACTIVE} {@link #NO_STATE} {@link #VAR_EQUALS}
	 * 					 {@link #VAR_GREATER_EQUALS_THAN} {@link #VAR_GREATER_THAN} {@link #VAR_LESS_EQUALS_THAN}
	 *                   {@link #VAR_LESS_THAN}
	 */
	public void setState(Integer state ) {
		this.state = state;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}
	
	public Object clone() throws CloneNotSupportedException {
		Condition c = (Condition) super.clone();
		c.id = (id != null ? new String(id) : null);
		c.state = state;
		c.type = type;
		return c;
	}
}
