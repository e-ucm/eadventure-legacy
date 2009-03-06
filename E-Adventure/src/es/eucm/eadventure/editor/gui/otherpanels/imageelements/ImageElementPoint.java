package es.eucm.eadventure.editor.gui.otherpanels.imageelements;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.scene.PointDataControl;

public class ImageElementPoint extends ImageElement {
	
	private PointDataControl point;
	
	public ImageElementPoint(PointDataControl point) {
		this.point = point;
		image = new BufferedImage(20, 20, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g = (Graphics2D) image.getGraphics();
		AlphaComposite alphaComposite = AlphaComposite.getInstance(
				AlphaComposite.SRC_OVER, 0.6f);
		g.setComposite(alphaComposite);
		g.setColor(Color.GREEN);
		g.fillOval(0, 0, 20, 20);
		g.setColor(Color.BLACK);
		g.drawOval(0, 0, 19, 19);
	}
	
	@Override
	public boolean canRescale() {
		return false;
	}

	@Override
	public boolean canResize() {
		return false;
	}

	@Override
	public void changePosition(int x, int y) {
		point.setPoint(x - 5 , y - 10);
	}

	@Override
	public void changeSize(int width, int height) {
	}

	@Override
	public DataControl getDataControl() {
		return point;
	}

	@Override
	public int getHeight() {
		return 20;
	}

	@Override
	public int getLayer() {
		return 0;
	}

	@Override
	public DataControl getReferencedDataControl() {
		return null;
	}

	@Override
	public float getScale() {
		return 1.0f;
	}

	@Override
	public int getWidth() {
		return 20;
	}

	@Override
	public int getX() {
		return point.getX() + 5;
	}

	@Override
	public int getY() {
		return point.getY() + 10;
	}

	@Override
	public void recreateImage() {
	}

	@Override
	public void setScale(float scale) {
	}

	@Override
	public boolean transparentPoint(int x, int y) {
		return false;
	}

}
