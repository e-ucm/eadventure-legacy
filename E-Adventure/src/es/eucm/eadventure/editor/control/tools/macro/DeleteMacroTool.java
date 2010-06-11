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
package es.eucm.eadventure.editor.control.tools.macro;

import es.eucm.eadventure.common.data.chapter.effects.Macro;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.macro.MacroDataControl;
import es.eucm.eadventure.editor.control.controllers.macro.MacroListDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class DeleteMacroTool extends Tool {

    private MacroListDataControl dataControl;

    private MacroDataControl element;

    private int position;

    public DeleteMacroTool( MacroListDataControl dataControl2, int selectedRow ) {

        this.dataControl = dataControl2;
        this.position = selectedRow;
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

        element = dataControl.getMacros( ).get( position );
        dataControl.deleteElement( dataControl.getMacros( ).get( position ), true );
        return true;
    }

    @Override
    public boolean redoTool( ) {

        dataControl.deleteElement( element, true );
        Controller.getInstance( ).getIdentifierSummary( ).deleteMacroId( element.getId( ) );
        Controller.getInstance( ).updatePanel( );
        return true;
    }

    @Override
    public boolean undoTool( ) {

        dataControl.getMacrosList( ).add( position, (Macro) element.getContent( ) );
        dataControl.getMacros( ).add( position, element );
        Controller.getInstance( ).getIdentifierSummary( ).addMacroId( element.getId( ) );
        Controller.getInstance( ).updatePanel( );
        return true;
    }
}
