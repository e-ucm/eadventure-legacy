package es.eucm.eadventure.editor.gui.otherpanels.positionimagepanels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.editor.control.controllers.AssetsController;

public class CategoryElementImagePanel extends PositionImagePanel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Element to be showed in the given position.
	 */
	private Image element;

	/**
	 * Stores whether one category's elements must be painted or not.
	 */
	private boolean[] categoryVisible;

	/**
	 * List of the images stored in each category.
	 */
	private List<List<Element>> categoryElements;

	/**
	 * Constructor. All the categories are visible by default.
	 * 
	 * @param categoryCount
	 *            Number of categories for the panel
	 * @param imagePath
	 *            Image to show
	 * @param elementPath
	 *            Element to be drawn on top of the image
	 */
	public CategoryElementImagePanel( int categoryCount, String imagePath, String elementPath ) {
		super( imagePath );

		// Create the arrays
		categoryVisible = new boolean[categoryCount];
		categoryElements = new ArrayList<List<Element>>( );
		for( int i = 0; i < categoryCount; i++ ) {
			categoryVisible[i] = true;
			categoryElements.add( new ArrayList<Element>( ) );
		}

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

	/**
	 * Sets if the visibility of a given category.
	 * 
	 * @param category
	 *            Number of the category
	 * @param visible
	 *            True if the category must be visible, false otherwise
	 */
	public void setCategoryVisible( int category, boolean visible ) {
		categoryVisible[category] = visible;
	}

	/**
	 * Adds a new element to be painted in the panel, stored in the given category.
	 * 
	 * @param category
	 *            Number of the category to store the image
	 * @param imagePath
	 *            Image path of the element
	 * @param x
	 *            X coordinate of the element
	 * @param y
	 *            Y coordinate of the element
	 */
	public void addCategoryElement( int category, String imagePath, int x, int y ) {
		// If the path is not null add the image
		if( imagePath != null ) {
			categoryElements.get( category ).add( new Element( AssetsController.getImage( imagePath ), x, y ) );
		}
	}

	@Override
	public void paint( Graphics g ) {
		super.paint( g );

		// If the background is loaded, paint an image
		if( isImageLoaded( ) ) {
			// Check and paint the visible categories
			for( int i = 0; i < categoryVisible.length; i++ ) {
				if( categoryVisible[i] ) {
					for( Element element : categoryElements.get( i ) ) {
						paintRelativeImage( g, element.image, element.x, element.y, false );
					}
				}
			}

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

	/**
	 * Private class to hold each element to show.
	 */
	private class Element {

		/**
		 * Image of the element
		 */
		public Image image;

		/**
		 * X coordinate of the element.
		 */
		public int x;

		/**
		 * Y coordinate of the element.
		 */
		public int y;

		/**
		 * Constructor.
		 * 
		 * @param image
		 *            Image of the element
		 * @param x
		 *            X coordinate of the element
		 * @param y
		 *            Y coordinate of the element
		 */
		public Element( Image image, int x, int y ) {
			this.image = image;
			this.x = x;
			this.y = y;
		}
	}
}
