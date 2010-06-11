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
