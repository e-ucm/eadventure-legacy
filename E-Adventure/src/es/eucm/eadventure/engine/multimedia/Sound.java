package es.eucm.eadventure.engine.multimedia;

/**
 * This abstract class defines any kind of sound event
 * managed in eAdventure.
 * <p>Any concrete class for a concrete
 * sound format must implement the playOnce() method.
 * stopPlaying() method should also be overriden,
 * calling this class' stopPlaying() method to stop the
 * play loop and actually stopping the sound currently
 * being played.
 * <p>The sound is played in a new thread, so execution is
 * not stopped while playing the sound.
 */
public abstract class Sound extends Thread {

    /**
     * Stores if the sound file must play as a loop
     */
    private boolean loop;

    /**
     * Stores if the sound file is stopped
     */
    protected boolean stop;

    /**
     * Creates a new Sound.
     * @param loop defines whether or not the sound must be played in a loop
     */
    public Sound( boolean loop ) {
        super();
        this.loop = loop;
        stop = false;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Thread#run()
     */
    public void run( ) {
        boolean playedAtLeastOnce = false;
        while( !stop && ( loop || !playedAtLeastOnce ) ) {
            playOnce( );
            playedAtLeastOnce = true;
        }
        finalize();
    }

    /**
     * Starts playing the sound.
     */
    public void startPlaying( ) {
        start( );
    }

    /**
     * Plays the sound just once. This method shouldn't be
     * called manually. Instead, create a new Sound with
     * loop = false, and call startPlaying().
     */
    public abstract void playOnce( );

    /**
     * Stops playing the sound.
     */
    public void stopPlaying( ) {
        stop = true;
    }
    
    /**
     * Finalice and released the resources used for the sound
     */
    public abstract void finalize();
    
    /**
     * Returns whether the sound is still playing
     * @return true if the sound is playing, false otherwise
     */
    public boolean isPlaying( ) {
        return isAlive( );
    }

}
