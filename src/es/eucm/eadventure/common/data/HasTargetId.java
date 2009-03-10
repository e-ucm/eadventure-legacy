package es.eucm.eadventure.common.data;

/**
 * The object has a target id
 */
public interface HasTargetId {

	/**
	 * Get the target id of the object
	 * 
	 * @return The objects target id
	 */
	public String getTargetId();
	
	/**
	 * Set the target id of the object
	 * 
	 * @param id The objects new target id 
	 */
	public void setTargetId(String id);
}
