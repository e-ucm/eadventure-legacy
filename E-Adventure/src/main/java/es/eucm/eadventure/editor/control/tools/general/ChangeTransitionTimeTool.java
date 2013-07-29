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
package es.eucm.eadventure.editor.control.tools.general;

import es.eucm.eadventure.common.data.chapter.NextScene;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class ChangeTransitionTimeTool extends Tool {

    private int newTime;

    private int oldTime;

    private NextScene nextScene;

    public ChangeTransitionTimeTool( NextScene nextScene, int value ) {

        this.oldTime = nextScene.getTransitionTime( );
        this.newTime = value;
        this.nextScene = nextScene;
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

        if( other instanceof ChangeTransitionTimeTool ) {
            ChangeTransitionTimeTool cttt = (ChangeTransitionTimeTool) other;
            if( cttt.nextScene == nextScene ) {
                this.timeStamp = cttt.timeStamp;
                this.newTime = cttt.newTime;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean doTool( ) {

        if( oldTime == newTime )
            return false;
        nextScene.setTransitionTime( newTime );
        return true;
    }

    @Override
    public boolean redoTool( ) {

        nextScene.setTransitionTime( newTime );
        Controller.getInstance( ).updatePanel( );
        return true;
    }

    @Override
    public boolean undoTool( ) {

        nextScene.setTransitionTime( oldTime );
        Controller.getInstance( ).updatePanel( );
        return true;
    }

}
