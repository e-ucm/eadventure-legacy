package es.eucm.eadventure.adventureeditor.gui.otherpanels.imagepanels;

import java.awt.Color;
import java.awt.Graphics;

public class RectangleImagePanel extends ImagePanel {

	/**
	 * Default color
	 */
	private static final Color DEFAULT_COLOR = Color.RED;
	
	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Most left position of the rectangle.
	 */
	private int x;

	/**
	 * Most top position of the rectangle.
	 */
	private int y;

	/**
	 * Width of the rectangle.
	 */
	private int width;

	/**
	 * Height of the rectangle.
	 */
	private int height;
	
	/**
	 * Color of the rectangles
	 */
	private Color color;

	/**
	 * Constructor.
	 * 
	 * @param imagePath
	 *            Path to the background image
	 * @param x
	 *            X coordinate of the rectangle first point
	 * @param y
	 *            Y coordinate of the rectangle first point
	 * @param width
	 *            Width of the rectangle
	 * @param height
	 *            Height of the rectangle
	 */
	public RectangleImagePanel( String imagePath, int x, int y, int width, int height, Color color ) {
		super( imagePath );
		this.color = color;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public RectangleImagePanel( String imagePath, int x, int y, int width, int height ) {
		this ( imagePath, x, y, width, height, DEFAULT_COLOR);
	}

	/**
	 * Returns the X coordinate of the upper left point of the rectangle
	 * 
	 * @return X coordinate of the upper left point
	 */
	public int getRectangleX( ) {
		return x;
	}

	/**
	 * Returns the Y coordinate of the upper left point of the rectangle
	 * 
	 * @return Y coordinate of the upper left point
	 */
	public int getRectangleY( ) {
		return y;
	}

	/**
	 * Returns the width of the rectangle.
	 * 
	 * @return Width of the rectangle
	 */
	public int getRectangleWidth( ) {
		return width;
	}

	/**
	 * Returns the height of the rectangle.
	 * 
	 * @return Height of the rectangle
	 */
	public int getRectangleHeight( ) {
		return height;
	}

	/**
	 * Sets the first point of the rectangle.
	 * 
	 * @param x
	 *            X coordinate of the point
	 * @param y
	 *            Y coordinate of the point
	 */
	public void setFirstPoint( int x, int y ) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Sets the size of the rectangle.
	 * 
	 * @param width
	 *            Width of the rectangle
	 * @param height
	 *            Height of the rectangle
	 */
	public void setRectangleSize( int width, int height ) {
		this.width = width;
		this.height = height;
	}

	/**
	 * Sets the second point of the rectangle.
	 * 
	 * @param x
	 *            X coordinate of the point
	 * @param y
	 *            Y coordinate of the point
	 */
	public void setSecondPoint( int x, int y ) {
		this.width = x - this.x;
		this.height = y - this.y;
	}

	/**
	 * Validates the data, so the width and the height are not negative.
	 */
	public void validateRectangle( ) {
		// If the width is negative, move the x coordinate and invert the width value
		if( width < 0 ) {
			x += width;
			width *= -1;
		}

		// If the height is negative, move the y coordinate and invert the height value
		if( height < 0 ) {
			y += height;
			height *= -1;
		}
	}

	@Override
	public void paint( Graphics g ) {
		super.paint( g );

		// If the image is loaded, draw the elements
		if( isImageLoaded( ) ) {
			// Correct the values if the width or height are negative
			int x = this.x;
			int y = this.y;
			int width = this.width;
			int height = this.height;

			// If the width is negative, move the x coordinate and invert the width value
			if( width < 0 ) {
				x += width;
				width *= -1;
			}

			// If the height is negative, move the y coordinate and invert the height value
			if( height < 0 ) {
				y += height;
				height *= -1;
			}

			// Paint the rectangle
			g.setColor( color );
			g.fillRect( getAbsoluteX( x ), getAbsoluteY( y ), getAbsoluteWidth( width ), getAbsoluteHeight( height ) );
			g.setColor( Color.BLACK );
			g.drawRect( getAbsoluteX( x ), getAbsoluteY( y ), getAbsoluteWidth( width ), getAbsoluteHeight( height ) );
		}
	}
}
