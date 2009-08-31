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

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import es.eucm.eadventure.editor.gui.editdialogs.HelpDialog;

public class InfoHeaderRenderer extends JPanel implements TableCellRenderer {

	private static final long serialVersionUID = 3777807533524838812L;

	private JButton infoButton;
	
	private MouseListener old = null;
	
	private String helpPath;
	
	public InfoHeaderRenderer() {
		this(null);
	}
	
	public InfoHeaderRenderer(String helpPath) {
		this.helpPath = helpPath;
		setLayout(new GridBagLayout());
	}
	
	public Component getTableCellRendererComponent(final JTable table, final Object value,
		boolean isSelected, boolean hasFocus, int row, final int column) {
		removeAll();
		setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.DARK_GRAY));
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		JLabel label = new JLabel(value.toString());
		label.setFont(label.getFont().deriveFont(Font.BOLD));
		add(label, c);
		if (helpPath != null) {
			infoButton = new JButton(new ImageIcon("img/icons/information.png"));
			infoButton.setContentAreaFilled( false );
			infoButton.setMargin( new Insets(0,0,0,0) );
			infoButton.setBorder(BorderFactory.createEmptyBorder());
			c.gridx = 1;
			add(infoButton, c);
			JTableHeader header = table.getTableHeader(); 
			if (old != null)
				header.removeMouseListener(old);
			old = new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int mouseX = e.getX();
					int mouseY = e.getY();
					mouseX -= infoButton.getX();
					mouseY -= infoButton.getY();
					for (int i = 0; i < column; i++) {
						mouseX -= table.getColumnModel().getColumn(i).getWidth();
					}
					if(infoButton.contains(mouseX, mouseY)) {
						new HelpDialog(helpPath);
					}
				}
			};
	        header.addMouseListener(old);
		}
		return this;
	}

}
