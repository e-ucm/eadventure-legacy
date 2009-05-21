package es.eucm.eadventure.editor.control.tools.adaptation;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationProfileDataControl;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationRuleDataControl;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentProfileDataControl;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentRuleDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class DeleteRuleTool extends Tool{

	protected DataControl dataControl;
	
	protected List<AdaptationRuleDataControl> oldAdapRules;
	protected List<AssessmentRuleDataControl> oldAssessRules;
	
	protected int type;
	
	protected int index;
	
	public DeleteRuleTool (DataControl dataControl,int type, int index){
		this.dataControl = dataControl;
		this.type = type;
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
		boolean added=false;
		//Check there is at least one flag
		if( dataControl.canBeDeleted( )) {
			
		    
			if (type == Controller.ADAPTATION_RULE){
			    oldAdapRules = new ArrayList(((AdaptationProfileDataControl)dataControl).getDataControls());
			    return dataControl.deleteElement(((AdaptationProfileDataControl)dataControl).getAdaptationRules().get(index), true);
			}if (type == Controller.ASSESSMENT_RULE||type == Controller.TIMED_ASSESSMENT_RULE){
			    oldAssessRules =new ArrayList( ((AssessmentProfileDataControl)dataControl).getDataControls());
			    return dataControl.deleteElement(((AssessmentProfileDataControl)dataControl).getAssessmentRules().get(index), true);
			}
			
			    
			
		}
		return false;
	}

	@Override
	public boolean redoTool() {
	    boolean added=false;
		//Check there is at least one flag
		if( dataControl.canBeDeleted( )) {			
		    if (type == Controller.ADAPTATION_RULE){
		    if (dataControl.deleteElement(((AdaptationProfileDataControl)dataControl).getAdaptationRules().get(index), true)){
			 Controller.getInstance().updatePanel();
			return true;
		    }else {
			return false;
		    }
		    }
		    
		    if (type == Controller.ASSESSMENT_RULE){
			    if (dataControl.deleteElement(((AssessmentProfileDataControl)dataControl).getAssessmentRules().get(index), true)){
				 Controller.getInstance().updatePanel();
				return true;
			    }else {
				return false;
			    }
			    }
		     

		}
		return false;
	}

	@Override
	public boolean undoTool() {
	    if (type == Controller.ADAPTATION_RULE){
		((AdaptationProfileDataControl)dataControl).setDataControlsAndData(new ArrayList(oldAdapRules));
		Controller.getInstance().getIdentifierSummary().addAdaptationRuleId(oldAdapRules.get(index).getId());
		Controller.getInstance().updatePanel();
		return true;
	    }
		else if (type == Controller.ASSESSMENT_RULE||type == Controller.TIMED_ASSESSMENT_RULE){
		    ((AssessmentProfileDataControl)dataControl).setDataControlsAndData(new ArrayList(oldAssessRules));
		    Controller.getInstance().getIdentifierSummary().addAssessmentRuleId(oldAssessRules.get(index).getId());
		    Controller.getInstance().updatePanel();
		    return true;
		}else 
		    return false;
		    
		
	}

}
