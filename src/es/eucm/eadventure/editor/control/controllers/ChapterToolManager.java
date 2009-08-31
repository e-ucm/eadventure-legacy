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
package es.eucm.eadventure.editor.control.controllers;

import java.util.Stack;

import es.eucm.eadventure.editor.control.tools.Tool;

public class ChapterToolManager {

	/**
	 * Global tool manager. For undo/redo in main window (real changes in the data structure)
	 */
	private ToolManager globalToolManager;
	
	/**
	 * Local tool managers. For undo/redo in dialogs (This will only reflect temporal changes, not real changes in data)
	 */
	private Stack<ToolManager> localToolManagers;
	
	
	public ChapterToolManager (){
		globalToolManager = new ToolManager(true);
		localToolManagers = new Stack<ToolManager>();
	}
	
	public void clear(){
		globalToolManager.clear();
		localToolManagers.clear();
	}
	
	// METHODS TO MANAGE UNDO/REDO
	
	public boolean addTool(Tool tool) {
		return addTool ( true, tool );
	}
	
	public boolean addTool( boolean execute, Tool tool ) {
		if (localToolManagers.isEmpty()){
			boolean added = globalToolManager.addTool(execute, tool);
			if (added)
				System.out.println("[ToolManager] Global Tool Manager: Tool \""+ tool.getToolName() + "\" ADDED");
			else
				System.out.println("[ToolManager] Global Tool Manager: Tool \""+ tool.getToolName() + "\" NOT ADDED");
			return added;
		}else{
			boolean added = localToolManagers.peek().addTool(execute, tool);
			if (added)
				System.out.println("[ToolManager] Local Tool Manager: Tool \""+ tool.getToolName() + "\" ADDED");
			else
				System.out.println("[ToolManager] Local Tool Manager: Tool \""+ tool.getToolName() + "\" NOT ADDED");
			return added;
		}
	}


	public void undoTool() {
		if (localToolManagers.isEmpty()){
			globalToolManager.undoTool();
			System.out.println("[ToolManager] Global Tool Manager: Undo Performed");
		}else {
			localToolManagers.peek().undoTool();
			System.out.println("[ToolManager] Local Tool Manager: Undo Performed");
		}
	}

	public void redoTool() {
		if (localToolManagers.isEmpty()){
			globalToolManager.redoTool();
			System.out.println("[ToolManager] Global Tool Manager: Redo Performed");
		}else{
			localToolManagers.peek().redoTool();
			System.out.println("[ToolManager] Local Tool Manager: Redo Performed");
		}
	}
	
	public void pushLocalToolManager(){
		localToolManagers.push(new ToolManager(false));
		System.out.println("[ToolManager] Local Tool Manager PUSHED: Total local tool managers = "+localToolManagers.size());
	}
	
	public void popLocalToolManager(){
		if (!localToolManagers.isEmpty()){
			localToolManagers.pop();
			System.out.println("[ToolManager] Local Tool Manager POPED: Total local tool managers = "+localToolManagers.size());
		} else{
			System.out.println("[ToolManager] Local Tool Manager Could NOT be POPED: Total local tool managers = "+localToolManagers.size());
		}
	}

	
	// ONLY FOR DEBUGGING
	/**
	 * @return the globalToolManager
	 */
	public ToolManager getGlobalToolManager() {
		return globalToolManager;
	}
}
