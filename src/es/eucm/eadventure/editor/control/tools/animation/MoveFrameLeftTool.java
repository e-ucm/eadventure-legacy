package es.eucm.eadventure.editor.control.tools.animation;

import es.eucm.eadventure.common.data.animation.Animation;
import es.eucm.eadventure.common.data.animation.Frame;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.animation.AnimationDataControl;
import es.eucm.eadventure.editor.control.controllers.animation.FrameDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class MoveFrameLeftTool extends Tool {

	private AnimationDataControl animationDataControl;
	
	private FrameDataControl frameDataControl;
	
	private Animation animation;
	
	private Frame frame;
	
	private int index;
	
	public MoveFrameLeftTool(AnimationDataControl animationDataControl,
			FrameDataControl frameDataControl) {
		this.animationDataControl = animationDataControl;
		this.frameDataControl = frameDataControl;
		this.animation = (Animation) animationDataControl.getContent();
		this.frame = (Frame) frameDataControl.getContent();
		this.index = animationDataControl.getFrameDataControls().indexOf(frameDataControl);

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
		if (index == 0)
			return false;
		animation.getFrames().remove(frame);
		animationDataControl.getFrameDataControls().remove(frameDataControl);
		
		animation.getFrames().add(index - 1, frame);
		animationDataControl.getFrameDataControls().add(index - 1, frameDataControl);
		return true;
	}

	@Override
	public boolean redoTool() {
		animation.getFrames().remove(frame);
		animationDataControl.getFrameDataControls().remove(frameDataControl);
		
		animation.getFrames().add(index - 1, frame);
		animationDataControl.getFrameDataControls().add(index - 1, frameDataControl);
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		animation.getFrames().remove(frame);
		animationDataControl.getFrameDataControls().remove(frameDataControl);
		
		animation.getFrames().add(index, frame);
		animationDataControl.getFrameDataControls().add(index, frameDataControl);
		Controller.getInstance().updatePanel();
		return true;
	}

}
