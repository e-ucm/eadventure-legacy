package es.eucm.eadventure.common.data.chapterdata.effects;

/**
 * An effect that makes the player to speak a line of text.
 */
public class SpeakPlayerEffect implements Effect {

	/**
	 * Text for the player to speak
	 */
	private String line;

	/**
	 * Creates a new SpeakPlayerEffect.
	 * 
	 * @param line
	 *            the text to be spoken
	 */
	public SpeakPlayerEffect( String line ) {
		this.line = line;
	}

	public int getType( ) {
		return SPEAK_PLAYER;
	}

	/**
	 * Returns the line that the player will speak
	 * 
	 * @return The line of the player
	 */
	public String getLine( ) {
		return line;
	}

	/**
	 * Sets the line that the player will speak
	 * 
	 * @param line
	 *            New line
	 */
	public void setLine( String line ) {
		this.line = line;
	}
}
