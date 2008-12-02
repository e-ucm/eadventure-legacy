package es.eucm.eadventure.engine.core.control.animations;

import java.awt.Image;


/**
 * This is the interface for all kinds of animations in the game.
 */
public interface Animation {
    
    /**
     * Starts this animation over from the beginning.
     * Must be implemented synchronized
     */
    public void start( );    
    
    /**
     * Returns if the animation is being played for first time or not.
     * @return True if the animation reached to and end and started over again, false otherwise
     */
    public boolean isPlayingForFirstTime();

    /**
     * Updates this animation's current image (frame) if neccesary.
     */
    public void update( long elapsedTime );

    /**
     * Get the current Image for the animation
     * 
     * @return The current Image for the animation
     */
    public Image getImage();
    
    /**
     * Moves the animation forward to the next image. Returns true
     * if the animation has finished.
     * 
     * @return True if the animation has finished
     */
    public boolean nextImage();
    

}