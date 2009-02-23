package es.eucm.eadventure.editor.control.tools.general.areaedition;

import java.awt.Point;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class ChangePointValueTool extends Tool {

	private Point point;
	
	private int x;
	
	private int y;
	
	private int originalX;
	
	private int originalY;
	
	public ChangePointValueTool(Point point, int x, int y) {
		this.point = point;
		this.x = x;
		this.y = y;
		this.originalX = (int) point.getX();
		this.originalY = (int) point.getY();
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
		if (other instanceof ChangePointValueTool) {
			ChangePointValueTool cpvt = (ChangePointValueTool) other;
			if (cpvt.point == point) {
				cpvt.x = x;
				cpvt.y = y;
				cpvt.timeStamp = timeStamp;
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean doTool() {
		point.setLocation(x, y);
		return true;
	}

	@Override
	public boolean redoTool() {
		point.setLocation(x, y);
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		point.setLocation(originalX, originalY);
		Controller.getInstance().updatePanel();
		return true;
	}

}
