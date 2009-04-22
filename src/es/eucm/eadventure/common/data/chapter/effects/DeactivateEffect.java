package es.eucm.eadventure.common.data.chapter.effects;

import es.eucm.eadventure.common.data.HasTargetId;

/**
 * An effect that deactivates a flag.
 */
public class DeactivateEffect extends AbstractEffect implements  HasTargetId {

	/**
	 * Name of the flag to be activated
	 */
	private String idFlag;

	/**
	 * Creates a new DeactivateEffect.
	 * 
	 * @param idFlag
	 *            the id of the flag to be deactivated
	 */
	public DeactivateEffect( String idFlag ) {
	    	super();
		this.idFlag = idFlag;
	}

	public int getType( ) {
		return DEACTIVATE;
	}

	/**
	 * Returns the idFlag
	 * 
	 * @return String containing the idFlag
	 */
	public String getTargetId( ) {
		return idFlag;
	}

	/**
	 * Sets the new idFlag
	 * 
	 * @param idFlag
	 *            New idFlag
	 */
	public void setTargetId( String idFlag ) {
		this.idFlag = idFlag;
	}
	
	public Object clone() throws CloneNotSupportedException {
		DeactivateEffect de = (DeactivateEffect) super.clone();
		de.idFlag = (idFlag != null ? new String(idFlag) : null);
		return de;
	}
}
