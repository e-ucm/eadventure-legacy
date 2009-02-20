package es.eucm.eadventure.common.data.chapter;

import es.eucm.eadventure.common.data.Documented;
import es.eucm.eadventure.common.data.HasTargetId;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;

/**
 * This class holds the data of a conversation reference in eAdventure
 */
public class ConversationReference implements Cloneable, Documented, HasTargetId{

	/**
	 * Id of the target conversation
	 */
	private String idTarget;

	/**
	 * Documentation of the conversation reference.
	 */
	private String documentation;

	/**
	 * Conditions to trigger the conversation
	 */
	private Conditions conditions;

	/**
	 * Creates a new ConversationReference
	 * 
	 * @param idTarget
	 *            the id of the conversation that is referenced
	 */
	public ConversationReference( String idTarget ) {
		this.idTarget = idTarget;

		documentation = null;
		conditions = new Conditions( );
	}

	/**
	 * Returns the id of the conversation that is referenced
	 * 
	 * @return the id of the conversation that is referenced
	 */
	public String getTargetId( ) {
		return idTarget;
	}

	/**
	 * Returns the documentation of the conversation.
	 * 
	 * @return the documentation of the conversation
	 */
	public String getDocumentation( ) {
		return documentation;
	}

	/**
	 * Returns the conditions for this conversation
	 * 
	 * @return the conditions for this conversation
	 */
	public Conditions getConditions( ) {
		return conditions;
	}

	/**
	 * Sets the new conversation id target.
	 * 
	 * @param idTarget
	 *            Id of the referenced conversation
	 */
	public void setTargetId( String idTarget ) {
		this.idTarget = idTarget;
	}

	/**
	 * Changes the documentation of this conversation reference.
	 * 
	 * @param documentation
	 *            The new documentation
	 */
	public void setDocumentation( String documentation ) {
		this.documentation = documentation;
	}

	/**
	 * Changes the conditions for this conversation
	 * 
	 * @param conditions
	 *            the new conditions
	 */
	public void setConditions( Conditions conditions ) {
		this.conditions = conditions;
	}
	
	public Object clone() throws CloneNotSupportedException {
		ConversationReference cr = (ConversationReference) super.clone();
		cr.conditions = (conditions != null ? (Conditions) conditions.clone() : null);
		cr.documentation = (documentation != null ? new String(documentation) : null);
		cr.idTarget = (idTarget != null ? new String(idTarget) : null);
		return cr;
	}
}
