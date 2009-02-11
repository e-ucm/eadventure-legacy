package es.eucm.eadventure.editor.gui.otherpanels.imageelements;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.scene.NodeDataControl;

public class ImageElementNode extends ImageElement {

	private NodeDataControl nodeDataControl;

	private Image playerImage;

	public ImageElementNode(NodeDataControl nodeDataControl) {
		this.nodeDataControl = nodeDataControl;
		String imagePath = nodeDataControl.getPlayerImagePath();
		if (imagePath != null && imagePath.length() > 0)
			playerImage = AssetsController.getImage(imagePath);
		if (playerImage != null) {
			image = new BufferedImage(
					(int) (playerImage.getWidth(null) * nodeDataControl
							.getScale()),
					(int) (playerImage.getHeight(null) * nodeDataControl
							.getScale()) + 10, BufferedImage.TYPE_4BYTE_ABGR);
		} else {
			image = new BufferedImage(20, 20, BufferedImage.TYPE_4BYTE_ABGR);
		}
		fillImage();
	}

	private void fillImage() {
		Graphics2D g = (Graphics2D) image.getGraphics();
		AlphaComposite alphaComposite = AlphaComposite.getInstance(
				AlphaComposite.SRC_OVER, 0.6f);
		g.setComposite(alphaComposite);
		if (playerImage != null) {
			int width = (int) (playerImage.getWidth(null) * nodeDataControl
					.getScale());
			int height = (int) (playerImage.getHeight(null) * nodeDataControl
					.getScale());
			g.drawImage(playerImage, 0, 0, width, height, null);
			alphaComposite = AlphaComposite.getInstance(
					AlphaComposite.SRC_OVER, 0.3f);
			g.setComposite(alphaComposite);
			if (nodeDataControl.isInitial())
				g.setColor(Color.RED);
			else
				g.setColor(Color.BLUE);
			g.fillOval(width / 2 - 10, height - 10, 19, 19);
			g.setColor(Color.BLACK);
			g.drawOval(width / 2 - 10, height - 10, 19, 19);
		} else {
			if (nodeDataControl.isInitial())
				g.setColor(Color.RED);
			else
				g.setColor(Color.BLUE);
			g.fillOval(0, 0, image.getWidth(null), image.getHeight(null));
			g.setColor(Color.BLACK);
			g.drawOval(0, 0, image.getWidth(null) - 1,
					image.getHeight(null) - 1);
		}
	}

	@Override
	public void changePosition(int x, int y) {
		nodeDataControl.setNode(x, y, nodeDataControl.getScale());
	}

	@Override
	public DataControl getDataControl() {
		return null;
	}

	@Override
	public int getLayer() {
		return 0;
	}

	@Override
	public float getScale() {
		return nodeDataControl.getScale();
	}

	@Override
	public int getX() {
		return nodeDataControl.getX();
	}

	@Override
	public int getY() {
		return nodeDataControl.getY() + 10;
	}

	@Override
	public void recreateImage() {
		if (playerImage != null) {
			image = new BufferedImage(
					(int) (playerImage.getWidth(null) * nodeDataControl
							.getScale()),
					(int) (playerImage.getHeight(null) * nodeDataControl
							.getScale()) + 10, BufferedImage.TYPE_4BYTE_ABGR);
		} else {
			image = new BufferedImage(20, 20, BufferedImage.TYPE_4BYTE_ABGR);
		}
		fillImage();
	}

	public NodeDataControl getNodeDataControl() {
		return nodeDataControl;
	}

	@Override
	public void setScale(float scale) {
		nodeDataControl.setNode(nodeDataControl.getX(), nodeDataControl.getY(),
				scale);
	}

	@Override
	public boolean canRescale() {
		return true;
	}

	@Override
	public boolean canResize() {
		return false;
	}

	@Override
	public void changeSize(int width, int height) {
	}

	@Override
	public int getHeight() {
		return image.getHeight(null);
	}

	@Override
	public int getWidth() {
		return image.getWidth(null);
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
