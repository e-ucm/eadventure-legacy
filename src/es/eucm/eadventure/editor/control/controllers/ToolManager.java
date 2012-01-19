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
