package es.eucm.eadventure.editor.gui.structurepanel.structureelements;

import javax.swing.ImageIcon;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ScenesListDataControl;
import es.eucm.eadventure.editor.gui.structurepanel.StructureElementFactory;
import es.eucm.eadventure.editor.gui.structurepanel.StructureListElement;
import es.eucm.eadventure.editor.gui.structurepanel.StructureElement;

public class ScenesListStructureElement extends StructureListElement {

	public ScenesListStructureElement(DataControl dataControl) {
		super(TextConstants.getText("ScenesList.Title"), dataControl);
		icon = new ImageIcon( "img/icons/scenes.png" );
	}
	
	@Override
	public int getChildCount() {
		return ((ScenesListDataControl) dataControl).getScenes().size();
	}

	@Override
	public StructureElement getChild(int i) {
		return StructureElementFactory.getStructureElement((((ScenesListDataControl) dataControl).getScenes().get(i)), this);
	}
}