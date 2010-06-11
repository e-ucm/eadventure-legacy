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
