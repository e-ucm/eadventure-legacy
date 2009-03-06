package es.eucm.eadventure.editor.control.tools.general.chapters;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.general.ChapterListDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class MoveChapterTool extends Tool{

	public static final int MODE_UP = 0;
	public static final int MODE_DOWN = 1;
	
	private ChapterListDataControl chaptersController;
	
	private Controller controller;
	
	private int mode;
	
	private int index;

	public MoveChapterTool ( int mode, ChapterListDataControl chaptersController ){
		this.mode = mode;
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
		boolean moved = false;
		index = chaptersController.getSelectedChapter();
		if ( mode == MODE_UP ){
			moved = moveChapterUp( index );
		}
		else if ( mode == MODE_DOWN ){
			moved = moveChapterDown( index );
		}

		return moved;
	}

	@Override
	public boolean redoTool() {
		boolean moved = false;
		if ( mode == MODE_UP ){
			moved = moveChapterUp( index );
		}
		else if ( mode == MODE_DOWN ){
			moved = moveChapterDown( index );
		}

		return moved;
	}

	@Override
	public boolean undoTool() {
		boolean moved = false;
		if ( mode == MODE_UP ){
			moved = moveChapterDown( index-1 );
		}
		else if ( mode == MODE_DOWN ){
			moved = moveChapterUp( index+1 );
		}

		return moved;
	}

	/**
	 * Moves the selected chapter to the previous position of the chapter's list.
	 */
	public boolean moveChapterUp( int index ) {
		boolean moved = false;
		// If the chapter can be moved
		moved=	chaptersController.moveChapterUp(index);
		
		// Update the main window
		if (moved)
			controller.reloadData(  );
		return moved;
	}

	/**
	 * Moves the selected chapter to the next position of the chapter's list.
	 * 
	 */
	public boolean moveChapterDown( int index ) {
		boolean moved = false;
		// Move the chapter and update the selected chapter
		moved=	chaptersController.moveChapterDown();
		// Update the main window
		if (moved)
			controller.reloadData(  );
		return moved;
	}
	
}
