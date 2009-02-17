package es.eucm.eadventure.common.data.chapter.conditions;

/**
 * A condition based on a reference to a global state
 * @author Javier
 *
 */
public class GlobalStateReference extends Condition{

	/**
	 * Constructor
	 * @param flagVar
	 * @param state
	 */
	public GlobalStateReference(String id ) {
		super(id, Condition.NO_STATE);
		type = Condition.GLOBAL_STATE_CONDITION;
	}
	
	public Object clone() throws CloneNotSupportedException {
		GlobalStateReference gsr = (GlobalStateReference) super.clone();
		type = Condition.GLOBAL_STATE_CONDITION;
		return gsr;
	}

}
