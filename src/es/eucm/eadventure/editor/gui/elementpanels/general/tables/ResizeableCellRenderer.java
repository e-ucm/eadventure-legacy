/**
 * <e-Adventure> is an <e-UCM> research project.
 * <e-UCM>, Department of Software Engineering and Artificial Intelligence.
 * Faculty of Informatics, Complutense University of Madrid (Spain).
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J.
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009
 * Web-site: http://e-adventure.e-ucm.es
 */

/*
    Copyright (C) 2004-2009 <e-UCM> research group

    This file is part of <e-Adventure> project, an educational game & game-like 
    simulation authoring tool, availabe at http://e-adventure.e-ucm.es. 

    <e-Adventure> is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    <e-Adventure> is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with <e-Adventure>; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

*/
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

	/**
	 * @return the size
	 */
	public int getSize() {
	    return size;
	}

}
