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
