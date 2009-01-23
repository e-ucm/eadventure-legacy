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
