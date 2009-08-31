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
package es.eucm.eadventure.editor.control.tools.scene;

import es.eucm.eadventure.common.data.chapter.Trajectory;
import es.eucm.eadventure.common.data.chapter.Trajectory.Side;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.scene.NodeDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.SceneDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.SideDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.TrajectoryDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class AddTrajectorySideTool extends Tool {

    private NodeDataControl startNode;

    private NodeDataControl endNode;

    private Trajectory trajectory;

    private TrajectoryDataControl trajectoryDataControl;

    private SceneDataControl sceneDataControl;

    private Side newSide;

    private SideDataControl newSideDataControl;

    public AddTrajectorySideTool( NodeDataControl startNode, NodeDataControl endNode, Trajectory trajectory, TrajectoryDataControl trajectoryDataControl, SceneDataControl sceneDataControl ) {

        this.startNode = startNode;
        this.endNode = endNode;
        this.trajectory = trajectory;
        this.trajectoryDataControl = trajectoryDataControl;
        this.sceneDataControl = sceneDataControl;
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

        newSide = trajectory.addSide( startNode.getID( ), endNode.getID( ) );
        if( newSide != null ) {
            newSideDataControl = new SideDataControl( sceneDataControl, trajectoryDataControl, newSide );
            trajectoryDataControl.getSides( ).add( newSideDataControl );
            return true;
        }
        return false;
    }

    @Override
    public boolean redoTool( ) {

        trajectory.getSides( ).add( newSide );
        trajectoryDataControl.getSides( ).add( newSideDataControl );
        Controller.getInstance( ).updatePanel( );
        return true;
    }

    @Override
    public boolean undoTool( ) {

        trajectory.getSides( ).remove( newSide );
        trajectoryDataControl.getSides( ).remove( newSideDataControl );
        Controller.getInstance( ).updatePanel( );
        return true;
    }

}
