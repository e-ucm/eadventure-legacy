package es.eucm.eadventure.editor.control.tools.books;

import es.eucm.eadventure.common.data.chapter.book.BookPage;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class ChangeBookPageScrollableTool extends Tool {

	private BookPage bookPage;
	
	private boolean newScrollable;
	
	private boolean oldScrollable;
	
	public ChangeBookPageScrollableTool(BookPage bookPage, boolean scrollable) {
		this.bookPage = bookPage;
		this.newScrollable = scrollable;
		this.oldScrollable = bookPage.getScrollable();
	}

	@Override
	public boolean canRedo() {
		return true;
	}

	@Override
	public boolean canUndo() {
		return false;
	}

	@Override
	public boolean combine(Tool other) {
		return false;
	}

	@Override
	public boolean doTool() {
		if (oldScrollable == newScrollable)
			return false;
		bookPage.setScrollable(newScrollable);
		return true;
	}

	@Override
	public boolean redoTool() {
		bookPage.setScrollable(newScrollable);
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		bookPage.setScrollable(oldScrollable);
		Controller.getInstance().updatePanel();
		return true;
	}

}
