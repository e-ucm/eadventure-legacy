package es.eucm.eadventure.engine.core.control.animations;

import java.awt.Image;

import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.gui.GUI;

/**
 * Abstract class that has a set of animations, with functions to react to
 * mouse events 
 */
public abstract class AnimationState {

    /**
     * The animations of this state
     */
    protected Animation[] animations;

    /**
     * The north animation: the character is heading the screen
     */
    public static final int NORTH = 0;

    /**
     * The south animation: the character is looking back the screen
     */
    public static final int SOUTH = 1;

    /**
     * The east animation: the character is looking at the right
     */
    public static final int EAST = 2;

    /**
     * The west animation: the character is looking at the left
     */
    public static final int WEST = 3;

    /**
     * Creates a new AnimationState
     */
    public AnimationState( ) {
        animations = new Animation[ 4 ];
    }

    /**
     * Updates the animation
     * @param elapsedTime the elapsed time from the last update
     */
    public abstract void update( long elapsedTime );

    /**
     * Initializes the animation
     */
    public abstract void initialize( );

    /**
     * Updates the animation
     */
    public void updateAnimation( ) {
        int tempDirection = -1;
        if( getCurrentDirection() == -1 ) setCurrentDirection( SOUTH );
        //Si es el eje Norte-Sur
        if( Math.abs( getVelocityY( ) ) > Math.abs( getVelocityX( ) ) ) {
            //Si baja
            //FIXME: EL = del IF se ha puesto a pelo, sin ver que pasa en juegos 3ªpersona
            if( getVelocityY( ) >= 0 ) {
                tempDirection = SOUTH;
            } else if( getVelocityY( ) < 0 ) {
                tempDirection = NORTH;
            }
            //Si es el eje Este-Oeste
        } else {
            //Si a la derecha
            //FIXME: EL = del IF se ha puesto a pelo, sin ver que pasa en juegos 3ªpersona
            if( getVelocityX( ) >= 0 ) {
                tempDirection = EAST;
            } else if( getVelocityX( ) < 0 ) {
                tempDirection = WEST;
            }
        }

        if( tempDirection != -1 && tempDirection != getCurrentDirection() ) {
            setCurrentDirection( tempDirection );
            animations[getCurrentDirection()].start( );
        }
    }

    /**
     * Returns the current frame of the animation
     */
    public Image getImage( ) {
        return animations[getCurrentDirection()].getImage( );
    }

    /**
     * Returns the current animation of the character
     */
    public Animation getCurrentAnimation( ) {
        return animations[getCurrentDirection()];
    }

    /**
     * Draws the current frame of the animation
     * @param x the horizontal position where paint the frame
     * @param y the vertical position where paint the frame
     * @param scale 
     * @param depth Position where will be drawn 
   	*/
    public void draw( int x, int y, float scale , int depth) {
        int realX = x - (int) ( getImage( ).getWidth( null ) * scale / 2 ) - Game.getInstance( ).getFunctionalScene( ).getOffsetX( );
        int realY = y - (int) ( getImage( ).getHeight( null ) * scale ); 
        Image tempImage = getImage().getScaledInstance(Math.round(getImage().getWidth(null)*scale), Math.round(getImage().getHeight(null)*scale), Image.SCALE_SMOOTH);
        if (depth == -1)
        	GUI.getInstance( ).addElementToDraw( tempImage, realX, realY, y );
        else 	
        	GUI.getInstance( ).addElementToDraw( tempImage, realX, realY, depth );
        
    }
    
    /**
     * Load the animation resources
     */
    public abstract void loadResources();
    
    /**
     * Changes the animation's current direction
     * @param direction new animation's direction
     */
    protected abstract void setCurrentDirection( int direction );
    
    protected abstract int getCurrentDirection();
    
    protected abstract float getVelocityX();
    
    protected abstract float getVelocityY();
}
