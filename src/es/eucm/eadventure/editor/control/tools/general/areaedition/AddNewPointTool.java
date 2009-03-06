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
