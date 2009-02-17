package es.eucm.eadventure.common.data.chapter.effects;

/**
 * An effect that raises a "bookscene".
 */
public class TriggerBookEffect implements Effect {

	/**
	 * Id of the book to be shown
	 */
	private String targetBookId;

	/**
	 * Creates a new TriggerBookEffect
	 * 
	 * @param targetBookId
	 *            the id of the book to be shown
	 */
	public TriggerBookEffect( String targetBookId ) {
		this.targetBookId = targetBookId;
	}

	public int getType( ) {
		return TRIGGER_BOOK;
	}

	/**
	 * Returns the targetBookId
	 * 
	 * @return String containing the targetBookId
	 */
	public String getTargetBookId( ) {
		return targetBookId;
	}

	/**
	 * Sets the new targetBookId
	 * 
	 * @param targetBookId
	 *            New targetBookId
	 */
	public void setTargetBookId( String targetBookId ) {
		this.targetBookId = targetBookId;
	}
	
	public Object clone() throws CloneNotSupportedException {
		TriggerBookEffect tbe = (TriggerBookEffect) super.clone();
		tbe.targetBookId = (targetBookId != null ? new String(targetBookId) : null);
		return tbe;
	}
}
