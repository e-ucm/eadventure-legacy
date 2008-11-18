package es.eucm.eadventure.common.data.chapter.effects;

/**
 * An effect that generates an object in the inventory.
 */
public class GenerateObjectEffect implements Effect {

	/**
	 * Id of the item to be generated
	 */
	private String idTarget;

	/**
	 * Creates a new GenerateObjectEffect.
	 * 
	 * @param idTarget
	 *            the id of the object to be generated
	 */
	public GenerateObjectEffect( String idTarget ) {
		this.idTarget = idTarget;
	}

	public int getType( ) {
		return GENERATE_OBJECT;
	}

	/**
	 * Returns the idTarget
	 * 
	 * @return String containing the idTarget
	 */
	public String getIdTarget( ) {
		return idTarget;
	}

	/**
	 * Sets the new idTarget
	 * 
	 * @param idTarget
	 *            New idTarget
	 */
	public void setIdTarget( String idTarget ) {
		this.idTarget = idTarget;
	}
}
