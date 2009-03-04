package es.eucm.eadventure.editor.control.tools.general.chapters;

import java.util.List;

import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AdventureDataControl;
import es.eucm.eadventure.editor.control.controllers.general.ChapterDataControl;
import es.eucm.eadventure.editor.control.controllers.general.ChapterListDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class AddChapterTool extends Tool{

	private Controller controller;
	
	private AdventureDataControl adventureData;
	
	private ChapterListDataControl chaptersController;
	
	
	@Override
	public boolean canRedo() {
		return false;
	}

	@Override
	public boolean canUndo() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean combine(Tool other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doTool() {
		// Show a dialog asking for the chapter title
		String chapterTitle = controller.showInputDialog( TextConstants.getText( "Operation.AddChapterTitle" ), TextConstants.getText( "Operation.AddChapterMessage" ), TextConstants.getText( "Operation.AddChapterDefaultValue" ) );

		// If some value was typed
		if( chapterTitle != null ) {
			// Create the new chapter, and the controller
			Chapter newChapter = new Chapter( chapterTitle, TextConstants.getText( "DefaultValue.SceneId" ) );
			adventureData.getChapters( ).add( newChapter );
			//chapterDataControlList.add( newChapterDataControl );
			chaptersController.addChapterDataControl(newChapter);

			return true;
		}
		return false;

	}

	@Override
	public boolean redoTool() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean undoTool() {
		chaptersController.removeChapterDataControl(chaptersController.getSelectedChapter()-1);
		return false;
	}
	

}
