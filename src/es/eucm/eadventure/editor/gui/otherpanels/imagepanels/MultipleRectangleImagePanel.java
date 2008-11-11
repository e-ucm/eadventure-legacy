package es.eucm.eadventure.editor.gui.otherpanels.imagepanels;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class MultipleRectangleImagePanel extends ImagePanel {

	/**
	 * Default color
	 */
	private static final Color DEFAULT_COLOR = Color.RED;
	
	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * List of rectangles to display.
	 */
	private List<Rectangle> rectangles;
	
	/**
	 * Color of the rectangles
	 */
	private Color color;

	/**
	 * Constructor.
	 * 
	 * @param imagePath
	 *            Path to the background image
	 */
	public MultipleRectangleImagePanel( String imagePath, Color color ) {
		super( imagePath );
		rectangles = new ArrayList<Rectangle>( );
		this.color = color;
	}
	
	public MultipleRectangleImagePanel( String imagePath ) {
		this (imagePath, DEFAULT_COLOR);
	}

	/**
	 * Adds a new rectangle to be painted in the panel.
	 * 
	 * @param x
	 *            X coordinate of the rectangle
	 * @param y
	 *            Y coordinate of the rectangle
	 * @param width
	 *            Width of the rectangle
	 * @param height
	 *            Height of the rectangle
	 */
	public void addRectangle( int x, int y, int width, int height ) {
		// Add the rectangle
		rectangles.add( new Rectangle( x, y, width, height ) );
	}

	@Override
	public void paint( Graphics g ) {
		super.paint( g );

		// If the image is loaded, draw the rectangles
		if( isImageLoaded( ) ) {
			for( Rectangle rectangle : rectangles ) {
				// Paint the rectangle
				g.setColor( color );
				g.fillRect( getAbsoluteX( rectangle.x ), getAbsoluteY( rectangle.y ), getAbsoluteWidth( rectangle.width ), getAbsoluteHeight( rectangle.height ) );
				g.setColor( Color.BLACK );
				g.drawRect( getAbsoluteX( rectangle.x ), getAbsoluteY( rectangle.y ), getAbsoluteWidth( rectangle.width ), getAbsoluteHeight( rectangle.height ) );
			}
		}
	}

	/**
	 * Private class to hold each rectangle to paint.
	 */
	private class Rectangle {

		/**
		 * X coordinate of the rectangle.
		 */
		public int x;

		/**
		 * Y coordinate of the rectangle.
		 */
		public int y;

		/**
		 * Width of the rectangle.
		 */
		public int width;

		/**
		 * Height of the rectangle.
		 */
		public int height;

		/**
		 * Constructor.
		 * 
		 * @param x
		 *            X coordinate of the rectangle
		 * @param y
		 *            Y coordinate of the rectangle
		 * @param width
		 *            Width of the rectangle
		 * @param height
		 *            Height of the rectangle
		 */
		public Rectangle( int x, int y, int width, int height ) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}
	}
}
