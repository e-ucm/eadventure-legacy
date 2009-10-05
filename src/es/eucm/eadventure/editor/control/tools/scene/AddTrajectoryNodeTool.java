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

import java.util.Random;

import es.eucm.eadventure.common.data.chapter.Trajectory;
import es.eucm.eadventure.common.data.chapter.Trajectory.Node;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.scene.NodeDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.SceneDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.TrajectoryDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class AddTrajectoryNodeTool extends Tool {

    private Trajectory trajectory;

    private TrajectoryDataControl trajectoryDataControl;

    private int x;

    private int y;

    private Node newNode;

    private NodeDataControl newNodeDataControl;

    private SceneDataControl sceneDataControl;

    private boolean wasInitial;

    public AddTrajectoryNodeTool( Trajectory trajectory, TrajectoryDataControl trajectoryDataControl, int x, int y, SceneDataControl sceneDataControl ) {

        this.trajectory = trajectory;
        this.trajectoryDataControl = trajectoryDataControl;
        this.x = x;
        this.y = y;
        this.sceneDataControl = sceneDataControl;
        this.wasInitial = false;
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

        String id = "node" + ( new Random( ) ).nextInt( 10000 );
        newNode = trajectory.addNode( id, x, y, 1.0f );
        newNodeDataControl = new NodeDataControl( sceneDataControl, newNode, trajectory );
        trajectoryDataControl.getNodes( ).add( newNodeDataControl );
        if( trajectory.getInitial( ) == newNode ) {
            trajectoryDataControl.initialNode = newNodeDataControl;
            wasInitial = true;
        }
        return true;
    }

    @Override
    public boolean redoTool( ) {

        trajectory.getNodes( ).add( newNode );
        trajectoryDataControl.getNodes( ).add( newNodeDataControl );
        if( wasInitial ) {
            trajectory.setInitial( newNode.getID( ) );
            trajectoryDataControl.initialNode = newNodeDataControl;
        }
        Controller.getInstance( ).updatePanel( );
        return true;
    }

    @Override
    public boolean undoTool( ) {

        trajectoryDataControl.getNodes( ).remove( newNodeDataControl );
        if( wasInitial ) {
            trajectoryDataControl.initialNode = null;
            trajectory.setInitial( null );
        }
        trajectory.getNodes( ).remove( newNode );
        Controller.getInstance( ).updatePanel( );
        return true;
    }

}
