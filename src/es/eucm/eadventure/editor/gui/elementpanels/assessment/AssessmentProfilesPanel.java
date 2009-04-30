package es.eucm.eadventure.editor.gui.elementpanels.assessment;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentProfileDataControl;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentProfilesDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.ElementPanel;
import es.eucm.eadventure.editor.gui.elementpanels.PanelTab;
import es.eucm.eadventure.editor.gui.elementpanels.general.ResizeableListPanel;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.ResizeableCellRenderer;

public class AssessmentProfilesPanel extends ElementPanel {
	/**
	 * Required
	 */
	private static final long serialVersionUID = 6602692300239491332L;

	public AssessmentProfilesPanel (AssessmentProfilesDataControl dataControl){
		addTab(new AssessmentProfilesPanelTab(dataControl));
	}

	private class AssessmentProfilesPanelTab extends PanelTab {
		private AssessmentProfilesDataControl sDataControl;
		
		public AssessmentProfilesPanelTab(AssessmentProfilesDataControl sDataControl) {
			super(TextConstants.getText( "AssessmentProfiles.Title" ), sDataControl);
			this.sDataControl = sDataControl;
		}

		@Override
		protected JComponent getTabComponent() {
			JPanel booksListPanel = new JPanel();
			booksListPanel.setLayout( new BorderLayout( ) );
			List<DataControl> dataControlList = new ArrayList<DataControl>();
			for (AssessmentProfileDataControl item : sDataControl.getProfiles()) {
				dataControlList.add(item);
			}
			ResizeableCellRenderer renderer = new AssessmentProfileCellRenderer();
			booksListPanel.add(new ResizeableListPanel(dataControlList, renderer, "AssessmentProfileListPanel"), BorderLayout.CENTER);
			return booksListPanel;
		}
	}

}
