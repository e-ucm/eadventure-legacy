package es.eucm.eadventure.editor.gui.otherpanels.scenelistelements;

import java.util.List;

import es.eucm.eadventure.editor.control.controllers.scene.ActiveAreaDataControl;

public class ActiveAreaElement {
	
	private ActiveAreaDataControl aadc;
	
	private List<String> sceneIdList;
	
	public ActiveAreaElement(ActiveAreaDataControl aadc, List<String> sceneIdList) {
		this.aadc = aadc;
		this.sceneIdList = sceneIdList;
	}
	
	public int getPosX() {
		return aadc.getX() + aadc.getWidth() / 2;
	}
	
	public int getPosY() {
		return aadc.getY() + aadc.getHeight() / 2;
	}
	
	public List<String> getSceneIds() {
		return sceneIdList;
	}
}
