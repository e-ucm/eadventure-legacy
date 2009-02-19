package es.eucm.eadventure.common.data.chapter.effects;

import es.eucm.eadventure.common.data.HasTargetId;

/**
 * An effect that makes a character to speak a line of text.
 */
public class SpeakCharEffect implements Effect , HasTargetId{

	/**
	 * Id of the character who will talk
	 */
	private String idTarget;

	/**
	 * Text for the character to speak
	 */
	private String line;

	/**
	 * Creates a new SpeakCharEffect.
	 * 
	 * @param idTarget
	 *            the id of the character who will speak
	 * @param line
	 *            the text to be spoken
	 */
	public SpeakCharEffect( String idTarget, String line ) {
		this.idTarget = idTarget;
		this.line = line;
	}

	public int getType( ) {
		return SPEAK_CHAR;
	}

	/**
	 * Returns the idTarget
	 * 
	 * @return String containing the idTarget
	 */
	public String getTargetId( ) {
		return idTarget;
	}

	/**
	 * Sets the new idTarget
	 * 
	 * @param idTarget
	 *            New idTarget
	 */
	public void setTargetId( String idTarget ) {
		this.idTarget = idTarget;
	}

	/**
	 * Returns the line that the character will speak
	 * 
	 * @return The line of the character
	 */
	public String getLine( ) {
		return line;
	}

	/**
	 * Sets the line that the character will speak
	 * 
	 * @param line
	 *            New line
	 */
	public void setLine( String line ) {
		this.line = line;
	}
	
	public Object clone() throws CloneNotSupportedException {
		SpeakCharEffect sce = (SpeakCharEffect) super.clone();
		sce.idTarget = (idTarget != null ? new String(idTarget) : null);
		sce.line = (line != null ? new String(line) : null);
		return sce;
	}
}
