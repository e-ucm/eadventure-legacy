package es.eucm.eadventure.editor.control.tools.general.areaedition;

import java.awt.Point;

import es.eucm.eadventure.common.data.chapter.Rectangle;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class AddNewPointTool extends Tool {

	Rectangle rectangle;
	
	Point newPoint;
	
	public AddNewPointTool(Rectangle rectangle, int x, int y) {
		this.rectangle = rectangle;
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
		return true;
	}

	@Override
	public boolean redoTool() {
		rectangle.getPoints().add(newPoint);
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		rectangle.getPoints().remove(newPoint);
		Controller.getInstance().updatePanel();
		return false;
	}

}
