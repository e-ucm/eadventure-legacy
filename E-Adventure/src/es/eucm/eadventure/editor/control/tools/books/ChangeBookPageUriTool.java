package es.eucm.eadventure.editor.control.tools.books;

import es.eucm.eadventure.common.data.chapter.book.BookPage;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class ChangeBookPageUriTool extends Tool {

	private BookPage bookPage;
	
	private String newURI;
	
	private String oldURI;
	
	public ChangeBookPageUriTool(BookPage bookPage, String newURI) {
		this.bookPage = bookPage;
		this.newURI = newURI;
		this.oldURI = bookPage.getUri();
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
		if (other instanceof ChangeBookPageUriTool) {
			ChangeBookPageUriTool cbput = (ChangeBookPageUriTool) other;
			if (cbput.bookPage == bookPage) {
				newURI = cbput.newURI;
				timeStamp = cbput.timeStamp;
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean doTool() {
		if (oldURI != null || !newURI.equals(oldURI)) {
			bookPage.setUri(newURI);
			return true;
		}
		return false;
	}

	@Override
	public boolean redoTool() {
		bookPage.setUri(newURI);
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		bookPage.setUri(oldURI);
		Controller.getInstance().updatePanel();
		return true;
	}

}
