package es.eucm.eadventure.editor.control.tools.scene;

import es.eucm.eadventure.common.data.chapter.scenes.Scene;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class ChangePlayerScaleTool extends Tool {

	private Scene scene;
	
	private float scale;
	
	private float oldScale;
	
	public ChangePlayerScaleTool(Scene scene, float scale) {
		this.scene = scene;
		this.scale = scale;
		this.oldScale = scene.getPlayerScale();
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
		if (other instanceof ChangePlayerScaleTool) {
			ChangePlayerScaleTool cpst = (ChangePlayerScaleTool) other;
			if (cpst.scene == scene) {
				scale = cpst.scale;
				timeStamp = cpst.timeStamp;
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean doTool() {
		scene.setPlayerScale(scale);
		return true;
	}

	@Override
	public boolean redoTool() {
		scene.setPlayerScale(scale);
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		scene.setPlayerScale(oldScale);
		Controller.getInstance().updatePanel();
		return true;
	}
}
