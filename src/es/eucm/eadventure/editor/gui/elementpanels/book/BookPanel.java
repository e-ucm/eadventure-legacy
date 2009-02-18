package es.eucm.eadventure.editor.gui.elementpanels.book;

import javax.swing.JTabbedPane;

import es.eucm.eadventure.common.data.chapter.book.Book;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.book.BookDataControl;

public class BookPanel extends JTabbedPane{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BookPanel (BookDataControl dataControl){
		if (dataControl.getType( ) == Book.TYPE_PARAGRAPHS)
			this.insertTab( TextConstants.getText("Book.Contents"), null, new BookParagraphsPanel(dataControl), TextConstants.getText("Book.Contents.Tip"), 0 );
		else
			this.insertTab( TextConstants.getText("Book.Contents"), null, new BookPagesPanel(dataControl), TextConstants.getText("Book.Contents.Tip"), 0 );
		this.insertTab( TextConstants.getText("Book.DocAndApp"), null, new BookDocAppPanel(dataControl), TextConstants.getText("Book.DocAndApp.Tip"), 1 );
	}
	
}
