package es.eucm.eadventure.editor.control.controllers;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import es.eucm.eadventure.editor.gui.otherpanels.imageelements.ImageElement;

public interface ScenePreviewEditionController extends MouseListener, MouseMotionListener {
	
	public ImageElement getUnderMouse();
}
