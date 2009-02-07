package es.eucm.eadventure.engine.core.control.animations;

import java.awt.Image;

import es.eucm.eadventure.engine.multimedia.MultimediaManager;
import es.eucm.eadventure.engine.resourcehandler.ResourceHandler;

/**
 * This class implements a set of images, that can be used as a set of
 * slides. 
 */
public class ImageSet implements Animation {
    
    /**
     * Set of images.
     */
    protected Image[] imageSet;

    /**
     * Index of the current frame.
     */
    protected int currentFrameIndex;

    /**
     * Constructor.
     */
    public ImageSet( ) {
        imageSet = null;
        currentFrameIndex = 0;
    }

    /**
     * Adds an image to the animation with the specified
     * duration (time to display the image).
     */
    public void setImages( Image[] imageSet ) {
        this.imageSet = imageSet;
        if (imageSet.length == 0)
        	this.imageSet = getNoAnimationAvailableImageSet ();
    }

    /**
     * Starts this animation over from the beginning.
     */
    public synchronized void start( ) {
        currentFrameIndex = 0;
    }

    public boolean nextImage( ) {
        boolean noMoreFrames = false;
        
        currentFrameIndex++;
        
        if( currentFrameIndex >= imageSet.length ) {
            currentFrameIndex %= imageSet.length;
            noMoreFrames = true;
        }
        
        return noMoreFrames;
    }

    /**
     * Returns the current image from the set.
     */
    public Image getImage( ) {
        return imageSet[currentFrameIndex];
    }

	public boolean isPlayingForFirstTime() {
		return true;
	}

	public void update(long elapsedTime) {
	}
	
	/**
	 * Dirty fix: Needed for avoiding null pointer exception when no available resources block
	 * @return
	 */
	protected Image[] getNoAnimationAvailableImageSet (){
		Image[] array = new Image[1];
		array[0] = MultimediaManager.getInstance().loadImage(ResourceHandler.DEFAULT_SLIDES, MultimediaManager.IMAGE_SCENE);
		return array;
	}
}
