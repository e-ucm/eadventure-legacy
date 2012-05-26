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

import es.eucm.eadventure.common.data.Named;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class ChangeNameTool extends Tool {

    private Named named;

    private String name;

    private String oldName;

    private Controller controller;

    public ChangeNameTool( Named scene, String name ) {

        this.named = scene;
        this.name = name;
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
    public boolean doTool( ) {

        if( !name.equals( named.getName( ) ) ) {
            oldName = named.getName( );
            named.setName( name );
            return true;
        }
        return false;
    }

    @Override
    public boolean redoTool( ) {

        named.setName( name );
        controller.updatePanel( );
        return true;
    }

    @Override
    public boolean undoTool( ) {

        named.setName( oldName );
        controller.updatePanel( );
        return true;
    }

    @Override
    public boolean combine( Tool other ) {

        if( other instanceof ChangeNameTool ) {
            ChangeNameTool cnt = (ChangeNameTool) other;
            if( cnt.named == named && cnt.oldName == name ) {
                name = cnt.name;
                timeStamp = cnt.timeStamp;
                return true;
            }
        }
        return false;
    }
}
