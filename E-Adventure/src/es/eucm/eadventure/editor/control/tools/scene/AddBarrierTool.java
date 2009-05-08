package es.eucm.eadventure.editor.control.tools.scene;

import es.eucm.eadventure.common.data.chapter.elements.Barrier;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.scene.BarrierDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.BarriersListDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.gui.otherpanels.ScenePreviewEditionPanel;

public class AddBarrierTool extends Tool {

	private BarriersListDataControl dataControl;
	
	private ScenePreviewEditionPanel spep;

	private BarrierDataControl newBarrier;

	public AddBarrierTool(BarriersListDataControl dataControl,
			ScenePreviewEditionPanel spep2) {
		this.dataControl = dataControl;
		this.spep = spep2;
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
		if (dataControl.addElement(dataControl.getAddableElements()[0], null)) {
			newBarrier = dataControl.getLastBarrier();
			spep.addBarrier(dataControl.getLastBarrier());
			spep.repaint();
			return true;
		}
		return false;
	}

	@Override
	public boolean redoTool() {
		dataControl.getBarriersList().add( (Barrier) newBarrier.getContent() );
		dataControl.getBarriers().add( newBarrier );
		spep.addBarrier(dataControl.getLastBarrier());
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		dataControl.deleteElement(newBarrier, false);
		spep.removeElement(newBarrier);
		Controller.getInstance().updatePanel();
		return true;
	}
}
