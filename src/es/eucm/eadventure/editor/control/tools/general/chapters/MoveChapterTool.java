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

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.general.ChapterListDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class MoveChapterTool extends Tool {

    public static final int MODE_UP = 0;

    public static final int MODE_DOWN = 1;

    private ChapterListDataControl chaptersController;

    private Controller controller;

    private int mode;

    private int index;

    public MoveChapterTool( int mode, ChapterListDataControl chaptersController ) {

        this.mode = mode;
        this.chaptersController = chaptersController;
        this.controller = Controller.getInstance( );
    }

    @Override
    public boolean canRedo( ) {

        return true;
    }

    @Override
    public boolean canUndo( ) {

        return true;
    }

    @Override
    public boolean combine( Tool other ) {

        return false;
    }

    @Override
    public boolean doTool( ) {

        boolean moved = false;
        index = chaptersController.getSelectedChapter( );
        if( mode == MODE_UP ) {
            moved = moveChapterUp( index );
        }
        else if( mode == MODE_DOWN ) {
            moved = moveChapterDown( index );
        }

        return moved;
    }

    @Override
    public boolean redoTool( ) {

        boolean moved = false;
        if( mode == MODE_UP ) {
            moved = moveChapterUp( index );
        }
        else if( mode == MODE_DOWN ) {
            moved = moveChapterDown( index );
        }

        return moved;
    }

    @Override
    public boolean undoTool( ) {

        boolean moved = false;
        if( mode == MODE_UP ) {
            moved = moveChapterDown( index - 1 );
        }
        else if( mode == MODE_DOWN ) {
            moved = moveChapterUp( index + 1 );
        }

        return moved;
    }

    /**
     * Moves the selected chapter to the previous position of the chapter's
     * list.
     */
    public boolean moveChapterUp( int index ) {

        boolean moved = false;
        // If the chapter can be moved
        moved = chaptersController.moveChapterUp( index );

        // Update the main window
        if( moved )
            controller.reloadData( );
        return moved;
    }

    /**
     * Moves the selected chapter to the next position of the chapter's list.
     * 
     */
    public boolean moveChapterDown( int index ) {

        boolean moved = false;
        // Move the chapter and update the selected chapter
        moved = chaptersController.moveChapterDown( index );
        // Update the main window
        if( moved )
            controller.reloadData( );
        return moved;
    }

}
