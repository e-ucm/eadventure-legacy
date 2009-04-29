package es.eucm.eadventure.editor.gui.elementpanels.book;

import javax.swing.JComponent;

import es.eucm.eadventure.common.data.chapter.book.Book;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.book.BookDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.ElementPanel;
import es.eucm.eadventure.editor.gui.elementpanels.PanelTab;

public class BookPanel extends ElementPanel {
	
	private static final long serialVersionUID = 1L;
	
	public BookPanel (BookDataControl dataControl){
		addTab(new BookContentPanel(dataControl));
		addTab(new BookAppPanelTab(dataControl));
		addTab(new BookDocPanelTab(dataControl));
	}
	
	private class BookDocPanelTab extends PanelTab {
		private BookDataControl dataControl;
		
		public BookDocPanelTab(BookDataControl dataControl) {
			super(TextConstants.getText("Book.Doc"), dataControl);
			setToolTipText(TextConstants.getText("Book.Doc.Tip"));
			this.dataControl = dataControl;
		}

		@Override
		protected JComponent getTabComponent() {
			return new BookDocPanel(dataControl);
		}
	}

	private class BookAppPanelTab extends PanelTab {
		private BookDataControl dataControl;
		
		public BookAppPanelTab(BookDataControl dataControl) {
			super(TextConstants.getText("Book.App"), dataControl);
			setToolTipText(TextConstants.getText("Book.App.Tip"));
			this.dataControl = dataControl;
		}

		@Override
		protected JComponent getTabComponent() {
			return new BookAppPanel(dataControl);
		}
	}

	private class BookContentPanel extends PanelTab {
		private BookDataControl dataControl;
		
		public BookContentPanel(BookDataControl dataControl) {
			super(TextConstants.getText("Book.Contents"), dataControl);
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
		
		@Override
		public DataControl getDataControl() {
			if (dataControl.getType( ) == Book.TYPE_PARAGRAPHS) {
				return dataControl.getBookParagraphsList();
			}
			return super.getDataControl();
		}
	}	
}
