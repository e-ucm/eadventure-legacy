package es.eucm.eadventure.editor.gui.elementpanels.character;

import javax.swing.JComponent;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.character.NPCDataControl;
import es.eucm.eadventure.editor.control.controllers.character.PlayerDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.ElementPanel;
import es.eucm.eadventure.editor.gui.elementpanels.PanelTab;

public class PlayerPanel extends ElementPanel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param playerDataControl
	 *            Player controller
	 */
	public PlayerPanel( PlayerDataControl dataControl ) {
		if(dataControl.buildResourcesTab()) {
			addTab(new LooksPanelTab(dataControl));
		}
		addTab(new DialogPanelTab(dataControl));
		addTab(new DocPanelTab(dataControl));
	}
	
	private class DocPanelTab extends PanelTab {
		private NPCDataControl dataControl;
		
		public DocPanelTab(NPCDataControl dataControl) {
			super(TextConstants.getText("NPC.DocPanelTitle"), dataControl);
			this.dataControl = dataControl;
		}

		@Override
		protected JComponent getTabComponent() {
			return new NPCDocPanel(dataControl);
		}
	}
	
	private class LooksPanelTab extends PanelTab {
		private NPCDataControl dataControl;
		
		public LooksPanelTab(NPCDataControl dataControl) {
			super(TextConstants.getText("NPC.LookPanelTitle"), dataControl);
			this.dataControl = dataControl;
		}

		@Override
		protected JComponent getTabComponent() {
			return new NPCLooksPanel(dataControl);
		}
	}
	
	private class DialogPanelTab extends PanelTab {
		private NPCDataControl dataControl;
		
		public DialogPanelTab(NPCDataControl dataControl) {
			super(TextConstants.getText("NPC.DialogPanelTitle"), dataControl);
			setToolTipText(TextConstants.getText("NPC.DialogPanelTip"));
			this.dataControl = dataControl;
		}

		@Override
		protected JComponent getTabComponent() {
			return new NPCDialogPanel(dataControl);
		}
	}

}
