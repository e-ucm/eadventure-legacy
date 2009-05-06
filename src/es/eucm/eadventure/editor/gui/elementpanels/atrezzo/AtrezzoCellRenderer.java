package es.eucm.eadventure.editor.gui.elementpanels.atrezzo;

import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JTable;

import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.atrezzo.AtrezzoDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.ResizeableCellRenderer;

public class AtrezzoCellRenderer extends ResizeableCellRenderer {

	private static final long serialVersionUID = 8128260157985286632L;
	
	public Component getTableCellRendererComponent(JTable table, Object value2, boolean isSelected, boolean hasFocus, int row, int column) {
		if (value2 == null)
			return new JPanel();

		value = (AtrezzoDataControl) value2;
		name = ((AtrezzoDataControl) value2).getId();
		image = AssetsController.getImage(((AtrezzoDataControl) value2).getPreviewImage());
		
		return createPanel();
	}
	
	public Component getTableCellEditorComponent(JTable table, Object value2, boolean isSelected, int row, int column) {
		if (value2 == null)
			return new JPanel();

		value = (AtrezzoDataControl) value2;
		name = ((AtrezzoDataControl) value2).getId();
		image = AssetsController.getImage(((AtrezzoDataControl) value2).getPreviewImage());

		return createPanel();
	}
}
