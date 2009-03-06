package es.eucm.eadventure.editor.control.tools;

import es.eucm.eadventure.common.gui.TextConstants;

public abstract class Tool {
	
	/**
	 * Stores the time when the tool was created
	 */
	protected long timeStamp = System.currentTimeMillis();
	
	protected boolean doesClone = false;
	
	public boolean doesClone(){
		return doesClone;
	}
	
	public void doesClone ( boolean doesClone ){
		this.doesClone = doesClone;
	}
	
	/**
	 * Get the time when the tool was created
	 * @return The time when the tool was created
	 */
	public long getTimeStamp() {
		return timeStamp;
	}
	
	/**
	 * Returns the tool name
	 * @return the tool name
	 */
	public String getToolName(){
		return TextConstants.getEditionToolName(getClass());
	}
	
	/**
	 * Do the actual work. Returns true if it could be
	 * done, false in other case.
	 * 
	 * @return True if the tool was applied correctly
	 */
	public abstract boolean doTool();

	/**
	 * Returns true if the tool can be undone
	 * @return True if the tool can be undone
	 */
	public abstract boolean canUndo();
	
	/**
	 * Undo the work done by the tool. Returns true if
	 * it could be undone, false in other case.
	 * 
	 * @return True if the tool was undone correctly
	 */
	public abstract boolean undoTool();
	
	/**
	 * Returns true if the tool can be redone
	 * 
	 * @return True if the tool can be redone
	 */
	public abstract boolean canRedo();
	
	/**
	 * Re-do the work done by the tool before it was undone.
	 * 
	 * @return True if the tool was re-done correctly
	 */
	public abstract boolean redoTool();
	
	/**
	 * Combines this tool with other similar tool (if possible). Useful for combining simple changes as characters typed in the same field
	 * @param other
	 * @return
	 */
	public abstract boolean combine(Tool other);
	
}
