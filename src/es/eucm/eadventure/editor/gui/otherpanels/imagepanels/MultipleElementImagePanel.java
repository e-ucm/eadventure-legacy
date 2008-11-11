package es.eucm.eadventure.editor.gui.otherpanels.imagepanels;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.editor.control.controllers.AssetsController;

public class MultipleElementImagePanel extends ImagePanel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * List of elements to display.
	 */
	private List<Element> elements;

	/**
	 * Constructor.
	 * 
	 * @param imagePath
	 *            Path to the background image
	 */
	public MultipleElementImagePanel( String imagePath ) {
		super( imagePath );
		elements = new ArrayList<Element>( );
	}

	/**
	 * Adds a new element to be painted in the panel.
	 * 
	 * @param imagePath
	 *            Image path of the element
	 * @param x
	 *            X coordinate of the element
	 * @param y
	 *            Y coordinate of the element
	 */
	public void addElement( String imagePath, int x, int y ) {
		// If the path is not null add the image
		if( imagePath != null ) {
			elements.add( new Element( AssetsController.getImage( imagePath ), x, y ) );
		}
	}

	@Override
	public void paint( Graphics g ) {
		super.paint( g );

		// If the image is loaded, draw the elements
		if( isImageLoaded( ) ) {
			for( Element element : elements ) {
				paintRelativeImage( g, element.image, element.x, element.y, false );
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
