package es.eucm.eadventure.adventureeditor.data.chapterdata.elements;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.adventureeditor.data.chapterdata.Action;

/**
 * This class holds the data of an item in eAdventure
 */
public class Item extends Element {

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
	 * Changes the list of actions of the item
	 * 
	 * @param actions
	 *            the new list of actions
	 */
	public void setActions( ArrayList<Action> actions ) {
		this.actions = actions;
	}
}
