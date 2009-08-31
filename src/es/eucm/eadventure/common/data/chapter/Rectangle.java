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
package es.eucm.eadventure.common.data.chapter;

import java.awt.Point;
import java.util.List;

/**
 * The object is a rectangle or polygon
 */
public interface Rectangle {

    /**
     * Set the values of the rectangle
     * 
     * @param x
     *            The x axis value
     * @param y
     *            The y axis value
     * @param width
     *            The width of the rectangle
     * @param height
     *            The height of the rectangle
     */
    public void setValues( int x, int y, int width, int height );

    /**
     * Get the x axis value
     * 
     * @return The x axis value
     */
    public int getX( );

    /**
     * Get the y axis value
     * 
     * @return The y axis value
     */
    public int getY( );

    /**
     * Get the width of the rectangle
     * 
     * @return The width of the rectangle
     */
    public int getWidth( );

    /**
     * Get the height of the rectangle
     * 
     * @return The height of the rectangle
     */
    public int getHeight( );

    /**
     * True if it is rectangular, false if it is a polygon
     * 
     * @return True if the object is rectangular
     */
    public boolean isRectangular( );

    /**
     * Make the object rectangular (true) or a polygon (false)
     * 
     * @param rectangular
     *            The rectangular value
     */
    public void setRectangular( boolean rectangular );

    /**
     * Get the list of points for the polygon
     * 
     * @return The list of points of the polygon
     */
    public List<Point> getPoints( );
}
