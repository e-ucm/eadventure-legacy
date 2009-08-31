/**
 * <e-Adventure> is an <e-UCM> research project.
 * <e-UCM>, Department of Software Engineering and Artificial Intelligence.
 * Faculty of Informatics, Complutense University of Madrid (Spain).
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J.
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009
 * Web-site: http://e-adventure.e-ucm.es
 */

/*
    Copyright (C) 2004-2009 <e-UCM> research group

    This file is part of <e-Adventure> project, an educational game & game-like 
    simulation authoring tool, availabe at http://e-adventure.e-ucm.es. 

    <e-Adventure> is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    <e-Adventure> is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with <e-Adventure>; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

*/
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
	
	public DeleteTrajectoryNodeTool(DataControl dataControl,
			Trajectory trajectory, TrajectoryDataControl trajectoryDataControl) {
		this.oldNodeDataControl = (NodeDataControl) dataControl;
		this.trajectory = trajectory;
		this.trajectoryDataControl = trajectoryDataControl;
		this.oldSides = new ArrayList<SideDataControl>();
		this.wasInitial = (trajectoryDataControl.getInitialNode() == oldNodeDataControl);
	}

	@Override
	public boolean canRedo() {
		return true;
	}

	@Override
	public boolean canUndo() {
		return true;
	}

	@Override
	public boolean combine(Tool other) {
		return false;
	}

	@Override
	public boolean doTool() {
		Node temp = (Node) oldNodeDataControl.getContent();
		trajectory.removeNode(temp.getX(), temp.getY());
		trajectoryDataControl.getNodes().remove(oldNodeDataControl);
		
		if (wasInitial) {
			trajectory.setInitial(null);
			trajectoryDataControl.initialNode = null;
			
			trajectory.setInitial(trajectory.getNodes().get(0).getID());
			trajectoryDataControl.initialNode = trajectoryDataControl.getNodes().get(0);
		}		
		
		for (SideDataControl side : trajectoryDataControl.getSides()) {
			if (!trajectory.getSides().contains(side.getContent())) {
				oldSides.add(side);
			}
		}
		for (SideDataControl side : oldSides) {
			trajectoryDataControl.getSides().remove(side);
		}
		
		return true;
	}

	@Override
	public boolean redoTool() {
		Node temp = (Node) oldNodeDataControl.getContent();
		trajectory.removeNode(temp.getX(), temp.getY());
		trajectoryDataControl.getNodes().remove(oldNodeDataControl);

		if (wasInitial) {
			trajectory.setInitial(null);
			trajectoryDataControl.initialNode = null;

			trajectory.setInitial(trajectory.getNodes().get(0).getID());
			trajectoryDataControl.initialNode = trajectoryDataControl.getNodes().get(0);
		}		

		for (SideDataControl side : trajectoryDataControl.getSides()) {
			if (!trajectory.getSides().contains(side.getContent())) {
				oldSides.add(side);
			}
		}
		for (SideDataControl side : oldSides) {
			trajectoryDataControl.getSides().remove(side);
		}
		
		Controller.getInstance().updatePanel();

		return true;
	}

	@Override
	public boolean undoTool() {
		Node temp = (Node) oldNodeDataControl.getContent();
		trajectory.getNodes().add(temp);
		trajectoryDataControl.getNodes().add(oldNodeDataControl);
		
		if (wasInitial) {
			trajectory.setInitial(temp.getID());
			trajectoryDataControl.initialNode = oldNodeDataControl;
		}
	
		for (SideDataControl side : oldSides) {
			trajectory.getSides().add((Side) side.getContent());
			trajectoryDataControl.getSides().add(side);
		}
		
		Controller.getInstance().updatePanel();
		
		return true;
	}

}
