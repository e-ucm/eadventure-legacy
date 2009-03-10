package es.eucm.eadventure.common.data.chapter;

import java.awt.Point;
import java.util.List;

/**
 * The object is a rectangle or polygon
 */
public interface Rectangle {

	/**
	 * Set the values of the rectangle
	 * 
	 * @param x The x axis value
	 * @param y The y axis value
	 * @param width The width of the rectangle
	 * @param height The height of the rectangle
	 */
	public void setValues(int x, int y, int width, int height);
	
	/**
	 * Get the x axis value
	 * 
	 * @return The x axis value
	 */
	public int getX();
	
	/**
	 * Get the y axis value
	 * 
	 * @return The y axis value
	 */
	public int getY();
	
	/**
	 * Get the width of the rectangle
	 * 
	 * @return The width of the rectangle
	 */
	public int getWidth();
	
	/**
	 * Get the height of the rectangle
	 * 
	 * @return The height of the rectangle
	 */
	public int getHeight();
	
	/**
	 * True if it is rectangular, false if it is a polygon
	 * 
	 * @return True if the object is rectangular
	 */
	public boolean isRectangular();
	
	/**
	 * Make the object rectangular (true) or a polygon (false)
	 * 
	 * @param rectangular The rectangular value
	 */
	public void setRectangular(boolean rectangular);
	
	/**
	 * Get the list of points for the polygon
	 * 
	 * @return The list of points of the polygon
	 */
	public List<Point> getPoints();
}
