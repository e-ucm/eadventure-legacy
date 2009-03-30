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
		return 5;
	}

	@Override
	public StructureElement getChild(int i) {
		if (i == 0)
			return StructureElementFactory.getStructureElement((((AdvancedFeaturesDataControl) dataControl).getTimersList()));
		if (i == 1)
			return StructureElementFactory.getStructureElement((((AdvancedFeaturesDataControl) dataControl).getAdaptationController()));
		if (i == 2)
			return StructureElementFactory.getStructureElement((((AdvancedFeaturesDataControl) dataControl).getAssessmentController()));
		if (i == 3)
			return StructureElementFactory.getStructureElement((((AdvancedFeaturesDataControl) dataControl).getGlobalStatesListDataControl()));
		if (i == 4)
			return StructureElementFactory.getStructureElement((((AdvancedFeaturesDataControl) dataControl).getMacrosListDataControl()));
		return null;
	}
}