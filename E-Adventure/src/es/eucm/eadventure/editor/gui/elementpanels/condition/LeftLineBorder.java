package es.eucm.eadventure.editor.gui.elementpanels.condition;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.Border;

public class LeftLineBorder implements Border{

	private Color line = Color.black;
	private Color background = Color.yellow;
	
	@Override
	public Insets getBorderInsets(Component c) {
		return new Insets (10,10,10,0);
	}

	@Override
	public boolean isBorderOpaque() {
		return false;
	}

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width,
			int height) {
		g.setColor(line);
		g.drawLine(x, y, x, y+height);
		//g.fillRoundRect(x, y, width+1, height-1, 30, 30);
	}

}
