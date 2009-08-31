/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author López Mañas, E., Pérez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, available at http://e-adventure.e-ucm.es.
 * 
 * <e-Adventure> is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * <e-Adventure>; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */
package es.eucm.eadventure.engine.adaptation;

import es.eucm.eadventure.engine.core.control.Game;

/**
 * Clock for the adaptation engine.
 */
public class AdaptationClock extends Thread {

    /**
     * Time elapsed for the tick.
     */
    private static final int TICK_TIME = 20000;

    /**
     * Adaptation engine to notify.
     */
    private AdaptationEngine adaptationEngine;

    /**
     * True if the thread is still running.
     */
    private boolean run;

    /**
     * Constructor.
     * 
     * @param adaptationEngine
     *            Dialog to notify
     */
    public AdaptationClock( AdaptationEngine adaptationEngine ) {

        this.adaptationEngine = adaptationEngine;
        run = true;
    }

    /**
     * Stops the clock.
     */
    public void stopClock( ) {

        run = false;
    }

    /*
     * Explanation: The first state is requested in the adaptation engine 
     * while still disconnected. If by the first time that the thread awakes
     * there has been no response from the server, the API will be disconnected
     * and the thread dies.
     * 
     */
    @Override
    public void run( ) {

        while( run ) {
            try {
                sleep( TICK_TIME );
                if( Game.getInstance( ).isConnected( ) ) {
                    adaptationEngine.requestNewState( );
                }
                else {
                    run = false;
                }
            }
            catch( InterruptedException e ) {
                e.printStackTrace( );
            }
        }
    }
}
