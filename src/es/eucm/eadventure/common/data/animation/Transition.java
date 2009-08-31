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
package es.eucm.eadventure.common.data.animation;

/**
 * This class holds the information for an animation transition
 * 
 * @author Eugenio Marchiori
 *
 */
public class Transition implements Cloneable, Timed {
	
	/**
	 * The transition does nothing
	 */
	public static final int TYPE_NONE = 0;
	
	/**
	 * The transition makes the previous frame disappear while the new one appears
	 */
	public static final int TYPE_FADEIN = 1;
	
	/**
	 * The transition places the new frame over the old one from left to right
	 */
	public static final int TYPE_VERTICAL = 2;
	
	/**
	 * The transition places the new frame over the old one from top to bottom
	 */
	public static final int TYPE_HORIZONTAL = 3;
	
	/**
	 * Time (duration) of the transition
	 */
	private long time;
	
	/**
	 * Type of the transition: {@link #TYPE_FADEIN}, {@link #TYPE_NONE}, {@link #TYPE_HORIZONTAL} or {@link #TYPE_VERTICAL}
	 */
	private int type;
	
	/**
	 * Creates a new empty transition
	 */
	public Transition() {
		time = 0;
		type = TYPE_NONE;
	}

	/**
	 * Returns the time (duration) of the transition in milliseconds
	 * 
	 * @return the time (duration) of the transition in milliseconds
	 */
	public long getTime() {
		return time;
	}

	/**
	 * Sets the time (duration) of the transition in milliseconds
	 * 
	 * @param time
	 * 		the new time (duration) of the transition in milliseconds
	 */
	public void setTime(long time) {
		this.time = time;
	}

	/**
	 * Returns the type of the transition
	 * 
	 * @return the type of the transition
 	 */
	public int getType() {
		return type;
	}

	/**
	 * Sets the type of the transition
	 * 
	 * @param type
	 * 			The new type of the transition
	 */
	public void setType(int type) {
		this.type = type;
	}
	
	public Object clone() throws CloneNotSupportedException {
		Transition t = (Transition) super.clone();
		t.time = time;
		t.type = type;
		return t;
	}

}
