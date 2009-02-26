package es.eucm.eadventure.editor.control.tools.animation;

import es.eucm.eadventure.common.data.animation.Animation;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class ChangeUseTransitionsTool extends Tool {

	private Animation animation;
	
	private boolean useTransitions;
	
	private boolean oldUseTransitions;
	
	public ChangeUseTransitionsTool(Animation animation, boolean useTransitions) {
		this.animation = animation;
		this.useTransitions = useTransitions;
		this.oldUseTransitions = animation.isUseTransitions();
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
		if (useTransitions == oldUseTransitions)
			return false;
		animation.setUseTransitions(useTransitions);
		return true;
	}

	@Override
	public boolean redoTool() {
		animation.setUseTransitions(useTransitions);
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		animation.setUseTransitions(oldUseTransitions);
		Controller.getInstance().updatePanel();
		return true;
	}

}
