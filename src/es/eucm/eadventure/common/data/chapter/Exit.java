package es.eucm.eadventure.common.data.chapter;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.Documented;
import es.eucm.eadventure.common.data.chapter.ExitLook;

/**
 * This class holds the data of an exit in eAdventure
 */
public class Exit implements Cloneable, Documented, Rectangle {

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

	/**
	 * Documentation of the exit.
	 */
	private String documentation;

	/**
	 * List of nextscenes of the exit
	 */
	private List<NextScene> nextScenes;
	
	 /**
     * Default exit look (it can exists or not)
     */
    private ExitLook defaultExitLook;

	/**
	 * Creates a new Exit
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
	public Exit( int x, int y, int width, int height ) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		documentation = null;
		nextScenes = new ArrayList<NextScene>( );
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
	 * Returns the documentation of the exit.
	 * 
	 * @return the documentation of the exit
	 */
	public String getDocumentation( ) {
		return documentation;
	}

	/**
	 * Returns the list of the next scenes from this scene
	 * 
	 * @return the list of the next scenes from this scene
	 */
	public List<NextScene> getNextScenes( ) {
		return nextScenes;
	}

	/**
	 * Changes the documentation of this exit.
	 * 
	 * @param documentation
	 *            The new documentation
	 */
	public void setDocumentation( String documentation ) {
		this.documentation = documentation;
	}

	/**
	 * Adds a next scene to the list of next scenes
	 * 
	 * @param nextScene
	 *            the next scene to add
	 */
	public void addNextScene( NextScene nextScene ) {
		nextScenes.add( nextScene );
	}
	
    /**
     * @return the defaultExitLook
     */
    public ExitLook getDefaultExitLook( ) {
        return defaultExitLook;
    }

    /**
     * @param defaultExitLook the defaultExitLook to set
     */
    public void setDefaultExitLook( ExitLook defaultExitLook ) {
        this.defaultExitLook = defaultExitLook;
    }
    
    /**
     * Returns whether a point is inside the exit
     * @param x the horizontal positon
     * @param y the vertical position
     * @return true if the point (x, y) is inside the exit, false otherwise
     */
    public boolean isPointInside( int x, int y ) {
        return x > getX0() && x < getX1() && y > getY0() && y < getY1();
    }

    /**
     * Returns the horizontal coordinate of the upper left corner of the exit
     * @return the horizontal coordinate of the upper left corner of the exit
     */
    public int getX0( ) {
        return getX();
    }

    /**
     * Returns the vertical coordinate of the upper left corner of the exit
     * @return the vertical coordinate of the upper left corner of the exit
     */
    public int getX1( ) {
        return getX()+getWidth();
    }

    /**
     * Returns the horizontal coordinate of the bottom right of the exit
     * @return the horizontal coordinate of the bottom right of the exit
     */
    public int getY0( ) {
        return getY();
    }

    /**
     * Returns the vertical coordinate of the bottom right of the exit
     * @return the vertical coordinate of the bottom right of the exit
     */
    public int getY1( ) {
        return getY()+getHeight();
    }
    
	public Object clone() throws CloneNotSupportedException {
		Exit e = (Exit) super.clone();
		e.defaultExitLook = (defaultExitLook != null ? (ExitLook) defaultExitLook.clone() : null);
		e.documentation = (documentation != null ? new String(documentation) : null);
		e.height = height;
		if (nextScenes != null) {
			e.nextScenes = new ArrayList<NextScene>();
			for (NextScene ns : nextScenes)
				e.nextScenes.add((NextScene) ns.clone());
		}
		e.width = width;
		e.x = x;
		e.y = y;
		return e;
	}
}