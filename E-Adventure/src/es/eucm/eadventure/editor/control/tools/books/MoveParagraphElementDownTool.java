package es.eucm.eadventure.editor.control.tools.books;

import java.util.List;

import es.eucm.eadventure.common.data.chapter.book.BookParagraph;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.book.BookDataControl;
import es.eucm.eadventure.editor.control.controllers.book.BookParagraphDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class MoveParagraphElementDownTool extends Tool {

	private List<BookParagraph> bookParagraphsList;
	
	private List<BookParagraphDataControl> bookParagraphsDataControlList;
	
	private DataControl elementDataControl;
	
	private int oldElementIndex;
	
	private int newElementIndex;
	
	public MoveParagraphElementDownTool(BookDataControl dataControl,
			BookParagraphDataControl paragraph) {
		this.bookParagraphsList = dataControl.getBookParagraphsList().getBookParagraphsList();
		this.bookParagraphsDataControlList = dataControl.getBookParagraphsList().getBookParagraphs();
		this.elementDataControl = paragraph;
		this.oldElementIndex = bookParagraphsDataControlList.indexOf(elementDataControl);
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
		if (other instanceof MoveParagraphElementDownTool) {
			MoveParagraphElementDownTool tool = (MoveParagraphElementDownTool) other;
			if (tool.elementDataControl == elementDataControl) {
				newElementIndex = tool.newElementIndex;
				timeStamp = tool.timeStamp;
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean doTool() {
		if(oldElementIndex >= 0 && oldElementIndex < bookParagraphsList.size() - 1 ) {
			bookParagraphsList.add( newElementIndex, bookParagraphsList.remove( oldElementIndex ) );
			bookParagraphsDataControlList.add( newElementIndex, bookParagraphsDataControlList.remove( oldElementIndex ) );
			return true;
		}
		return false;
	}

	@Override
	public boolean redoTool() {
		bookParagraphsList.add( newElementIndex, bookParagraphsList.remove( oldElementIndex ) );
		bookParagraphsDataControlList.add( newElementIndex, bookParagraphsDataControlList.remove( oldElementIndex ) );
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		bookParagraphsList.add( oldElementIndex, bookParagraphsList.remove( newElementIndex ) );
		bookParagraphsDataControlList.add( oldElementIndex, bookParagraphsDataControlList.remove( newElementIndex ) );
		Controller.getInstance().updatePanel();
		return true;
	}

}
