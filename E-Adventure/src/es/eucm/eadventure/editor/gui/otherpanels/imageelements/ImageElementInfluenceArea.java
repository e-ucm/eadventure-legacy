
package es.eucm.eadventure.editor.gui.otherpanels.imageelements;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ElementReferenceDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.InfluenceAreaDataControl;

public class ImageElementInfluenceArea extends ImageElement {

	private InfluenceAreaDataControl influenceAreaDataControl;
	
	private ImageElement imageElementReference;
	
	public ImageElementInfluenceArea(InfluenceAreaDataControl influenceAreaDataControl, ImageElement reference) {
		this.influenceAreaDataControl = influenceAreaDataControl;
		this.imageElementReference = reference;
		if (influenceAreaDataControl.hasInfluenceArea())
			image = new BufferedImage(influenceAreaDataControl.getWidth(), influenceAreaDataControl.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		else {
			int width = (int) (reference.getImage().getWidth(null) * reference.getScale() + 40);
			int height = (int) (reference.getImage().getHeight(null) * reference.getScale() + 40);
			image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		}
		fillImage();
	}
	
	private void fillImage() {
		if (image != null) {
			Graphics2D g = (Graphics2D) image.getGraphics();
			AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
			g.setComposite(alphaComposite);
			g.setColor(Color.GREEN);
			g.fillRect(0, 0, image.getWidth(null), image.getHeight(null));
			g.setColor(Color.BLACK);
			g.drawRect(0, 0, image.getWidth(null) - 1, image.getHeight(null) - 1);
		}
	}
	
	@Override
	public void changePosition(int x, int y) {
		if (!influenceAreaDataControl.hasInfluenceArea()) {
			int width = (int) (imageElementReference.getImage().getWidth(null) * imageElementReference.getScale() + 40);
			int height = (int) (imageElementReference.getImage().getHeight(null) * imageElementReference.getScale() + 40);
			influenceAreaDataControl.setInfluenceArea(- 20, - 20, width, height);
		}
		
		int width = influenceAreaDataControl.getWidth();
		int height = influenceAreaDataControl.getHeight();
		int imageWidth = (int) (imageElementReference.getWidth() * imageElementReference.getScale());
		int imageHeight = (int) (imageElementReference.getHeight() * imageElementReference.getScale());
		int imageX = imageElementReference.getX();
		int imageY = imageElementReference.getY();
		
		
		int imageTempX = imageX - imageWidth / 2;
		int imageTempY = imageY - imageHeight;
		int tempX = x - width / 2;
		int tempY = y - height;

		tempX = tempX - imageTempX;
		tempY = tempY - imageTempY;
		
		if (tempX > 0)
			tempX = 0;
		if (tempY > 0)
			tempY = 0;
		if (tempX < imageElementReference.getWidth() * imageElementReference.getScale() - width)
			tempX = (int) (imageElementReference.getWidth() * imageElementReference.getScale() - width);
		if (tempY < imageElementReference.getHeight() * imageElementReference.getScale() - height)
			tempY = (int) (imageElementReference.getHeight() * imageElementReference.getScale() - height);
		
		influenceAreaDataControl.setInfluenceArea(tempX, tempY, width, height);
	}

	@Override
	public DataControl getDataControl() {
		return null;
	}

	@Override
	public int getLayer() {
		return Integer.MAX_VALUE;
	}

	@Override
	public float getScale() {
		return 1.0f;
	}

	@Override
	public int getX() {
		if (influenceAreaDataControl.hasInfluenceArea()) {
			int left = (int) (imageElementReference.getX() - imageElementReference.getWidth() * imageElementReference.getScale() / 2);
			return left + influenceAreaDataControl.getX() + influenceAreaDataControl.getWidth() / 2;
		} else
			return imageElementReference.getX();
	}

	@Override
	public int getY() {
		if (influenceAreaDataControl.hasInfluenceArea()) {
			int upper = (int) (imageElementReference.getY() - imageElementReference.getHeight() * imageElementReference.getScale());
			return upper + influenceAreaDataControl.getY() + influenceAreaDataControl.getHeight();
		} else
			return imageElementReference.getY() + 20;
	}

	@Override
	public void recreateImage() {
		if (influenceAreaDataControl.hasInfluenceArea())
			image = new BufferedImage(influenceAreaDataControl.getWidth(), influenceAreaDataControl.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		else {
			int width = (int) (imageElementReference.getImage().getWidth(null) * imageElementReference.getScale() + 40);
			int height = (int) (imageElementReference.getImage().getHeight(null) * imageElementReference.getScale() + 40);
			image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		}
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
		int x = influenceAreaDataControl.getX();
		int y = influenceAreaDataControl.getY();
				
		int imageWidth = (int) (imageElementReference.getWidth() * imageElementReference.getScale());
		int imageHeight = (int) (imageElementReference.getHeight() * imageElementReference.getScale());		
		
		if (width + x < imageWidth)
			width = imageWidth - x;
		if (height + y < imageHeight)
			height = imageHeight - y;
		
		influenceAreaDataControl.setInfluenceArea(x ,y , width, height);
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
