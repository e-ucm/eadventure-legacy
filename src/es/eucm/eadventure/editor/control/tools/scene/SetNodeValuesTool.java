package es.eucm.eadventure.editor.control.tools.scene;

import es.eucm.eadventure.common.data.chapter.Trajectory.Node;
import es.eucm.eadventure.editor.control.tools.Tool;

public class SetNodeValuesTool extends Tool{

	private int oldX;
	private int oldY;
	private float oldScale;
	
	private int newX;
	private int newY;
	private float newScale;
	
	private Node node;
	
	public SetNodeValuesTool (Node node, int newX, int newY, float newScale ){
		this.newX = newX;
		this.newY = newY;
		this.newScale = newScale;
		this.node = node;
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
		if (other instanceof SetNodeValuesTool) {
			SetNodeValuesTool crvt = (SetNodeValuesTool) other;
			if (crvt.node != node)
				return false;
			if (crvt.newX!=newX || crvt.newY!=newY || crvt.newScale!=newScale) {
				newX = crvt.newX;
				newY = crvt.newY;
				newScale = crvt.newScale;
				timeStamp = crvt.timeStamp;
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean doTool() {
		node.setValues(newX, newY, newScale);
		return true;
	}

	@Override
	public boolean redoTool() {
		node.setValues(newX, newY, newScale);
		return true;
	}

	@Override
	public boolean undoTool() {
		node.setValues(oldX, oldY, oldScale);
		return true;
	}

}
