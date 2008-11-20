package es.eucm.eadventure.editor.gui.displaydialogs;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.AssetsController;

/**
 * This class plays a set of slides, the slide can change with a mouse click in the dialog.
 * 
 * @author Bruno Torijano Bueno
 */
public class SlidesDialog extends GraphicDialog {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * List of slides.
	 */
	private Image[] slides;

	/**
	 * Index of the current slide.
	 */
	private int currentSlideIndex;

	/**
	 * Creates a new animation dialog with the given path.
	 * 
	 * @param slidesPath
	 *            Path of the animation, without suffix ("_01.jpg")
	 */
	public SlidesDialog( String slidesPath ) {
		// Load the slides
		slides = AssetsController.getAnimation( slidesPath + "_01.jpg" );

		// Add a mouse listener to the glass pane to increase the selected index
		getGlassPane( ).setVisible( true );
		getGlassPane( ).addMouseListener( new MouseAdapter( ) {
			public void mouseClicked( MouseEvent e ) {
				currentSlideIndex = ( currentSlideIndex + 1 ) % slides.length;
				repaint( );
			}
		} );

		// Set the title and show the dialog
		setTitle( TextConstants.getText( "SlidesDialog.Title", AssetsController.getFilename( slidesPath ) ) );
		setVisible( true );
	}

	@Override
	protected Image getCurrentImage( ) {
		return slides[currentSlideIndex];
	}

	@Override
	protected double getCurrentImageRatio( ) {
		// In the slides, the ratio is always 4:3
		return 1.333d;
	}

	@Override
	protected void deleteImages( ) {
		// Flush all slides from the set
		for( Image slide : slides )
			slide.flush( );
	}
}
