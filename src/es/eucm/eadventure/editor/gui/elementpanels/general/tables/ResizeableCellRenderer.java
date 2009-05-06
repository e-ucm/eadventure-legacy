package es.eucm.eadventure.editor.gui.elementpanels.general.tables;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.gui.structurepanel.StructureControl;

public abstract class ResizeableCellRenderer extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

	private static final long serialVersionUID = 8128260157985286632L;
	
	protected DataControl value;
	
	private int size = 1;
	
	protected String name;
	
	protected Image image;
	
	protected JPanel createPanel() {
		if (image == null)
			image = AssetsController.getImage("img/assets/EmptyImage.png");
		
		if (size == 2 && image.getHeight(null) > 160) {
			int newWidth = (int) ((float) image.getWidth(null) / (float) image.getHeight(null) * 160.0);
			image = image.getScaledInstance(newWidth, 160, Image.SCALE_FAST);
		}
		if (size == 1 && image.getHeight(null) > 60) {
			int newWidth = (int) ((float) image.getWidth(null) / (float) image.getHeight(null) * 60.0);
			image = image.getScaledInstance(newWidth, 60, Image.SCALE_FAST);
		}
		ImageIcon icon = new ImageIcon(image);
		
		
		JButton goToButton = new JButton(TextConstants.getText("GeneralText.Edit"));
		goToButton.addActionListener(new EditButtonActionListener(value));
		
		JPanel panel = new JPanel();
		
		panel.setLayout(new BorderLayout());
		
		if (size == 1 || size == 2) {
			panel.add(new JLabel(name), BorderLayout.NORTH);
			panel.add(new JLabel(icon), BorderLayout.CENTER);
			panel.add(goToButton, BorderLayout.SOUTH);
		} else if (size == 0) {
			panel.add(new JLabel(name), BorderLayout.CENTER);
			panel.add(goToButton, BorderLayout.EAST);
		}
		
		panel.setBackground(Color.WHITE);
		return panel;
	}
	
	private class EditButtonActionListener implements ActionListener {

		private DataControl dataControl;
		
		public EditButtonActionListener(DataControl dataControl) {
			this.dataControl = dataControl;
		}
		
		public void actionPerformed(ActionEvent e) {
			StructureControl.getInstance().changeDataControl(dataControl);
		}
	}

	public Object getCellEditorValue() {
		return null;
	}
	
	public void setSize(int size) {
		this.size = size;
	}

}
