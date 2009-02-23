package es.eucm.eadventure.common.data.chapter.elements;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.Rectangle;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;

/**
 * This class holds the data of an exit in eAdventure
 */
public class ActiveArea extends Item implements Rectangle {

	/**
	 * X position of the upper left corner of the exit
	 */
	private int x;

	/**
	 * Y position of the upper left corner of the exit
	 */
	private int y;

	/**
	 * Width of the exit
	 */
	private int width;

	/**
	 * Height of the exit
	 */
	private int height;
	
	private boolean rectangular;
	
	private List<Point> points;
	
	/**
	 * Conditions of the active area
	 */
	private Conditions conditions;
	
	

	/**
	 * Creates a new Exit
	 * @param rectangular 
	 * 
	 * @param x
	 *            The horizontal coordinate of the upper left corner of the exit
	 * @param y
	 *            The vertical coordinate of the upper left corner of the exit
	 * @param width
	 *            The width of the exit
	 * @param height
	 *            The height of the exit
	 */
	public ActiveArea( String id, boolean rectangular, int x, int y, int width, int height ) {
		super(id);
		this.rectangular = rectangular;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		points = new ArrayList<Point>();
		conditions = new Conditions();
	}

	/**
	 * Returns the horizontal coordinate of the upper left corner of the exit
	 * 
	 * @return the horizontal coordinate of the upper left corner of the exit
	 */
	public int getX( ) {
		return x;
	}

	/**
	 * Returns the horizontal coordinate of the bottom right of the exit
	 * 
	 * @return the horizontal coordinate of the bottom right of the exit
	 */
	public int getY( ) {
		return y;
	}

	/**
	 * Returns the width of the exit
	 * 
	 * @return Width of the exit
	 */
	public int getWidth( ) {
		return width;
	}

	/**
	 * Returns the height of the exit
	 * 
	 * @return Height of the exit
	 */
	public int getHeight( ) {
		return height;
	}

	public boolean isRectangular( ) {
		return rectangular;
	}
	
	public List<Point> getPoints() {
		return points;
	}
	
	public void addPoint(Point point) {
		points.add(point);
	}
	
	/**
	 * Set the values of the exit.
	 * 
	 * @param x
	 *            X coordinate of the upper left point
	 * @param y
	 *            Y coordinate of the upper left point
	 * @param width
	 *            Width of the exit area
	 * @param height
	 *            Height of the exit area
	 */
	public void setValues( int x, int y, int width, int height ) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/**
	 * @return the conditions
	 */
	public Conditions getConditions( ) {
		return conditions;
	}

	/**
	 * @param conditions the conditions to set
	 */
	public void setConditions( Conditions conditions ) {
		this.conditions = conditions;
	}
	
	public Object clone() throws CloneNotSupportedException {
		ActiveArea aa = (ActiveArea) super.clone();
		aa.conditions = (conditions != null ? (Conditions) conditions.clone() : null);
		aa.height = height;
		aa.width = width;
		aa.x = x;
		aa.y = y;
		return aa;
	}

	public void setRectangular(boolean rectangular) {
		this.rectangular = rectangular;
	}
}