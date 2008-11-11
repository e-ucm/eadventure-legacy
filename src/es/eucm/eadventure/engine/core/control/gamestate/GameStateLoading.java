package es.eucm.eadventure.engine.core.control.gamestate;

import java.awt.Graphics2D;

import es.eucm.eadventure.engine.core.gui.GUI;

/**
 * A game main loop while a scene is being loaded
 */
public class GameStateLoading extends GameState {

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.control.gamestate.GameState#EvilLoop(long, int)
     */
    public void mainLoop( long elapsedTime, int fps ) {
        Graphics2D g = GUI.getInstance( ).getGraphics( );
        g.clearRect( 0, 0, GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT );
        GUI.drawString( g, "Please wait", 400, 280 );
        GUI.drawString( g, "Loading ...", 400, 300 );
        GUI.getInstance( ).endDraw( );
        g.dispose( );
    }
}
