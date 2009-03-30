package es.eucm.eadventure.editor.gui.structurepanel.structureelements;

import javax.swing.ImageIcon;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.conversation.ConversationsListDataControl;
import es.eucm.eadventure.editor.gui.structurepanel.StructureElementFactory;
import es.eucm.eadventure.editor.gui.structurepanel.StructureListElement;
import es.eucm.eadventure.editor.gui.structurepanel.StructureElement;

public class ConversationsListStructureElement extends StructureListElement {

	public ConversationsListStructureElement(DataControl dataControl) {
		super(TextConstants.getText("ConversationsList.Title"), dataControl);
		icon = new ImageIcon( "img/icons/conversations.png" );
	}
	
	@Override
	public int getChildCount() {
		return ((ConversationsListDataControl) dataControl).getConversations().size();
	}

	@Override
	public StructureElement getChild(int i) {
		return StructureElementFactory.getStructureElement((((ConversationsListDataControl) dataControl).getConversations().get(i)));
	}
}