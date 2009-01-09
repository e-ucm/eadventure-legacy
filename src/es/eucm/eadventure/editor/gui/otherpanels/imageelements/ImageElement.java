package es.eucm.eadventure.editor.gui.otherpanels.imageelements;

import java.awt.Image;

import es.eucm.eadventure.editor.control.controllers.scene.ElementReferenceDataControl;

public abstract class ImageElement implements Comparable<Object> {

	protected Image image;
	
	public abstract float getScale();

	public abstract ElementReferenceDataControl getElementReferenceDataControl();
	
	public Image getImage() {
		return image;
	}
	
	public abstract int getX();
	
	public abstract int getY();

	public abstract void recreateImage();
	
	public abstract int getLayer();
	
	public abstract void changePosition(int x, int y);

	public int compareTo(Object arg0) {
		if (arg0 == null || !(arg0 instanceof ImageElement)) {
			return 1;
		}
		ImageElement temp = (ImageElement) arg0;
		
		int tempLayer = temp.getLayer();
		int thisLayer = this.getLayer();

		return tempLayer - thisLayer;
	}

}
