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

        if( mode == SET_ID || mode == SET_VALUE ) {
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
