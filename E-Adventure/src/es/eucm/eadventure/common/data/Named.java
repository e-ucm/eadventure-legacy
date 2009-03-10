package es.eucm.eadventure.common.data;

/**
 * The object has a name
 */
public interface Named {

	/**
	 * Set the name of the object
	 * 
	 * @param name The new name of the object
	 */
	public void setName(String name);
	
	/**
	 * Get the name of the object
	 * 
	 * @return The name of the object
	 */
	public String getName();
	
}
