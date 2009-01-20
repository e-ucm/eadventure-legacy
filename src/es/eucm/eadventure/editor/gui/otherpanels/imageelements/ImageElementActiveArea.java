package es.eucm.eadventure.editor.gui.otherpanels.imageelements;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import es.eucm.eadventure.editor.control.controllers.scene.ActiveAreaDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ElementReferenceDataControl;

public class ImageElementActiveArea extends ImageElement {

	private ActiveAreaDataControl activeAreaDataControl;
	
	public ImageElementActiveArea(ActiveAreaDataControl barrierDataControl) {
		this.activeAreaDataControl = barrierDataControl;
		image = new BufferedImage(barrierDataControl.getWidth(), barrierDataControl.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		fillImage();
	}
	
	private void fillImage() {
		Graphics2D g = (Graphics2D) image.getGraphics();
		AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
		g.setComposite(alphaComposite);
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, image.getWidth(null), image.getHeight(null));
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, image.getWidth(null) - 1, image.getHeight(null) - 1);
	}
	
	@Override
	public void changePosition(int x, int y) {
		int width = activeAreaDataControl.getWidth();
		int height = activeAreaDataControl.getHeight();
		activeAreaDataControl.setActiveArea(x - width / 2, y - height, width, height);
	}

	@Override
	public ElementReferenceDataControl getElementReferenceDataControl() {
		return null;
	}

	@Override
	public int getLayer() {
		return -1;
	}

	@Override
	public float getScale() {
		return 1.0f;
	}

	@Override
	public int getX() {
		return activeAreaDataControl.getX() + activeAreaDataControl.getWidth() / 2;
	}

	@Override
	public int getY() {
		return activeAreaDataControl.getY() + activeAreaDataControl.getHeight();
	}

	@Override
	public void recreateImage() {
		image = new BufferedImage(activeAreaDataControl.getWidth(), activeAreaDataControl.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		fillImage();
	}

	@Override
	public void setScale(float scale) {
	}

	@Override
	public boolean canRescale() {
		return false;
	}

	@Override
	public boolean canResize() {
		return true;
	}

	@Override
	public void changeSize(int width, int height) {
		int x = activeAreaDataControl.getX();
		int y = activeAreaDataControl.getY();
		if (width < 1)
			width = 1;
		if (height < 1)
			height = 1;
		activeAreaDataControl.setActiveArea(x ,y , width, height);
	}

	@Override
	public int getHeight() {
		return activeAreaDataControl.getHeight();
	}

	@Override
	public int getWidth() {
		return activeAreaDataControl.getWidth();
	}

	@Override
	public boolean transparentPoint(int x, int y) {
		return false;
	}

}
