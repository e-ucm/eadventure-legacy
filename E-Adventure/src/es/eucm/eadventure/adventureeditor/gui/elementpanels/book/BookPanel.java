package es.eucm.eadventure.adventureeditor.gui.elementpanels.book;

import javax.swing.JTabbedPane;

import es.eucm.eadventure.adventureeditor.control.controllers.book.BookDataControl;
import es.eucm.eadventure.adventureeditor.data.chapterdata.book.Book;
import es.eucm.eadventure.adventureeditor.gui.TextConstants;

public class BookPanel extends JTabbedPane{

	private BookDataControl dataControl;
	
	public BookPanel (BookDataControl dataControl){
		this.dataControl = dataControl;
		if (dataControl.getType( ) == Book.TYPE_PARAGRAPHS)
			this.insertTab( TextConstants.getText("Book.Contents"), null, new BookParagraphsPanel(dataControl), TextConstants.getText("Book.Contents.Tip"), 0 );
		else
			this.insertTab( TextConstants.getText("Book.Contents"), null, new BookPagesPanel(dataControl), TextConstants.getText("Book.Contents.Tip"), 0 );
		this.insertTab( TextConstants.getText("Book.DocAndApp"), null, new BookDocAppPanel(dataControl), TextConstants.getText("Book.DocAndApp.Tip"), 1 );
	}
	
}
