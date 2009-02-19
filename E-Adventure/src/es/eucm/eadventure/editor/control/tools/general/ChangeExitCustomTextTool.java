package es.eucm.eadventure.editor.control.tools.general;

import es.eucm.eadventure.common.data.chapter.ExitLook;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class ChangeExitCustomTextTool extends Tool{
	protected ExitLook exitLook;
	
	protected String newText;
	
	protected String oldText;
	
	protected Controller controller;
	
	public ChangeExitCustomTextTool(ExitLook exitLook, String newText) {
		this.exitLook = exitLook;
		this.newText = newText;
		this.controller = Controller.getInstance();
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
	public boolean doTool() {
		if( newText!=null && !newText.equals( exitLook.getExitText() ) ) {
			oldText = exitLook.getExitText();
			exitLook.setExitText( newText );
			return true;
		} else if ( newText==null && exitLook.getExitText()!=null ){
			oldText = exitLook.getExitText();
			exitLook.setExitText( null );
			return true;			
		}
		return false;
	}


	@Override
	public boolean redoTool() {
		exitLook.setExitText( newText );
		controller.reloadPanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		exitLook.setExitText( oldText );
		controller.reloadPanel();
		return true;
	}
	
	@Override
	public boolean combine(Tool other) {
		if (other instanceof ChangeNameTool) {
			ChangeExitCustomTextTool cnt = (ChangeExitCustomTextTool) other;
			if (cnt.exitLook == exitLook && cnt.oldText == newText) {
				newText = cnt.newText;
				timeStamp = cnt.timeStamp;
				return true;
			}
		}
		return false;
	}
}
