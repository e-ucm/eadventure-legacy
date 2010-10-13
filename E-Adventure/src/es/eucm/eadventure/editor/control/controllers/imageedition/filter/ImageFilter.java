package es.eucm.eadventure.editor.control.controllers.imageedition.filter;

import java.awt.image.BufferedImage;

/**
 * Image Filter
 *
 */
public interface ImageFilter {

	/**
	 * Transform an image
	 * @param image image to be transform
	 */
	void transform( BufferedImage image, int x, int y );
	
}
