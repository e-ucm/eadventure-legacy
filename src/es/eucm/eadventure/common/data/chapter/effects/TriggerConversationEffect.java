package es.eucm.eadventure.common.data.chapter.effects;

import es.eucm.eadventure.common.data.HasTargetId;

/**
 * An effect that triggers a conversation.
 */
public class TriggerConversationEffect implements Effect, HasTargetId {

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
	public String getTargetId( ) {
		return targetConversationId;
	}

	/**
	 * Sets the new targetConversationId
	 * 
	 * @param targetConversationId
	 *            New targetConversationId
	 */
	public void setTargetId( String targetConversationId ) {
		this.targetConversationId = targetConversationId;
	}
	
	public Object clone() throws CloneNotSupportedException {
		TriggerConversationEffect tce = (TriggerConversationEffect) super.clone();
		tce.targetConversationId = (targetConversationId != null ? new String(targetConversationId) : null);
		return tce;
	}
}
