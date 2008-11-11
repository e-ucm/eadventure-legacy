package es.eucm.eadventure.engine.core.data.gamedata.elements;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.engine.core.data.gamedata.Action;

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
    private ArrayList<Action> actions;

    /**
     * Creates a new Item
     * @param id the id of the item
     */
    public Item( String id ) {
        super( id );
        actions = new ArrayList<Action>( );
    }

    /**
     * Creates a new Item
     * @param id the id of the item
     * @param name the name of the item
     * @param description the brief description of the item
     * @param detailedDescription the detailed description of the item
     */
    public Item( String id, String name, String description, String detailedDescription ) {
        super( id, name, description, detailedDescription );
        actions = new ArrayList<Action>( );

    }

    /**
     * Adds an action to this item
     * @param action the action to add
     */
    public void addAction( Action action ) {
        actions.add( action );
    }

    /**
     * Returns the action in the given position
     * @param index the position of the action
     * @return the action in the given position
     */
    public Action getAction( int index ) {
        return actions.get( index );
    }

    /**
     * Returns the list of actions of the item
     * @return the list of actions of the item
     */
    public List<Action> getActions( ) {
        return actions;
    }

    /**
     * Changes the list of actions of the item
     * @param actions the new list of actions
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
