package es.eucm.eadventure.editor.control.tools.general;

import es.eucm.eadventure.common.data.Titled;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class ChangeTitleTool extends Tool {

	private Titled titled;
	
	private String title;
	
	private String oldTitle;
	
	private Controller controller;
	
	public ChangeTitleTool(Titled titled, String title) {
		this.titled = titled;
		this.title = title;
		controller = Controller.getInstance();
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
		if( !title.equals( titled.getTitle( ) ) ) {
			oldTitle = titled.getTitle();
			titled.setTitle( title );
			controller.updateTree( );
			controller.updateChapterMenu( );
			return true;
		} 
		return false;
	}

	@Override
	public String getToolName() {
		return "Change Chapter Title";
	}

	@Override
	public boolean redoTool() {
		titled.setTitle( title );
		controller.updateTree( );
		controller.updateChapterMenu( );
		controller.updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		titled.setTitle( oldTitle );
		controller.updateTree( );
		controller.updateChapterMenu( );
		controller.updatePanel();
		return true;
	}
	
	@Override
	public boolean combine(Tool other) {
		if (other instanceof ChangeTitleTool) {
			ChangeTitleTool ctt = (ChangeTitleTool) other;
			if (ctt.titled == titled && ctt.oldTitle == title) {
				title = ctt.title;
				timeStamp = ctt.timeStamp;
				return true;
			}
		}
		return false;
	}


}
