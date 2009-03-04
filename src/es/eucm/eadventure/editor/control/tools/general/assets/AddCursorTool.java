package es.eucm.eadventure.editor.control.tools.general.assets;

import es.eucm.eadventure.common.data.adventure.AdventureData;
import es.eucm.eadventure.common.data.adventure.CustomCursor;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class AddCursorTool extends Tool{

	private AdventureData adventureData;
	
	private CustomCursor cursor;
	
	public AddCursorTool (AdventureData adventureData, CustomCursor cursor){
		this.adventureData = adventureData;
		this.cursor = cursor;
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
		adventureData.getCursors().add( cursor );
		return true;
	}

	@Override
	public boolean redoTool() {
		adventureData.getCursors().add( cursor );
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		adventureData.getCursors().remove( cursor );
		Controller.getInstance().updatePanel();
		return true;
	}

}
