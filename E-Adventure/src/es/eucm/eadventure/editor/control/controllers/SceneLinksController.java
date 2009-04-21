package es.eucm.eadventure.editor.control.controllers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import es.eucm.eadventure.editor.gui.otherpanels.SceneLinksPanel;
import es.eucm.eadventure.editor.gui.otherpanels.scenelistelements.SceneElement;
import es.eucm.eadventure.editor.gui.structurepanel.StructureControl;

public class SceneLinksController implements MouseListener, MouseMotionListener {

	private SceneLinksPanel slp;
	
	private SceneElement under;
	
	private int startDragX;
	
	private int startDragY;
	
	private int originalX;
	
	private int originalY;
	
	private SceneElement showName;
	
	public SceneLinksController(SceneLinksPanel sceneLinksPanel) {
		this.slp = sceneLinksPanel;
	}
	
	public void mouseClicked(MouseEvent e) {
		setUnderMouse(e.getX(), e.getY());
		if (under != null && e.getClickCount() == 2) {
			StructureControl.getInstance().changeDataControl(under.getDataControl());
		}
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		setUnderMouse(e.getX(), e.getY());
		if (under != null) {
			startDragX = e.getX();
			startDragY = e.getY();
			originalX = under.getPosX();
			originalY = under.getPosY();
		}
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseDragged(MouseEvent e) {
		if (under != null) {
			int changeX = slp.getRealWidth(e.getX() - startDragX);
			int changeY = slp.getRealHeight(e.getY() - startDragY);
			int x = originalX + changeX;
			int y = originalY + changeY;
			under.changePosition(x, y);
			slp.repaint();
		}
	}

	public void mouseMoved(MouseEvent e) {
		SceneElement temp = slp.getSceneElement(e.getX(), e.getY());
		if (temp != showName) {
			if (showName != null)
				showName.setShowName(false);
			showName = temp;
			if (showName != null)
				showName.setShowName(true);
			slp.repaint();
		}
	}
	
	private void setUnderMouse(int mouseX, int mouseY) {
		under = slp.getSceneElement(mouseX, mouseY);
	}

}
