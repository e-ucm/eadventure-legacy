package es.eucm.eadventure.common.data.chapter.effects;

import es.eucm.eadventure.common.data.HasTargetId;

/**
 * An effect that increments a var according to a given value
 */
public class IncrementVarEffect implements Effect, HasTargetId {

	/**
	 * Name of the var
	 */
	private String idVar;
	
	/**
	 * Value to be incremented
	 */
	private int value;

	/**
	 * Creates a new Activate effect.
	 * 
	 * @param idVar
	 *            the id of the var to be activated
	 */
	public IncrementVarEffect( String idVar, int value ) {
		this.idVar = idVar;
		this.value = value;
	}

	public int getType( ) {
		return INCREMENT_VAR;
	}

	/**
	 * Returns the idVar
	 * 
	 * @return String containing the idVar
	 */
	public String getTargetId( ) {
		return idVar;
	}

	/**
	 * Sets the new idVar
	 * 
	 * @param idVar
	 *            New idVar
	 */
	public void setTargetId( String idVar ) {
		this.idVar = idVar;
	}

	/**
	 * @return the value
	 */
	public int getIncrement() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setIncrement(int value) {
		this.value = value;
	}
	
	public Object clone() throws CloneNotSupportedException {
		IncrementVarEffect ive = (IncrementVarEffect) super.clone();
		ive.idVar = (idVar != null ? new String(idVar) : null);
		ive.value = value;
		return ive;
	}
}
