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
package es.eucm.eadventure.editor.control.controllers;

import java.awt.event.MouseEvent;

import es.eucm.eadventure.editor.gui.otherpanels.ScenePreviewEditionPanel;
import es.eucm.eadventure.editor.gui.otherpanels.imageelements.ImageElement;
import es.eucm.eadventure.editor.gui.otherpanels.imageelements.ImageElementInfluenceArea;
import es.eucm.eadventure.editor.gui.otherpanels.imageelements.ImageElementReference;
import es.eucm.eadventure.editor.gui.structurepanel.StructureControl;

public class NormalScenePreviewEditionController implements ScenePreviewEditionController {

	/**
	 * The object under the mouse pointer
	 */
	protected ImageElement underMouse;
	
	/**
	 * The panel where the scene is shown
	 */
	protected ScenePreviewEditionPanel spep;
	
	/**
	 * The x axis value of the point where the drag started
	 */
	protected int startDragX;
	
	/**
	 * The y axis value of the point where the drag started
	 */
	protected int startDragY;
	
	/**
	 * The original x value of the dragged object
	 */
	protected int originalX;
	
	/**
	 * The original y value of the dragged object
	 */
	protected int originalY;

	/**
	 * The original width of the object being resized
	 */
	protected int originalWidth;
	
	/**
	 * The original height of the object being resized
	 */
	protected int originalHeight;
	
	/**
	 * The original scale of the object being rescaled
	 */
	protected float originalScale;
	
	public NormalScenePreviewEditionController(ScenePreviewEditionPanel spep) {
		this.spep = spep;
	}
	
	public void mouseClicked(MouseEvent e) {
		setMouseUnder(e.getX(), e.getY());
		if (spep.getSelectedElement() != null && e.getClickCount() == 2 && spep.getSelectedElement() instanceof ImageElementReference) {
			StructureControl.getInstance().changeDataControl(spep.getSelectedElement().getReferencedDataControl());
		} else if (underMouse != null && !spep.getFixedSelectedElement() && !(spep.getSelectedElement() instanceof ImageElementInfluenceArea)) {
			spep.setSelectedElement(underMouse);
			spep.notifySelectionListener();
			spep.repaint();
		} else if (underMouse == null && !spep.getFixedSelectedElement()) {
			spep.setSelectedElement((ImageElement) null);
			spep.notifySelectionListener();
			spep.repaint();
		} else if (spep.getFixedSelectedElement()) {
			int x = spep.getRealX(e.getX());
			int y = spep.getRealY(e.getY());
			spep.getSelectedElement().changePosition(x, y);
			spep.updateTextEditionPanel();
			spep.repaint();
		}
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		setMouseUnder(e.getX(), e.getY());
		if (underMouse != null) {
			startDragX = e.getX();
			startDragY = e.getY();
			originalX = underMouse.getX();
			originalY = underMouse.getY();
			originalWidth = underMouse.getWidth();
			originalHeight = underMouse.getHeight();
			originalScale = underMouse.getScale();
		} else if (underMouse != null && !spep.getFixedSelectedElement()) {
			spep.setSelectedElement(underMouse);
			spep.repaint();
		}
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
		if (underMouse != null && !spep.isRescale() && !spep.isResize() && !spep.isResizeInflueceArea()) {
			int changeX = spep.getRealWidth(e.getX() - startDragX);
			int changeY = spep.getRealHeight(e.getY() - startDragY);
			int x = originalX + changeX;
			int y = originalY + changeY;
			underMouse.changePosition(x, y);
			spep.updateTextEditionPanel();
			spep.repaint();
		} else if (underMouse != null && spep.isRescale() && !spep.isResize() && !spep.isResizeInflueceArea()) {
			double changeX = (e.getX() - startDragX);
			double changeY = - (e.getY() - startDragY);
			double width = underMouse.getImage().getWidth(null);
			double height = underMouse.getImage().getHeight(null);
			
			double tempX = changeX / width;
			double tempY = changeY / height;
			
			float scale = originalScale;
			if (tempX*tempX > tempY*tempY)
				scale = (float) (((width * originalScale) + spep.getRealWidth((int) changeX)) / width);
			else
				scale = (float) (((height * originalScale) + spep.getRealHeight((int) changeY)) / height);
			
			if (scale <= 0)
				scale = 0.01f;
			
			underMouse.setScale(scale);
			spep.updateTextEditionPanel();
			spep.repaint();
		} else if (underMouse != null && !spep.isRescale() && (spep.isResize() || spep.isResizeInflueceArea())) {
			int changeX = spep.getRealWidth(e.getX() - startDragX);
			int changeY = spep.getRealHeight(e.getY() - startDragY);
			underMouse.changeSize(originalWidth + changeX, originalHeight + changeY);
			underMouse.recreateImage();
			spep.updateTextEditionPanel();
			spep.repaint();
		}
	}

	public void mouseMoved(MouseEvent e) {
		setMouseUnder(e.getX(), e.getY());
	}
	
	protected void setMouseUnder(int mouseX, int mouseY) {
		int x = spep.getRealX(mouseX);
		int y = spep.getRealY(mouseY);
		ImageElement imageElement = spep.getMovableElement(x, y);
		ImageElement rescaleElement = spep.getRescaleElement(x, y);
		ImageElement resizeElement = spep.getResizeElement(x, y);

		if (rescaleElement != null) {
			underMouse = rescaleElement;
			spep.setRescale(true);
			spep.setResize(false);
			spep.setResizeInflueceArea(false);
			spep.repaint();
		} else if (resizeElement != null) {
			underMouse = resizeElement;
			if (resizeElement instanceof ImageElementInfluenceArea) {
				spep.setResizeInflueceArea(true);
				spep.setResize(false);
			} else {
				spep.setResizeInflueceArea(false);
				spep.setResize(true);
			}
			spep.setRescale(false);
			spep.repaint();
		} else if (imageElement != underMouse || (imageElement != null && (spep.isRescale() || spep.isResize() || spep.isResizeInflueceArea()))) {
			underMouse = imageElement;
			spep.setRescale(false);
			spep.setResize(false);
			spep.setResizeInflueceArea(false);
			spep.repaint();
		} else if (imageElement == null){
			underMouse = null;
			spep.setRescale(false);
			spep.setResizeInflueceArea(false);
			spep.setResize(false);
		}
	}
	
	public ImageElement getUnderMouse() {
		return underMouse;
	}
}
