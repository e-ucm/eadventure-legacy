
package es.eucm.eadventure.editor.gui.elementpanels.scene;

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
		SceneLinksPanel sceneLinksPanel = new SceneLinksPanel(scenesListDataControl);
		
		PanelTab tab1 = new PanelTab(TextConstants.getText("ScenesList.Title"), sceneLinksPanel);
		
		this.addTab(tab1);
	}

}
