package es.eucm.eadventure.editor.control.tools.general.assets;

import es.eucm.eadventure.common.data.adventure.AdventureData;
import es.eucm.eadventure.common.data.adventure.CustomButton;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class DeleteButtonTool extends Tool{

	private AdventureData adventureData;
	
	private CustomButton cursorDeleted;
	
	private String action;
	
	private String type;
	
	private int index;
	
	public DeleteButtonTool (AdventureData adventureData, String action, String type){
		this.adventureData = adventureData;
		this.action = action;
		this.type = type;
		this.setGlobal(true);
	}
	
	@Override
	public boolean canRedo() {
		return true;
	}

	@Override
	public boolean canUndo() {
		return cursorDeleted!=null;
	}

	@Override
	public boolean combine(Tool other) {
		return false;
	}

	@Override
	public boolean doTool() {
		boolean deleted = false;
		CustomButton button = new CustomButton(action, type, null);
		for (int i=0; i<adventureData.getButtons().size(); i++) {
			CustomButton cb = adventureData.getButtons().get(i);
			if (cb.equals(button)){
				cursorDeleted = adventureData.getButtons().remove(i);
				index = i;
				deleted = true;
				break;
			}
		}
		return deleted;
	}

	@Override
	public boolean redoTool() {
		adventureData.getButtons().remove( index );
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		adventureData.getButtons().add( index, cursorDeleted );
		Controller.getInstance().updatePanel();
		return true;
	}

}
