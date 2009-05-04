package es.eucm.eadventure.common.data.chapter.conditions;

/**
 * Specific class for a Var-based Condition  
 * @author Javier
 *
 */
public class VarCondition extends Condition{

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
	 * MIN VALUE
	 */
	public static final int MIN_VALUE = 0;
	
	/**
	 * MAX VALUE
	 */
	public static final int MAX_VALUE = Integer.MAX_VALUE;
	
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
		super(VAR_CONDITION, flagVar, state);
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public Integer getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Integer value) {
		this.value = value;
	}
	
	public Object clone() throws CloneNotSupportedException {
		VarCondition vc = (VarCondition) super.clone();
		vc.id = (id != null ? new String(id) : null);
		vc.state = state;
		vc.type = type;
		vc.value = value;
		return vc;
	}

}
