package es.eucm.eadventure.engine.core.control.animations;

/**
 * This class implements an Animation, based on an ImageSet.
 */
public class ImageAnimation extends ImageSet {
    
	/**
     * Time per animation frame.
     */
    private static final int TIME_PER_FRAME = 100;
    
    /**
     * Accumulated played time of the animation.
     */
    private long accumulatedAnimationTime;
    
    /**
     * Stores if the animation is has looped or not.
     */
    private boolean playingFirstTime;

    public ImageAnimation( ) {
        super( );
        accumulatedAnimationTime = 0;
        playingFirstTime = true;
    }

    /**
     * Starts this animation over from the beginning.
     */
    public synchronized void start( ) {
        super.start( );
        accumulatedAnimationTime = 0;
        playingFirstTime = true;
    }
    
    /**
     * Returns if the animation is being played for first time or not.
     * @return True if the animation reached to and end and started over again, false otherwise
     */
    public boolean isPlayingForFirstTime( ) {
        return playingFirstTime;
    }

    /**
     * Updates this animation's current image (frame) if neccesary.
     */
    public void update( long elapsedTime ) {
        if( imageSet.length > 1 ) {
            // Add the elapsed time to the accumulated
            accumulatedAnimationTime += elapsedTime;

            // Skip frame for every TIME_PER_FRAME miliseconds
            while( accumulatedAnimationTime > TIME_PER_FRAME ) {
                accumulatedAnimationTime -= TIME_PER_FRAME;
                currentFrameIndex++;
            }
            
            if( currentFrameIndex >= imageSet.length ) {
                currentFrameIndex %= imageSet.length;
                playingFirstTime = false;
            }
        }
    }
}
