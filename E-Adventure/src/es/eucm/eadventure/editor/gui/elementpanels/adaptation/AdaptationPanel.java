package es.eucm.eadventure.editor.gui.elementpanels.adaptation;

import javax.swing.JComponent;

import es.eucm.eadventure.common.gui.TextConstants;

import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationProfileDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.ElementPanel;
import es.eucm.eadventure.editor.gui.elementpanels.PanelTab;

public class AdaptationPanel extends ElementPanel{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public AdaptationPanel(AdaptationProfileDataControl dataControl){
    	addTab(new AdaptationEditionPanelTab(dataControl));
    }
    
	private class AdaptationEditionPanelTab extends PanelTab {
		private AdaptationProfileDataControl sDataControl;
		
		public AdaptationEditionPanelTab(AdaptationProfileDataControl sDataControl) {
			super(TextConstants.getText( "AdaptationProfile.Title" ), sDataControl);
			//this.setHelpPath("assessment/assessmentProfile.html");
			this.sDataControl = sDataControl;
		}

		@Override
		protected JComponent getTabComponent() {
			return new AdaptationEditionPanel(sDataControl);
		}
	}

}
