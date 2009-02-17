package es.eucm.eadventure.common.data.chapter.elements;

import es.eucm.eadventure.common.data.chapter.Action;

/**
 * This class holds the data of an item in eAdventure
 */
public class Item extends Element {

	/**
     * The tag of the item's image
     */
    public static final String RESOURCE_TYPE_IMAGE = "image";
    
    /**
     * The tag of the item's icon
     */
    public static final String RESOURCE_TYPE_ICON = "icon";

	/**
	 * Creates a new Item
	 * 
	 * @param id
	 *            the id of the item
	 */
	public Item( String id ) {
		super( id );
	}
	
	/**
	 * Convenient constructor for ActiveAreas 
	 */
	public Item( String id, String name, String description, String detailedDescription ){
		this (id);
		this.name = name;
		this.description = description;
		this.detailedDescription = detailedDescription;
	}

	
    /*
     *  (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString( ) {
        StringBuffer sb = new StringBuffer( 40 );

        sb.append( "\n" );
        sb.append( super.toString( ) );
        for( Action action : actions )
            sb.append( action.toString( ) );

        sb.append( "\n" );

        return sb.toString( );
    }

	public Object clone() throws CloneNotSupportedException {
		Item i = (Item) super.clone();
		return i;
	}
}
