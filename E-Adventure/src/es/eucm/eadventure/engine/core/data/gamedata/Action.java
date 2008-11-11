package es.eucm.eadventure.engine.core.data.gamedata;

import es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects.FunctionalEffects;
import es.eucm.eadventure.common.data.chapterdata.conditions.Conditions;

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
     * Stores the action type
     */
    private int type;

    /**
     * Id of the target of the action (in give to and use with)
     */
    private String idTarget;

    /**
     * Conditions of the action
     */
    private Conditions conditions;

    /**
     * FunctionalEffects of performing the action
     */
    private FunctionalEffects effects;

    /**
     * Creates a new Action.
     * @param type the type of the action
     * @param idTarget the target of the action
     * @param conditions the conditions of the action
     * @param effects the effects of the action
     */
    public Action( int type, String idTarget, Conditions conditions, FunctionalEffects effects ) {
        this.type = type;
        this.idTarget = idTarget;
        this.conditions = conditions;
        this.effects = effects;
    }

    /**
     * Creates a new Action.
     * @param type the type of the action
     * @param conditions the conditions of the action
     * @param effects the effects of the action
     */
    public Action( int type, Conditions conditions, FunctionalEffects effects ) {
        this.type = type;
        this.idTarget = null;
        this.conditions = conditions;
        this.effects = effects;
    }

    /**
     * Returns the type of the action.
     * @return the type of the action
     */
    public int getType( ) {
        return type;
    }

    /** 
     * Returns the target of the action.
     * @return the target of the action
     */
    public String getIdTarget( ) {
        return idTarget;
    }

    /**
     * Returns the conditions of the action.
     * @return the conditions of the action
     */
    public Conditions getConditions( ) {
        return conditions;
    }

    /**
     * Returns the effects of the action.
     * @return the effects of the action
     */
    public FunctionalEffects getEffects( ) {
        return effects;
    }
}
