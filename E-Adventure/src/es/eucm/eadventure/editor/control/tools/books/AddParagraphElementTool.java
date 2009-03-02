package es.eucm.eadventure.editor.control.tools.books;

import es.eucm.eadventure.common.data.chapter.book.BookParagraph;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.book.BookDataControl;
import es.eucm.eadventure.editor.control.controllers.book.BookParagraphDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class AddParagraphElementTool extends Tool {

	private BookDataControl dataControl;
	
	private int type;
	
	private int selectedRow;
	
	private BookParagraph newBookParagraph;
	
	private BookParagraphDataControl newBookParagraphDataControl;
	
	public AddParagraphElementTool(BookDataControl dataControl, int type,
			int selectedRow) {
		this.dataControl = dataControl;
		this.type = type;
		this.selectedRow = selectedRow;
	}

	@Override
	public boolean canRedo() {
		return true;
	}

	@Override
	public boolean canUndo() {
		return true;
	}

	@Override
	public boolean combine(Tool other) {
		return false;
	}

	@Override
	public boolean doTool() {
		newBookParagraph = null;
		if( type == Controller.BOOK_TITLE_PARAGRAPH )
			newBookParagraph = new BookParagraph( BookParagraph.TITLE );

		else if( type == Controller.BOOK_TEXT_PARAGRAPH )
			newBookParagraph = new BookParagraph( BookParagraph.TEXT );

		else if( type == Controller.BOOK_BULLET_PARAGRAPH )
			newBookParagraph = new BookParagraph( BookParagraph.BULLET );

		else if( type == Controller.BOOK_IMAGE_PARAGRAPH )
			newBookParagraph = new BookParagraph( BookParagraph.IMAGE );


		newBookParagraphDataControl = new BookParagraphDataControl( newBookParagraph );
		if( newBookParagraph != null ) {
			if (selectedRow != -1 && selectedRow < dataControl.getBookParagraphsList().getBookParagraphs().size()) {
				dataControl.getBookParagraphsList().getBookParagraphsList().add(selectedRow + 1,  newBookParagraph );
				dataControl.getBookParagraphsList().getBookParagraphs().add(selectedRow + 1, newBookParagraphDataControl );
			} else {
				dataControl.getBookParagraphsList().getBookParagraphsList().add( newBookParagraph );
				dataControl.getBookParagraphsList().getBookParagraphs().add( newBookParagraphDataControl );
			}
		}

		return newBookParagraph != null;
	}

	@Override
	public boolean redoTool() {
		if( newBookParagraph != null ) {
			if (selectedRow != -1 && selectedRow < dataControl.getBookParagraphsList().getBookParagraphs().size()) {
				dataControl.getBookParagraphsList().getBookParagraphsList().add(selectedRow + 1,  newBookParagraph );
				dataControl.getBookParagraphsList().getBookParagraphs().add(selectedRow + 1, newBookParagraphDataControl );
			} else {
				dataControl.getBookParagraphsList().getBookParagraphsList().add( newBookParagraph );
				dataControl.getBookParagraphsList().getBookParagraphs().add( newBookParagraphDataControl );
			}
		}
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		dataControl.getBookParagraphsList().getBookParagraphsList().remove( newBookParagraph );
		dataControl.getBookParagraphsList().getBookParagraphs().remove( newBookParagraphDataControl );
		Controller.getInstance().updatePanel();
		return true;
	}

}
