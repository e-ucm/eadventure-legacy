package es.eucm.eadventure.editor.gui.structurepanel.structureelements;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.general.ChapterDataControl;
import es.eucm.eadventure.editor.gui.structurepanel.StructureListElement;
import es.eucm.eadventure.editor.gui.structurepanel.StructureElement;

public class ChapterStructureElement extends StructureListElement {

	public ChapterStructureElement(DataControl dataControl) {
		super(TextConstants.getText("Chapter.Title"), dataControl);
		icon = null;
	}
	
	@Override
	public String getName() {
		return ((ChapterDataControl) dataControl).getTitle();
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