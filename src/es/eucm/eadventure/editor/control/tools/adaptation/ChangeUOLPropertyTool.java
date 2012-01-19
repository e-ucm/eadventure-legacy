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

import es.eucm.eadventure.common.data.adaptation.AdaptationRule;
import es.eucm.eadventure.common.data.adaptation.UOLProperty;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

/**
 * Tool for changing an adaptation rule (whatever the adaptation rule is)
 * 
 * @author Javier
 * 
 */
public class ChangeUOLPropertyTool extends Tool {

    public static final int SET_ID = 2;

    public static final int SET_VALUE = 3;

    public static final int SET_OP = 4;

    protected UOLProperty oldProperty;

    protected UOLProperty newProperty;

    protected AdaptationRule parent;

    protected int mode;

    protected int index;

    public ChangeUOLPropertyTool( AdaptationRule parent, String newData, int index, int mode ) {

        this.mode = mode;
        this.oldProperty = parent.getUOLProperties( ).get( index );
        this.parent = parent;
        this.index = index;

        if( mode == SET_ID ) {
            newProperty = new UOLProperty( newData, oldProperty.getValue( ), oldProperty.getOperation( ) );
        }
        else if( mode == SET_VALUE ) {
            newProperty = new UOLProperty( oldProperty.getId( ), newData, oldProperty.getOperation( ) );
        }
        else if( mode == SET_OP ) {
            newProperty = new UOLProperty( oldProperty.getId( ), oldProperty.getValue( ), newData );
        }
    }

    @Override
    public boolean canRedo( ) {

        return true;
    }

    @Override
    public boolean canUndo( ) {

        return mode == SET_ID || mode == SET_VALUE || mode == SET_OP;
    }

    @Override
    public boolean combine( Tool other ) {

        return false;
    }

    @Override
    public boolean doTool( ) {

        if( mode == SET_ID || mode == SET_VALUE || mode == SET_OP ) {
            if( index >= 0 && index < parent.getUOLProperties( ).size( ) ) {
                parent.getUOLProperties( ).remove( index );
                parent.getUOLProperties( ).add( index, newProperty );
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean redoTool( ) {

        if( mode == SET_ID || mode == SET_VALUE || mode == SET_OP) {
            parent.getUOLProperties( ).remove( index );
            parent.getUOLProperties( ).add( index, newProperty );
            Controller.getInstance( ).updatePanel( );
            return true;
        }
        return false;
    }

    @Override
    public boolean undoTool( ) {

        if( mode == SET_ID || mode == SET_VALUE || mode == SET_OP ) {
            parent.getUOLProperties( ).remove( index );
            parent.getUOLProperties( ).add( index, oldProperty );
            Controller.getInstance( ).updatePanel( );
            return true;
        }
        return false;
    }

    /**
     * Constructors. Will change the
     * 
     * @param oldRule
     */
}
