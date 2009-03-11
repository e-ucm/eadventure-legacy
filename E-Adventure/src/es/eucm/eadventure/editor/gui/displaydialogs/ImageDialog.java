package es.eucm.eadventure.editor.gui.displaydialogs;

import java.awt.Image;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.AssetsController;

/**
 * This class displays an image.
 * 
 * @author Bruno Torijano Bueno
 */
public class ImageDialog extends GraphicDialog {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Image to dosplay.
	 */
	private Image image;

	/**
	 * Creates a new image dialog with the given path.
	 * 
	 * @param imagePath
	 *            Path and extension of the image
	 */
	public ImageDialog( String imagePath ) {
		// Load the image
		image = AssetsController.getImage( imagePath );

		// Set the dialog and show it
		setTitle( TextConstants.getText( "ImageDialog.Title", AssetsController.getFilename( imagePath ) ) );
		setVisible( true );
	}

	@Override
	protected Image getCurrentImage( ) {
		return image;
	}

	@Override
	protected void deleteImages( ) {
		if (image!=null)
		image.flush( );
	}
}
