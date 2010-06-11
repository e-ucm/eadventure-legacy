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

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.Trajectory;
import es.eucm.eadventure.common.data.chapter.Trajectory.Node;
import es.eucm.eadventure.common.data.chapter.Trajectory.Side;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.scene.NodeDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.SideDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.TrajectoryDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class DeleteTrajectoryNodeTool extends Tool {

    NodeDataControl oldNodeDataControl;

    Trajectory trajectory;

    TrajectoryDataControl trajectoryDataControl;

    List<SideDataControl> oldSides;

    private boolean wasInitial;

    public DeleteTrajectoryNodeTool( DataControl dataControl, Trajectory trajectory, TrajectoryDataControl trajectoryDataControl ) {

        this.oldNodeDataControl = (NodeDataControl) dataControl;
        this.trajectory = trajectory;
        this.trajectoryDataControl = trajectoryDataControl;
        this.oldSides = new ArrayList<SideDataControl>( );
        this.wasInitial = ( trajectoryDataControl.getInitialNode( ) == oldNodeDataControl );
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

        Node temp = (Node) oldNodeDataControl.getContent( );
        trajectory.removeNode( temp.getX( ), temp.getY( ) );
        trajectoryDataControl.getNodes( ).remove( oldNodeDataControl );

        if( wasInitial ) {
            trajectory.setInitial( null );
            trajectoryDataControl.initialNode = null;

            trajectory.setInitial( trajectory.getNodes( ).get( 0 ).getID( ) );
            trajectoryDataControl.initialNode = trajectoryDataControl.getNodes( ).get( 0 );
        }

        for( SideDataControl side : trajectoryDataControl.getSides( ) ) {
            if( !trajectory.getSides( ).contains( side.getContent( ) ) ) {
                oldSides.add( side );
            }
        }
        for( SideDataControl side : oldSides ) {
            trajectoryDataControl.getSides( ).remove( side );
        }

        return true;
    }

    @Override
    public boolean redoTool( ) {

        Node temp = (Node) oldNodeDataControl.getContent( );
        trajectory.removeNode( temp.getX( ), temp.getY( ) );
        trajectoryDataControl.getNodes( ).remove( oldNodeDataControl );

        if( wasInitial ) {
            trajectory.setInitial( null );
            trajectoryDataControl.initialNode = null;

            trajectory.setInitial( trajectory.getNodes( ).get( 0 ).getID( ) );
            trajectoryDataControl.initialNode = trajectoryDataControl.getNodes( ).get( 0 );
        }

        for( SideDataControl side : trajectoryDataControl.getSides( ) ) {
            if( !trajectory.getSides( ).contains( side.getContent( ) ) ) {
                oldSides.add( side );
            }
        }
        for( SideDataControl side : oldSides ) {
            trajectoryDataControl.getSides( ).remove( side );
        }

        Controller.getInstance( ).updatePanel( );

        return true;
    }

    @Override
    public boolean undoTool( ) {

        Node temp = (Node) oldNodeDataControl.getContent( );
        trajectory.getNodes( ).add( temp );
        trajectoryDataControl.getNodes( ).add( oldNodeDataControl );

        if( wasInitial ) {
            trajectory.setInitial( temp.getID( ) );
            trajectoryDataControl.initialNode = oldNodeDataControl;
        }

        for( SideDataControl side : oldSides ) {
            trajectory.getSides( ).add( (Side) side.getContent( ) );
            trajectoryDataControl.getSides( ).add( side );
        }

        Controller.getInstance( ).updatePanel( );

        return true;
    }

}
