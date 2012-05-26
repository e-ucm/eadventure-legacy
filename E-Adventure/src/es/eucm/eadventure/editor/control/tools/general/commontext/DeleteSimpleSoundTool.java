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

import es.eucm.eadventure.common.data.HasSound;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;


public class DeleteSimpleSoundTool extends Tool {

    private HasSound objectWithSound;
    
    private String oldPath;
    
    public DeleteSimpleSoundTool( HasSound objectWithSound ){
        this.objectWithSound = objectWithSound;
        oldPath = objectWithSound.getSoundPath( );
    }
    
    @Override
    public boolean doTool( ) {
        objectWithSound.setSoundPath( null );
        Controller.getInstance( ).updatePanel( );
        return true;
    }

    @Override
    public boolean redoTool( ) {
        objectWithSound.setSoundPath( null );
        Controller.getInstance( ).updatePanel( );
        return true;
    }

    @Override
    public boolean undoTool( ) {
        objectWithSound.setSoundPath( oldPath );
        Controller.getInstance( ).updatePanel( );
        return true;
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
        return true;
    }

}
