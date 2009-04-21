package es.eucm.eadventure.editor.gui.elementpanels.book;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.book.BookDataControl;
import es.eucm.eadventure.editor.control.controllers.book.BooksListDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.general.ResizeableListPanel;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.ResizeableCellRenderer;

public class BooksListPanel extends JPanel {

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
		setLayout( new BorderLayout( ) );
		setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "BooksList.Title" ) ) );
		List<DataControl> dataControlList = new ArrayList<DataControl>();
		for (BookDataControl item : booksListDataControl.getBooks()) {
			dataControlList.add(item);
		}
		ResizeableCellRenderer renderer = new BookCellRenderer();
		add(new ResizeableListPanel(dataControlList, renderer), BorderLayout.CENTER);
	}

}
