package es.eucm.eadventure.editor.control.controllers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import es.eucm.eadventure.editor.gui.otherpanels.ScenePreviewEditionPanel;
import es.eucm.eadventure.editor.gui.otherpanels.imageelements.ImageElement;

public class ScenePreviewEditionController implements MouseListener, MouseMotionListener {

	private ImageElement underMouse;
	
	private ScenePreviewEditionPanel spep;
	
	private int startDragX;
	
	private int startDragY;
	
	private int originalX;
	
	private int originalY;

	private float originalScale;

	private boolean resize;

	public ScenePreviewEditionController(ScenePreviewEditionPanel spep) {
		this.spep = spep;
	}
	
	public void mouseClicked(MouseEvent e) {
		setMouseUnder(e.getX(), e.getY());
		if (underMouse != null) {
			spep.setSelectedElement(underMouse);
			spep.paintBackBuffer();
			spep.flip();
		}
		if (underMouse == null) {
			spep.setSelectedElement((ImageElement) null);
			spep.paintBackBuffer();
			spep.flip();
		}
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		setMouseUnder(e.getX(), e.getY());
		if (underMouse != null && underMouse == spep.getSelectedElement()) {
			startDragX = e.getX();
			startDragY = e.getY();
			originalX = underMouse.getX();
			originalY = underMouse.getY();
			originalScale = underMouse.getScale();
		}
		else if (underMouse != null) {
			spep.setSelectedElement(underMouse);
			spep.paintBackBuffer();
			spep.flip();
		}

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseDragged(MouseEvent e) {
		if (underMouse != null && !resize) {
			int changeX = (int) ((e.getX() - startDragX) / spep.getSizeRatio());
			int changeY = (int) ((e.getY() - startDragY) / spep.getSizeRatio());
			int x = originalX + changeX;
			int y = originalY + changeY;
			underMouse.changePosition(x, y);
			spep.paintBackBuffer();
			spep.flip();
		} else if (underMouse != null && resize) {
			double changeX = (e.getX() - startDragX);
			double changeY = - (e.getY() - startDragY);
			double width = underMouse.getImage().getWidth(null);
			double heigth = underMouse.getImage().getHeight(null);
			
			double temp = changeX / width;
			double temp2 = changeY / heigth;
			
			float scale = originalScale;
			if (temp*temp > temp2*temp2)
				scale += temp;
			
			underMouse.getElementReferenceDataControl().setElementScale(scale);
			spep.paintBackBuffer();
			spep.flip();
		}
	}

	public void mouseMoved(MouseEvent e) {
		setMouseUnder(e.getX(), e.getY());
	}
	
	private void setMouseUnder(int mouseX, int mouseY) {
		int x = (int) ((mouseX - spep.getMarginX()) / spep.getSizeRatio());
		int y = (int) ((mouseY - spep.getMarginY()) / spep.getSizeRatio());
		ImageElement imageElement = spep.getMovableElement(x, y);
		ImageElement resizeElement = spep.getResizeElement(x, y);
		if (imageElement != underMouse) {
			underMouse = imageElement;
			resize = false;
			spep.paintBackBuffer();
			spep.flip();
		} else if (resizeElement != null && (!resize || imageElement != underMouse)) {
			underMouse = resizeElement;
			resize = true;
			spep.paintBackBuffer();
			spep.flip();
		} else if (resizeElement == null) {
			underMouse = null;
			resize = false;
		}
	}
	
	public ImageElement getUnderMouse() {
		return underMouse;
	}
}
