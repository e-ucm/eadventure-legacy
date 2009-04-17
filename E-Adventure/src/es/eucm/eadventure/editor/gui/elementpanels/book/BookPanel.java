package es.eucm.eadventure.editor.gui.elementpanels.book;

import javax.swing.JComponent;

import es.eucm.eadventure.common.data.chapter.book.Book;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.book.BookDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.ElementPanel;
import es.eucm.eadventure.editor.gui.elementpanels.PanelTab;

public class BookPanel extends ElementPanel {
	
	private static final long serialVersionUID = 1L;
	
	public BookPanel (BookDataControl dataControl){
		addTab(new BookContentPanel(dataControl));
		addTab(new BookDocPanel(dataControl));
	}
	
	private class BookDocPanel extends PanelTab {
		private BookDataControl dataControl;
		
		public BookDocPanel(BookDataControl dataControl) {
			super(TextConstants.getText("Book.DocAndApp"));
			setToolTipText(TextConstants.getText("Book.DocAndApp.Tip"));
			this.dataControl = dataControl;
		}

		@Override
		protected JComponent getTabComponent() {
			return new BookDocAppPanel(dataControl);
		}
	}
	
	private class BookContentPanel extends PanelTab {
		private BookDataControl dataControl;
		
		public BookContentPanel(BookDataControl dataControl) {
			super(TextConstants.getText("Book.Contents"));
			setToolTipText(TextConstants.getText("Book.Contents.Tip"));
			this.dataControl = dataControl;
		}

		@Override
		protected JComponent getTabComponent() {
			if (dataControl.getType( ) == Book.TYPE_PARAGRAPHS) {
				return new BookParagraphsPanel(dataControl);
			} else {
				return new BookPagesPanel(dataControl);
			}
		}
	}	
}
