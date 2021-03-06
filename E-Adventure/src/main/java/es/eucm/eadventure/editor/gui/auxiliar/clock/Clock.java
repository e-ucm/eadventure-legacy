/*******************************************************************************
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the e-UCM
 *          research group.
 *   
 *    Copyright 2005-2012 e-UCM research group.
 *  
 *     e-UCM is a research group of the Department of Software Engineering
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
 * This file is part of eAdventure, version 1.5.
 * 
 *   You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with Adventure.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.gui.auxiliar.clock;

import es.eucm.eadventure.common.auxiliar.ReportDialog;

/**
 * Clock for the animations.
 */
public class Clock extends Thread {

    /**
     * Time of tick (the listener is responsible for tracking the total time).
     */
    public static final int TICK_TIME = 100;

    /**
     * Listener to notify.
     */
    private ClockListener clockListener;

    /**
     * True if the thread is still running.
     */
    private boolean run;

    /**
     * Constructor.
     * 
     * @param clockListener
     *            Listener to notify
     */
    public Clock( ClockListener clockListener ) {

        this.clockListener = clockListener;
        run = true;
    }

    /**
     * Stops the clock.
     */
    public void stopClock( ) {

        run = false;
    }

    @Override
    public void run( ) {

        while( run ) {
            try {
                clockListener.update( TICK_TIME );
                sleep( TICK_TIME );
            }
            catch( InterruptedException e ) {
                ReportDialog.GenerateErrorReport( e, true, "UNKNOWERROR" );
            }
        }
    }
}
