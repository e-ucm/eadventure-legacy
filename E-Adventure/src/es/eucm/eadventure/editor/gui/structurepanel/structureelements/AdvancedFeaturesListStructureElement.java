package es.eucm.eadventure.editor.gui.structurepanel.structureelements;

import javax.swing.ImageIcon;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.general.AdvancedFeaturesDataControl;
import es.eucm.eadventure.editor.gui.structurepanel.StructureElementFactory;
import es.eucm.eadventure.editor.gui.structurepanel.StructureListElement;
import es.eucm.eadventure.editor.gui.structurepanel.StructureElement;

public class AdvancedFeaturesListStructureElement extends StructureListElement {

	public AdvancedFeaturesListStructureElement(DataControl dataControl) {
		super(TextConstants.getText("AdvancedFeatures.Title"), dataControl);
		icon = new ImageIcon( "img/icons/advanced.png" );
	}
	
	@Override
	public int getChildCount() {
		return 0;
	}

	@Override
	public StructureElement getChild(int i) {
		return null;
	}
}