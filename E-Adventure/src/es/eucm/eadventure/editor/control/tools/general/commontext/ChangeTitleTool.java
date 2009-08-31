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
