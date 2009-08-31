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
package es.eucm.eadventure.editor.control.tools.general.areaedition;

import java.awt.Point;

import es.eucm.eadventure.common.data.chapter.InfluenceArea;
import es.eucm.eadventure.common.data.chapter.Rectangle;
import es.eucm.eadventure.common.data.chapter.elements.ActiveArea;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.scene.ActiveAreaDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.InfluenceAreaDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class AddNewPointTool extends Tool {

	private Rectangle rectangle;
	
	private Point newPoint;
	
	private InfluenceAreaDataControl iadc;
	
	private InfluenceArea oldInfluenceArea;
	
	private InfluenceArea newInfluenceArea;
	
	public AddNewPointTool(Rectangle rectangle, int x, int y) {
		this.rectangle = rectangle;
		newPoint = new Point(x, y);
	}

	public AddNewPointTool(Rectangle rectangle, int x, int y, InfluenceAreaDataControl iadc) {
		this.rectangle = rectangle;
		this.iadc = iadc;
		oldInfluenceArea = (InfluenceArea) iadc.getContent();
		newPoint = new Point(x, y);
	}

	@Override
	public boolean canRedo() {
		return true;
	}
	

	@Override
	public boolean canUndo() {
		return true;
	}

	@Override
	public boolean combine(Tool other) {
		return false;
	}

	@Override
	public boolean doTool() {
		if (rectangle.isRectangular()) {
			return false;
		}
		rectangle.getPoints().add(newPoint);
		
		if (iadc != null) {
			int minX = Integer.MAX_VALUE;
			int	minY = Integer.MAX_VALUE;
			int maxX = 0;
			int maxY = 0;
			for (Point point : rectangle.getPoints()) {
				if (point.getX() > maxX) maxX = (int) point.getX();
				if (point.getX() < minX) minX = (int) point.getX();
				if (point.getY() > maxY) maxY = (int) point.getY();
				if (point.getY() < minY) minY = (int) point.getY();
			}
			newInfluenceArea = new InfluenceArea();
			newInfluenceArea.setX(-20);
			newInfluenceArea.setY(-20);
			newInfluenceArea.setHeight(maxY - minY + 40);
			newInfluenceArea.setWidth(maxX - minX + 40);
			
			ActiveArea aa = (ActiveArea) rectangle;
			aa.setInfluenceArea(newInfluenceArea);
			iadc.setInfluenceArea(newInfluenceArea);
		}
		return true;
	}

	@Override
	public boolean redoTool() {
		rectangle.getPoints().add(newPoint);
		if (iadc != null) {
			ActiveArea aa = (ActiveArea) rectangle;
			aa.setInfluenceArea(newInfluenceArea);
			iadc.setInfluenceArea(newInfluenceArea);
		}
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		rectangle.getPoints().remove(newPoint);
		if (iadc != null) {
			ActiveArea aa = (ActiveArea) rectangle;
			aa.setInfluenceArea(oldInfluenceArea);
			iadc.setInfluenceArea(oldInfluenceArea);
		}
		Controller.getInstance().updatePanel();
		return true;
	}

}
