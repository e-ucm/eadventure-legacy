package es.eucm.eadventure.editor.control.tools.animation;

import es.eucm.eadventure.common.data.animation.Transition;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class ChangeTransitionTypeTool extends Tool {

	private Transition transition;
	
	private int newType;
	
	private int oldType;
	
	public ChangeTransitionTypeTool(Transition transition, int type) {
		this.transition = transition;
		this.newType = type;
		this.oldType = transition.getType();
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
		if (other instanceof ChangeTransitionTypeTool) {
			ChangeTransitionTypeTool cttt = (ChangeTransitionTypeTool) other;
			if (cttt.transition == transition) {
				newType = cttt.newType;
				timeStamp = cttt.timeStamp;
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean doTool() {
		transition.setType(newType);
		return true;
	}

	@Override
	public boolean redoTool() {
		transition.setType(newType);
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		transition.setType(oldType);
		Controller.getInstance().updatePanel();
		return true;
	}

}
