package es.eucm.eadventure.editor.gui.otherpanels.imageelements;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ExitDataControl;

public class ImageElementExit extends ImageElement {

	private ExitDataControl exitDataControl;

	public ImageElementExit(ExitDataControl exitDataControl) {
		this.exitDataControl = exitDataControl;
		image = new BufferedImage(exitDataControl.getWidth(), exitDataControl
				.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		fillImage();
	}

	private void fillImage() {
		Graphics2D g = (Graphics2D) image.getGraphics();
		AlphaComposite alphaComposite = AlphaComposite.getInstance(
				AlphaComposite.SRC_OVER, 0.3f);
		g.setComposite(alphaComposite);
		g.setColor(Color.RED);
		g.fillRect(0, 0, image.getWidth(null), image.getHeight(null));
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, image.getWidth(null) - 1, image.getHeight(null) - 1);
	}

	@Override
	public void changePosition(int x, int y) {
		int width = exitDataControl.getWidth();
		int height = exitDataControl.getHeight();
		exitDataControl.setExit(x - width / 2, y - height, width, height);
	}

	@Override
	public DataControl getDataControl() {
		return exitDataControl;
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
		return exitDataControl.getX() + exitDataControl.getWidth() / 2;
	}

	@Override
	public int getY() {
		return exitDataControl.getY() + exitDataControl.getHeight();
	}

	@Override
	public void recreateImage() {
		image = new BufferedImage(exitDataControl.getWidth(), exitDataControl
				.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
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
		int x = exitDataControl.getX();
		int y = exitDataControl.getY();
		if (width < 1)
			width = 1;
		if (height < 1)
			height = 1;
		exitDataControl.setExit(x, y, width, height);
	}

	@Override
	public int getHeight() {
		return exitDataControl.getHeight();
	}

	@Override
	public int getWidth() {
		return exitDataControl.getWidth();
	}

	@Override
	public boolean transparentPoint(int x, int y) {
		return false;
	}

	@Override
	public DataControl getReferencedDataControl() {
		return null;
	}

}
