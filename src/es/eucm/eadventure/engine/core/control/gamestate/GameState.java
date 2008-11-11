package es.eucm.eadventure.engine.core.control.gamestate;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import es.eucm.eadventure.engine.core.control.Game;

/**
 * A state of the game main loop
 */
public abstract class GameState {

    /**
     * Instance of game
     */
    protected Game game;

    /**
     * Creates a new GameState
     */
    public GameState( ) {
        this.game = Game.getInstance( );
    }

    /**
     * Perform an interation in the loop
     * @param elapsedTime the elapsed time from the last iteration
     * @param fps current frames per second
     */
    public abstract void mainLoop( long elapsedTime, int fps );

    /**
     * Notifies that the user has clicked the screen
     * @param e The mouse event
     */
    public void mouseClicked( MouseEvent e ) { }
    
    /**
     * Notifies that the user has pressed a mouse button on the screen
     * @param e The mouse event
     */
    public void mousePressed( MouseEvent e ) { }
    
    /**
     * Notifies that the user has released a mouse button on the screen
     * @param e The mouse event
     */
    public void mouseReleased( MouseEvent e ) { }
    
    /**
     * Notifies that the user is dragging the mouse on the screen
     * @param e The mouse event
     */
    public void mouseDragged( MouseEvent e ) { }

    /**
     * Notify that the user has moved the mouse
     * @param e The mouse event
     */
    public void mouseMoved( MouseEvent e ) { }
    
    /**
     * Notify that the user has pressed a key
     * @param e The key event
     */
    public void keyPressed( KeyEvent e ) { }

}
