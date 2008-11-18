package es.eucm.eadventure.common.data.chapter.effects;

/**
 * An effect that activates a flag
 */
public class ActivateEffect implements Effect {

	/**
	 * Name of the flag to be activated
	 */
	private String idFlag;

	/**
	 * Creates a new Activate effect.
	 * 
	 * @param idFlag
	 *            the id of the flag to be activated
	 */
	public ActivateEffect( String idFlag ) {
		this.idFlag = idFlag;
	}

	public int getType( ) {
		return ACTIVATE;
	}

	/**
	 * Returns the idFlag
	 * 
	 * @return String containing the idFlag
	 */
	public String getIdFlag( ) {
		return idFlag;
	}

	/**
	 * Sets the new idFlag
	 * 
	 * @param idFlag
	 *            New idFlag
	 */
	public void setIdFlag( String idFlag ) {
		this.idFlag = idFlag;
	}
}
