package es.eucm.eadventure.editor.control.tools.books;

import java.util.List;

import es.eucm.eadventure.common.data.chapter.book.BookPage;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class MoveBookPageDownTool extends Tool {

	private List<BookPage> bookPagesList;
	
	private BookPage bookPage;
	
	private int oldElementIndex;
	
	private int newElementIndex;
	
	public MoveBookPageDownTool(List<BookPage> bookPagesList, BookPage page) {
		this.bookPagesList = bookPagesList;
		this.bookPage = page;
		this.oldElementIndex = bookPagesList.indexOf(bookPage);
		this.newElementIndex = oldElementIndex + 1;
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
		if (other instanceof MoveBookPageDownTool) {
			MoveBookPageDownTool mbput = (MoveBookPageDownTool) other;
			if (mbput.bookPage == bookPage) {
				newElementIndex = mbput.newElementIndex;
				timeStamp = mbput.timeStamp;
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean doTool() {
		if( oldElementIndex < bookPagesList.size( )-1  ) {
			bookPagesList.add( newElementIndex , bookPagesList.remove( oldElementIndex ) );
			return true;
		}
		return false;
	}

	@Override
	public boolean redoTool() {
		bookPagesList.add( newElementIndex, bookPagesList.remove(oldElementIndex));
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		bookPagesList.add( oldElementIndex, bookPagesList.remove(newElementIndex));
		Controller.getInstance().updatePanel();
		return true;
	}

}
