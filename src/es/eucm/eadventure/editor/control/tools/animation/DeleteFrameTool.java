package es.eucm.eadventure.editor.control.tools.animation;

import es.eucm.eadventure.common.data.animation.Animation;
import es.eucm.eadventure.common.data.animation.Frame;
import es.eucm.eadventure.common.data.animation.Transition;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.animation.AnimationDataControl;
import es.eucm.eadventure.editor.control.controllers.animation.FrameDataControl;
import es.eucm.eadventure.editor.control.controllers.animation.TransitionDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class DeleteFrameTool extends Tool {

	private FrameDataControl frameDataControl;
	
	private AnimationDataControl animationDataControl;
	
	private Transition transition;
	
	private TransitionDataControl transitionDataControl;
	
	private Frame frame;
	
	private Animation animation;
	
	private int index;
	
	public DeleteFrameTool(AnimationDataControl animationDataControl, FrameDataControl frameDataControl) {
		this.frameDataControl = frameDataControl;
		this.animationDataControl = animationDataControl;
		this.animation = (Animation) animationDataControl.getContent();
		this.frame = (Frame) frameDataControl.getContent();
		index = animation.getFrames().indexOf((Frame) frameDataControl.getContent());
		this.transitionDataControl = this.animationDataControl.getTransitionDataControls().get(index + 1);
		this.transition = (Transition) this.transitionDataControl.getContent();
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
		if (animationDataControl.getFrameCount() == 1)
			return false;
		animationDataControl.getFrameDataControls().remove(frameDataControl);
		animationDataControl.getTransitionDataControls().remove(transitionDataControl);
		animation.getFrames().remove(frame);
		animation.getTransitions().remove(transition);
		return true;
	}

	@Override
	public boolean redoTool() {
		animationDataControl.getFrameDataControls().remove(frameDataControl);
		animationDataControl.getTransitionDataControls().remove(transitionDataControl);
		animation.getFrames().remove(frame);
		animation.getTransitions().remove(transition);
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		animationDataControl.getFrameDataControls().add(index, frameDataControl);
		animationDataControl.getTransitionDataControls().add(index + 1, transitionDataControl);
		animation.getFrames().add(index, frame);
		animation.getTransitions().add(index + 1, transition);
		Controller.getInstance().updatePanel();
		return true;
	}

}
