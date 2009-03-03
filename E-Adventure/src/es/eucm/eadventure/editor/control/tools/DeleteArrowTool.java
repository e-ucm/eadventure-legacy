package es.eucm.eadventure.editor.control.tools;

import es.eucm.eadventure.common.data.adventure.AdventureData;
import es.eucm.eadventure.common.data.adventure.CustomArrow;
import es.eucm.eadventure.editor.control.Controller;

public class DeleteArrowTool extends Tool{

	private AdventureData adventureData;
	
	private CustomArrow arrowDeleted;
	
	private int index;
	
	public DeleteArrowTool (AdventureData adventureData, int index){
		this.adventureData = adventureData;
		this.index = index;
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
		arrowDeleted = adventureData.getArrows().remove( index );
		return true;
	}

	@Override
	public boolean redoTool() {
		arrowDeleted = adventureData.getArrows().remove( index );
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		adventureData.getArrows().add( index, arrowDeleted );
		Controller.getInstance().updatePanel();
		return true;
	}

}
