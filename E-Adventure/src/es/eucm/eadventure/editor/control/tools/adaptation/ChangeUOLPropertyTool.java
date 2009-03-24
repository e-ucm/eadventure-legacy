package es.eucm.eadventure.editor.control.tools.adaptation;

import es.eucm.eadventure.common.data.adaptation.AdaptationRule;
import es.eucm.eadventure.common.data.adaptation.UOLProperty;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

/**
 * Tool for changing an adaptation rule (whatever the adaptation rule is)
 * @author Javier
 *
 */
public class ChangeUOLPropertyTool extends Tool{

	public static final int SET_ID=2;
	public static final int SET_VALUE=3;
	public static final int SET_OP = 4;
	
	protected UOLProperty oldProperty;
	protected UOLProperty newProperty;
	protected AdaptationRule parent;
	
	protected int mode;
	
	protected int index;
	
	public ChangeUOLPropertyTool (AdaptationRule parent, String newData, int index, int mode){
		this.mode = mode;
		this.oldProperty = parent.getUOLProperties().get(index);
		this.parent = parent;
		this.index = index;
		
		if (mode == SET_ID){
			newProperty = new UOLProperty(newData, oldProperty.getValue(),oldProperty.getOperation());
		} else if (mode == SET_VALUE){
			newProperty = new UOLProperty(oldProperty.getId(), newData , oldProperty.getOperation());
		}else if (mode == SET_OP){
			newProperty = new UOLProperty(oldProperty.getId(), oldProperty.getValue(), newData );
		}
	}
	
	@Override
	public boolean canRedo() {
		return true;
	}

	@Override
	public boolean canUndo() {
		return mode == SET_ID || mode ==SET_VALUE || mode == SET_OP;
	}

	@Override
	public boolean combine(Tool other) {
		return false;
	}

	@Override
	public boolean doTool() {
		if (mode == SET_ID || mode == SET_VALUE || mode == SET_OP){
			if (index>=0 && index<parent.getUOLProperties().size()){
				parent.getUOLProperties().remove(index);
				parent.getUOLProperties().add(index, newProperty);
			}
			return true;
		} 	
		return false;
	}

	@Override
	public boolean redoTool() {
		if (mode == SET_ID || mode == SET_VALUE){
			parent.getUOLProperties().remove(index);
			parent.getUOLProperties().add(index, newProperty);
			Controller.getInstance().updatePanel();
			return true;
		} 	
		return false;
	}

	@Override
	public boolean undoTool() {
		if (mode == SET_ID || mode == SET_VALUE||mode == SET_OP){
			parent.getUOLProperties().remove(index);
			parent.getUOLProperties().add(index, oldProperty);
			Controller.getInstance().updatePanel();
			return true;
		} 	
		return false;
	}

	
	/**
	 * Constructors. Will change the 
	 * @param oldRule
	 */
}
