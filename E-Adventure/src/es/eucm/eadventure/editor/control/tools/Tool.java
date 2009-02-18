package es.eucm.eadventure.editor.control.tools;

public abstract class Tool {
	
	protected long timeStamp = System.currentTimeMillis();
	
	public long getTimeStamp() {
		return timeStamp;
	}
	
	/**
	 * Returns the tool name
	 * @return the tool name
	 */
	public abstract String getToolName();
	
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
	
	public abstract boolean combine(Tool other);
	
}
