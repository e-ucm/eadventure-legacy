package es.eucm.eadventure.common.data.chapter.elements;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.Described;
import es.eucm.eadventure.common.data.Documented;
import es.eucm.eadventure.common.data.Named;
import es.eucm.eadventure.common.data.chapter.Action;
import es.eucm.eadventure.common.data.chapter.resources.Resources;

/**
 * This class holds the common data for any element in eAdventure. Here, element means item or character
 */
public abstract class Element implements Cloneable, Named, Documented, Described {

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
	 * List of actions associated with the item
	 */
	protected List<Action> actions;

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
		actions = new ArrayList<Action>( );
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
	
	/**
	 * Adds an action to this item
	 * 
	 * @param action
	 *            the action to add
	 */
	public void addAction( Action action ) {
		actions.add( action );
	}

	/**
	 * Returns the list of actions of the item
	 * 
	 * @return the list of actions of the item
	 */
	public List<Action> getActions( ) {
		return actions;
	}

	/**
	 * Returns the size of the list of actions
	 * @return Size (int) of the list of actions
	 */
	public int getActionsCount(){
		if (actions == null)
			return 0;
		else
			return actions.size();
	}
	
	/**
	 * Returns Action object at place i
	 * @param i
	 * @return
	 */
	public Action getAction (int i){
		return actions.get(i);
	}
	
	/**
	 * Changes the list of actions of the item
	 * 
	 * @param actions
	 *            the new list of actions
	 */
	public void setActions( ArrayList<Action> actions ) {
		this.actions = actions;
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
	
	public Object clone() throws CloneNotSupportedException {
		Element e = (Element) super.clone();
		if (actions != null) {
			e.actions = new ArrayList<Action>();
			for (Action action : actions) {
				e.actions.add((Action) action.clone());
			}
		}
		e.description = (description != null ? new String(description) : null);
		e.detailedDescription = (detailedDescription != null ? new String(detailedDescription) : null);
		e.documentation = (documentation != null ? new String(documentation) : null);
		e.id = (id != null ? new String(id) : null);
		e.name = (name != null ? new String(name) : null);
		if (resources != null) {
			e.resources = new ArrayList<Resources>();
			for (Resources r : resources) {
				e.resources.add((Resources) r.clone());
			}
		}
		return e;
	}

}