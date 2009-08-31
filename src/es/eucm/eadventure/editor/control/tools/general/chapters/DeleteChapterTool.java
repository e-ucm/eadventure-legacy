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
		doesClone(true);
	}
	
	@Override
	public boolean canRedo() {
		return false;
	}

	@Override
	public boolean canUndo() {
		return false;
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
