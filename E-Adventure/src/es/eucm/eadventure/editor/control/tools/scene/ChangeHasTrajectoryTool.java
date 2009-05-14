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
	
	public ChangeHasTrajectoryTool(boolean selected,
			SceneDataControl sceneDataControl) {
		this.selected = selected;
		this.sceneDataControl = sceneDataControl;
		this.oldTrajectoryDataControl = sceneDataControl.getTrajectory();
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
		if (selected == sceneDataControl.getTrajectory().hasTrajectory())
			return false;
		if (!selected) {
			newTrajectoryDataControl = new TrajectoryDataControl(sceneDataControl, null);
			sceneDataControl.setTrajectory(null);
			sceneDataControl.setTrajectoryDataControl(newTrajectoryDataControl);
		} else {
			Trajectory trajectory = new Trajectory();
			trajectory.addNode("node" + (new Random()).nextInt(10000), 300, 300, 1.0f);
			newTrajectoryDataControl = new TrajectoryDataControl(sceneDataControl, trajectory);
			sceneDataControl.setTrajectory(trajectory);
			sceneDataControl.setTrajectoryDataControl(newTrajectoryDataControl);
		}
		return true;
	}

	@Override
	public boolean redoTool() {
		if (newTrajectoryDataControl.hasTrajectory())
			sceneDataControl.setTrajectory((Trajectory) newTrajectoryDataControl.getContent());
		else
			sceneDataControl.setTrajectory(null);
		sceneDataControl.setTrajectoryDataControl(newTrajectoryDataControl);
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		if (oldTrajectoryDataControl.hasTrajectory())
			sceneDataControl.setTrajectory((Trajectory) oldTrajectoryDataControl.getContent());
		else
			sceneDataControl.setTrajectory(null);
		sceneDataControl.setTrajectoryDataControl(oldTrajectoryDataControl);
		Controller.getInstance().updatePanel();
		return true;
	}

}
