package es.eucm.eadventure.editor.control.tools.general.chapters;

import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.general.ChapterListDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class DeleteChapterTool extends Tool{

	private Controller controller;
	
	private ChapterListDataControl chaptersController;
	
	// Removed data
	private Chapter chapterRemoved;
	
	private int index;
	
	public DeleteChapterTool ( ChapterListDataControl chaptersController ){
		this.chaptersController = chaptersController;
		controller = Controller.getInstance();
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
		boolean done = false;
		// Check the number of chapters, the chapters can be deleted when there are more than one
		if( chaptersController.getChaptersCount() > 1 ) {
			// Ask for confirmation
			if( controller.showStrictConfirmDialog( TextConstants.getText( "Operation.DeleteChapterTitle" ), TextConstants.getText( "Operation.DeleteChapterMessage" ) ) ) {
				// Delete the chapter and the controller
				index = chaptersController.getSelectedChapter();
				chapterRemoved = ((Chapter)chaptersController.removeChapterDataControl().getContent());
				//chapterDataControlList.remove( selectedChapter );
				done = true;
				
				// Update the main window
				controller.reloadData( );
			}
		}

		// If there is only one chapter, show an error message
		else
			controller.showErrorDialog( TextConstants.getText( "Operation.DeleteChapterTitle" ), TextConstants.getText( "Operation.DeleteChapterErrorLastChapter" ) );
		
		return done;
	}

	@Override
	public boolean redoTool() {
		chaptersController.removeChapterDataControl( index );
		// Update the main window
		controller.reloadData();
		return true;
	}

	@Override
	public boolean undoTool() {
		chaptersController.addChapterDataControl(index, chapterRemoved );
		// Update the main window
		controller.reloadData( );
		return true;
	}

}
