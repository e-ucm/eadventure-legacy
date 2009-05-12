package es.eucm.eadventure.editor.control.tools.adaptation;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.data.adaptation.AdaptationProfile;
import es.eucm.eadventure.common.data.adaptation.AdaptedState;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationProfileDataControl;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationRuleDataControl;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentProfileDataControl;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentRuleDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class AddRuleTool extends Tool{

	protected DataControl dataControl;
	
	protected List<AdaptationRuleDataControl> oldAdapRules;
	protected List<AssessmentRuleDataControl> oldAssessRules;
	
	protected int type;
	
	public AddRuleTool (DataControl dataControl,int type){
		this.dataControl = dataControl;
		this.type = type;
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
		if( dataControl.canAddElement( type )) {
			String defaultId = dataControl.getDefaultId(type);
			String id = defaultId;
			int count = 0;
			if (type == Controller.ADAPTATION_RULE)
			    oldAdapRules = new ArrayList(((AdaptationProfileDataControl)dataControl).getDataControls());
			if (type == Controller.ASSESSMENT_RULE)
			    oldAssessRules =new ArrayList( ((AssessmentProfileDataControl)dataControl).getDataControls());
			
			while (!Controller.getInstance().isElementIdValid( id, false )) {
				count++;
				id = defaultId + count;
			}
			return dataControl.addElement( type, id );
			    
			
		}
		return false;
	}

	@Override
	public boolean redoTool() {
	    boolean result = false;
		result = doTool();
		Controller.getInstance().updatePanel();
		return result ;
	}

	@Override
	public boolean undoTool() {
	    if (type == Controller.ADAPTATION_RULE){
		((AdaptationProfileDataControl)dataControl).setDataControls(oldAdapRules);
		Controller.getInstance().updatePanel();
		return true;
	    }
		else if (type == Controller.ASSESSMENT_RULE){
		    ((AssessmentProfileDataControl)dataControl).setDataControls(oldAssessRules);
		    Controller.getInstance().updatePanel();
		    return true;
		}else 
		    return false;
		    
		
	}

}
