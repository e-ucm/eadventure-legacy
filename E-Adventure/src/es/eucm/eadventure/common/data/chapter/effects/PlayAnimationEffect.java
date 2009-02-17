package es.eucm.eadventure.common.data.chapter.effects;

/**
 * An effect that plays a sound
 */
public class PlayAnimationEffect implements Effect {

	/**
	 * The path to the base animation file
	 */
	private String path;

	/**
	 * Upper-left coordinate of the animation in the X
	 */
	private int x;

	/**
	 * Upper-left coordinate of the animation in the Y
	 */
	private int y;

	/**
	 * Creates a new FunctionalPlayAnimationEffect.
	 * 
	 * @param path
	 *            path to the animation file
	 * @param x
	 *            X coordinate for the animation to play
	 * @param y
	 *            Y coordinate for the animation to play
	 */
	public PlayAnimationEffect( String path, int x, int y ) {
		this.path = path;
		this.x = x;
		this.y = y;
	}

	public int getType( ) {
		return PLAY_ANIMATION;
	}

	/**
	 * Returns the path of the animation.
	 * 
	 * @return Path of the animation
	 */
	public String getPath( ) {
		return path;
	}

	/**
	 * Sets the new path for the animation.
	 * 
	 * @param path
	 *            New path for the animation
	 */
	public void setPath( String path ) {
		this.path = path;
	}

	/**
	 * Returns the destiny x position for the animation.
	 * 
	 * @return Destiny x coord
	 */
	public int getX( ) {
		return x;
	}

	/**
	 * Returns the destiny y position for the animation.
	 * 
	 * @return Destiny y coord
	 */
	public int getY( ) {
		return y;
	}

	/**
	 * Sets the new destiny position for the animation.
	 * 
	 * @param x
	 *            New destiny X coordinate
	 * @param y
	 *            New destiny Y coordinate
	 */
	public void setDestiny( int x, int y ) {
		this.x = x;
		this.y = y;
	}

	public Object clone() throws CloneNotSupportedException {
		PlayAnimationEffect pae = (PlayAnimationEffect) super.clone();
		pae.path = (path != null ? new String(path) : null);
		pae.x = x;
		pae.y = y;
		return pae;
	}
}
