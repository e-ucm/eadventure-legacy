package es.eucm.eadventure.adventureeditor.data.chapterdata.elements;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.adventureeditor.data.chapterdata.resources.Resources;

/**
 * This class holds the common data for any element in eAdventure. Here, element means item or character
 */
public abstract class Element {

	/**
	 * The element's id
	 */
	protected String id;

	/**
	 * Documentation of the element.
	 */
	private String documentation;

	/**
	 * The element's name
	 */
	protected String name;

	/**
	 * The element's brief description
	 */
	protected String description;

	/**
	 * The element's detailed description
	 */
	protected String detailedDescription;

	/**
	 * The element's set of resources
	 */
	private List<Resources> resources;

	/**
	 * Creates a new element
	 * 
	 * @param id
	 *            the element's id
	 */
	public Element( String id ) {
		this.id = id;
		this.name = "";
		this.description = "";
		this.detailedDescription = "";
		resources = new ArrayList<Resources>( );
	}

	/**
	 * Returns the element's id
	 * 
	 * @return the element's id
	 */
	public String getId( ) {
		return id;
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
	 * Returns the element's name
	 * 
	 * @return the element's name
	 */
	public String getName( ) {
		return name;
	}

	/**
	 * Returns the element's brief description
	 * 
	 * @return the element's brief description
	 */
	public String getDescription( ) {
		return description;
	}

	/**
	 * Returns the element's detailed description
	 * 
	 * @return the element's detailed description
	 */
	public String getDetailedDescription( ) {
		return detailedDescription;
	}

	/**
	 * Returns the element's list of resources
	 * 
	 * @return the element's list of resources
	 */
	public List<Resources> getResources( ) {
		return resources;
	}

	/**
	 * Sets the a new identifier for the element.
	 * 
	 * @param id
	 *            New identifier
	 */
	public void setId( String id ) {
		this.id = id;
	}

	/**
	 * Changes the documentation of this element.
	 * 
	 * @param documentation
	 *            The new documentation
	 */
	public void setDocumentation( String documentation ) {
		this.documentation = documentation;
	}

	/**
	 * Changes the element's name
	 * 
	 * @param name
	 *            the new name
	 */
	public void setName( String name ) {
		this.name = name;
	}

	/**
	 * Changes the element's brief description
	 * 
	 * @param description
	 *            the new brief description
	 */
	public void setDescription( String description ) {
		this.description = description;
	}

	/**
	 * Changes the element's detailed description
	 * 
	 * @param detailedDescription
	 *            the new detailed description
	 */
	public void setDetailedDescription( String detailedDescription ) {
		this.detailedDescription = detailedDescription;
	}

	/**
	 * Adds some resources to the list of resources
	 * 
	 * @param resources
	 *            the resources to add
	 */
	public void addResources( Resources resources ) {
		this.resources.add( resources );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString( ) {
		StringBuffer sb = new StringBuffer( 40 );

		sb.append( "Name: " );
		sb.append( name );

		sb.append( "\nDescription: " );
		sb.append( description );

		sb.append( "\nDetailed description:" );
		sb.append( detailedDescription );

		sb.append( "\n" );

		return sb.toString( );
	}
}