package es.eucm.eadventure.editor.control.tools.adaptation;

import es.eucm.eadventure.common.data.adaptation.AdaptationProfile;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationProfileDataControl;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentProfileDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;


/**
 * Changes the type of the profile between scorm12, scorm2004 and normal
 *
 */
public class ChangeAdaptationProfileTypeTool extends Tool{

    public static final int NORMAL=0;
    
    public static final int SCORM12=1;
    
    public static final int SCORM2004=2;
    
    private int type;
    
    private AdaptationProfile adapProfile;
    
    private int oldType;
    
    public ChangeAdaptationProfileTypeTool(AdaptationProfile profile, int type,boolean scorm12,boolean scorm2004){
	this.type = type;
	this.adapProfile = profile;
	
	// get the old type, as result of previous values
	// the combination scorm12 and scorm2004 is not possible
	if (!scorm12&&!scorm2004)
	    oldType=NORMAL;
	else if (scorm12&&!scorm2004)
	    oldType=SCORM12;
	else if (!scorm12&&scorm2004)
	    oldType=SCORM2004;
	
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
	if (type==NORMAL)
	    adapProfile.changeToNormalProfile();
	else if (type==SCORM12)
	    adapProfile.changeToScorm12Profile();
	else if (type==SCORM2004)
	    adapProfile.changeToScorm2004Profile();
	Controller.getInstance().updatePanel();
        return true;
    }
    
    @Override
    public boolean redoTool() {
	doTool();
        return true;
    }
    
    @Override
    public boolean undoTool() {
	if (oldType==NORMAL)
	    adapProfile.changeToNormalProfile();
	else if (oldType==SCORM12)
	    adapProfile.changeToScorm12Profile();
	else if (oldType==SCORM2004)
	    adapProfile.changeToScorm2004Profile();
	Controller.getInstance().updatePanel();
        return true;
    }
}
