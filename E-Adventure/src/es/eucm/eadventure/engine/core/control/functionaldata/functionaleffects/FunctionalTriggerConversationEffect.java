package es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects;

import es.eucm.eadventure.common.data.chapter.effects.TriggerConversationEffect;
import es.eucm.eadventure.engine.core.control.Game;

/**
 * An effect that triggers a conversation.
 */
public class FunctionalTriggerConversationEffect extends FunctionalEffect {

    /**
     * Creates a new TriggerConversationEffect.
     * @param targetConversationId the id of the conversation to be triggered
     */
    public FunctionalTriggerConversationEffect( TriggerConversationEffect effect ) {
    	super(effect);
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.data.effects.Effect#triggerEffect()
     */
    public void triggerEffect( ) {
        Game.getInstance( ).setConversation( ((TriggerConversationEffect)effect).getTargetConversationId() );
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
