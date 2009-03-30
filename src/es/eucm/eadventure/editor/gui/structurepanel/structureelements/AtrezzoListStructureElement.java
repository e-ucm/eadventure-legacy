package es.eucm.eadventure.editor.gui.structurepanel.structureelements;

import javax.swing.ImageIcon;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.atrezzo.AtrezzoListDataControl;
import es.eucm.eadventure.editor.gui.structurepanel.StructureElementFactory;
import es.eucm.eadventure.editor.gui.structurepanel.StructureListElement;
import es.eucm.eadventure.editor.gui.structurepanel.StructureElement;

public class AtrezzoListStructureElement extends StructureListElement {

	public AtrezzoListStructureElement(DataControl dataControl) {
		super(TextConstants.getText("AtrezzoList.Title"), dataControl);
		icon = new ImageIcon( "img/icons/Atrezzo-List-1.png" );
	}
	
	@Override
	public int getChildCount() {
		return ((AtrezzoListDataControl) dataControl).getAtrezzoList().size();
	}

	@Override
	public StructureElement getChild(int i) {
		return StructureElementFactory.getStructureElement((((AtrezzoListDataControl) dataControl).getAtrezzoList().get(i)));
	}
}