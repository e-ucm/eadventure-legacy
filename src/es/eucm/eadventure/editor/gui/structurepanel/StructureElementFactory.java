package es.eucm.eadventure.editor.gui.structurepanel;

import javax.swing.ImageIcon;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationProfilesDataControl;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentProfilesDataControl;
import es.eucm.eadventure.editor.control.controllers.cutscene.CutsceneDataControl;
import es.eucm.eadventure.editor.control.controllers.globalstate.GlobalStateListDataControl;
import es.eucm.eadventure.editor.control.controllers.macro.MacroListDataControl;
import es.eucm.eadventure.editor.control.controllers.timer.TimersListDataControl;

public class StructureElementFactory {

	public static StructureElement getStructureElement(DataControl dataControl, StructureListElement parent) {
		if (dataControl instanceof TimersListDataControl) {
			StructureElement temp = new StructureElement(dataControl, parent);
			temp.setName(TextConstants.getText("TimersList.Title"));
			temp.setIcon(new ImageIcon( "img/icons/advanced.png" ));
			return temp;
		}
		if (dataControl instanceof AdaptationProfilesDataControl) {
			StructureElement temp = new StructureElement(dataControl, parent);
			temp.setName(TextConstants.getText("AdaptationProfiles.Title"));
			temp.setIcon(new ImageIcon( "img/icons/adaptationProfiles.png" ));
			return temp;
		}
		if (dataControl instanceof AssessmentProfilesDataControl) {
			StructureElement temp = new StructureElement(dataControl, parent);
			temp.setName(TextConstants.getText("AssessmentProfiles.Title"));
			temp.setIcon(new ImageIcon( "img/icons/assessmentProfiles.png" ));
			return temp;
		}
		if (dataControl instanceof GlobalStateListDataControl) {
			StructureElement temp = new StructureElement(dataControl, parent);
			temp.setName(TextConstants.getText("GlobalStatesList.Title"));
			temp.setIcon(new ImageIcon( "img/icons/groups16.png" ));
			return temp;
		}
		if (dataControl instanceof MacroListDataControl) {
			StructureElement temp = new StructureElement(dataControl, parent);
			temp.setName(TextConstants.getText("MacrosList.Title"));
			temp.setIcon(new ImageIcon( "img/icons/macros.png" ));
			return temp;
		}
		if (dataControl instanceof CutsceneDataControl) {
			StructureElement temp = new StructureElement(dataControl, parent);
			if (((CutsceneDataControl) dataControl).getType() == Controller.CUTSCENE_SLIDES)
				temp.setIcon(new ImageIcon( "img/icons/slidescene.png"));
			if (((CutsceneDataControl) dataControl).getType() == Controller.CUTSCENE_VIDEO)
				temp.setIcon(new ImageIcon( "img/icons/videoscene.png"));
			return temp;
		}
		return new StructureElement(dataControl, parent);
	}
	
}
