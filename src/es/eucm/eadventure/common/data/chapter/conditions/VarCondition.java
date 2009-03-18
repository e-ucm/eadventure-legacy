package es.eucm.eadventure.common.data.chapter.conditions;

/**
 * Specific class for a Var-based Condition  
 * @author Javier
 *
 */
public class VarCondition extends Condition{

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
		super(flagVar, state);
		this.value = value;
		this.type = VAR_CONDITION;
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