package es.eucm.eadventure.adventureeditor.data.chapterdata.effects;

/**
 * An effect that consumes an object in the inventory
 */
public class ConsumeObjectEffect implements Effect {

	/**
	 * Id of the item to be consumed
	 */
	private String idTarget;

	/**
	 * Creates a new ConsumeObjectEffect.
	 * 
	 * @param idTarget
	 *            the id of the object to be consumed
	 */
	public ConsumeObjectEffect( String idTarget ) {
		this.idTarget = idTarget;
	}

	public int getType( ) {
		return CONSUME_OBJECT;
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
