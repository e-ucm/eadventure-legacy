package es.eucm.eadventure.common.data.chapter.effects;

/**
 * An effect that sets a var with a given value
 */
public class SetValueEffect implements Effect {

	/**
	 * Name of the var to be activated
	 */
	private String idVar;
	
	/**
	 * Value to be set
	 */
	private int value;

	/**
	 * Creates a new Activate effect.
	 * 
	 * @param idVar
	 *            the id of the var to be activated
	 */
	public SetValueEffect( String idVar, int value ) {
		this.idVar = idVar;
		this.value = value;
	}

	public int getType( ) {
		return SET_VALUE;
	}

	/**
	 * Returns the idVar
	 * 
	 * @return String containing the idVar
	 */
	public String getIdVar( ) {
		return idVar;
	}

	/**
	 * Sets the new idVar
	 * 
	 * @param idVar
	 *            New idVar
	 */
	public void setIdVar( String idVar ) {
		this.idVar = idVar;
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
