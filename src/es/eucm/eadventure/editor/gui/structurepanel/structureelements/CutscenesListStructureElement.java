package es.eucm.eadventure.editor.gui.structurepanel.structureelements;

import javax.swing.ImageIcon;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.cutscene.CutscenesListDataControl;
import es.eucm.eadventure.editor.gui.structurepanel.StructureElementFactory;
import es.eucm.eadventure.editor.gui.structurepanel.StructureListElement;
import es.eucm.eadventure.editor.gui.structurepanel.StructureElement;

public class CutscenesListStructureElement extends StructureListElement {

	public CutscenesListStructureElement(DataControl dataControl) {
		super(TextConstants.getText("CutscenesList.Title"), dataControl);
		icon = new ImageIcon( "img/icons/cutscenes.png" );
	}

	@Override
	public int getChildCount() {
		return ((CutscenesListDataControl) dataControl).getCutscenes().size();
	}

	@Override
	public StructureElement getChild(int i) {
		return StructureElementFactory.getStructureElement(((CutscenesListDataControl) dataControl).getCutscenes().get(i));
	}
	
}
