package es.eucm.eadventure.editor.gui.otherpanels.imageelements;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.scene.ElementReferenceDataControl;

public class ImageElementReference extends ImageElement {

	private ElementReferenceDataControl elementReferenceDataControl;

	public ImageElementReference(ElementReferenceDataControl elementReferenceDataControl) {
		this.elementReferenceDataControl = elementReferenceDataControl;
		String imagePath = Controller.getInstance( ).getElementImagePath( elementReferenceDataControl.getElementId( ) );
		if (imagePath != null)
			image = AssetsController.getImage( imagePath );
		else
			image = (new ImageIcon("img/assets/EmptyImage.png")).getImage();
	}
	
	public int getX() {
		return elementReferenceDataControl.getElementX();
	}
	
	public int getY() {
		return elementReferenceDataControl.getElementY();
	}

	public float getScale() {
		return elementReferenceDataControl.getElementScale();
	}

	public ElementReferenceDataControl getElementReferenceDataControl() {
		return elementReferenceDataControl;
	}

	public void recreateImage() {
		String imagePath = Controller.getInstance( ).getElementImagePath( elementReferenceDataControl.getElementId( ) );
		if (imagePath != null)
			image = AssetsController.getImage( imagePath );
		else
			image = (new ImageIcon("img/assets/EmptyImage.png")).getImage();
	}
	
	public int getLayer() {
		return elementReferenceDataControl.getElementReference().getLayer();
	}
	
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (!(o instanceof ImageElementReference))
			return false;
		ImageElementReference temp = (ImageElementReference) o;
		if (temp.elementReferenceDataControl.getElementId().equals(elementReferenceDataControl.getElementId()))
			return true;
		return false;
	}

	public void changePosition(int x, int y) {
		elementReferenceDataControl.setElementPosition(x, y);			
	}

	@Override
	public void setScale(float scale) {
		int incrementX = (int) (image.getWidth(null) * scale - image.getWidth(null) * this.getScale());
		int incrementY = (int) (image.getHeight(null) * scale - image.getHeight(null) * this.getScale());
		elementReferenceDataControl.getInfluenceArea().referenceScaleChanged(incrementX, incrementY);
		elementReferenceDataControl.setElementScale(scale);
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
		if (image == null)
			return false;
		else {
            int alpha = ((BufferedImage) this.image).getRGB( (int) (x / this.getScale()), (int) (y / this.getScale())) >>> 24;
            return !(alpha > 128);
		}
	}
}
