/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *          research group.
 *   
 *    Copyright 2005-2012 <e-UCM> research group.
 *  
 *     <e-UCM> is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *  
 *          C Profesor Jose Garcia Santesmases sn,
 *          28040 Madrid (Madrid), Spain.
 *  
 *          For more info please visit:  <http://e-adventure.e-ucm.es> or
 *          <http://www.e-ucm.es>
 *  
 *  ****************************************************************************
 * This file is part of <e-Adventure>, version 1.4.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       <e-Adventure> is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      <e-Adventure> is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.engine.core.control.gamestate;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.tracking.pub._GameLog;

/**
 * A state of the game main loop
 */
public abstract class GameState {

    /**
     * Instance of game
     */
    protected Game game;
    
    /**
     * Instance of gameLog for tracking user's interaction. 
     */
    protected _GameLog gameLog;

    /**
     * Creates a new GameState
     */
    public GameState( ) {
        this.game = Game.getInstance( );
        this.gameLog = game.getGameLog( );
    }

    /**
     * Perform an interation in the loop
     * 
     * @param elapsedTime
     *            the elapsed time from the last iteration
     * @param fps
     *            current frames per second
     */
    public abstract void mainLoop( long elapsedTime, int fps );

    /**
     * Notifies that the user has clicked the screen
     * 
     * @param e
     *            The mouse event
     */
    public void mouseClicked( MouseEvent e ) {

    }

    /**
     * Notifies that the user has pressed a mouse button on the screen
     * 
     * @param e
     *            The mouse event
     */
    public void mousePressed( MouseEvent e ) {

    }

    /**
     * Notifies that the user has released a mouse button on the screen
     * 
     * @param e
     *            The mouse event
     */
    public void mouseReleased( MouseEvent e ) {

    }

    /**
     * Notifies that the user is dragging the mouse on the screen
     * 
     * @param e
     *            The mouse event
     */
    public void mouseDragged( MouseEvent e ) {

    }

    /**
     * Notify that the user has moved the mouse
     * 
     * @param e
     *            The mouse event
     */
    public void mouseMoved( MouseEvent e ) {

    }

    /**
     * Notify that the user has pressed a key
     * 
     * @param e
     *            The key event
     */
    public void keyPressed( KeyEvent e ) {

    }

}
