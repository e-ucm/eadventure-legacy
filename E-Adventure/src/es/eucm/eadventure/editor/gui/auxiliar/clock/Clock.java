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
			} catch( InterruptedException e ) {
	        	ReportDialog.GenerateErrorReport(e, true, "UNKNOWERROR");
			}
		}
	}
}
