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
package es.eucm.eadventure.common.data.chapter.elements;

import java.awt.Point;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.Rectangle;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;

/**
 * This class holds the data of an exit in eAdventure
 */
public class Barrier extends Element implements Rectangle {

	/**
	 * X position of the upper left corner of the exit
	 */
	private int x;

	/**
	 * Y position of the upper left corner of the exit
	 */
	private int y;

	/**
	 * Width of the exit
	 */
	private int width;

	/**
	 * Height of the exit
	 */
	private int height;
	
	/**
	 * Conditions of the active area
	 */
	private Conditions conditions;

	/**
	 * Creates a new Exit
	 * 
	 * @param x
	 *            The horizontal coordinate of the upper left corner of the exit
	 * @param y
	 *            The vertical coordinate of the upper left corner of the exit
	 * @param width
	 *            The width of the exit
	 * @param height
	 *            The height of the exit
	 */
	public Barrier( String id, int x, int y, int width, int height ) {
		super(id);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		conditions = new Conditions();
	}

	/**
	 * Returns the horizontal coordinate of the upper left corner of the exit
	 * 
	 * @return the horizontal coordinate of the upper left corner of the exit
	 */
	public int getX( ) {
		return x;
	}

	/**
	 * Returns the horizontal coordinate of the bottom right of the exit
	 * 
	 * @return the horizontal coordinate of the bottom right of the exit
	 */
	public int getY( ) {
		return y;
	}

	/**
	 * Returns the width of the exit
	 * 
	 * @return Width of the exit
	 */
	public int getWidth( ) {
		return width;
	}

	/**
	 * Returns the height of the exit
	 * 
	 * @return Height of the exit
	 */
	public int getHeight( ) {
		return height;
	}

	/**
	 * Set the values of the exit.
	 * 
	 * @param x
	 *            X coordinate of the upper left point
	 * @param y
	 *            Y coordinate of the upper left point
	 * @param width
	 *            Width of the exit area
	 * @param height
	 *            Height of the exit area
	 */
	public void setValues( int x, int y, int width, int height ) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/**
	 * @return the conditions
	 */
	public Conditions getConditions( ) {
		return conditions;
	}

	/**
	 * @param conditions the conditions to set
	 */
	public void setConditions( Conditions conditions ) {
		this.conditions = conditions;
	}
	
	public Object clone() throws CloneNotSupportedException {
		Barrier b = (Barrier) super.clone();
		b.conditions = (conditions != null ? (Conditions) conditions.clone() : null);
		b.height = height;
		b.width = width;
		b.x = x;
		b.y = y;
		return b;
	}

	public List<Point> getPoints() {
		return null;
	}

	public boolean isRectangular() {
		return true;
	}

	public void setRectangular(boolean rectangular) {
	}
}