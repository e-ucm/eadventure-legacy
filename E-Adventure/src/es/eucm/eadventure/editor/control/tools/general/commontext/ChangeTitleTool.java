/*******************************************************************************
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the e-UCM
 *          research group.
 *   
 *    Copyright 2005-2012 e-UCM research group.
 *  
 *     e-UCM is a research group of the Department of Software Engineering
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
 * This file is part of eAdventure, version 1.5.
 * 
 *   You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with Adventure.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.control.tools.general.commontext;

import es.eucm.eadventure.common.data.Titled;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class ChangeTitleTool extends Tool {

    private Titled titled;

    private String title;

    private String oldTitle;

    private Controller controller;

    public ChangeTitleTool( Titled titled, String title ) {

        this.titled = titled;
        this.title = title;
        controller = Controller.getInstance( );
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
    public boolean doTool( ) {

        if( !title.equals( titled.getTitle( ) ) ) {
            oldTitle = titled.getTitle( );
            titled.setTitle( title );
            controller.updateStructure( );
            controller.updateChapterMenu( );
            return true;
        }
        return false;
    }

    @Override
    public String getToolName( ) {

        return "Change Chapter Title";
    }

    @Override
    public boolean redoTool( ) {

        titled.setTitle( title );
        controller.updateStructure( );
        controller.updateChapterMenu( );
        controller.updatePanel( );
        return true;
    }

    @Override
    public boolean undoTool( ) {

        titled.setTitle( oldTitle );
        controller.updateStructure( );
        controller.updateChapterMenu( );
        controller.updatePanel( );
        return true;
    }

    @Override
    public boolean combine( Tool other ) {

        if( other instanceof ChangeTitleTool ) {
            ChangeTitleTool ctt = (ChangeTitleTool) other;
            if( ctt.titled == titled && ctt.oldTitle == title ) {
                title = ctt.title;
                timeStamp = ctt.timeStamp;
                return true;
            }
        }
        return false;
    }

}
