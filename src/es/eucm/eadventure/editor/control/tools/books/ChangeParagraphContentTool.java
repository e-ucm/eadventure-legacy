package es.eucm.eadventure.editor.control.tools.books;

import es.eucm.eadventure.common.data.chapter.book.BookParagraph;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class ChangeParagraphContentTool extends Tool {

	private BookParagraph bookParagraph;

	private String newContent;
	
	private String oldContent;
	
	public ChangeParagraphContentTool(BookParagraph bookParagraph,
			String content) {
		this.bookParagraph = bookParagraph;
		this.newContent = content;
		this.oldContent = bookParagraph.getContent();
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
		if (other instanceof ChangeParagraphContentTool) {
			ChangeParagraphContentTool tool = (ChangeParagraphContentTool) other;
			if (tool.bookParagraph == bookParagraph) {
				newContent = tool.newContent;
				timeStamp = tool.timeStamp;
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean doTool() {
		if (oldContent != null && oldContent.equals(newContent))
			return false;
		bookParagraph.setContent(newContent);
		return true;
	}

	@Override
	public boolean redoTool() {
		bookParagraph.setContent(newContent);
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		bookParagraph.setContent(oldContent);
		Controller.getInstance().updatePanel();
		return true;
	}

}
