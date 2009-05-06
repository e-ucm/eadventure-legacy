package es.eucm.eadventure.editor.gui.otherpanels.scenelistelements;

import java.util.List;

import es.eucm.eadventure.editor.control.controllers.scene.ElementReferenceDataControl;

public class ItemReferenceElement {
	
	private ElementReferenceDataControl aadc;
	
	private List<String> sceneIdList;
	
	private int height;
	
	public ItemReferenceElement(ElementReferenceDataControl aadc, int height, List<String> sceneIdList) {
		this.aadc = aadc;
		this.sceneIdList = sceneIdList;
		this.height = height;
	}
	
	public int getPosX() {
		return aadc.getElementX();
	}
	
	public int getPosY() {
		return aadc.getElementY() - height/2;
	}
	
	public List<String> getSceneIds() {
		return sceneIdList;
	}
}