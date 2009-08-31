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
package es.eucm.eadventure.editor.control.controllers;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

/**
 * Controller that manages a double list of tools for undo/redo
 * 
 * @author Javier
 * 
 */
public class ToolManager {

    private List<Tool> undoList;

    private List<Tool> redoList;

    private Tool lastUndo = null;

    private boolean notifyController;

    /**
     * Void and private constructor.
     */
    public ToolManager( boolean notifyController ) {

        undoList = new ArrayList<Tool>( );
        redoList = new ArrayList<Tool>( );
        this.notifyController = notifyController;
    }

    public boolean addTool( Tool tool ) {

        return addTool( true, tool );
    }

    public boolean addTool( boolean execute, Tool tool ) {

        boolean done = execute ? tool.doTool( ) : true;
        if( done ) {
            if( undoList.size( ) == 0 )
                undoList.add( tool );
            else {
                Tool last = undoList.get( undoList.size( ) - 1 );
                if( last.getTimeStamp( ) < tool.getTimeStamp( ) - 1500 || !last.combine( tool ) )
                    undoList.add( tool );
            }
            redoList.clear( );
            if( notifyController )
                Controller.getInstance( ).dataModified( );

            if( !tool.canUndo( ) ) {
                undoList.clear( );
            }
        }
        return done;
    }

    public boolean undoTool( ) {

        if( undoList.size( ) > 0 ) {
            Tool temp = undoList.remove( undoList.size( ) - 1 );
            boolean undone = temp.undoTool( );
            if( undone ) {
                lastUndo = temp;
                if( temp.canRedo( ) )
                    redoList.add( temp );
                else
                    redoList.clear( );
                return true;
            }
        }
        return false;
    }

    public boolean redoTool( ) {

        if( redoList.size( ) > 0 ) {
            Tool temp = redoList.remove( redoList.size( ) - 1 );
            boolean done = temp.redoTool( );
            if( done ) {
                undoList.add( temp );
                if( !temp.canUndo( ) ) {
                    undoList.clear( );
                }
            }
            return done;
        }
        return false;
    }

    public void clear( ) {

        undoList.clear( );
        redoList.clear( );
    }

    // DEbugging
    /**
     * @return the undoList
     */
    public List<Tool> getUndoList( ) {

        return undoList;
    }

    /**
     * @return the redoList
     */
    public List<Tool> getRedoList( ) {

        return redoList;
    }

}
