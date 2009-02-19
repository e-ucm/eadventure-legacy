package es.eucm.eadventure.engine.core.gui.hud;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.gui.GUI;

/**
 * This abstract class takes care about the actions buttons of the game and the text
 * to show the current action and element selected.
 */
public abstract class HUD {
    
    /**
     * Reference to the game main class
     */
    protected Game game;
    
    /**
     * Reference to the GUI of the game
     */
    protected GUI gui;
    
    /**
     * If the Hus is shown
     */
    protected boolean showHud;
    
    /**
     * Function that initializa the HUD class
     */
    public void init( ) {
        game = Game.getInstance( );
        gui = GUI.getInstance( );
        showHud = true;
    }
    
    /**
     * Returns the width of the playable area of the screen
     * @return Width of the playable area
     */
    public abstract int getGameAreaWidth( );
    
    /**
     * Returns the height of the playable area of the screen
     * @return Height of the playable area
     */
    public abstract int getGameAreaHeight( );
    
    /**
     * Returns the X point of the response block text
     * @return X point of the response block text
     */
    public abstract int getResponseTextX( );
    
    /**
     * Returns the Y point of the response block text
     * @return Y point of the response block text
     */
    public abstract int getResponseTextY( );
    
    /**
     * Returns the number of lines of the response text block
     * @return Number of response lines
     */
    public abstract int getResponseTextNumberLines( );
    
    /**
     * Tells the HUD that there is a change in the action selected
     */
    public abstract void newActionSelected( );

    /**
     * There has been a mouse moved in the HUD in that coordinates
     * @param e Mouse event
     * @return boolean If the move is in the HUD
     */
    public abstract boolean mouseMoved( MouseEvent e );

    /**
     * There has been a click in the HUD in that coordinates
     * @param e Mouse event
     * @return boolean If the click is in the HUD
     */
    public abstract boolean mouseClicked( MouseEvent e );
    
    /**
     * Draw the HUD with the action button, action and element selected
     * @param g Graphics2D where will be drawn
     */
    public abstract void draw( Graphics2D g );
    
    /**
     * Updates the HUD representation
     * @param elapsedTime Elapsed time since last update
     */
    public abstract void update( long elapsedTime );
    
    /**
     * Toggle the HUD on or off
     * @param show If the Hud is shown or not
     */
    public void toggleHud(boolean show){
        showHud = show;
    }

	public abstract boolean mouseReleased(MouseEvent e);
	
	public abstract boolean mousePressed(MouseEvent e);

}
