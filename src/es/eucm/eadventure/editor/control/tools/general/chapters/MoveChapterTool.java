/**
 * <e-Adventure> is an <e-UCM> research project.
 * <e-UCM>, Department of Software Engineering and Artificial Intelligence.
 * Faculty of Informatics, Complutense University of Madrid (Spain).
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J.
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009
 * Web-site: http://e-adventure.e-ucm.es
 */

/*
    Copyright (C) 2004-2009 <e-UCM> research group

    This file is part of <e-Adventure> project, an educational game & game-like 
    simulation authoring tool, availabe at http://e-adventure.e-ucm.es. 

    <e-Adventure> is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    <e-Adventure> is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with <e-Adventure>; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

*/
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
		moved=	chaptersController.moveChapterDown(index);
		// Update the main window
		if (moved)
			controller.reloadData(  );
		return moved;
	}
	
}
