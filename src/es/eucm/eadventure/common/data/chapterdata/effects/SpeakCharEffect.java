package es.eucm.eadventure.common.data.chapterdata.effects;

/**
 * An effect that makes a character to speak a line of text.
 */
public class SpeakCharEffect implements Effect {

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
	public String getIdTarget( ) {
		return idTarget;
	}

	/**
	 * Sets the new idTarget
	 * 
	 * @param idTarget
	 *            New idTarget
	 */
	public void setIdTarget( String idTarget ) {
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
}
