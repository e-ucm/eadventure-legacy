package es.eucm.eadventure.editor.control.tools.books;

import java.util.List;

import es.eucm.eadventure.common.data.chapter.book.BookPage;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class DeleteBookPageTool extends Tool {

	private List<BookPage> bookPagesList;
	
	private BookPage bookPage;
	
	private int index;
	
	public DeleteBookPageTool(List<BookPage> bookPagesList, BookPage page) {
		this.bookPagesList = bookPagesList;
		this.bookPage = page;
		this.index = bookPagesList.indexOf(bookPage);
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
		return bookPagesList.remove(bookPage);
	}

	@Override
	public boolean redoTool() {
		boolean temp = bookPagesList.remove(bookPage);
		Controller.getInstance().updatePanel();
		return temp;
	}

	@Override
	public boolean undoTool() {
		bookPagesList.add(index, bookPage);
		Controller.getInstance().updatePanel();
		return true;
	}

}
