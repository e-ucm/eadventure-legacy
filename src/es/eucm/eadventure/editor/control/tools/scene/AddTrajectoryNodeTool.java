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
	
	public AddTrajectoryNodeTool(Trajectory trajectory,
			TrajectoryDataControl trajectoryDataControl, int x, int y, SceneDataControl sceneDataControl) {
		this.trajectory = trajectory;
		this.trajectoryDataControl = trajectoryDataControl;
		this.x = x;
		this.y = y;
		this.sceneDataControl = sceneDataControl;
		this.wasInitial = false;
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
		String id = "node" + (new Random()).nextInt(10000);
		newNode = trajectory.addNode(id, x, y, 1.0f);
		newNodeDataControl = new NodeDataControl(sceneDataControl, newNode);
		trajectoryDataControl.getNodes().add(newNodeDataControl);
		if (trajectory.getInitial() == newNode) {
			trajectoryDataControl.initialNode = newNodeDataControl;
			wasInitial = true;
		}
		return true;
	}

	@Override
	public boolean redoTool() {
		trajectory.getNodes().add(newNode);
		trajectoryDataControl.getNodes().add(newNodeDataControl);
		if (wasInitial) {
			trajectory.setInitial(newNode.getID());
			trajectoryDataControl.initialNode = newNodeDataControl;
		}
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		trajectoryDataControl.getNodes().remove(newNodeDataControl);
		if (wasInitial) {
			trajectoryDataControl.initialNode = null;
			trajectory.setInitial(null);
		}
		trajectory.getNodes().remove(newNode);
		Controller.getInstance().updatePanel();
		return true;
	}

}
