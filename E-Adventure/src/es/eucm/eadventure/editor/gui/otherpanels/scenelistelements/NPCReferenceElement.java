package es.eucm.eadventure.editor.gui.otherpanels.scenelistelements;

import java.util.List;

import es.eucm.eadventure.editor.control.controllers.scene.ElementReferenceDataControl;

public class NPCReferenceElement {
	
	private ElementReferenceDataControl aadc;
	
	private List<String> sceneIdList;
	
	public NPCReferenceElement(ElementReferenceDataControl aadc, List<String> sceneIdList) {
		this.aadc = aadc;
		this.sceneIdList = sceneIdList;
	}
	
	public int getPosX() {
		return aadc.getElementX();
	}
	
	public int getPosY() {
		return aadc.getElementY();
	}
	
	public List<String> getSceneIds() {
		return sceneIdList;
	}
}
