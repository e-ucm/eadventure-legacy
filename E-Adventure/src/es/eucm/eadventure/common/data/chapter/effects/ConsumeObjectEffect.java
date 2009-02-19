package es.eucm.eadventure.common.data.chapter.effects;

import es.eucm.eadventure.common.data.HasTargetId;

/**
 * An effect that consumes an object in the inventory
 */
public class ConsumeObjectEffect implements Effect, HasTargetId {

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
	public String getTargetId( ) {
		return idTarget;
	}

	/**
	 * Sets the new idTarget
	 * 
	 * @param idTarget
	 *            New idTarget
	 */
	public void setTargetId( String idTarget ) {
		this.idTarget = idTarget;
	}
	
	public Object clone() throws CloneNotSupportedException {
		ConsumeObjectEffect coe = (ConsumeObjectEffect) super.clone();
		coe.idTarget = (idTarget != null ? new String(idTarget) : null);
		return coe;
	}
}
