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
package es.eucm.eadventure.editor.control.tools.adaptation;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.data.adaptation.AdaptedState;
import es.eucm.eadventure.common.data.adaptation.ContainsAdaptedState;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

/**
 * Edition Tool for adding an action in an adaptation rule (adapted state)
 * 
 * @author Javier
 * 
 */
public class DeleteActionTool extends Tool {

    protected ContainsAdaptedState containsAS;

    protected AdaptedState state;

    protected AdaptedState oldState;

    protected int index;

    protected int mode;

    public DeleteActionTool( ContainsAdaptedState rule, int index ) {

        this.containsAS = rule;
        this.state = rule.getAdaptedState( );
        this.index = index;
    }

    @Override
    public boolean canRedo( ) {

        return true;
    }

    @Override
    public boolean canUndo( ) {

        return oldState != null;
    }

    @Override
    public boolean combine( Tool other ) {

        return false;
    }

    @Override
    public boolean doTool( ) {

        boolean deleted = false;
        if( index >= 0 && index < state.getFlagsVars( ).size( ) ) {
            try {
                oldState = (AdaptedState) state.clone( );
                state.removeFlagVar( index );
                Controller.getInstance( ).updateVarFlagSummary( );
                deleted = true;
                //Controller.getInstance().updatePanel();
            }
            catch( CloneNotSupportedException e ) {
                ReportDialog.GenerateErrorReport( e, false, "Could not clone adaptedState " + ( ( state == null ) ? "null" : state.getClass( ).toString( ) ) );
            }

        }

        return deleted;
    }

    @Override
    public boolean redoTool( ) {

        return undoTool( );
    }

    @Override
    public boolean undoTool( ) {

        try {
            AdaptedState temp = (AdaptedState) state.clone( );
            // Restore initial scene id
            state.setTargetId( oldState.getTargetId( ) );
            // Restore all FlagVars
            state.getFlagsVars( ).clear( );
            for( String flagVar : oldState.getFlagsVars( ) ) {
                state.getFlagsVars( ).add( flagVar );
            }
            // Restore all actions
            state.getActionsValues( ).clear( );
            for( String flagVar : oldState.getActionsValues( ) ) {
                state.getActionsValues( ).add( flagVar );
            }
            oldState = temp;
            Controller.getInstance( ).updateVarFlagSummary( );
            Controller.getInstance( ).updatePanel( );
            return true;
        }
        catch( CloneNotSupportedException e ) {
            ReportDialog.GenerateErrorReport( e, false, "Could not clone adaptedState " + ( ( state == null ) ? "null" : state.getClass( ).toString( ) ) );
            return false;
        }
    }

}
