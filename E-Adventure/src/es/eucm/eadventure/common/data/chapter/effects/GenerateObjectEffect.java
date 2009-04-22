package es.eucm.eadventure.common.data.chapter.effects;

import es.eucm.eadventure.common.data.HasTargetId;

/**
 * An effect that generates an object in the inventory.
 */
public class GenerateObjectEffect extends AbstractEffect implements HasTargetId {

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
	    	super();
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
		GenerateObjectEffect goe = (GenerateObjectEffect) super.clone();
		goe.idTarget = (idTarget != null ? new String(idTarget) : null);
		return goe;
	}
}
