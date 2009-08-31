/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author López Mañas, E., Pérez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, available at http://e-adventure.e-ucm.es.
 * 
 * <e-Adventure> is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * <e-Adventure>; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */
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
}
