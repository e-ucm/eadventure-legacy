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