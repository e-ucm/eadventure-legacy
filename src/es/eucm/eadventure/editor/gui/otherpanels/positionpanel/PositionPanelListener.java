package es.eucm.eadventure.editor.gui.otherpanels.positionpanel;

/**
 * Listener interface for the panels that want a position panel on them. This interface notifies them when changes has
 * been made in the position of the panels.
 * 
 * @author Bruno Torijano Bueno
 */
public interface PositionPanelListener {

	/**
	 * Called when the position panel updates the position.
	 * 
	 * @param x
	 *            New value for X coordinate
	 * @param y
	 *            New value for Y coordinate
	 */
	public void updatePositionValues( int x, int y );

}
