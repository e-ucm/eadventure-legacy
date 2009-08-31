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
package es.eucm.eadventure.engine.core.control.functionaldata;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.Trajectory.Side;

public class FunctionalPath implements Comparable<FunctionalPath> {

	private float length;
	
	private List<FunctionalSide> sides;
	
	private float destX;
	
	private float destY;
	
	private boolean getsTo;
	
	private float distance;
	
	public FunctionalPath(float length, float distance, List<FunctionalSide> sides) {
		this.length = length;
		this.sides = new ArrayList<FunctionalSide>(sides);
		this.distance = distance;
		getsTo = false;
	}

	public void setLength(float length) {
		this.length = length;
	}

	public void setGetsTo(boolean b) {
		getsTo = b;
	}

	public boolean isGetsTo() {
		return getsTo;
	}
	
	public void updateUpTo(float dist, float posX, float posY) {
		if (dist < distance) {
			destX = posX;
			destY = posY;
			distance = dist;
		}
	}

	public List<FunctionalSide> getSides() {
		return sides;
	}
	
	public List<Side> getNormalSides() {
		List<Side> temp = new ArrayList<Side>();
		for (FunctionalSide side : sides)
			temp.add(side.getSide());
		return temp;
	}
	
	public float getLength() {
		return length;
	}
	
	public float getDistance() {
		return distance;
	}
	
	public void addSide(float lenght, float distance, FunctionalSide side) {
		sides.add(side);
		this.length += lenght;
		this.distance = distance;
	}

	public FunctionalPath newFunctionalPath(float length, float distance, FunctionalSide side) {
		if (sides.contains(side))
			return null;
		for (FunctionalSide tempSide : sides)
			if (tempSide.getSide() == side.getSide())
				return null;
		
		FunctionalPath temp = new FunctionalPath(this.length, this.distance, this.sides);
		temp.addSide(length, distance, side);
		return temp;
	}


	public int compareTo(FunctionalPath arg0) {
		if (this.getsTo && !arg0.getsTo) {
			return 1;
		} else if (!this.getsTo && arg0.getsTo) {
			return -1;
		}
		int distDif = (int) (arg0.distance - distance);
		if (Math.abs(distDif) < 100) {
			return (int) (arg0.length - length);
		} else {
			return distDif;
		}
	}		
	
	public float getDestX() {
		return destX;
	}
	
	public float getDestY() {
		return destY;
	}

	public void print() {
		for (FunctionalSide side : sides) {
			System.out.print(side.getStartNode().getID() + "->" );
		}
		System.out.println(sides.get(sides.size() - 1).getEndNode().getID());
	}
}
