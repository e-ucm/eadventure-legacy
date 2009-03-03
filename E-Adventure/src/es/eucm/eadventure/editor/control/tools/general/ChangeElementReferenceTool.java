package es.eucm.eadventure.editor.control.tools.general;

import es.eucm.eadventure.common.data.chapter.ElementReference;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class ChangeElementReferenceTool extends Tool {

	private ElementReference elementReference;
	
	private int x, y;
	
	private int oldX, oldY;
	
	private boolean changePosition;
	
	private boolean changeScale;
	
	private float scale, oldScale;
	
	public ChangeElementReferenceTool(ElementReference elementReference, int x, int y) {
		this.elementReference = elementReference;
		this.x = x;
		this.y = y;
		this.oldX = elementReference.getX();
		this.oldY = elementReference.getY();
		changePosition = true;
		changeScale = false;
	}
	
	public ChangeElementReferenceTool(ElementReference elementReference2,
			float scale2) {
		this.elementReference = elementReference2;
		this.scale = scale2;
		this.oldScale = elementReference.getScale();
		changePosition = false;
		changeScale = true;
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
		if (other instanceof ChangeElementReferenceTool) {
			ChangeElementReferenceTool crvt = (ChangeElementReferenceTool) other;
			if (crvt.elementReference != elementReference)
				return false;
			if (crvt.changePosition && changePosition) {
				x = crvt.x;
				y = crvt.y;
				timeStamp = crvt.timeStamp;
				return true;
			}
			if (crvt.changeScale && changeScale) {
				scale = crvt.scale;
				timeStamp = crvt.timeStamp;
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean doTool() {
		if (changeScale) {
			elementReference.setScale(scale);
		} else if (changePosition){
			elementReference.setPosition(x, y);
		}
		return true;
	}

	@Override
	public String getToolName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean redoTool() {
		if (changeScale) {
			elementReference.setScale(scale);
		} else if (changePosition){
			elementReference.setPosition(x, y);
		}
		Controller.getInstance().reloadPanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		if (changeScale) {
			elementReference.setScale(oldScale);
		} else if (changePosition){
			elementReference.setPosition(oldX, oldY);
		}
		Controller.getInstance().reloadPanel();
		return true;
	}

}
