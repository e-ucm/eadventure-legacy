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
