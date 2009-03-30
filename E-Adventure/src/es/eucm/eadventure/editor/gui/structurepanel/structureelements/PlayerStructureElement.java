package es.eucm.eadventure.editor.gui.structurepanel.structureelements;

import javax.swing.ImageIcon;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.gui.structurepanel.StructureListElement;
import es.eucm.eadventure.editor.gui.structurepanel.StructureElement;

public class PlayerStructureElement extends StructureListElement {

	public PlayerStructureElement(DataControl dataControl) {
		super(TextConstants.getText("Player.Title"), dataControl);
		icon = new ImageIcon( "img/icons/player.png" );
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