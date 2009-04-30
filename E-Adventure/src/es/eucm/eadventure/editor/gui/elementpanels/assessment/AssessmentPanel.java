package es.eucm.eadventure.editor.gui.elementpanels.assessment;

import javax.swing.JComponent;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentProfileDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.ElementPanel;
import es.eucm.eadventure.editor.gui.elementpanels.PanelTab;

public class AssessmentPanel extends ElementPanel {

	private static final long serialVersionUID = 1L;

	public AssessmentPanel(AssessmentProfileDataControl dataControl) {
		addTab(new AssessmentEditionPanelTab(dataControl));
	}
	
	private class AssessmentEditionPanelTab extends PanelTab {
		private AssessmentProfileDataControl sDataControl;
		
		public AssessmentEditionPanelTab(AssessmentProfileDataControl sDataControl) {
			super(TextConstants.getText( "AssessmentProfile.Title" ), sDataControl);
			this.sDataControl = sDataControl;
		}

		@Override
		protected JComponent getTabComponent() {
			return new AssessmentEditionPanel(sDataControl);
		}
	}
}
