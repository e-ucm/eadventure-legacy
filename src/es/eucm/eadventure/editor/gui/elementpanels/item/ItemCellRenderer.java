package es.eucm.eadventure.editor.gui.elementpanels.item;

import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JTable;

import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.item.ItemDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.ResizeableCellRenderer;

public class ItemCellRenderer extends ResizeableCellRenderer {

	private static final long serialVersionUID = 8128260157985286632L;
	
	public Component getTableCellRendererComponent(JTable table, Object value2, boolean isSelected, boolean hasFocus, int row, int column) {
		if (value2 == null)
			return new JPanel();

		value = (ItemDataControl) value2;
		name = ((ItemDataControl) value2).getId();
		image = AssetsController.getImage(((ItemDataControl) value2).getPreviewImage());
		
		return createPanel();
	}
	
	public Component getTableCellEditorComponent(JTable table, Object value2, boolean isSelected, int row, int column) {
		if (value2 == null)
			return new JPanel();

		value = (ItemDataControl) value2;
		name = ((ItemDataControl) value2).getId();
		image = AssetsController.getImage(((ItemDataControl) value2).getPreviewImage());

		return createPanel();
	}
}
