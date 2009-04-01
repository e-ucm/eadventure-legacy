package es.eucm.eadventure.editor.control.tools.general.chapters;

import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.tools.Tool;

public class SetNoSelectedProfileTool extends Tool{
	
	public static final int MODE_ADAPTATION = AssetsController.CATEGORY_ADAPTATION;
	public static final int MODE_ASSESSMENT = AssetsController.CATEGORY_ASSESSMENT;
	public static final int MODE_UNKNOWN = -1;
	
	protected Chapter chapter;
	
	protected int mode;
	
	protected Controller controller;
	
	protected String oldValue;
	
	protected String newValue;
	
	public SetNoSelectedProfileTool ( Chapter chapter, int mode ){
		this.chapter = chapter;
		this.mode = mode;
		controller = Controller.getInstance();
		newValue="";
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
		boolean done = false;
		
		// Get old Value
		if (mode==MODE_ASSESSMENT){
			oldValue = chapter.getAssessmentName();
		} else if (mode==MODE_ADAPTATION){
			oldValue = chapter.getAdaptationName();
		}

		// If oldValue is null, it is a bug. FIX IT
		if (oldValue == null)
			setData(newValue);
		else if (!oldValue.equals(newValue)){
			setData(newValue);done=true;
		}
		// update var/flags summary, because in adaptation and/or assessement profiles may have new var/flag
		controller.updateFlagSummary();
		return done;
	}
	
	private void setData(String data){
		if (mode==MODE_ASSESSMENT){
			chapter.setAssessmentName(data);
		} else if (mode==MODE_ADAPTATION){
			chapter.setAdaptationName(data);
		}		
	}

	@Override
	public boolean redoTool() {
		setData(newValue);
		// update var/flags summary, because in adaptation and/or assessement profiles may have new var/flag
		controller.updateFlagSummary();
		controller.reloadPanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		setData(oldValue);
		// update var/flags summary, because in adaptation and/or assessement profiles may have new var/flag
		controller.updateFlagSummary();
		controller.reloadPanel();
		return true;
	}
	
	
	
}
