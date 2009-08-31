/**
 * <e-Adventure> is an <e-UCM> research project.
 * <e-UCM>, Department of Software Engineering and Artificial Intelligence.
 * Faculty of Informatics, Complutense University of Madrid (Spain).
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J.
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009
 * Web-site: http://e-adventure.e-ucm.es
 */

/*
    Copyright (C) 2004-2009 <e-UCM> research group

    This file is part of <e-Adventure> project, an educational game & game-like 
    simulation authoring tool, availabe at http://e-adventure.e-ucm.es. 

    <e-Adventure> is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    <e-Adventure> is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with <e-Adventure>; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

*/
package es.eucm.eadventure.engine.core.gui.hud;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
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
     * Processes KeyEvents for the HUD. Its main purpose is to support the use of Esc 
     * for canceling an action
     * @param e Key Event
     * @return True if any changes made. False otherwise
     */
    public abstract boolean keyTyped ( KeyEvent e );
    
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
	
	public abstract boolean mouseDragged(MouseEvent e);

}
