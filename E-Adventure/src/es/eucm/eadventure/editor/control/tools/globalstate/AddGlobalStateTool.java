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
package es.eucm.eadventure.editor.control.tools.globalstate;

import es.eucm.eadventure.common.data.chapter.conditions.GlobalState;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.globalstate.GlobalStateDataControl;
import es.eucm.eadventure.editor.control.controllers.globalstate.GlobalStateListDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class AddGlobalStateTool extends Tool {

    private GlobalStateListDataControl dataControl;

    private GlobalStateDataControl newGlobalState;

    public AddGlobalStateTool( GlobalStateListDataControl dataControl2 ) {

        this.dataControl = dataControl2;
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

        if( dataControl.addElement( dataControl.getAddableElements( )[0], null ) ) {
            newGlobalState = dataControl.getLastGlobalState( );
            return true;
        }
        return false;
    }

    @Override
    public boolean redoTool( ) {

        dataControl.getGlobalStates( ).add( newGlobalState );
        dataControl.getGlobalStatesList( ).add( (GlobalState) newGlobalState.getContent( ) );
        Controller.getInstance( ).getIdentifierSummary( ).addGlobalStateId( newGlobalState.getId( ) );
        Controller.getInstance( ).updatePanel( );
        return true;
    }

    @Override
    public boolean undoTool( ) {

        dataControl.deleteElement( newGlobalState, false );
        Controller.getInstance( ).getIdentifierSummary( ).deleteGlobalStateId( newGlobalState.getId( ) );
        Controller.getInstance( ).updatePanel( );
        return true;
    }
}
