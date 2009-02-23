package es.eucm.eadventure.editor.control.tools.general.areaedition;

import java.awt.Point;

import es.eucm.eadventure.common.data.chapter.Rectangle;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class DeletePointTool extends Tool {

	private Rectangle rectangle;
	
	private Point oldPoint;
	
	private int oldIndex;
	
	public DeletePointTool(Rectangle rectangle, Point point) {
		this.rectangle = rectangle;
		this.oldPoint = point;
		this.oldIndex = rectangle.getPoints().indexOf(point);
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
			return true;
		}
		return false;
	}

	@Override
	public boolean redoTool() {
		if (rectangle.getPoints().contains(oldPoint)) {
			rectangle.getPoints().remove(oldPoint);
			Controller.getInstance().reloadPanel();
			return true;
		}
		return false;
	}

	@Override
	public boolean undoTool() {
		rectangle.getPoints().add(oldIndex, oldPoint);
		Controller.getInstance().reloadPanel();
		return true;
	}

}
