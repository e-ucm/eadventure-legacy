package es.eucm.eadventure.editor.gui.elementpanels.cutscene;

import javax.swing.JComponent;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.cutscene.CutsceneDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.ElementPanel;
import es.eucm.eadventure.editor.gui.elementpanels.PanelTab;

public class CutscenePanel extends ElementPanel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param cutsceneDataControl
	 *            Cutscene controller
	 */
	public CutscenePanel( CutsceneDataControl cutsceneDataControl ) {
		addTab(new CutsceneAppPanelTab(cutsceneDataControl));
		addTab(new CutsceneEndPanelTab(cutsceneDataControl));
		addTab(new CutsceneDocPanelTab(cutsceneDataControl));
	}
	
	private class CutsceneAppPanelTab extends PanelTab {
		private CutsceneDataControl sDataControl;
		
		public CutsceneAppPanelTab(CutsceneDataControl sDataControl) {
			super(TextConstants.getText( "Cutscene.App" ), sDataControl);
			this.sDataControl = sDataControl;
		}

		@Override
		protected JComponent getTabComponent() {
			return new CutsceneLooksPanel(sDataControl);
		}
	}

	private class CutsceneDocPanelTab extends PanelTab {
		private CutsceneDataControl sDataControl;
		
		public CutsceneDocPanelTab(CutsceneDataControl sDataControl) {
			super(TextConstants.getText( "Cutscene.Doc" ), sDataControl);
			this.sDataControl = sDataControl;
		}

		@Override
		protected JComponent getTabComponent() {
			return new CutsceneDocPanel(sDataControl);
		}
	}

	private class CutsceneEndPanelTab extends PanelTab {
		private CutsceneDataControl sDataControl;
		
		public CutsceneEndPanelTab(CutsceneDataControl sDataControl) {
			super(TextConstants.getText( "Cutscene.CutsceneEnd" ), sDataControl);
			this.sDataControl = sDataControl;
		}

		@Override
		protected JComponent getTabComponent() {
			return new CutsceneEndPanel(sDataControl);
		}
	}

}
