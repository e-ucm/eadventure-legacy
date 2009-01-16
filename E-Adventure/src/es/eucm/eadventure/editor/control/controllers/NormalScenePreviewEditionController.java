package es.eucm.eadventure.editor.control.controllers;

import java.awt.event.MouseEvent;

import es.eucm.eadventure.editor.gui.otherpanels.ScenePreviewEditionPanel;
import es.eucm.eadventure.editor.gui.otherpanels.imageelements.ImageElement;

public class NormalScenePreviewEditionController implements ScenePreviewEditionController {

	protected ImageElement underMouse;
	
	private ScenePreviewEditionPanel spep;
	
	private int startDragX;
	
	private int startDragY;
	
	private int originalX;
	
	private int originalY;

	private int originalWidth;
	
	private int originalHeight;
	
	private float originalScale;
	
	public NormalScenePreviewEditionController(ScenePreviewEditionPanel spep) {
		this.spep = spep;
	}
	
	public void mouseClicked(MouseEvent e) {
		setMouseUnder(e.getX(), e.getY());
		if (underMouse != null && !spep.getFixedSelectedElement()) {
			spep.setSelectedElement(underMouse);
			spep.paintBackBuffer();
			spep.flip();
		} else if (underMouse == null && !spep.getFixedSelectedElement()) {
			spep.setSelectedElement((ImageElement) null);
			spep.paintBackBuffer();
			spep.flip();
		} else if (spep.getFixedSelectedElement()) {
			int x = (int) ((e.getX() - spep.getMarginX()) / spep.getSizeRatio());
			int y = (int) ((e.getY() - spep.getMarginY()) / spep.getSizeRatio());
			spep.getSelectedElement().changePosition(x, y);
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
		if (underMouse != null && (underMouse == spep.getSelectedElement() || underMouse == spep.getInfluenceArea())) {
			startDragX = e.getX();
			startDragY = e.getY();
			originalX = underMouse.getX();
			originalY = underMouse.getY();
			originalWidth = underMouse.getWidth();
			originalHeight = underMouse.getHeight();
			originalScale = underMouse.getScale();
		} else if (underMouse != null && !spep.getFixedSelectedElement()) {
			spep.setSelectedElement(underMouse);
			spep.paintBackBuffer();
			spep.flip();
		}
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseDragged(MouseEvent e) {
		if (underMouse != null && !spep.isRescale() && !spep.isResize()) {
			int changeX = (int) ((e.getX() - startDragX) / spep.getSizeRatio());
			int changeY = (int) ((e.getY() - startDragY) / spep.getSizeRatio());
			int x = originalX + changeX;
			int y = originalY + changeY;
			underMouse.changePosition(x, y);
			spep.paintBackBuffer();
			spep.flip();
		} else if (underMouse != null && spep.isRescale() && !spep.isResize()) {
			double changeX = (e.getX() - startDragX);
			double changeY = - (e.getY() - startDragY);
			double width = underMouse.getImage().getWidth(null);
			double height = underMouse.getImage().getHeight(null);
			
			double tempX = changeX / width;
			double tempY = changeY / height;
			
			float scale = originalScale;
			if (tempX*tempX > tempY*tempY)
				scale = (float) (((width * originalScale) + (changeX / spep.getSizeRatio())) / width);
			else
				scale = (float) (((height * originalScale) + (changeY / spep.getSizeRatio())) / height);
			
			if (scale <= 0)
				scale = 0.01f;
			
			underMouse.setScale(scale);
			spep.paintBackBuffer();
			spep.flip();
		} else if (underMouse != null && !spep.isRescale() && spep.isResize()) {
			int changeX = (int) ((e.getX() - startDragX) / spep.getSizeRatio());
			int changeY = (int) ((e.getY() - startDragY) / spep.getSizeRatio());
			underMouse.changeSize(originalWidth + changeX, originalHeight + changeY);
			underMouse.recreateImage();
			spep.paintBackBuffer();
			spep.flip();
		}
	}

	public void mouseMoved(MouseEvent e) {
		setMouseUnder(e.getX(), e.getY());
	}
	
	protected void setMouseUnder(int mouseX, int mouseY) {
		int x = (int) ((mouseX - spep.getMarginX()) / spep.getSizeRatio());
		int y = (int) ((mouseY - spep.getMarginY()) / spep.getSizeRatio());
		ImageElement imageElement = spep.getMovableElement(x, y);
		ImageElement rescaleElement = spep.getRescaleElement(x, y);
		ImageElement resizeElement = spep.getResizeElement(x, y);

		if (rescaleElement != null) {
			underMouse = rescaleElement;
			spep.setRescale(true);
			spep.setResize(false);
			spep.paintBackBuffer();
			spep.flip();
		} else if (resizeElement != null) {
			underMouse = resizeElement;
			spep.setResize(true);
			spep.setRescale(false);
			spep.paintBackBuffer();
			spep.flip();
		} else if (imageElement != underMouse || (imageElement != null && (spep.isRescale() || spep.isResize()))) {
			underMouse = imageElement;
			spep.setRescale(false);
			spep.setResize(false);
			spep.paintBackBuffer();
			spep.flip();
		} else if (imageElement == null){
			underMouse = null;
			spep.setRescale(false);
			spep.setResize(false);
		}
	}
	
	public ImageElement getUnderMouse() {
		return underMouse;
	}
}
