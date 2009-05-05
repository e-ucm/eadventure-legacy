package es.eucm.eadventure.engine.core.control.functionaldata;

import java.awt.Color;

import es.eucm.eadventure.common.data.chapter.Action;
import es.eucm.eadventure.common.data.chapter.CustomAction;
import es.eucm.eadventure.common.data.chapter.InfluenceArea;
import es.eucm.eadventure.common.data.chapter.elements.Element;

/**
 * This class defines the common behaviour from the characters and
 * the objects in the game
 */
public abstract class FunctionalElement implements Renderable {

    /**
     * Horizontal position of the element
     */
    protected float x;

    /**
     * Vertical position of the element
     */
    protected float y;
    
    protected float scale;
    
    /**
     * The position in which will be painted this element
     */
    protected int layer;

    /**
     * Creates a new FunctionalElement
     * @param x horizontal position of the element
     * @param y vertical position of the element
     */
    public FunctionalElement( int x, int y ) {
        this.x = x;
        this.y = y;
        this.scale = 1;
        this.layer = -1;
    }

    /**
     * Returns this element's data
     * @return this element's data
     */
    public abstract Element getElement( );

    /**
     * Returns the horizontal position of this element
     * @return the horizontal position of this element
     */
    public float getX( ) {
        return x;
    }

    /**
     * Returns the vertical position of this element
     * @return the vertical position of this element
     */
    public float getY( ) {
        return y;
    }

    public float getScale() {
    	return scale;
    }
    
    /**
     * Gets this Sprite's width, based on the size of the current image.
     */
    public abstract int getWidth( );

    /**
     * Gets this Sprite's height, based on the size of the current image.
     */
    public abstract int getHeight( );

    /**
     * Sets the horizontal position of this element
     * @param x the new horizontal position
     */
    public void setX( float x ) {
        this.x = x;
    }

    /**
     * Sets the vertical position of this element
     * @param y the new vertical position
     */
    public void setY( float y ) {
        this.y = y;
    }

    public void setScale(float scale) {
    	this.scale = scale;
    }
    /**
     * Returns whether the given point is inside this element
     * @param x the horizontal position of the point
     * @param y the vertical position of the point
     * @return true if the given point is inside this element, false otherwise
     */
    public abstract boolean isPointInside( float x, float y );

    /**
     * Returns whether this element can perform the given action
     * @param action the action to be performed
     * @return true if the action can be performed, false otherwise
     */
    public abstract boolean canPerform( int action );
    
    /**
     * Returns if the element can be used alone, without any other element
     * @return True if the element can be used, false otherwise
     */
    public boolean canBeUsedAlone( ) {
        return false;
    }

    /**
     * Triggers the examining action associated with the element
     * @return True if the element was examined, false otherwise
     */
    public boolean examine( ) {
        return false;
    }

    /**
     * Returns whether this element is in the player's inventory
     * @return true if this is in the inventory, false otherwise
     */
    public boolean isInInventory( ) {
        return false;
    }
    
    /**
     * Returns a color from the given string
     * @param colorText Color in format "#RRGGBB"
     * @return Described color
     */
    protected Color generateColor( String colorText ) {
        return new Color( Integer.valueOf( colorText.substring( 1 ), 16 ).intValue( ) );
    }
    
    public abstract Action getFirstValidAction(int actionType);
    
    public abstract CustomAction getFirstValidCustomAction(String actionName);

    public abstract CustomAction getFirstValidCustomInteraction(String actionName);

	public int getLayer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	public abstract InfluenceArea getInfluenceArea();

	public boolean use() {
		return false;
	}
}

