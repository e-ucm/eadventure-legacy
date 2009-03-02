package es.eucm.eadventure.editor.control.tools.books;

import java.util.List;

import es.eucm.eadventure.common.data.chapter.book.BookPage;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class AddBookPageTool extends Tool {

	private List<BookPage> bookPagesList;
	
	private BookPage newBookPage;
	
	private int selectedPage;
	
	public AddBookPageTool(List<BookPage> bookPagesList, BookPage newBookPage,
			int selectedPage) {
		this.bookPagesList = bookPagesList;
		this.newBookPage = newBookPage;
		this.selectedPage = selectedPage;
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
		if (selectedPage>=0 && selectedPage<bookPagesList.size()) {
			bookPagesList.add(selectedPage + 1, newBookPage);
		} else {
			bookPagesList.add(newBookPage);
		}
		return true;
	}

	@Override
	public boolean redoTool() {
		if (selectedPage>=0 && selectedPage<bookPagesList.size()) {
			bookPagesList.add(selectedPage + 1, newBookPage);
		} else {
			bookPagesList.add(newBookPage);
		}
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		bookPagesList.remove(newBookPage);
		Controller.getInstance().updatePanel();
		return true;
	}

}
