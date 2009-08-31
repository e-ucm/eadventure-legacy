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
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.scene.SceneDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.TrajectoryDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class ChangeHasTrajectoryTool extends Tool {

    private boolean selected;

    private SceneDataControl sceneDataControl;

    private TrajectoryDataControl oldTrajectoryDataControl;

    private TrajectoryDataControl newTrajectoryDataControl;

    public ChangeHasTrajectoryTool( boolean selected, SceneDataControl sceneDataControl ) {

        this.selected = selected;
        this.sceneDataControl = sceneDataControl;
        this.oldTrajectoryDataControl = sceneDataControl.getTrajectory( );
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

        if( selected == sceneDataControl.getTrajectory( ).hasTrajectory( ) )
            return false;
        if( !selected ) {
            newTrajectoryDataControl = new TrajectoryDataControl( sceneDataControl, null );
            sceneDataControl.setTrajectory( null );
            sceneDataControl.setTrajectoryDataControl( newTrajectoryDataControl );
        }
        else {
            Trajectory trajectory = new Trajectory( );
            trajectory.addNode( "node" + ( new Random( ) ).nextInt( 10000 ), 300, 300, 1.0f );
            newTrajectoryDataControl = new TrajectoryDataControl( sceneDataControl, trajectory );
            sceneDataControl.setTrajectory( trajectory );
            sceneDataControl.setTrajectoryDataControl( newTrajectoryDataControl );
        }
        return true;
    }

    @Override
    public boolean redoTool( ) {

        if( newTrajectoryDataControl.hasTrajectory( ) )
            sceneDataControl.setTrajectory( (Trajectory) newTrajectoryDataControl.getContent( ) );
        else
            sceneDataControl.setTrajectory( null );
        sceneDataControl.setTrajectoryDataControl( newTrajectoryDataControl );
        Controller.getInstance( ).updatePanel( );
        return true;
    }

    @Override
    public boolean undoTool( ) {

        if( oldTrajectoryDataControl.hasTrajectory( ) )
            sceneDataControl.setTrajectory( (Trajectory) oldTrajectoryDataControl.getContent( ) );
        else
            sceneDataControl.setTrajectory( null );
        sceneDataControl.setTrajectoryDataControl( oldTrajectoryDataControl );
        Controller.getInstance( ).updatePanel( );
        return true;
    }

}
