package es.eucm.eadventure.common.data.chapter.effects;

/**
 * An effect that triggers a conversation.
 */
public class TriggerConversationEffect implements Effect {

	/**
	 * Id of the conversation to be played
	 */
	private String targetConversationId;

	/**
	 * Creates a new TriggerConversationEffect.
	 * 
	 * @param targetConversationId
	 *            the id of the conversation to be triggered
	 */
	public TriggerConversationEffect( String targetConversationId ) {
		this.targetConversationId = targetConversationId;
	}

	public int getType( ) {
		return TRIGGER_CONVERSATION;
	}

	/**
	 * Returns the targetConversationId
	 * 
	 * @return String containing the targetConversationId
	 */
	public String getTargetConversationId( ) {
		return targetConversationId;
	}

	/**
	 * Sets the new targetConversationId
	 * 
	 * @param targetConversationId
	 *            New targetConversationId
	 */
	public void setTargetConversationId( String targetConversationId ) {
		this.targetConversationId = targetConversationId;
	}
}
