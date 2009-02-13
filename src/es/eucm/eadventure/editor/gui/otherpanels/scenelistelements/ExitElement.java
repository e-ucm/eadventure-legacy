package es.eucm.eadventure.editor.gui.otherpanels.scenelistelements;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.editor.control.controllers.general.NextSceneDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ExitDataControl;

public class ExitElement {

	private ExitDataControl exitDataControl;
	
	public ExitElement(ExitDataControl exitDataControl) {
		this.exitDataControl = exitDataControl;
	}
	
	public int getPosX() {
		return exitDataControl.getX() + exitDataControl.getWidth() / 2;
	}
	
	public int getPosY() {
		return exitDataControl.getY() + exitDataControl.getHeight() / 2;
	}
	
	public List<String> getSceneIds() {
		List<String> temp = new ArrayList<String>();
		for (NextSceneDataControl next : exitDataControl.getNextScenes()) {
			temp.add(next.getNextSceneId());
		}
		return temp;
	}
	
}
