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
