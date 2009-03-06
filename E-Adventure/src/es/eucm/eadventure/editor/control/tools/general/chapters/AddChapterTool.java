package es.eucm.eadventure.editor.control.tools.general.chapters;


import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.general.ChapterListDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class AddChapterTool extends Tool{

	private Controller controller;
	
	private ChapterListDataControl chaptersController;
	
	private Chapter newChapter;
	
	private int index;
	
	public AddChapterTool ( ChapterListDataControl chaptersController ){
		this.chaptersController = chaptersController;
		this.controller = Controller.getInstance();
		setGlobal(true);
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
		// Show a dialog asking for the chapter title
		String chapterTitle = controller.showInputDialog( TextConstants.getText( "Operation.AddChapterTitle" ), TextConstants.getText( "Operation.AddChapterMessage" ), TextConstants.getText( "Operation.AddChapterDefaultValue" ) );

		// If some value was typed
		if( chapterTitle != null ) {
			// Create the new chapter, and the controller
			newChapter = new Chapter( chapterTitle, TextConstants.getText( "DefaultValue.SceneId" ) );
			chaptersController.addChapterDataControl(newChapter);
			index = chaptersController.getSelectedChapter();

			controller.reloadData();
			return true;
		}
		return false;

	}

	@Override
	public boolean redoTool() {
		chaptersController.addChapterDataControl( index, newChapter );
		controller.reloadData();
		return true;
	}

	@Override
	public boolean undoTool() {
		boolean done = (chaptersController.removeChapterDataControl(index)) != null;
		controller.reloadData();
		return done;
	}
	

}
