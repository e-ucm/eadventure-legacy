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
