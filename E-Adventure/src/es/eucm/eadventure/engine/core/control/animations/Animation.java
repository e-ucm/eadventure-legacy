/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *         research group.
 *  
 *   Copyright 2005-2010 <e-UCM> research group.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *         http://e-adventure.e-ucm.es/contributors
 * 
 *   <e-UCM> is a research group of the Department of Software Engineering
 *         and Artificial Intelligence at the Complutense University of Madrid
 *         (School of Computer Science).
 * 
 *         C Profesor Jose Garcia Santesmases sn,
 *         28040 Madrid (Madrid), Spain.
 * 
 *         For more info please visit:  <http://e-adventure.e-ucm.es> or
 *         <http://www.e-ucm.es>
 * 
 * ****************************************************************************
 * 
 * This file is part of <e-Adventure>, version 1.2.
 * 
 *     <e-Adventure> is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     <e-Adventure> is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 * 
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.engine.core.control.animations;

import java.awt.Image;

/**
 * This is the interface for all kinds of animations in the game.
 */
public interface Animation {

    /**
     * Starts this animation over from the beginning. Must be implemented
     * synchronized
     */
    public void start( );

    /**
     * Returns if the animation is being played for first time or not.
     * 
     * @return True if the animation reached to and end and started over again,
     *         false otherwise
     */
    public boolean isPlayingForFirstTime( );

    /**
     * Updates this animation's current image (frame) if neccesary.
     */
    public void update( long elapsedTime );

    /**
     * Get the current Image for the animation
     * 
     * @return The current Image for the animation
     */
    public Image getImage( );

    /**
     * Moves the animation forward to the next image. Returns true if the
     * animation has finished.
     * 
     * @return True if the animation has finished
     */
    public boolean nextImage( );

}
