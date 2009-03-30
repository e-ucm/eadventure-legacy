package es.eucm.eadventure.editor.gui.structurepanel.structureelements;

import javax.swing.ImageIcon;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.book.BooksListDataControl;
import es.eucm.eadventure.editor.gui.structurepanel.StructureElementFactory;
import es.eucm.eadventure.editor.gui.structurepanel.StructureListElement;
import es.eucm.eadventure.editor.gui.structurepanel.StructureElement;

public class BooksListStructureElement extends StructureListElement {

	public BooksListStructureElement(DataControl dataControl) {
		super(TextConstants.getText("BooksList.Title"), dataControl);
		icon = new ImageIcon( "img/icons/books.png" );
	}
	
	@Override
	public int getChildCount() {
		return ((BooksListDataControl) dataControl).getBooks().size();
	}
	
	@Override
	public StructureElement getChild(int i) {
		return StructureElementFactory.getStructureElement(((BooksListDataControl) dataControl).getBooks().get(i), this);
	}
	
}
