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
package es.eucm.eadventure.editor.control.tools.scene;

import es.eucm.eadventure.common.data.chapter.Trajectory;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.scene.NodeDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.TrajectoryDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class SetTrajectoryInitialNodeTool extends Tool {

    private Trajectory trajectory;

    private TrajectoryDataControl trajectoryDataControl;

    private NodeDataControl nodeDataControl;

    private NodeDataControl oldInitialNodeDataControl;

    public SetTrajectoryInitialNodeTool( Trajectory trajectory, TrajectoryDataControl trajectoryDataControl, NodeDataControl nodeDataControl ) {

        this.trajectory = trajectory;
        this.trajectoryDataControl = trajectoryDataControl;
        this.nodeDataControl = nodeDataControl;
        this.oldInitialNodeDataControl = trajectoryDataControl.getInitialNode( );
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

        if( trajectory.getInitial( ) != null && trajectory.getInitial( ).getID( ).equals( nodeDataControl.getID( ) ) )
            return false;

        trajectory.setInitial( nodeDataControl.getID( ) );

        if( trajectoryDataControl.initialNode != null ) {
            trajectoryDataControl.initialNode.setInitial( false );
        }

        trajectoryDataControl.initialNode = nodeDataControl;
        trajectoryDataControl.initialNode.setInitial( true );

        return true;
    }

    @Override
    public boolean redoTool( ) {

        trajectory.setInitial( nodeDataControl.getID( ) );

        if( trajectoryDataControl.initialNode != null ) {
            trajectoryDataControl.initialNode.setInitial( false );
        }

        trajectoryDataControl.initialNode = nodeDataControl;
        trajectoryDataControl.initialNode.setInitial( true );

        Controller.getInstance( ).updatePanel( );

        return true;
    }

    @Override
    public boolean undoTool( ) {

        nodeDataControl.setInitial( false );
        trajectoryDataControl.initialNode = oldInitialNodeDataControl;

        if( trajectoryDataControl.initialNode != null ) {
            trajectory.setInitial( trajectoryDataControl.getInitialNode( ).getID( ) );
            trajectoryDataControl.initialNode.setInitial( true );
        }
        else {
            trajectory.setInitial( "" );
        }

        Controller.getInstance( ).updatePanel( );
        return true;
    }

}
