package es.eucm.eadventure.editor.control.tools.adaptation;

import es.eucm.eadventure.common.data.adaptation.AdaptationProfile;
import es.eucm.eadventure.common.data.adaptation.AdaptationRule;
import es.eucm.eadventure.common.data.adaptation.UOLProperty;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class AddUOLPropertyTool extends Tool{

	protected UOLProperty propertyAdded;
	
	protected AdaptationRule parent;
	
	protected int index;
	
	public AddUOLPropertyTool (AdaptationRule parent, int index){
		this.parent = parent;
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
		propertyAdded = new UOLProperty("PropertyId", "PropertyValue",AdaptationProfile.EQUALS);
		parent.getUOLProperties( ).add( index,  propertyAdded);
		return true;
	}

	@Override
	public boolean redoTool() {
		parent.getUOLProperties().add(index, propertyAdded);
		Controller.getInstance().updatePanel();
		return true;
	}

	@Override
	public boolean undoTool() {
		parent.getUOLProperties().remove(index);
		Controller.getInstance().updatePanel();
		return true;
	}

}
