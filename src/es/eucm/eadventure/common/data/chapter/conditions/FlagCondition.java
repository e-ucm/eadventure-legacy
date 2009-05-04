package es.eucm.eadventure.common.data.chapter.conditions;

/**
 * A condition based on a reference to a global state
 * @author Javier
 *
 */
public class FlagCondition extends Condition{

	/**
	 * Constant for active flag.
	 */
	public static final int FLAG_ACTIVE = 0;

	/**
	 * Constant for inactive flag.
	 */
	public static final int FLAG_INACTIVE = 1;
	
	/**
	 * Constructor
	 * @param flagVar
	 * @param state
	 */
	public FlagCondition( String id ) {
		super(Condition.FLAG_CONDITION, id, FlagCondition.FLAG_ACTIVE);
	}
	
	/**
	 * Constructor
	 * @param flagVar
	 * @param state
	 */
	public FlagCondition( String id , int state) {
		super(Condition.FLAG_CONDITION, id, state);
		if (state!=FLAG_ACTIVE && state!=FLAG_INACTIVE){
			state = FLAG_ACTIVE;
		}
	}
	
	public Object clone() throws CloneNotSupportedException {
		FlagCondition gsr = (FlagCondition) super.clone();
		return gsr;
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

}
