package es.eucm.eadventure.common.data.chapter;

import es.eucm.eadventure.common.data.chapter.conditions.Conditions;
import es.eucm.eadventure.common.data.chapter.effects.Effects;

/**
 * An action that can be done during the game.
 */
public class Action {

	/**
	 * An action of type examine.
	 */
	public static final int EXAMINE = 0;

	/**
	 * An action of type grab.
	 */
	public static final int GRAB = 1;

	/**
	 * An action of type give-to.
	 */
	public static final int GIVE_TO = 2;

	/**
	 * An action of type use-with.
	 */
	public static final int USE_WITH = 3;

	/**
	 * An action of type use
	 */
	public static final int USE = 4;
	
	/**
	 * A custom action
	 */
	public static final int CUSTOM = 5;
	
	/**
	 * A custom interaction action
	 */
	public static final int CUSTOM_INTERACT = 6;
	
	/**
	 * Stores the action type
	 */
	private int type;

	/**
	 * Documentation of the action.
	 */
	private String documentation;

	/**
	 * Id of the target of the action (in give to and use with)
	 */
	private String idTarget;

	/**
	 * Conditions of the action
	 */
	private Conditions conditions;

	/**
	 * Effects of performing the action
	 */
	private Effects effects;

	/**
	 * Indicates whether the character needs to go up to the object
	 */
	private boolean needsGoTo;
	
	/**
	 * Indicates the minimum distance the character should leave
	 * between the object and himself
	 */
	private int keepDistance;
	
	/**
	 * Constructor.
	 * 
	 * @param type
	 *            The type of the action
	 */
	public Action( int type ) {
		this( type, null, new Conditions( ), new Effects( ) );
	}

	/**
	 * Constructor.
	 * 
	 * @param type
	 *            The type of the action
	 * @param idTarget
	 *            The target of the action
	 */
	public Action( int type, String idTarget ) {
		this( type, idTarget, new Conditions( ), new Effects( ) );
	}

	/**
	 * Constructor
	 * 
	 * @param type
	 *            The type of the action
	 * @param conditions
	 *            The conditions of the action (must not be null)
	 * @param effects
	 *            The effects of the action (must not be null)
	 */
	public Action( int type, Conditions conditions, Effects effects ) {
		this( type, null, conditions, effects );
	}

	/**
	 * Constructor
	 * 
	 * @param type
	 *            The type of the action
	 * @param idTarget
	 *            The target of the action
	 * @param conditions
	 *            The conditions of the action (must not be null)
	 * @param effects
	 *            The effects of the action (must not be null)
	 */
	public Action( int type, String idTarget, Conditions conditions, Effects effects ) {
		this.type = type;
		this.idTarget = idTarget;
		this.conditions = conditions;
		this.effects = effects;
		documentation = null;
	}

	/**
	 * Returns the type of the action.
	 * 
	 * @return the type of the action
	 */
	public int getType( ) {
		return type;
	}

	/**
	 * Returns the documentation of the action.
	 * 
	 * @return the documentation of the action
	 */
	public String getDocumentation( ) {
		return documentation;
	}

	/**
	 * Returns the target of the action.
	 * 
	 * @return the target of the action
	 */
	public String getIdTarget( ) {
		return idTarget;
	}

	/**
	 * Returns the conditions of the action.
	 * 
	 * @return the conditions of the action
	 */
	public Conditions getConditions( ) {
		return conditions;
	}

	/**
	 * Returns the effects of the action.
	 * 
	 * @return the effects of the action
	 */
	public Effects getEffects( ) {
		return effects;
	}

	/**
	 * Changes the documentation of this action.
	 * 
	 * @param documentation
	 *            The new documentation
	 */
	public void setDocumentation( String documentation ) {
		this.documentation = documentation;
	}

	/**
	 * Changes the id target of this action.
	 * 
	 * @param idTarget
	 *            The new id target
	 */
	public void setIdTarget( String idTarget ) {
		this.idTarget = idTarget;
	}

	/**
	 * Changes the conditions for this next scene
	 * 
	 * @param conditions
	 *            the new conditions
	 */
	public void setConditions( Conditions conditions ) {
		this.conditions = conditions;
	}

	/**
	 * Changes the effects for this next scene
	 * 
	 * @param effects
	 *            the new effects
	 */
	public void setEffects( Effects effects ) {
		this.effects = effects;
	}
	
	/**
	 * @return the needsGoTo
	 */
	public boolean isNeedsGoTo() {
		return needsGoTo;
	}

	/**
	 * @param needsGoTo the needsGoTo to set
	 */
	public void setNeedsGoTo(boolean needsGoTo) {
		this.needsGoTo = needsGoTo;
	}

	/**
	 * @return the keepDistance
	 */
	public int getKeepDistance() {
		return keepDistance;
	}

	/**
	 * @param keepDistance the keepDistance to set
	 */
	public void setKeepDistance(int keepDistance) {
		this.keepDistance = keepDistance;
	}

}
