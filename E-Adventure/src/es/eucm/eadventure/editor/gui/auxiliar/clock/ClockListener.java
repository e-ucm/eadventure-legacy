package es.eucm.eadventure.editor.gui.auxiliar.clock;

/**
 * Interface for the clock listener.
 * 
 * @author Bruno Torijano Bueno
 */
public interface ClockListener {

	/**
	 * Called everytime the clock gives a tick.
	 * 
	 * @param time
	 *            Time elapsed since last update
	 */
	public void update( long time );

}
