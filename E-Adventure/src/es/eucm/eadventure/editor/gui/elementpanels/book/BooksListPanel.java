package es.eucm.eadventure.editor.gui.elementpanels.book;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.book.BookDataControl;
import es.eucm.eadventure.editor.control.controllers.book.BooksListDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.ElementPanel;
import es.eucm.eadventure.editor.gui.elementpanels.PanelTab;
import es.eucm.eadventure.editor.gui.elementpanels.general.ResizeableListPanel;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.ResizeableCellRenderer;

public class BooksListPanel extends ElementPanel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param booksListDataControl
	 *            Books list controller
	 */
	public BooksListPanel( BooksListDataControl booksListDataControl ) {
		addTab(new BooksListPanelTab(booksListDataControl));
	}

	private class BooksListPanelTab extends PanelTab {
		private BooksListDataControl sDataControl;
		
		public BooksListPanelTab(BooksListDataControl sDataControl) {
			super(TextConstants.getText( "BooksList.Title" ), sDataControl);
			this.sDataControl = sDataControl;
		}

		@Override
		protected JComponent getTabComponent() {
			JPanel booksListPanel = new JPanel();
			booksListPanel.setLayout( new BorderLayout( ) );
			List<DataControl> dataControlList = new ArrayList<DataControl>();
			for (BookDataControl item : sDataControl.getBooks()) {
				dataControlList.add(item);
			}
			ResizeableCellRenderer renderer = new BookCellRenderer();
			booksListPanel.add(new ResizeableListPanel(dataControlList, renderer, "BooksListPanel"), BorderLayout.CENTER);
			return booksListPanel;
		}
	}
}