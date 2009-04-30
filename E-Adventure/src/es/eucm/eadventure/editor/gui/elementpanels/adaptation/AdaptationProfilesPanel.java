package es.eucm.eadventure.editor.gui.elementpanels.adaptation;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationProfileDataControl;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationProfilesDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.ElementPanel;
import es.eucm.eadventure.editor.gui.elementpanels.PanelTab;
import es.eucm.eadventure.editor.gui.elementpanels.general.ResizeableListPanel;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.ResizeableCellRenderer;

public class AdaptationProfilesPanel extends ElementPanel {
	/**
	 * Required
	 */
	private static final long serialVersionUID = 6602692300239491332L;

	public AdaptationProfilesPanel (AdaptationProfilesDataControl dataControl){
		addTab(new AdaptationProfilesPanelTab(dataControl));
	}

	private class AdaptationProfilesPanelTab extends PanelTab {
		private AdaptationProfilesDataControl sDataControl;
		
		public AdaptationProfilesPanelTab(AdaptationProfilesDataControl sDataControl) {
			super(TextConstants.getText( "AdaptationProfiles.Title" ), sDataControl);
			this.sDataControl = sDataControl;
		}

		@Override
		protected JComponent getTabComponent() {
			JPanel booksListPanel = new JPanel();
			booksListPanel.setLayout( new BorderLayout( ) );
			List<DataControl> dataControlList = new ArrayList<DataControl>();
			for (AdaptationProfileDataControl item : sDataControl.getProfiles()) {
				dataControlList.add(item);
			}
			ResizeableCellRenderer renderer = new AdaptationProfileCellRenderer();
			booksListPanel.add(new ResizeableListPanel(dataControlList, renderer, "AdaptationProfileListPanel"), BorderLayout.CENTER);
			return booksListPanel;
		}
	}

}
