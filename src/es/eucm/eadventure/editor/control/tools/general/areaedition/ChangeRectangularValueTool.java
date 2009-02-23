package es.eucm.eadventure.editor.control.tools.general.areaedition;

import es.eucm.eadventure.common.data.chapter.Rectangle;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class ChangeRectangularValueTool extends Tool {

	private Rectangle rectangle;
	
	private boolean rectangular;
	
	public ChangeRectangularValueTool(Rectangle rectangle, boolean rectangular) {
		this.rectangle = rectangle;
		this.rectangular = rectangular;
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
		if (rectangle.isRectangular() != rectangular) {
			rectangle.setRectangular(rectangular);
			return true;
		}
		return false;
	}

	@Override
	public boolean redoTool() {
		rectangle.setRectangular(rectangular);
		Controller.getInstance().reloadPanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		rectangle.setRectangular(!rectangular);
		Controller.getInstance().reloadPanel();
		return true;
	}

}
