package es.eucm.eadventure.editor.control.tools.scene;

import es.eucm.eadventure.common.data.chapter.Trajectory;
import es.eucm.eadventure.common.data.chapter.Trajectory.Side;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.scene.SideDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.TrajectoryDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class DeleteTrajectorySideTool extends Tool {

	private SideDataControl sideDataControl;
	
	private Trajectory trajectory;
	
	private TrajectoryDataControl trajectoryDataControl;
	
	public DeleteTrajectorySideTool(SideDataControl dataControl,
			Trajectory trajectory, TrajectoryDataControl trajectoryDataControl) {
		this.sideDataControl = dataControl;
		this.trajectory = trajectory;
		this.trajectoryDataControl = trajectoryDataControl;
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
		trajectoryDataControl.getSides().remove(sideDataControl);
		trajectory.getSides().remove((Side) sideDataControl.getContent());
		return true;
	}

	@Override
	public boolean redoTool() {
		trajectoryDataControl.getSides().remove(sideDataControl);
		trajectory.getSides().remove((Side) sideDataControl.getContent());
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		trajectoryDataControl.getSides().add(sideDataControl);
		trajectory.getSides().add((Side) sideDataControl.getContent());
		Controller.getInstance().updatePanel();
		return true;
	}

}
