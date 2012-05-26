/*******************************************************************************
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the e-UCM
 *          research group.
 *   
 *    Copyright 2005-2012 e-UCM research group.
 *  
 *     e-UCM is a research group of the Department of Software Engineering
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
 * This file is part of eAdventure, version 1.5.
 * 
 *   You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with Adventure.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.control.tools.general.commontext;

import es.eucm.eadventure.common.data.HasId;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

/**
 * Edition Tool for Changing a Target ID. Supports undo, redo but not combine
 * 
 * @author Javier
 * 
 */
public class ChangeIdTool extends Tool {

    /**
     * The new id
     */
    protected String id;

    /**
     * The old id (for undo/redo)
     */
    protected String oldId;

    /**
     * Tells if the tree must be reloaded after do/undo/redo
     */
    protected boolean updateTree;

    /**
     * Tells if the panel must be reloaded after do/undo/redo
     */
    protected boolean reloadPanel;

    /**
     * The main controller
     */
    protected Controller controller;

    /**
     * The element which contains the targetId
     */
    protected HasId elementWithTargetId;

    /**
     * Default constructor. Does not update neither tree nor panel
     * 
     * @param elementWithTargetId
     * @param newId
     */
    public ChangeIdTool( HasId elementWithTargetId, String newId ) {

        this( elementWithTargetId, newId, false, true );
    }

    public ChangeIdTool( HasId elementWithTargetId, String newId, boolean updateTree, boolean reloadPanel ) {

        this.elementWithTargetId = elementWithTargetId;
        this.id = newId;
        this.oldId = elementWithTargetId.getId( );
        this.updateTree = updateTree;
        this.reloadPanel = reloadPanel;
        this.controller = Controller.getInstance( );
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

        boolean done = false;
        if( !elementWithTargetId.getId( ).equals( id ) ) {
            elementWithTargetId.setId( id );
            done = true;
            if( updateTree )
                controller.updateStructure( );
            if( reloadPanel )
                controller.updatePanel( );
        }
        return done;
    }

    @Override
    public boolean redoTool( ) {

        return undoTool( );
    }

    @Override
    public boolean undoTool( ) {

        elementWithTargetId.setId( oldId );
        String temp = oldId;
        oldId = id;
        id = temp;
        if( updateTree )
            controller.updateStructure( );
        if( reloadPanel )
            controller.updatePanel( );

        return true;
    }
}
