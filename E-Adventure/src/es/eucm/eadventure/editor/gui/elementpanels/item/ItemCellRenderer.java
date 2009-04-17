package es.eucm.eadventure.editor.gui.elementpanels.item;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.item.ItemDataControl;

public class ItemCellRenderer implements TableCellRenderer {

	private static final long serialVersionUID = 8128260157985286632L;
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value2, boolean isSelected, boolean hasFocus, int row, int column) {
		if (value2 == null)
			return new JPanel();
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(new JLabel(((ItemDataControl) value2).getId()), BorderLayout.NORTH);
		Image image = AssetsController.getImage(((ItemDataControl) value2).getPreviewImage());
		if (image.getHeight(null) > 180) {
			int newWidth = (int) ((float) image.getWidth(null) / (float) image.getHeight(null) * 180.0);
			image = image.getScaledInstance(newWidth, 180, Image.SCALE_FAST);
		}
		ImageIcon icon = new ImageIcon(image);
		
		panel.add(new JLabel(icon), BorderLayout.CENTER);
		
		return panel;
	}
}
