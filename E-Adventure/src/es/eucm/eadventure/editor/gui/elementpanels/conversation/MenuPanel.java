package es.eucm.eadventure.editor.gui.elementpanels.conversation;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MenuPanel extends JPanel {

	public MenuPanel() {
		super();
		this.setOpaque(false);
	}
	
	@Override
	public void paint(Graphics g) {
		if (this.getMousePosition() == null) {
			this.setOpaque(false);
			AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
			((Graphics2D) g).setComposite(alphaComposite);
			super.paint(g);
		} else {
			this.setOpaque(true);
			super.paint(g);
		}
	}
}
