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
package es.eucm.eadventure.editor.control.tools.adaptation;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.data.adaptation.AdaptedState;
import es.eucm.eadventure.common.data.adaptation.ContainsAdaptedState;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

/**
 * Edition Tool for changing an action in an adaptation rule (adapted state)
 * 
 * @author Javier
 * 
 */
public class ChangeActionTool extends Tool {

    public static final int SET_ID = 2;

    public static final int SET_VALUE = 3;

    protected ContainsAdaptedState containsAS;

    protected AdaptedState state;

    protected AdaptedState oldState;

    protected int index;

    protected int mode;

    protected String newValue;

    public ChangeActionTool( ContainsAdaptedState rule, int index, String newValue, int mode ) {

        this.containsAS = rule;
        this.state = rule.getAdaptedState( );
        this.index = index;
        this.newValue = newValue;
        this.mode = mode;
    }

    @Override
    public boolean canRedo( ) {

        return true;
    }

    @Override
    public boolean canUndo( ) {

        return oldState != null && ( mode == SET_ID || mode == SET_VALUE );
    }

    @Override
    public boolean combine( Tool other ) {

        return false;
    }

    @Override
    public boolean doTool( ) {

        boolean added = false;
        //Check there is at least one flag

        if( index >= 0 && index < state.getFlagsVars( ).size( ) ) {
            try {
                this.oldState = (AdaptedState) state.clone( );
                //	By default, the flag is activated. Default flag will be the first one
                if( mode == SET_ID ) {
                    if( !state.getFlagVar( index ).equals( newValue ) ) {
                        state.changeFlag( index, newValue );
                        added = true;
                    }

                }
                else if( mode == SET_VALUE ) {
                    if( !state.getAction( index ).equals( newValue ) ) {
                        state.changeAction( index, newValue );
                        added = true;
                    }
                }
                if( added )
                    Controller.getInstance( ).updateVarFlagSummary( );
            }
            catch( CloneNotSupportedException e ) {
                ReportDialog.GenerateErrorReport( e, false, "Could not clone adaptedState " + ( ( state == null ) ? "null" : state.getClass( ).toString( ) ) );
            }
        }

        //Otherwise, prompt error message
        // If the list had no elements, show an error message
        else
            Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Adaptation.ErrorNoFlags.Title" ), TextConstants.getText( "Adaptation.ErrorNoFlags" ) );

        return added;
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
