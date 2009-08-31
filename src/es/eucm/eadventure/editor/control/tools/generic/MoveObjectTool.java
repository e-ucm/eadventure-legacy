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
package es.eucm.eadventure.editor.control.tools.generic;

import java.util.List;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

/**
 * Convenient edition tool for moving up or down an object in a list (one unit)
 * 
 * @author Javier
 * 
 */
public class MoveObjectTool extends Tool {

    public static final int MODE_UP = 0;

    public static final int MODE_DOWN = 1;

    private List list;

    private int index;

    private int newIndex;

    private int mode;

    /**
     * Constructor.
     * 
     * @param list
     *            The List which contains the object to be moved
     * @param index
     *            The index of the object in the list
     * @param mode
     *            MODE_UP if the object must be moved one position up MODE_DOWN
     *            if the object must be moved one position down
     */
    public MoveObjectTool( List list, int index, int mode ) {

        this.list = list;
        this.index = index;
        this.mode = mode;
    }

    /**
     * Constructor.
     * 
     * @param list
     *            The List which contains the object to be moved
     * @param object
     *            The object in the list. It must be compulsorily in the list
     * @param mode
     *            MODE_UP if the object must be moved one position up MODE_DOWN
     *            if the object must be moved one position down
     */
    public MoveObjectTool( List list, Object object, int mode ) {

        this( list, list.indexOf( object ), mode );
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

        if( mode == MODE_UP )
            newIndex = moveUp( );
        else if( mode == MODE_DOWN )
            newIndex = moveDown( );
        return ( newIndex != -1 );
    }

    @Override
    public boolean redoTool( ) {

        boolean done = false;
        if( mode == MODE_UP )
            done = moveUp( ) != -1;
        else if( mode == MODE_DOWN )
            done = moveDown( ) != -1;

        if( done )
            Controller.getInstance( ).updatePanel( );
        return done;
    }

    @Override
    public boolean undoTool( ) {

        boolean done = false;
        if( mode == MODE_UP ) {
            int temp = index;
            index = newIndex;
            done = moveDown( ) != -1;
            index = temp;
        }
        else if( mode == MODE_DOWN ) {
            int temp = index;
            index = newIndex;
            done = moveUp( ) != -1;
            index = temp;

        }

        if( done )
            Controller.getInstance( ).updatePanel( );
        return done;

    }

    @Override
    public boolean combine( Tool other ) {

        return false;
    }

    private int moveUp( ) {

        int moved = -1;

        if( index > 0 ) {
            list.add( index - 1, list.remove( index ) );
            moved = index - 1;
        }

        return moved;
    }

    private int moveDown( ) {

        int moved = -1;

        if( index < list.size( ) - 1 ) {
            list.add( index + 1, list.remove( index ) );
            moved = index + 1;
        }

        return moved;
    }

}
