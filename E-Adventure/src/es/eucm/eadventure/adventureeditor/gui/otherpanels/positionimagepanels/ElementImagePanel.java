package es.eucm.eadventure.adventureeditor.gui.otherpanels.positionimagepanels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import es.eucm.eadventure.adventureeditor.control.controllers.AssetsController;

public class ElementImagePanel extends PositionImagePanel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Element to be showed in the given position.
	 */
	private Image element;

	/**
	 * Constructor.
	 * 
	 * @param imagePath
	 *            Image to show
	 * @param elementPath
	 *            Element to be drawn on top of the image
	 */
	public ElementImagePanel( String imagePath, String elementPath ) {
		super( imagePath );

		// Load the element image
		if( elementPath != null )
			element = AssetsController.getImage( elementPath );
		else
			element = null;
	}

	/**
	 * Loads a new element with the given path.
	 * 
	 * @param elementPath
	 *            Path of the new element
	 */
	public void loadElement( String elementPath ) {
		// Load the element image
		if( elementPath != null )
			element = AssetsController.getImage( elementPath );
		else
			element = null;
	}

	@Override
	public void paint( Graphics g ) {
		super.paint( g );

		// If the background is loaded, paint an image
		if( isImageLoaded( ) ) {
			// If the element is avalaible, paint it
			if( element != null )
				paintRelativeImage( g, element, selectedX, selectedY, true );

			// If it is not avalaible, draw a circle
			else {
				int realX = getAbsoluteX( selectedX );
				int realY = getAbsoluteY( selectedY );

				g.setColor( Color.RED );
				g.fillOval( realX - 3, realY - 3, 8, 8 );
				g.setColor( Color.BLACK );
				g.drawOval( realX - 3, realY - 3, 7, 7 );
			}
		}
	}
}
