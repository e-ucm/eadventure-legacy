package es.eucm.eadventure.engine.core.data.gamedata.elements;

import java.util.ArrayList;

import es.eucm.eadventure.engine.core.data.gamedata.resources.Resources;

/**
 * This class holds the common data for any element in eAdventure.
 * Here, element means item or character
 */
public abstract class Element {
    
    /**
     * The element's id
     */
    protected String id;

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
    private ArrayList<Resources> resources;
    
    /**
     * Creates a new element
     * @param id the element's id
     */
    public Element( String id ) {
        this.id = id;
        this.name = null;
        this.description = null;
        this.detailedDescription = null;
        resources = new ArrayList<Resources>( );
    }

    /**
     * Creates a new Element
     * @param id the element's id
     * @param name the element's name
     * @param description the element's description
     * @param detailedDescription the element's detailed description
     */
    public Element( String id, String name, String description, String detailedDescription ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.detailedDescription = detailedDescription;
        resources = new ArrayList<Resources>( );
    }

    /**
     * Returns the element's id
     * @return the element's id
     */
    public String getId( ) {
        return id;
    }

    /**
     * Returns the element's name
     * @return the element's name
     */
    public String getName( ) {
        return name;
    }
    
    /**
     * Returns the element's brief description
     * @return the element's brief description
     */
    public String getDescription( ) {
        return description;
    }
    
    /**
     * Returns the element's detailed description
     * @return the element's detailed description
     */
    public String getDetailedDescription( ) {
        return detailedDescription;
    }
    
    /**
     * Returns the element's list of resources
     * @return the element's list of resources
     */
    public ArrayList<Resources> getResources( ) {
        return resources;
    }
    
    /**
     * Changes the element's name
     * @param name the new name
     */
    public void setName( String name ) {
        this.name = name;
    }

    /**
     * Changes the element's brief description
     * @param description the new brief description
     */
    public void setDescription( String description ) {
        this.description = description;
    }

    /**
     * Changes the element's detailed description
     * @param detailedDescription the new detailed description
     */
    public void setDetailedDescription( String detailedDescription ) {
        this.detailedDescription = detailedDescription;
    }
    
    /**
     * Adds some resources to the list of resources
     * @param resources the resources to add
     */
    public void addResources( Resources resources ) {
        this.resources.add( resources );
    }

    /*
     *  (non-Javadoc)
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