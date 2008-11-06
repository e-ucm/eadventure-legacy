package es.eucm.eadventure.adventureeditor.data.chapterdata.effects;

/**
 * An effect that plays a sound
 */
public class PlaySoundEffect implements Effect {

	/**
	 * Whether the sound must be played in background
	 */
	private boolean background;

	/**
	 * The path to the sound file
	 */
	private String path;

	/**
	 * Creates a new PlaySoundEffect
	 * 
	 * @param background
	 *            whether to play the sound in background
	 * @param path
	 *            path to the sound file
	 */
	public PlaySoundEffect( boolean background, String path ) {
		this.background = background;
		this.path = path;
	}

	public int getType( ) {
		return PLAY_SOUND;
	}

	/**
	 * Returns whether the sound must be played in background
	 * 
	 * @return True if the sound must be played in background, false otherwise
	 */
	public boolean isBackground( ) {
		return background;
	}

	/**
	 * Sets the value which tells if the sound must be played in background
	 * 
	 * @param background
	 *            New value for background
	 */
	public void setBackground( boolean background ) {
		this.background = background;
	}

	/**
	 * Returns the path of the sound
	 * 
	 * @return Path of the sound
	 */
	public String getPath( ) {
		return path;
	}

	/**
	 * Sets the new path for the sound to be played
	 * 
	 * @param path
	 *            New path of the sound
	 */
	public void setPath( String path ) {
		this.path = path;
	}

}
