/**
 * <e-Adventure> is an <e-UCM> research project.
 * <e-UCM>, Department of Software Engineering and Artificial Intelligence.
 * Faculty of Informatics, Complutense University of Madrid (Spain).
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J.
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009
 * Web-site: http://e-adventure.e-ucm.es
 */

/*
    Copyright (C) 2004-2009 <e-UCM> research group

    This file is part of <e-Adventure> project, an educational game & game-like 
    simulation authoring tool, availabe at http://e-adventure.e-ucm.es. 

    <e-Adventure> is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    <e-Adventure> is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with <e-Adventure>; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

*/
package es.eucm.eadventure.engine.core.control.animations;

import java.awt.Image;

import es.eucm.eadventure.engine.multimedia.MultimediaManager;
import es.eucm.eadventure.engine.resourcehandler.ResourceHandler;

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
        // Add the elapsed time to the accumulated
        accumulatedAnimationTime += elapsedTime;

        if( imageSet.length > 1 ) {

            // Skip frame for every TIME_PER_FRAME miliseconds
            while( accumulatedAnimationTime > TIME_PER_FRAME ) {
                accumulatedAnimationTime -= TIME_PER_FRAME;
                currentFrameIndex++;
            }
            
            if( currentFrameIndex >= imageSet.length ) {
                currentFrameIndex %= imageSet.length;
                playingFirstTime = false;
            }
        } else if (accumulatedAnimationTime > TIME_PER_FRAME) {
        	playingFirstTime = false;
        }
    }
    
	protected Image[] getNoAnimationAvailableImageSet (){
		Image[] array = new Image[1];
		array[0] = MultimediaManager.getInstance().loadImage(ResourceHandler.DEFAULT_ANIMATION, MultimediaManager.IMAGE_SCENE);
		return array;
	}
}
