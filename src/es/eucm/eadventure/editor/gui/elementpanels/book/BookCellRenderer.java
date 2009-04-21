package es.eucm.eadventure.editor.gui.elementpanels.book;

import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JTable;

import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.book.BookDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.ResizeableCellRenderer;

public class BookCellRenderer extends ResizeableCellRenderer {

	private static final long serialVersionUID = 8128260157985286632L;
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value2, boolean isSelected, boolean hasFocus, int row, int column) {
		if (value2 == null)
			return new JPanel();

		value = (BookDataControl) value2;
		name = ((BookDataControl) value2).getId();
		image = AssetsController.getImage(((BookDataControl) value2).getPreviewImage());
		
		return createPanel();
	}
	
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value2, boolean isSelected, int row, int column) {
		if (value2 == null)
			return new JPanel();

		value = (BookDataControl) value2;
		name = ((BookDataControl) value2).getId();
		image = AssetsController.getImage(((BookDataControl) value2).getPreviewImage());

		return createPanel();
	}
}
