package es.eucm.eadventure.adventureeditor.gui.otherpanels.positionimagepanels;

import es.eucm.eadventure.adventureeditor.gui.otherpanels.imagepanels.ImagePanel;

public abstract class PositionImagePanel extends ImagePanel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * X coord of the center of the element.
	 */
	protected int selectedX;

	/**
	 * Y coord of the bottom of the element.
	 */
	protected int selectedY;

	/**
	 * Constructor.
	 * 
	 * @param imagePath
	 *            Image to show
	 */
	public PositionImagePanel( String imagePath ) {
		super( imagePath );

		selectedX = 0;
		selectedY = 0;
	}

	/**
	 * Sets the new point in the position panel.
	 * 
	 * @param x
	 *            X coordinate of the new position
	 * @param y
	 *            Y coordinate of the new position
	 */
	public void setSelectedPoint( int x, int y ) {
		selectedX = x;
		selectedY = y;
	}

	/**
	 * Returns the selected X coordinate.
	 * 
	 * @return Selected X coordinate
	 */
	public int getSelectedtX( ) {
		return selectedX;
	}

	/**
	 * Returns the selected Y coordinate.
	 * 
	 * @return Selected Y coordinate
	 */
	public int getSelectedtY( ) {
		return selectedY;
	}

}
