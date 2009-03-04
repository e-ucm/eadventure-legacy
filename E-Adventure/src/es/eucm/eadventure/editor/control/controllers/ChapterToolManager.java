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
		if (localToolManagers.isEmpty()){
			System.out.println("[ToolManager] Global Tool Manager: Tool ADDED");
			return globalToolManager.addTool(tool);
		}else{
			System.out.println("[ToolManager] Local Tool Manager: Tool ADDED");
			return localToolManagers.peek().addTool(tool);
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
}
