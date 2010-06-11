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
package es.eucm.eadventure.editor.control.tools.general.assets;

import es.eucm.eadventure.common.data.adventure.AdventureData;
import es.eucm.eadventure.common.data.adventure.CustomButton;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class DeleteButtonTool extends Tool {

    private AdventureData adventureData;

    private CustomButton cursorDeleted;

    private String action;

    private String type;

    private int index;

    public DeleteButtonTool( AdventureData adventureData, String action, String type ) {

        this.adventureData = adventureData;
        this.action = action;
        this.type = type;
    }

    @Override
    public boolean canRedo( ) {

        return true;
    }

    @Override
    public boolean canUndo( ) {

        return cursorDeleted != null;
    }

    @Override
    public boolean combine( Tool other ) {

        return false;
    }

    @Override
    public boolean doTool( ) {

        boolean deleted = false;
        CustomButton button = new CustomButton( action, type, null );
        for( int i = 0; i < adventureData.getButtons( ).size( ); i++ ) {
            CustomButton cb = adventureData.getButtons( ).get( i );
            if( cb.equals( button ) ) {
                cursorDeleted = adventureData.getButtons( ).remove( i );
                index = i;
                deleted = true;
                break;
            }
        }
        return deleted;
    }

    @Override
    public boolean redoTool( ) {

        adventureData.getButtons( ).remove( index );
        Controller.getInstance( ).updatePanel( );
        return true;
    }

    @Override
    public boolean undoTool( ) {

        adventureData.getButtons( ).add( index, cursorDeleted );
        Controller.getInstance( ).updatePanel( );
        return true;
    }

}
