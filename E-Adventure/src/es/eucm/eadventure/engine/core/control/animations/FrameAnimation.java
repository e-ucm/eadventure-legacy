package es.eucm.eadventure.engine.core.control.animations;

import java.awt.Image;

/**
 * Class that represents an animation made up of frames in the engine. It uses
 * the logic in {@link es.eucm.eadventure.common.data.animation.Animation}.
 * 
 * 
 * @author Eugenio Marchiori
 */
public class FrameAnimation implements Animation {

	/**
	 * The animation with the frames and the logic
	 */
	private es.eucm.eadventure.common.data.animation.Animation animation;
		
	/**
	 * The time accumulated in the playing of the animation
	 */
	private long accumulatedTime;
	
	/**
	 * Indicates whether the images should be showed as they are or vertically mirrored
	 */
	private boolean mirror;
	
	/**
	 * Create a new instance using an animation.
	 * 
	 * @param animation The animation with the frames and the logic
	 */
	public FrameAnimation(es.eucm.eadventure.common.data.animation.Animation animation) {
		this.animation = animation;
		accumulatedTime = 0;
	}
	
	public Image getImage() {
		return animation.getImage(accumulatedTime);
	}

	public boolean isPlayingForFirstTime() {
		//return playingForFirstTime;
		return !animation.finishedFirstTime(accumulatedTime);
	}

	public void start() {
		accumulatedTime = 0;
	}

	public void update(long elapsedTime) {
		accumulatedTime += elapsedTime;
	}
	
    public boolean nextImage( ) {
        boolean noMoreFrames = false;
        
        accumulatedTime = animation.skipFrame(accumulatedTime);
        
        if( accumulatedTime >= animation.getTotalTime() ) {
            accumulatedTime %= animation.getTotalTime();
            noMoreFrames = true;
        }
        
        return noMoreFrames;
    }


	public void setAnimation(
			es.eucm.eadventure.common.data.animation.Animation animation) {
		this.animation = animation;
	}

	public void setMirror(boolean mirror) {
		this.mirror = mirror;
		animation.setMirror(mirror);
	}

	public void setFullscreen(boolean b) {
		animation.setFullscreen(b);
	}
}
