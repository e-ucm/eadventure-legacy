/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *          research group.
 *   
 *    Copyright 2005-2012 <e-UCM> research group.
 *  
 *     <e-UCM> is a research group of the Department of Software Engineering
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
 * This file is part of <e-Adventure>, version 1.4.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       <e-Adventure> is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      <e-Adventure> is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.common.data.chapter;

import java.awt.Point;
import java.util.List;

/**
 * The influence area for an item reference or active area
 */
public class InfluenceArea implements Cloneable, Rectangle {

    /**
     * True if the influence area exists (is defined)
     */
    private boolean exists = false;

    /**
     * The x axis value of the influence area, relative to the objects top left
     * corner
     */
    private int x;

    /**
     * The y axis value of the influence area, relative to the objects top left
     * corner
     */
    private int y;

    /**
     * The width of the active area
     */
    private int width;

    /**
     * The height of the active area
     */
    private int height;

    public InfluenceArea( ) {

    }

    /**
     * Creates a new influence area with the given parameters
     * 
     * @param x
     *            The x axis value
     * @param y
     *            The y axis value
     * @param width
     *            The width of the influence area
     * @param height
     *            The height of the influence area
     */
    public InfluenceArea( int x, int y, int width, int height ) {

        exists = true;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * @return the exists
     */
    public boolean isExists( ) {

        return exists;
    }

    /**
     * @param exists
     *            the exists to set
     */
    public void setExists( boolean exists ) {

        this.exists = exists;
    }

    /**
     * @return the x
     */
    public int getX( ) {

        return x;
    }

    /**
     * @param x
     *            the x to set
     */
    public void setX( int x ) {

        if( x > 0 )
            this.x = x;
    }

    /**
     * @return the y
     */
    public int getY( ) {

        return y;
    }

    /**
     * @param y
     *            the y to set
     */
    public void setY( int y ) {

        if( y > 0 )
            this.y = y;
    }

    /**
     * @return the width
     */
    public int getWidth( ) {

        return width;
    }

    /**
     * @param width
     *            the width to set
     */
    public void setWidth( int width ) {

        if( width > 0 )
            this.width = width;
    }

    /**
     * @return the height
     */
    public int getHeight( ) {

        return height;
    }

    /**
     * @param height
     *            the height to set
     */
    public void setHeight( int height ) {

        if( height > 0 )
            this.height = height;
    }

    public void setValues( int x, int y, int width, int height ) {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        InfluenceArea ia = (InfluenceArea) super.clone( );
        ia.exists = exists;
        ia.height = height;
        ia.width = width;
        ia.x = x;
        ia.y = y;
        return ia;
    }

    public boolean isRectangular( ) {

        return true;
    }

    public void setRectangular( boolean rectangular ) {

    }

    public List<Point> getPoints( ) {

        return null;
    }
}
