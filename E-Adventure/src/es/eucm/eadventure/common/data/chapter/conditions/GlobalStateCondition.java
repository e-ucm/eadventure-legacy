package es.eucm.eadventure.common.data.chapter.conditions;

/**
 * A condition based on a reference to a global state
 * @author Javier
 *
 */
public class GlobalStateCondition extends Condition{

	public static final int GS_SATISFIED = 7;
	public static final int GS_NOT_SATISFIED = 8;
	
	
	/**
	 * Constructor
	 * @param flagVar
	 * @param state
	 */
	public GlobalStateCondition(String id ) {
		super(Condition.GLOBAL_STATE_CONDITION,id, Condition.NO_STATE);
	}
	
	/**
	 * Constructor
	 * @param flagVar
	 * @param state
	 */
	public GlobalStateCondition(String id, int state ) {
		super(Condition.GLOBAL_STATE_CONDITION,id, state);
		if (state!=GS_SATISFIED && state!=GS_NOT_SATISFIED)
			state = GS_SATISFIED;
	}

	
	public Object clone() throws CloneNotSupportedException {
		GlobalStateCondition gsr = (GlobalStateCondition) super.clone();
		return gsr;
	}

}
