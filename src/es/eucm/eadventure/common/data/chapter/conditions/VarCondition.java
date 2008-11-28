package es.eucm.eadventure.common.data.chapter.conditions;

/**
 * Specific class for a Var-based Condition  
 * @author Javier
 *
 */
public class VarCondition extends Condition{

	/**
	 * The value of the var-condition
	 */
	private int value;
	
	/**
	 * Constructor
	 * @param flagVar
	 * @param state
	 */
	
	public VarCondition(String flagVar, int state, int value) {
		super(flagVar, state);
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}

}
