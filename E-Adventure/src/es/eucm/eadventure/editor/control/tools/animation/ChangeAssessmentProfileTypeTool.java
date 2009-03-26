package es.eucm.eadventure.editor.control.tools.animation;

import es.eucm.eadventure.common.data.adaptation.AdaptationProfile;
import es.eucm.eadventure.common.data.assessment.AssessmentProfile;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

public class ChangeAssessmentProfileTypeTool extends Tool{

    public static final int NORMAL=0;
    
    public static final int SCORM12=1;
    
    public static final int SCORM2004=2;
    
    private int type;
    
    private AssessmentProfile assessProfile;
    
    private int oldType;
    
    public ChangeAssessmentProfileTypeTool(AssessmentProfile profile, int type,boolean scorm12,boolean scorm2004){
	this.type = type;
	this.assessProfile = profile;
	
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
	    assessProfile.changeToNormalProfile();
	else if (type==SCORM12)
	    assessProfile.changeToScorm12Profile();
	else if (type==SCORM2004)
	    assessProfile.changeToScorm2004Profile();
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
	    assessProfile.changeToNormalProfile();
	else if (oldType==SCORM12)
	    assessProfile.changeToScorm12Profile();
	else if (oldType==SCORM2004)
	    assessProfile.changeToScorm2004Profile();
	Controller.getInstance().updatePanel();
        return true;
    }
}
