package es.eucm.eadventure.editor.gui.otherpanels.imageelements;

import java.awt.Image;

import es.eucm.eadventure.editor.control.controllers.scene.ElementReferenceDataControl;

/**
 * Abstract class for all the ImageElements that can be shown and edited
 * in the ScenePreviewEditionPanel.
 * 
 * @author Eugenio Marchiori
 */
public abstract class ImageElement implements Comparable<Object> {

	/**
	 * The asociated image that is shown in the panel
	 */
	protected Image image;
	
	/**
	 * Returns the scale of the reference or component
	 * 
	 * @return the scale
	 */
	public abstract float getScale();

	/**
	 * Returns the ElementReferenceDataControl asociated with the ImageElement
	 * in case it exits
	 * 
	 * @return the ElementReferenceDataControl
	 */
	public abstract ElementReferenceDataControl getElementReferenceDataControl();
	
	/**
	 * Get the image of the element
	 * 
	 * @return the image of the element
	 */
	public Image getImage() {
		return image;
	}
	
	/**
	 * Get the position along the x-axis
	 * 
	 * @return the position along the x-axis
	 */
	public abstract int getX();
	
	/**
	 * Get the position along the y-axis
	 * 
	 * @return the position along the y-axis
	 */
	public abstract int getY();

	/**
	 * Recreate the image, in case a change was made to the controller
	 */
	public abstract void recreateImage();
	
	/**
	 * Get the layer of the reference
	 * 
	 * @return the layer
	 */
	public abstract int getLayer();
	
	/**
	 * Change the position of the element
	 * 
	 * @param x The new x-axis value
	 * @param y The new y-axis value
	 */
	public abstract void changePosition(int x, int y);

	/**
	 * Change the scale of the element
	 * 
	 * @param scale the new scale of the element
	 */
	public abstract void setScale(float scale);

	public abstract void changeSize(int width, int height);
	
	public abstract boolean canResize();
	
	public abstract boolean canRescale();
	
	public abstract int getWidth();
	
	public abstract int getHeight();
	
	public int compareTo(Object arg0) {
		if (arg0 == null || !(arg0 instanceof ImageElement)) {
			return 1;
		}
		ImageElement temp = (ImageElement) arg0;
		
		int tempLayer = temp.getLayer();
		int thisLayer = this.getLayer();

		return tempLayer - thisLayer;
	}

	public abstract boolean transparentPoint(int x, int y);


}
