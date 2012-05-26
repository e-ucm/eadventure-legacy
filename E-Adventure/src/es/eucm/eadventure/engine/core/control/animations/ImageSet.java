/*******************************************************************************
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the e-UCM
 *          research group.
 *   
 *    Copyright 2005-2012 e-UCM research group.
 *  
 *     e-UCM is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *  
 *          C Profesor Jose Garcia Santesmases sn,
 *          28040 Madrid (Madrid), Spain.
 *  
 *          For more info please visit:  <http://e-adventure.e-ucm.es> or
 *          <http://www.e-ucm.es>
 *  
 *  ****************************************************************************
 * This file is part of eAdventure, version 1.5.
 * 
 *   You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with Adventure.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.engine.core.control.animations;

import java.awt.Image;

import es.eucm.eadventure.engine.multimedia.MultimediaManager;
import es.eucm.eadventure.engine.resourcehandler.ResourceHandler;

/**
 * This class implements a set of images, that can be used as a set of slides.
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
     * Adds an image to the animation with the specified duration (time to
     * display the image).
     */
    public void setImages( Image[] imageSet ) {

        this.imageSet = imageSet;
        if( imageSet.length == 0 )
            this.imageSet = getNoAnimationAvailableImageSet( );
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

    public boolean isPlayingForFirstTime( ) {

        return true;
    }

    public void update( long elapsedTime ) {

    }

    /**
     * Dirty fix: Needed for avoiding null pointer exception when no available
     * resources block
     * 
     * @return
     */
    protected Image[] getNoAnimationAvailableImageSet( ) {

        Image[] array = new Image[ 1 ];
        array[0] = MultimediaManager.getInstance( ).loadImage( ResourceHandler.DEFAULT_SLIDES, MultimediaManager.IMAGE_SCENE );
        return array;
    }

    public void stopMusic( ) {

        // TODO Auto-generated method stub
        
    }
}
