/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *          research group.
 *   
 *    Copyright 2005-2012 <e-UCM> research group.
 *  
 *     <e-UCM> is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *  
 *          C Profesor Jose Garcia Santesmases sn,
 *          28040 Madrid (Madrid), Spain.
 *  
 *          For more info please visit:  <http://e-adventure.e-ucm.es> or
 *          <http://www.e-ucm.es>
 *  
 *  ****************************************************************************
 * This file is part of <e-Adventure>, version 1.4.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       <e-Adventure> is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      <e-Adventure> is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.control.tools.general.chapters;

import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.general.ChapterListDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class AddChapterTool extends Tool {

    private Controller controller;

    private ChapterListDataControl chaptersController;

    private Chapter newChapter;

    private int index;

    private String chapterTitle;

    public AddChapterTool( ChapterListDataControl chaptersController ) {

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

        // Show a dialog asking for the chapter title
        chapterTitle = controller.showInputDialog( TC.get( "Operation.AddChapterTitle" ), TC.get( "Operation.AddChapterMessage" ), TC.get( "Operation.AddChapterDefaultValue" ) );

        // If some value was typed
        if( chapterTitle != null ) {
            if( !chaptersController.exitsChapter( chapterTitle ) ) {
                // Create the new chapter, and the controller
                newChapter = new Chapter( chapterTitle, TC.get( "DefaultValue.SceneId" ) );
                chaptersController.addChapterDataControl( newChapter );
                index = chaptersController.getSelectedChapter( );

                controller.reloadData( );
                return true;
            }
            else {
                controller.showErrorDialog( TC.get( "Operation.CreateAdaptationFile.FileName.ExistValue.Title" ), TC.get( "Operation.NewChapter.ExistingName" ) );
                return false;
            }
        }
        return false;

    }

    @Override
    public boolean redoTool( ) {

        // Create the new chapter, and the controller
        newChapter = new Chapter( chapterTitle, TC.get( "DefaultValue.SceneId" ) );
        chaptersController.addChapterDataControl( newChapter );
        index = chaptersController.getSelectedChapter( );

        controller.reloadData( );
        return true;
    }

    @Override
    public boolean undoTool( ) {

        boolean done = ( chaptersController.removeChapterDataControl( index ) ) != null;
        controller.reloadData( );
        return done;
    }

}
