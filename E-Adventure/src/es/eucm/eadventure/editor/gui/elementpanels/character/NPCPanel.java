package es.eucm.eadventure.editor.gui.elementpanels.character;

import javax.swing.JComponent;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.character.NPCDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.ElementPanel;
import es.eucm.eadventure.editor.gui.elementpanels.PanelTab;
import es.eucm.eadventure.editor.gui.elementpanels.general.ActionsListPanel;

public class NPCPanel extends ElementPanel {

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
	public NPCPanel( NPCDataControl dataControl ) {
		addTab(new LooksPanelTab(dataControl));
		addTab(new DocPanelTab(dataControl));
		addTab(new DialogPanelTab(dataControl));
		addTab(new ActionsPanelTab(dataControl));
	}
	
	private class DocPanelTab extends PanelTab {
		private NPCDataControl dataControl;
		
		public DocPanelTab(NPCDataControl dataControl) {
			super(TextConstants.getText("NPC.DocPanelTitle"));
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
			super(TextConstants.getText("NPC.LookPanelTitle"));
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
			super(TextConstants.getText("NPC.DialogPanelTitle"));
			this.dataControl = dataControl;
		}

		@Override
		protected JComponent getTabComponent() {
			return new NPCDialogPanel(dataControl);
		}
	}

	private class ActionsPanelTab extends PanelTab {
		private NPCDataControl dataControl;

		public ActionsPanelTab(NPCDataControl dataControl) {
			super(TextConstants.getText( "NPCPanel.Actions" ));
			this.dataControl = dataControl;
		}

		@Override
		protected JComponent getTabComponent() {
			return new ActionsListPanel ( dataControl.getActionsList());
		}
	}
}
