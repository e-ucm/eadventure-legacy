/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author López Mañas, E., Pérez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, available at http://e-adventure.e-ucm.es.
 * 
 * <e-Adventure> is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * <e-Adventure>; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */
package es.eucm.eadventure.editor.control.tools.general.chapters;

import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.common.gui.TextConstants;
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
        chapterTitle = controller.showInputDialog( TextConstants.getText( "Operation.AddChapterTitle" ), TextConstants.getText( "Operation.AddChapterMessage" ), TextConstants.getText( "Operation.AddChapterDefaultValue" ) );

        // If some value was typed
        if( chapterTitle != null ) {
            if( !chaptersController.exitsChapter( chapterTitle ) ) {
                // Create the new chapter, and the controller
                newChapter = new Chapter( chapterTitle, TextConstants.getText( "DefaultValue.SceneId" ) );
                chaptersController.addChapterDataControl( newChapter );
                index = chaptersController.getSelectedChapter( );

                controller.reloadData( );
                return true;
            }
            else {
                controller.showErrorDialog( TextConstants.getText( "Operation.CreateAdaptationFile.FileName.ExistValue.Title" ), TextConstants.getText( "Operation.NewChapter.ExistingName" ) );
                return false;
            }
        }
        return false;

    }

    @Override
    public boolean redoTool( ) {

        // Create the new chapter, and the controller
        newChapter = new Chapter( chapterTitle, TextConstants.getText( "DefaultValue.SceneId" ) );
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
