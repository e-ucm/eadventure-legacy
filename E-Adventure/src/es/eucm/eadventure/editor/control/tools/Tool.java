package es.eucm.eadventure.editor.control.tools;

public interface Tool {
	
	/**
	 * Returns the tool name
	 * @return the tool name
	 */
	public String getToolName();
	
	/**
	 * Do the actual work. Returns true if it could be
	 * done, false in other case.
	 * 
	 * @return True if the tool was applied correctly
	 */
	public boolean doTool();

	/**
	 * Returns true if the tool can be undone
	 * @return True if the tool can be undone
	 */
	public boolean canUndo();
	
	/**
	 * Undo the work done by the tool. Returns true if
	 * it could be undone, false in other case.
	 * 
	 * @return True if the tool was undone correctly
	 */
	public boolean undoTool();
	
	/**
	 * Returns true if the tool can be redone
	 * 
	 * @return True if the tool can be redone
	 */
	public boolean canRedo();
	
	/**
	 * Re-do the work done by the tool before it was undone.
	 * 
	 * @return True if the tool was re-done correctly
	 */
	public boolean redoTool();
	
}
