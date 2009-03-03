package es.eucm.eadventure.editor.control.tools;

import es.eucm.eadventure.common.data.adventure.AdventureData;
import es.eucm.eadventure.common.data.adventure.CustomCursor;
import es.eucm.eadventure.editor.control.Controller;

public class DeleteCursorTool extends Tool{

	private AdventureData adventureData;
	
	private CustomCursor cursorDeleted;
	
	private int index;
	
	public DeleteCursorTool (AdventureData adventureData, int index){
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
		cursorDeleted = adventureData.getCursors().remove( index );
		return true;
	}

	@Override
	public boolean redoTool() {
		cursorDeleted = adventureData.getCursors().remove( index );
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		adventureData.getCursors().add( index, cursorDeleted );
		Controller.getInstance().updatePanel();
		return true;
	}

}
