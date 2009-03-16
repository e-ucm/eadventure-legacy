package es.eucm.eadventure.editor.gui.elementpanels.book;

import javax.swing.JTabbedPane;

import es.eucm.eadventure.common.data.chapter.book.Book;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.book.BookDataControl;
import es.eucm.eadventure.editor.gui.Updateable;

public class BookPanel extends JTabbedPane implements Updateable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BookDocAppPanel bookDocAppPanel;
	private BookParagraphsPanel bookParagraphsPanel;
	private BookPagesPanel bookPagesPanel;
	
	private BookDataControl dataControl;
	
	public BookPanel (BookDataControl dataControl){
		this.dataControl = dataControl;
		if (dataControl.getType( ) == Book.TYPE_PARAGRAPHS) {
			bookParagraphsPanel = new BookParagraphsPanel(dataControl);
			this.insertTab( TextConstants.getText("Book.Contents"), null, bookParagraphsPanel, TextConstants.getText("Book.Contents.Tip"), 0 );
		} else {
			bookPagesPanel = new BookPagesPanel(dataControl);
			this.insertTab( TextConstants.getText("Book.Contents"), null, bookPagesPanel, TextConstants.getText("Book.Contents.Tip"), 0 );
		}
		bookDocAppPanel = new BookDocAppPanel(dataControl);
		this.insertTab( TextConstants.getText("Book.DocAndApp"), null, bookDocAppPanel, TextConstants.getText("Book.DocAndApp.Tip"), 1 );
	}

	public boolean updateFields() {
		int selectedTab = this.getSelectedIndex();
		bookDocAppPanel.updateFields();
		this.removeTabAt(0);
		if (dataControl.getType( ) == Book.TYPE_PARAGRAPHS) {
			bookParagraphsPanel = new BookParagraphsPanel(dataControl);
			this.insertTab( TextConstants.getText("Book.Contents"), null, bookParagraphsPanel, TextConstants.getText("Book.Contents.Tip"), 0 );
		} else {
			bookPagesPanel = new BookPagesPanel(dataControl);
			this.insertTab( TextConstants.getText("Book.Contents"), null, bookPagesPanel, TextConstants.getText("Book.Contents.Tip"), 0 );
		}
		this.setSelectedIndex(selectedTab);
		return true;
	}
	
}
