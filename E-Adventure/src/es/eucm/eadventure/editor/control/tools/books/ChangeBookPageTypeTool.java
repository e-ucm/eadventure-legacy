package es.eucm.eadventure.editor.control.tools.books;

import es.eucm.eadventure.common.data.chapter.book.BookPage;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class ChangeBookPageTypeTool extends Tool {

	private BookPage bookPage;
	
	private int newType;
	
	private int oldType;
	
	private String oldUri;
	
	public ChangeBookPageTypeTool(BookPage bookPage, int newType) {
		this.bookPage = bookPage;
		this.newType = newType;
		this.oldType = bookPage.getType();
		this.oldUri = bookPage.getUri();
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
		if (newType != oldType) {
			bookPage.setType(newType);
			if (newType == BookPage.TYPE_RESOURCE)
				bookPage.setUri( "" );
			else if (newType == BookPage.TYPE_IMAGE)
				bookPage.setUri( "" );
			else
				bookPage.setUri( "http://www." );
			return true;
		}
		return false;
	}

	@Override
	public boolean redoTool() {
		bookPage.setType(newType);
		if (newType == BookPage.TYPE_RESOURCE)
			bookPage.setUri( "" );
		else if (newType == BookPage.TYPE_IMAGE)
			bookPage.setUri( "" );
		else
			bookPage.setUri( "http://www." );
		Controller.getInstance().updatePanel();
		return true;		
	}

	@Override
	public boolean undoTool() {
		bookPage.setType(oldType);
		bookPage.setUri(oldUri);
		Controller.getInstance().updatePanel();
		return true;
	}

}
