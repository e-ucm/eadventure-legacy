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
	
	private String chapterTitle;
	
	public AddChapterTool ( ChapterListDataControl chaptersController ){
		this.chaptersController = chaptersController;
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
	public boolean combine(Tool other) {
		return false;
	}

	@Override
	public boolean doTool() {
		// Show a dialog asking for the chapter title
		chapterTitle = controller.showInputDialog( TextConstants.getText( "Operation.AddChapterTitle" ), TextConstants.getText( "Operation.AddChapterMessage" ), TextConstants.getText( "Operation.AddChapterDefaultValue" ) );

		// If some value was typed
		if( chapterTitle != null ) {
		    if (!chaptersController.exitsChapter(chapterTitle)){
			// Create the new chapter, and the controller
			newChapter = new Chapter( chapterTitle, TextConstants.getText( "DefaultValue.SceneId" ) );
			chaptersController.addChapterDataControl(newChapter);
			index = chaptersController.getSelectedChapter();

			controller.reloadData();
			return true;
		    } else {
			    controller.showErrorDialog(TextConstants.getText("Operation.CreateAdaptationFile.FileName.ExistValue.Title"), TextConstants.getText("Operation.NewChapter.ExistingName"));
			    return false;
		    }
		}
		return false;

	}

	@Override
	public boolean redoTool() {
		// Create the new chapter, and the controller
		newChapter = new Chapter( chapterTitle, TextConstants.getText( "DefaultValue.SceneId" ) );
		chaptersController.addChapterDataControl(newChapter);
		index = chaptersController.getSelectedChapter();

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
