package es.eucm.eadventure.editor.control.controllers;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

/**
 * Controller that manages a double list of tools for undo/redo
 * @author Javier
 *
 */
public class ToolManager {
	private List<Tool> undoList;

	private List<Tool> redoList;
	
	private Tool lastUndo = null;
	
	private boolean notifyController;
	
	/**
	 * Void and private constructor.
	 */
	public ToolManager( boolean notifyController ) {
		undoList = new ArrayList<Tool>();
		redoList = new ArrayList<Tool>();
		this.notifyController = notifyController;
	}
	
	public boolean addTool(Tool tool) {
		boolean done = tool.doTool();
		if (done) {
			if (undoList.size() == 0)
				undoList.add(tool);
			else {
				Tool last = undoList.get(undoList.size() - 1);
				if (last.getTimeStamp() < tool.getTimeStamp() - 1500 || !last.combine(tool)) 
					undoList.add(tool);
			}
			redoList.clear();
			if(notifyController)
				Controller.getInstance().dataModified();
			
			if (!tool.canUndo()) {
				undoList.clear();
			}
		}
		return done;
	}
	
	public boolean undoTool() {
		if (undoList.size() > 0) {
			Tool temp = undoList.remove(undoList.size() - 1);
			boolean undone = temp.undoTool();
			if (undone) {
				lastUndo = temp;
				if (temp.canRedo())
					redoList.add(temp);
				else
					redoList.clear();
				return true;
			}
		}
		return false;
	}
	
	public boolean redoTool() {
		if (redoList.size() > 0) {
			Tool temp = redoList.get(redoList.size() - 1);
			redoList.remove(temp);
			boolean done = temp.redoTool();
			if (done) {
				undoList.add(temp);
				if (!temp.canUndo()) {
					undoList.clear();
				}
			}
			return done;
		}
		return false;
	}
}
