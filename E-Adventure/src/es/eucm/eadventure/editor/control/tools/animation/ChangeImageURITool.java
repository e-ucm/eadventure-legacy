package es.eucm.eadventure.editor.control.tools.animation;

import es.eucm.eadventure.common.data.animation.Frame;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class ChangeImageURITool extends Tool {

	private Frame frame;
	
	private String newUri;
	
	private String oldUri;
	
	public ChangeImageURITool(Frame frame, String uri) {
		this.frame = frame;
		this.newUri = uri;
		this.oldUri = frame.getUri();
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
		if (other instanceof ChangeImageURITool) {
			ChangeImageURITool ciut = (ChangeImageURITool) other;
			if (ciut.frame == frame) {
				newUri = ciut.newUri;
				timeStamp = ciut.timeStamp;
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean doTool() {
		if (newUri == oldUri)
			return false;
		frame.setUri(newUri);
		return true;
	}

	@Override
	public boolean redoTool() {
		frame.setUri(newUri);
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		frame.setUri(oldUri);
		Controller.getInstance().updatePanel();
		return true;
	}

}
