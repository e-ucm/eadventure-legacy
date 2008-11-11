package es.eucm.eadventure.editor.gui.audio;

/**
 * This abstract class defines any kind of sound event managed in eAdventure. <p>Any concrete class for a concrete
 * sound format must implement the playOnce() method. stopPlaying() method should also be overriden, calling this class'
 * stopPlaying() method to stop the play loop and actually stopping the sound currently being played. <p>The sound is
 * played in a new thread, so execution is not stopped while playing the sound.
 */
public abstract class Sound extends Thread {

	/**
	 * Creates a new Sound.
	 */
	public Sound( ) {
		super( );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	public void run( ) {
		playOnce( );
	}

	/**
	 * Starts playing the sound.
	 */
	public void startPlaying( ) {
		start( );
	}

	/**
	 * Plays the sound just once. This method shouldn't be called manually. Instead, create a new Sound with loop =
	 * false, and call startPlaying().
	 */
	protected abstract void playOnce( );

	/**
	 * Stops playing the sound.
	 */
	public abstract void stopPlaying( );

}
