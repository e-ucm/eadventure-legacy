/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *         research group.
 *  
 *   Copyright 2005-2010 <e-UCM> research group.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *         http://e-adventure.e-ucm.es/contributors
 * 
 *   <e-UCM> is a research group of the Department of Software Engineering
 *         and Artificial Intelligence at the Complutense University of Madrid
 *         (School of Computer Science).
 * 
 *         C Profesor Jose Garcia Santesmases sn,
 *         28040 Madrid (Madrid), Spain.
 * 
 *         For more info please visit:  <http://e-adventure.e-ucm.es> or
 *         <http://www.e-ucm.es>
 * 
 * ****************************************************************************
 * 
 * This file is part of <e-Adventure>, version 1.2.
 * 
 *     <e-Adventure> is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     <e-Adventure> is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 * 
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.engine.core.gui.hud;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.gui.GUI;

/**
 * This abstract class takes care about the actions buttons of the game and the
 * text to show the current action and element selected.
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
     * 
     * @return Width of the playable area
     */
    public abstract int getGameAreaWidth( );

    /**
     * Returns the height of the playable area of the screen
     * 
     * @return Height of the playable area
     */
    public abstract int getGameAreaHeight( );

    /**
     * Returns the X point of the response block text
     * 
     * @return X point of the response block text
     */
    public abstract int getResponseTextX( );

    /**
     * Returns the Y point of the response block text
     * 
     * @return Y point of the response block text
     */
    public abstract int getResponseTextY( );

    /**
     * Returns the number of lines of the response text block
     * 
     * @return Number of response lines
     */
    public abstract int getResponseTextNumberLines( );

    /**
     * Tells the HUD that there is a change in the action selected
     */
    public abstract void newActionSelected( );

    /**
     * There has been a mouse moved in the HUD in that coordinates
     * 
     * @param e
     *            Mouse event
     * @return boolean If the move is in the HUD
     */
    public abstract boolean mouseMoved( MouseEvent e );

    /**
     * There has been a click in the HUD in that coordinates
     * 
     * @param e
     *            Mouse event
     * @return boolean If the click is in the HUD
     */
    public abstract boolean mouseClicked( MouseEvent e );

    /**
     * Processes KeyEvents for the HUD. Its main purpose is to support the use
     * of Esc for canceling an action
     * 
     * @param e
     *            Key Event
     * @return True if any changes made. False otherwise
     */
    public abstract boolean keyTyped( KeyEvent e );

    /**
     * Draw the HUD with the action button, action and element selected
     * 
     * @param g
     *            Graphics2D where will be drawn
     */
    public abstract void draw( Graphics2D g );

    /**
     * Updates the HUD representation
     * 
     * @param elapsedTime
     *            Elapsed time since last update
     */
    public abstract void update( long elapsedTime );

    /**
     * Toggle the HUD on or off
     * 
     * @param show
     *            If the Hud is shown or not
     */
    public void toggleHud( boolean show ) {

        showHud = show;
    }

    public abstract boolean mouseReleased( MouseEvent e );

    public abstract boolean mousePressed( MouseEvent e );

    public abstract boolean mouseDragged( MouseEvent e );

    public void setLastMouseMove( MouseEvent e ) {

    }

}
