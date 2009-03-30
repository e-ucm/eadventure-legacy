package es.eucm.eadventure.editor.gui.structurepanel.structureelements;

import javax.swing.ImageIcon;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.character.NPCsListDataControl;
import es.eucm.eadventure.editor.gui.structurepanel.StructureElementFactory;
import es.eucm.eadventure.editor.gui.structurepanel.StructureListElement;
import es.eucm.eadventure.editor.gui.structurepanel.StructureElement;

public class NPCsListStructureElement extends StructureListElement {

	public NPCsListStructureElement(DataControl dataControl) {
		super(TextConstants.getText("NPCsList.Title"), dataControl);
		icon = new ImageIcon( "img/icons/npcs.png" );
	}
	
	@Override
	public int getChildCount() {
		return ((NPCsListDataControl) dataControl).getNPCs().size();
	}

	@Override
	public StructureElement getChild(int i) {
		return StructureElementFactory.getStructureElement((((NPCsListDataControl) dataControl).getNPCs().get(i)), this);
	}
}