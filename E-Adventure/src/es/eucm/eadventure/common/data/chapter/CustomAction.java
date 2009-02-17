package es.eucm.eadventure.common.data.chapter;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.Named;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;
import es.eucm.eadventure.common.data.chapter.effects.Effects;
import es.eucm.eadventure.common.data.chapter.resources.Resources;

/**
 * An customizable action that can be done during the game
 * 
 * @author Eugenio Marchiori
 *
 */
public class CustomAction extends Action implements Named {
	
	/**
	 * Name of the action
	 */
	private String name;
	
	/**
	 * Resources used by the action (such as icons for the button)
	 */
	private List<Resources> resources;
	
	
	/**
	 * Default constructor for actions that only need one object
	 * 
	 * @param type The type of the custom action
	 */
	public CustomAction(int type) {
		super(type);
		resources = new ArrayList<Resources>();
	}

	/**
	 * Constructor with id, for actions that need two objects
	 * 
 	 * @param type the type of the custom action
	 * @param idTarget the id of the other object
	 */
	public CustomAction( int type, String idTarget ) {
		super( type, idTarget );
		resources = new ArrayList<Resources>();
	}

	/**
	 * Constructor with conditions and effects, for actions that only
	 * need one object
	 * 
	 * @param type the type of the action
	 * @param conditions the conditions of the action
	 * @param effects the effects of the action
	 */
	public CustomAction( int type, Conditions conditions, Effects effects ) {
		super( type, conditions, effects );
		resources = new ArrayList<Resources>();
	}

	/**
	 * Constructor with conditions and effects, for actions that need
	 * two objects
	 * 
	 * @param type the type of the action
	 * @param idTarget the id of the other object
	 * @param conditions the conditions of the action
	 * @param effects the effects of the action
	 */
	public CustomAction( int type, String idTarget, Conditions conditions, Effects effects ) {
		super( type, idTarget, conditions, effects);
		resources = new ArrayList<Resources>();
	}
	
	/**
	 * Constructor that uses a default action
	 * 
	 * @param action a normal action
	 */
	public CustomAction(Action action) {
		super(action.getType(), action.getIdTarget(), action.getConditions(), action.getEffects());
		resources = new ArrayList<Resources>();
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the name value
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param resources the resources to add
	 */
	public void addResources(Resources resources) {
		this.resources.add(resources);
	}

	/**
	 * @return the list of resources
	 */
	public List<Resources> getResources() {
		return resources;
	}
	
	public Object clone() throws CloneNotSupportedException {
		CustomAction ca = (CustomAction) super.clone();
		ca.name = (name != null ? new String(name) : null);
		if (resources != null) {
			ca.resources = new ArrayList<Resources>();
			for (Resources r : resources)
				ca.resources.add((Resources) r.clone());
		}
		return ca;
	}

}
