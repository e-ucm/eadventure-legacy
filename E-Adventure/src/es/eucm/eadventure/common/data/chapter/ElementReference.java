package es.eucm.eadventure.common.data.chapter;

import es.eucm.eadventure.common.data.chapter.conditions.Conditions;

/**
 * This class holds the data for a element reference in eAdventure
 */
public class ElementReference {

	/**
	 * Id of the element referenced
	 */
	private String idTarget;

	/**
	 * X position of the referenced element
	 */
	private int x;

	/**
	 * Y position of the referenced element
	 */
	private int y;

	/**
	 * Documentation of the element reference.
	 */
	private String documentation;

	/**
	 * Conditions for the element to be placed
	 */
	private Conditions conditions;

	/**
	 * Creates a new ElementReference
	 * 
	 * @param idTarget
	 *            the id of the element that is referenced
	 * @param x
	 *            the horizontal position of the element
	 * @param y
	 *            the vertical position of the element
	 */
	public ElementReference( String idTarget, int x, int y ) {
		this.idTarget = idTarget;
		this.x = x;
		this.y = y;

		documentation = null;
		conditions = new Conditions( );
	}

	/**
	 * Returns the id of the element that is referenced
	 * 
	 * @return the id of the element that is referenced
	 */
	public String getIdTarget( ) {
		return idTarget;
	}

	/**
	 * Returns the horizontal position of the element
	 * 
	 * @return the horizontal position of the element
	 */
	public int getX( ) {
		return x;
	}

	/**
	 * Returns the vertical position of the element
	 * 
	 * @return the vertical position of the element
	 */
	public int getY( ) {
		return y;
	}

	/**
	 * Returns the documentation of the element.
	 * 
	 * @return the documentation of the element
	 */
	public String getDocumentation( ) {
		return documentation;
	}

	/**
	 * Returns the conditions for this element
	 * 
	 * @return the conditions for this element
	 */
	public Conditions getConditions( ) {
		return this.conditions;
	}

	/**
	 * Sets the new id of the referenced element.
	 * 
	 * @param idTarget
	 *            Id of the new referenced element
	 */
	public void setIdTarget( String idTarget ) {
		this.idTarget = idTarget;
	}

	/**
	 * Sets the new position for the element reference.
	 * 
	 * @param x
	 *            X coordinate of the element reference
	 * @param y
	 *            Y coordinate of the element reference
	 */
	public void setPosition( int x, int y ) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Changes the documentation of this element reference.
	 * 
	 * @param documentation
	 *            The new documentation
	 */
	public void setDocumentation( String documentation ) {
		this.documentation = documentation;
	}

	/**
	 * Changes the conditions for this element
	 * 
	 * @param conditions
	 *            the new conditions
	 */
	public void setConditions( Conditions conditions ) {
		this.conditions = conditions;
	}
}
