package es.eucm.eadventure.editor.control.tools.general;

import es.eucm.eadventure.common.data.chapter.NextScene;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

/**
 * Abstract Tool class for NextScene edition. Contains common data. Allows undo/redo but not combine.
 * @author Javier
 *
 */
public abstract class NextSceneTool extends Tool{

	/**
	 * Contained next scene structure.
	 */
	protected NextScene nextScene;
	
	
	/**
	 * Main controller
	 */
	protected Controller controller;
	
	/**
	 * Constructor
	 * @param nextScene
	 */
	public NextSceneTool (NextScene nextScene){
		this.nextScene = nextScene;
		controller = Controller.getInstance();
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

}
