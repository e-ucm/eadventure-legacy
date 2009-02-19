package es.eucm.eadventure.editor.control.tools.general;

import es.eucm.eadventure.common.data.HasTargetId;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

/**
 * Edition Tool for Changing a Target ID. Supports undo, redo but not combine
 * @author Javier
 *
 */
public class ChangeTargetIdTool extends Tool{

	/**
	 * The new id
	 */
	protected String id;
	
	/**
	 * The old id (for undo/redo)
	 */
	protected String oldId;
	
	/**
	 * Tells if the tree must be reloaded after do/undo/redo
	 */
	protected boolean updateTree;
	
	/**
	 * Tells if the panel must be reloaded after do/undo/redo
	 */
	protected boolean reloadPanel;
	
	/**
	 * The main controller
	 */
	protected Controller controller;
	
	/**
	 * The element which contains the targetId
	 */
	protected HasTargetId elementWithTargetId;
	
	/**
	 * Default constructor. Does not update neither tree nor panel
	 * @param elementWithTargetId
	 * @param newId
	 */
	public ChangeTargetIdTool (HasTargetId elementWithTargetId, String newId){
		this (elementWithTargetId, newId, false,true);
	}
	
	public ChangeTargetIdTool (HasTargetId elementWithTargetId, String newId, boolean updateTree, boolean reloadPanel){
		this.elementWithTargetId = elementWithTargetId;
		this.id = newId;
		this.oldId = elementWithTargetId.getTargetId();
		this.updateTree = updateTree;
		this.reloadPanel = reloadPanel;
		this.controller = Controller.getInstance();
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
		return false;
	}

	@Override
	public boolean doTool() {
		boolean done = false;
		if (!elementWithTargetId.getTargetId().equals(id)){
			elementWithTargetId.setTargetId(id);
			done = true;
			if (updateTree)
				controller.updateTree();
			if (reloadPanel)
				controller.updatePanel();
		}
		return done;
	}

	@Override
	public boolean redoTool() {
		return undoTool();
	}

	@Override
	public boolean undoTool() {
		elementWithTargetId.setTargetId(oldId);
		String temp = oldId;
		oldId = id;
		id = temp;
		if (updateTree)
			controller.updateTree();
		if (reloadPanel)
			controller.updatePanel();
		
		return true;
	}
}
