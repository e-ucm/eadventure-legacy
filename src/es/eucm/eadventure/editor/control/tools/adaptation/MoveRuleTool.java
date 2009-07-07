package es.eucm.eadventure.editor.control.tools.adaptation;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationProfileDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class MoveRuleTool extends Tool {

    
    public static final int MODE_UP = 0;
    public static final int MODE_DOWN = 1;
    
    private DataControl dataControl;
    
    private int mode;
    
    private AdaptationProfileDataControl adaptDataControl;
    
    public MoveRuleTool(AdaptationProfileDataControl adaptDataControl,DataControl dataControl,int mode){
	this.adaptDataControl = adaptDataControl;
	this.dataControl = dataControl;
	this.mode = mode;
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
		if (mode == MODE_UP)
		    return adaptDataControl.moveElementUp(dataControl);
		else if (mode == MODE_DOWN)
		    return adaptDataControl.moveElementDown(dataControl);
		else 
		    return false;
	}


	@Override
	public boolean redoTool() {
		boolean done = false;
		if (mode == MODE_UP)
		    done = adaptDataControl.moveElementUp(dataControl);
		else if (mode == MODE_DOWN)
		    done = adaptDataControl.moveElementDown(dataControl);
		
		if (done)
			Controller.getInstance().updatePanel();
		return done;
	}

	@Override
	public boolean undoTool() {
		boolean done = false;
		if (mode == MODE_UP){
		    done = adaptDataControl.moveElementDown(dataControl);
		}else if (mode == MODE_DOWN){
		    done = adaptDataControl.moveElementUp(dataControl);

		}
		
		if (done)
			Controller.getInstance().updatePanel();
		return done;

	}

	@Override
	public boolean combine(Tool other) {
	    return false;
	}
}
