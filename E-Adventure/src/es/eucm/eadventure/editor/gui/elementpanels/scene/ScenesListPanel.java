
package es.eucm.eadventure.editor.gui.elementpanels.scene;

import javax.swing.JComponent;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.scene.ScenesListDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.ElementPanel;
import es.eucm.eadventure.editor.gui.elementpanels.PanelTab;
import es.eucm.eadventure.editor.gui.otherpanels.SceneLinksPanel;

public class ScenesListPanel extends ElementPanel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 * 
	 * @param scenesListDataControl
	 *            Scenes list controller
	 */
	public ScenesListPanel( ScenesListDataControl scenesListDataControl ) {
		this.addTab(new SceneListPanelTab(scenesListDataControl));
	}
	
	private class SceneListPanelTab extends PanelTab {
		private ScenesListDataControl scenesListDataControl;
		
		public SceneListPanelTab(ScenesListDataControl scenesListDataControl) {
			super(TextConstants.getText("ScenesList.Title"), scenesListDataControl);
			this.setHelpPath("scenes/Scenes_General.html");
			this.scenesListDataControl = scenesListDataControl;
		}

		@Override
		protected JComponent getTabComponent() {
			return new SceneLinksPanel(scenesListDataControl);
		}
	}

}
