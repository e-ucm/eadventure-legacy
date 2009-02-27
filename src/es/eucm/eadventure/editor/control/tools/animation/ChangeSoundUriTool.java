package es.eucm.eadventure.editor.control.tools.animation;

import es.eucm.eadventure.common.data.animation.Frame;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class ChangeSoundUriTool extends Tool {

	private Frame frame;
	
	private String newUri;
	
	private String oldUri;
	public ChangeSoundUriTool(Frame frame, String uri) {
		this.frame = frame;
		this.newUri = uri;
		this.oldUri = frame.getSoundUri();
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
		frame.setSoundUri(newUri);
		return true;
	}

	@Override
	public boolean redoTool() {
		frame.setSoundUri(newUri);
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		frame.setSoundUri(oldUri);
		Controller.getInstance().updatePanel();
		return true;
	}

}
