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
	
	public AddTrajectorySideTool(NodeDataControl startNode,
			NodeDataControl endNode, Trajectory trajectory,
			TrajectoryDataControl trajectoryDataControl,
			SceneDataControl sceneDataControl) {
		this.startNode = startNode;
		this.endNode = endNode;
		this.trajectory = trajectory;
		this.trajectoryDataControl = trajectoryDataControl;
		this.sceneDataControl = sceneDataControl;
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
		newSide = trajectory.addSide(startNode.getID(), endNode.getID());
		if (newSide != null) {
			newSideDataControl = new SideDataControl(sceneDataControl, trajectoryDataControl, newSide);
			trajectoryDataControl.getSides().add(newSideDataControl);
			return true;
		}
		return false;
	}

	@Override
	public boolean redoTool() {
		trajectory.getSides().add(newSide);
		trajectoryDataControl.getSides().add(newSideDataControl);
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		trajectory.getSides().remove(newSide);
		trajectoryDataControl.getSides().remove(newSideDataControl);
		Controller.getInstance().updatePanel();
		return true;
	}

}
