package es.eucm.eadventure.engine.core.data.gamedata.conditions;

/**
 * This class manages a condition in eAdventure
 */
public class Condition {

    /**
     * Id of the flag to be checked
     */
    private String id;

    /**
     * Stores if the flag must be active or inactive
     */
    private boolean active;

    /**
     * Creates a new condition
     * @param id the id of the condition
     * @param active determines whether the flag must be activated or deactivated for this
     * condition to be satisfied
     */
    public Condition( String id, boolean active ) {
        this.id = id;
        this.active = active;
    }

    /**
     * Returns the condition id
     * @return the condition id
     */
    public String getId( ) {
        return id;
    }

    /**
     * Returns whether the flag must be activated or deactivated for this
     * condition to be satisfied
     * @return true if the flag must be activated for this condition to be satisfied,
     * false if it has to be deactivated
     */
    public boolean isActive( ) {
        return active;
    }

}
