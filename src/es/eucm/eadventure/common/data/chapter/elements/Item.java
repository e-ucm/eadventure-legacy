package es.eucm.eadventure.common.data.chapter.elements;

import java.util.ArrayList;
import java.util.List;

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
	 * List of actions associated with the item
	 */
	private List<Action> actions;

	/**
	 * Creates a new Item
	 * 
	 * @param id
	 *            the id of the item
	 */
	public Item( String id ) {
		super( id );
		actions = new ArrayList<Action>( );
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

}
