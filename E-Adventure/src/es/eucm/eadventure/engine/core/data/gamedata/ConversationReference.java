package es.eucm.eadventure.engine.core.data.gamedata;

import es.eucm.eadventure.common.data.chapterdata.conditions.Conditions;

/**
 * This class holds the data of a conversation reference in eAdventure
 */
public class ConversationReference {

    /**
     * Id of the target conversation
     */
    private String idTarget;

    /**
     * Conditions to trigger the conversation
     */
    private Conditions conditions;

    /**
     * Creates a new ConversationReference
     * @param idTarget the id of the conversation that is referenced
     */
    public ConversationReference( String idTarget ) {
        this.idTarget = idTarget;
        
        conditions = new Conditions( );
    }

    /**
     * Returns the id of the conversation that is referenced
     * @return the id of the conversation that is referenced
     */
    public String getIdTarget( ) {
        return idTarget;
    }

    /**
     * Returns the conditions for this conversation
     * @return the conditions for this conversation
     */
    public Conditions getConditions( ) {
        return this.conditions;
    }

    /**
     * Changes the conditions for this conversation
     * @param conditions the new conditions
     */
    public void setConditions( Conditions conditions ) {
        this.conditions = conditions;
    }
}
