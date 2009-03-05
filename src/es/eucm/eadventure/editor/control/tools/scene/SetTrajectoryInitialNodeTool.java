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
	
	public SetTrajectoryInitialNodeTool(Trajectory trajectory,
			TrajectoryDataControl trajectoryDataControl,
			NodeDataControl nodeDataControl) {
		this.trajectory = trajectory;
		this.trajectoryDataControl = trajectoryDataControl;
		this.nodeDataControl = nodeDataControl;
		this.oldInitialNodeDataControl = trajectoryDataControl.getInitialNode();
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
		if (trajectory.getInitial() != null && trajectory.getInitial().getID().equals(nodeDataControl.getID()))
			return false;
		
		trajectory.setInitial(nodeDataControl.getID());
		
		if (trajectoryDataControl.initialNode != null) {
			trajectoryDataControl.initialNode.setInitial(false);
		}
		
		trajectoryDataControl.initialNode = nodeDataControl;
		trajectoryDataControl.initialNode.setInitial(true);

		return true;
	}

	@Override
	public boolean redoTool() {
		trajectory.setInitial(nodeDataControl.getID());
		
		if (trajectoryDataControl.initialNode != null) {
			trajectoryDataControl.initialNode.setInitial(false);
		}
		
		trajectoryDataControl.initialNode = nodeDataControl;
		trajectoryDataControl.initialNode.setInitial(true);

		Controller.getInstance().updatePanel();

		return true;
	}

	@Override
	public boolean undoTool() {
		nodeDataControl.setInitial(false);
		trajectoryDataControl.initialNode = oldInitialNodeDataControl;
		
		if (trajectoryDataControl.initialNode != null) {
			trajectory.setInitial(trajectoryDataControl.getInitialNode().getID());
			trajectoryDataControl.initialNode.setInitial(true);
		} else {
			trajectory.setInitial("");
		}
		
		Controller.getInstance().updatePanel();
		return true;
	}

}
