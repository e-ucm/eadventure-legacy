package es.eucm.eadventure.editor.gui.otherpanels.scenelistelements;

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
	
	public String getNextSceneId() {
		return exitDataControl.getNextSceneId();
	}
	
}
