package es.eucm.eadventure.editor.gui.structurepanel.structureelements;

import javax.swing.ImageIcon;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationProfilesDataControl;
import es.eucm.eadventure.editor.gui.structurepanel.StructureElement;
import es.eucm.eadventure.editor.gui.structurepanel.StructureElementFactory;
import es.eucm.eadventure.editor.gui.structurepanel.StructureListElement;

public class AdaptationControllerStructureElement extends StructureListElement {

	
	public AdaptationControllerStructureElement(DataControl dataControl) {
		super(TextConstants.getText("AdaptationProfiles.Title"), dataControl);
		icon = new ImageIcon( "img/icons/adaptationProfiles.png" );
	}
	
	@Override
	public StructureElement getChild(int i) {
		StructureElement temp = StructureElementFactory.getStructureElement(((AdaptationProfilesDataControl) dataControl).getProfiles().get(i), this);
		temp.setName(((AdaptationProfilesDataControl) dataControl).getProfiles().get(i).getFileName());
		return temp;
	}

	@Override
	public int getChildCount() {
		return ((AdaptationProfilesDataControl) dataControl).getProfiles().size();
	}

}
