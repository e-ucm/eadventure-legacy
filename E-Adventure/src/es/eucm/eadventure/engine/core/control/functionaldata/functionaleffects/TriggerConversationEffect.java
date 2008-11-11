package es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects;

import es.eucm.eadventure.engine.core.control.Game;

/**
 * An effect that triggers a conversation.
 */
public class TriggerConversationEffect extends FunctionalEffect {

    /**
     * Id of the conversation to be played
     */
    private String targetConversationId;

    /**
     * Creates a new TriggerConversationEffect.
     * @param targetConversationId the id of the conversation to be triggered
     */
    public TriggerConversationEffect( String targetConversationId ) {
    	super(null);
        this.targetConversationId = targetConversationId;
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#triggerEffect()
     */
    public void triggerEffect( ) {
        Game.getInstance( ).setConversation( targetConversationId );
        Game.getInstance( ).setState( Game.STATE_CONVERSATION ); 
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#isInstantaneous()
     */
    public boolean isInstantaneous( ) {
        return false;
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects.FunctionalEffect#isStillRunning()
     */
    public boolean isStillRunning( ) {
        return false;
    }
}
