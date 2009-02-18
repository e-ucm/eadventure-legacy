package es.eucm.eadventure.editor.control.tools;

import es.eucm.eadventure.common.data.chapter.Rectangle;
import es.eucm.eadventure.editor.control.Controller;

public class ChangeRectangleValueTool extends Tool {

	private Rectangle rectangle;
	
	private int x, y, width, height;
	
	private int oldX, oldY, oldWidth, oldHeight;
	
	public ChangeRectangleValueTool(Rectangle rectangle, int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.rectangle = rectangle;
		
		this.oldX = rectangle.getX();
		this.oldY = rectangle.getY();
		this.oldWidth = rectangle.getWidth();
		this.oldHeight = rectangle.getHeight();
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
		if (other instanceof ChangeRectangleValueTool) {
			ChangeRectangleValueTool crvt = (ChangeRectangleValueTool) other;
			if (crvt.rectangle != rectangle)
				return false;
			if (crvt.isChangePos() && isChangePos()) {
				x = crvt.x;
				y = crvt.y;
				timeStamp = crvt.timeStamp;
				return true;
			}
			if (crvt.isChangeSize() && isChangeSize()) {
				width = crvt.width;
				height = crvt.height;
				timeStamp = crvt.timeStamp;
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean doTool() {
		rectangle.setValues(x, y, width, height);
		return true;
	}

	@Override
	public String getToolName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean redoTool() {
		rectangle.setValues(x, y, width, height);
		Controller.getInstance().reloadPanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		rectangle.setValues(oldX, oldY, oldWidth, oldHeight);
		Controller.getInstance().reloadPanel();
		return true;
	}
	
	private boolean isChangeSize() {
		if (x == oldX && y == oldY)
			return true;
		return false;
	}
	
	private boolean isChangePos() {
		if (width == oldWidth && height == oldHeight)
			return true;
		return false;
	}
	

}
