package es.eucm.eadventure.editor.control.controllers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import es.eucm.eadventure.editor.gui.otherpanels.SceneLinksPanel;
import es.eucm.eadventure.editor.gui.otherpanels.scenelistelements.SceneElement;
import es.eucm.eadventure.editor.gui.treepanel.TreeNodeControl;

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
	
	@Override
	public void mouseClicked(MouseEvent e) {
		setUnderMouse(e.getX(), e.getY());
		if (under != null && e.getClickCount() == 2) {
			TreeNodeControl.getInstance().changeTreeNode(under.getDataControl());
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		setUnderMouse(e.getX(), e.getY());
		if (under != null) {
			startDragX = e.getX();
			startDragY = e.getY();
			originalX = under.getPosX();
			originalY = under.getPosY();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
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

	@Override
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
