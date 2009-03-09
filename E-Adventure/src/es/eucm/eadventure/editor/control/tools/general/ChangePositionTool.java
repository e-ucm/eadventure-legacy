package es.eucm.eadventure.editor.control.tools.general;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.Positioned;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class ChangePositionTool extends Tool{

	/**
	 * Old position X (for undo/redo)
	 */
	protected int oldX;
	
	/**
	 * Old position Y (for undo/redo)
	 */
	protected int oldY;
	
	/**
	 * New position X
	 */
	protected int x;
	
	/**
	 * New position Y 
	 */
	protected int y;
	
	/**
	 * The data which contains the (x,y) position
	 */
	protected Positioned data;
	
	/**
	 * Listeners. Useful to notify updates
	 */
	protected List<ChangePositionToolListener> listeners;
	/**
	 * Constructor
	 * @param nextScene
	 * @param newX
	 * @param newY
	 */
	public ChangePositionTool(Positioned data, int newX, int newY){
		this.oldX = data.getPositionX();
		this.oldY = data.getPositionY();
		this.x = newX;
		this.y = newY;
		this.data = data;
		listeners= new ArrayList<ChangePositionToolListener>(); 
	}
	
	@Override
	public boolean canRedo() {
		return true;
	}

	@Override
	public boolean canUndo() {
		return true;
	}

	@Override
	public boolean combine(Tool other) {
		if (other instanceof ChangePositionTool) {
			ChangePositionTool cpt = (ChangePositionTool) other;
			if (cpt.data == data) {
				x = cpt.x;
				y = cpt.y;
				timeStamp = cpt.timeStamp;
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean doTool() {
		boolean done = false;
		// If the values are different
		if( x != data.getPositionX( ) || y != data.getPositionY( ) ) {
			// Set the new destiny position and modify the data
			data.setPositionX( x );
			data.setPositionY( y );
			done = true;
		}		
		return done;
	}

	@Override
	public boolean redoTool() {
		return undoTool();
	}

	@Override
	public boolean undoTool() {
		data.setPositionX(oldX);
		data.setPositionY(oldY);
		int tempX = oldX;
		int tempY = oldY;
		oldX = x;
		oldY = y;
		x= tempX;
		y= tempY;
		// Notify listeners
		for (ChangePositionToolListener l: listeners){
			l.positionUpdated(oldX, oldY);
		}
		Controller.getInstance().updatePanel();
		return true;
	}

	public void addListener(ChangePositionToolListener listener){
	}
	
	/**
	 * Listener to notify changes in the tool
	 * @author Javier
	 *
	 */
	public interface ChangePositionToolListener {
		public void positionUpdated(int newX, int newY);
	}
}
