package es.eucm.eadventure.editor.control.tools.general;

import es.eucm.eadventure.common.data.chapter.NextScene;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class ChangeTransitionTypeTool extends Tool {
	
	private int newType;
	
	private int oldType;
	
	private NextScene nextScene;

	public ChangeTransitionTypeTool(NextScene nextScene, int type) {
		this.newType = type;
		this.oldType = nextScene.getTransitionType();
		this.nextScene = nextScene;
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
		if (newType == oldType)
			return false;
		nextScene.setTransitionType(newType);
		return true;
	}

	@Override
	public boolean redoTool() {
		nextScene.setTransitionType(newType);
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		nextScene.setTransitionType(oldType);
		Controller.getInstance().updatePanel();
		return true;
	}

}
