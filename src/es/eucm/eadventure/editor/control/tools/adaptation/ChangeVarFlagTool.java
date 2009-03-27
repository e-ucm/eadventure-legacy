package es.eucm.eadventure.editor.control.tools.adaptation;

import es.eucm.eadventure.common.data.adaptation.AdaptationProfile;
import es.eucm.eadventure.common.data.adaptation.AdaptedState;
import es.eucm.eadventure.common.data.assessment.AssessmentProfile;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class ChangeVarFlagTool extends Tool{

    
    private int index;
    
    private String name;
    
    private String oldName;
    
    private AdaptedState adapState;
    
    public ChangeVarFlagTool(AdaptedState adapState,int index,String name){
	this.adapState = adapState;
	this.index =  index;
	this.name = name;
	this.oldName = adapState.getFlagVar(index);
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
	adapState.change(index, name);
	//Controller.getInstance().updatePanel();
	return true;
    }

    @Override
    public boolean redoTool() {
	adapState.change(index, name);
	Controller.getInstance().updatePanel();
	return true;
    }

    @Override
    public boolean undoTool() {
	adapState.change(index, oldName);
	Controller.getInstance().updatePanel();
	return true;
    }

}
