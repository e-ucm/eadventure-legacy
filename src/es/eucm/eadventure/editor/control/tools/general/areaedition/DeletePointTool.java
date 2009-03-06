package es.eucm.eadventure.editor.control.tools.general.areaedition;

import java.awt.Point;

import es.eucm.eadventure.common.data.chapter.InfluenceArea;
import es.eucm.eadventure.common.data.chapter.Rectangle;
import es.eucm.eadventure.common.data.chapter.elements.ActiveArea;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.scene.InfluenceAreaDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class DeletePointTool extends Tool {

	private Rectangle rectangle;
	
	private Point oldPoint;
	
	private int oldIndex;
	
	private InfluenceAreaDataControl iadc;
	
	private InfluenceArea oldInfluenceArea;
	
	private InfluenceArea newInfluenceArea;

	public DeletePointTool(Rectangle rectangle, Point point) {
		this.rectangle = rectangle;
		this.oldPoint = point;
		this.oldIndex = rectangle.getPoints().indexOf(point);
	}

	public DeletePointTool(Rectangle rectangle, Point point, InfluenceAreaDataControl iadc) {
		this.rectangle = rectangle;
		this.oldPoint = point;
		this.oldIndex = rectangle.getPoints().indexOf(point);
		this.iadc = iadc;
		oldInfluenceArea = (InfluenceArea) iadc.getContent();
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
		if (rectangle.isRectangular())
			return false;
		if (rectangle.getPoints().contains(oldPoint)) {
			rectangle.getPoints().remove(oldPoint);
			
			if (iadc != null && rectangle.getPoints().size() > 3) {
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
		return false;
	}

	@Override
	public boolean redoTool() {
		if (rectangle.getPoints().contains(oldPoint)) {
			rectangle.getPoints().remove(oldPoint);
			if (iadc != null) {
				ActiveArea aa = (ActiveArea) rectangle;
				aa.setInfluenceArea(newInfluenceArea);
				iadc.setInfluenceArea(newInfluenceArea);
			}
			Controller.getInstance().reloadPanel();
			return true;
		}
		return false;
	}

	@Override
	public boolean undoTool() {
		rectangle.getPoints().add(oldIndex, oldPoint);
		if (iadc != null) {
			ActiveArea aa = (ActiveArea) rectangle;
			aa.setInfluenceArea(oldInfluenceArea);
			iadc.setInfluenceArea(oldInfluenceArea);
		}
		Controller.getInstance().reloadPanel();
		return true;
	}

}
