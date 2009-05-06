package es.eucm.eadventure.editor.gui.elementpanels.adaptation;

import java.awt.Component;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.JTable;

import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationProfileDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.ResizeableCellRenderer;

public class AdaptationProfileCellRenderer extends ResizeableCellRenderer {

	private static final long serialVersionUID = 8128260157985286632L;
	
	public Component getTableCellRendererComponent(JTable table, Object value2, boolean isSelected, boolean hasFocus, int row, int column) {
		if (value2 == null)
			return new JPanel();

		value = (AdaptationProfileDataControl) value2;
		name = ((AdaptationProfileDataControl) value2).getName();
		image = new BufferedImage(200, 200, BufferedImage.TYPE_4BYTE_ABGR);
		
		return createPanel();
	}
	
	public Component getTableCellEditorComponent(JTable table, Object value2, boolean isSelected, int row, int column) {
		if (value2 == null)
			return new JPanel();

		value = (AdaptationProfileDataControl) value2;
		name = ((AdaptationProfileDataControl) value2).getName();
		image = new BufferedImage(200, 200, BufferedImage.TYPE_4BYTE_ABGR);

		return createPanel();
	}
}
