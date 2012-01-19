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
