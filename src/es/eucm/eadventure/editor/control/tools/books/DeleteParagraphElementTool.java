package es.eucm.eadventure.editor.control.tools.books;

import java.util.List;

import es.eucm.eadventure.common.data.chapter.book.BookParagraph;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.book.BookDataControl;
import es.eucm.eadventure.editor.control.controllers.book.BookParagraphDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class DeleteParagraphElementTool extends Tool {

	private List<BookParagraph> bookParagraphsList;
	
	private List<BookParagraphDataControl> bookParagraphsDataControlList;
	
	private DataControl elementDataControl;
	
	private int listIndex;
	
	private int dataControlListIndex;
	
	public DeleteParagraphElementTool(BookDataControl dataControl,
			BookParagraphDataControl paragraph) {
		this.bookParagraphsList = dataControl.getBookParagraphsList().getBookParagraphsList();
		this.bookParagraphsDataControlList = dataControl.getBookParagraphsList().getBookParagraphs();
		this.elementDataControl = paragraph;
		this.listIndex = bookParagraphsList.indexOf(elementDataControl.getContent());
		this.dataControlListIndex = bookParagraphsDataControlList.indexOf(elementDataControl);
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
		boolean temp = bookParagraphsList.remove(elementDataControl.getContent());
		if (temp) {
			bookParagraphsDataControlList.remove(elementDataControl);
			return true;
		}
		return false;
	}

	@Override
	public boolean redoTool() {
		boolean temp = bookParagraphsList.remove(elementDataControl.getContent());
		if (temp) {
			bookParagraphsDataControlList.remove(elementDataControl);
			Controller.getInstance().updatePanel();
			return true;
		}
		return false;
	}

	@Override
	public boolean undoTool() {
		bookParagraphsList.add(listIndex, (BookParagraph) elementDataControl.getContent());
		bookParagraphsDataControlList.add(dataControlListIndex, (BookParagraphDataControl) elementDataControl);
		Controller.getInstance().updatePanel();
		return true;
	}

}
