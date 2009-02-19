package es.eucm.eadventure.editor.control.tools.general;

import es.eucm.eadventure.common.data.Named;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class ChangeNameTool extends Tool {

	private Named named;
	
	private String name;
	
	private String oldName;
	
	private Controller controller;
	
	public ChangeNameTool(Named scene, String name) {
		this.named = scene;
		this.name = name;
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
	public boolean doTool() {
		if( !name.equals( named.getName( ) ) ) {
			oldName = named.getName();
			named.setName( name );
			return true;
		}
		return false;
	}

	@Override
	public String getToolName() {
		return "Change name";
	}

	@Override
	public boolean redoTool() {
		named.setName( name );
		controller.updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		named.setName( oldName );
		controller.updatePanel();
		return true;
	}
	
	@Override
	public boolean combine(Tool other) {
		if (other instanceof ChangeNameTool) {
			ChangeNameTool cnt = (ChangeNameTool) other;
			if (cnt.named == named && cnt.oldName == name) {
				name = cnt.name;
				timeStamp = cnt.timeStamp;
				return true;
			}
		}
		return false;
	}
}
