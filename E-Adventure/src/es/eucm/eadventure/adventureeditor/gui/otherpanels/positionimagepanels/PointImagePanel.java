package es.eucm.eadventure.adventureeditor.gui.otherpanels.positionimagepanels;

import java.awt.Color;
import java.awt.Graphics;

public class PointImagePanel extends PositionImagePanel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public PointImagePanel( ) {
		this( null );

		selectedX = 0;
		selectedY = 0;
	}

	/**
	 * Constructor.
	 * 
	 * @param imagePath
	 *            Image to show
	 */
	public PointImagePanel( String imagePath ) {
		super( imagePath );

		selectedX = 0;
		selectedY = 0;
	}

	@Override
	public void paint( Graphics g ) {
		super.paint( g );

		// If the image is loaded, draw the point
		if( isImageLoaded( ) ) {
			int realX = getAbsoluteX( selectedX );
			int realY = getAbsoluteY( selectedY );

			g.setColor( Color.RED );
			g.fillOval( realX - 3, realY - 3, 8, 8 );
			g.setColor( Color.BLACK );
			g.drawOval( realX - 3, realY - 3, 7, 7 );
		}
	}
}
