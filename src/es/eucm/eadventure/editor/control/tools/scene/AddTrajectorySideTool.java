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

        newSide = trajectory.addSide( startNode.getID( ), endNode.getID( ) , -1);
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
