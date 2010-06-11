/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *         research group.
 *  
 *   Copyright 2005-2010 <e-UCM> research group.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *         http://e-adventure.e-ucm.es/contributors
 * 
 *   <e-UCM> is a research group of the Department of Software Engineering
 *         and Artificial Intelligence at the Complutense University of Madrid
 *         (School of Computer Science).
 * 
 *         C Profesor Jose Garcia Santesmases sn,
 *         28040 Madrid (Madrid), Spain.
 * 
 *         For more info please visit:  <http://e-adventure.e-ucm.es> or
 *         <http://www.e-ucm.es>
 * 
 * ****************************************************************************
 * 
 * This file is part of <e-Adventure>, version 1.2.
 * 
 *     <e-Adventure> is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     <e-Adventure> is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 * 
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.control.tools.general.chapters;

import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.general.ChapterListDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class DeleteChapterTool extends Tool {

    private Controller controller;

    private ChapterListDataControl chaptersController;

    // Removed data
    private Chapter chapterRemoved;

    private int index;

    public DeleteChapterTool( ChapterListDataControl chaptersController ) {

        this.chaptersController = chaptersController;
        controller = Controller.getInstance( );
        doesClone( true );
    }

    @Override
    public boolean canRedo( ) {

        return false;
    }

    @Override
    public boolean canUndo( ) {

        return false;
    }

    @Override
    public boolean combine( Tool other ) {

        return false;
    }

    @Override
    public boolean doTool( ) {

        boolean done = false;
        // Check the number of chapters, the chapters can be deleted when there are more than one
        if( chaptersController.getChaptersCount( ) > 1 ) {
            // Ask for confirmation
            if( controller.showStrictConfirmDialog( TC.get( "Operation.DeleteChapterTitle" ), TC.get( "Operation.DeleteChapterMessage" ) ) ) {
                // Delete the chapter and the controller
                index = chaptersController.getSelectedChapter( );
                chapterRemoved = ( (Chapter) chaptersController.removeChapterDataControl( ).getContent( ) );
                //chapterDataControlList.remove( selectedChapter );
                done = true;

                // Update the main window
                controller.reloadData( );
            }
        }

        // If there is only one chapter, show an error message
        else
            controller.showErrorDialog( TC.get( "Operation.DeleteChapterTitle" ), TC.get( "Operation.DeleteChapterErrorLastChapter" ) );

        return done;
    }

    @Override
    public boolean redoTool( ) {

        chaptersController.removeChapterDataControl( index );
        // Update the main window
        controller.reloadData( );
        return true;
    }

    @Override
    public boolean undoTool( ) {

        chaptersController.addChapterDataControl( index, chapterRemoved );
        // Update the main window
        controller.reloadData( );
        return true;
    }

}
