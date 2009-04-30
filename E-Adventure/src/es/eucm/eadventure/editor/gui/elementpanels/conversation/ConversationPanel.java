package es.eucm.eadventure.editor.gui.elementpanels.conversation;

import javax.swing.JComponent;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.conversation.ConversationDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.ElementPanel;
import es.eucm.eadventure.editor.gui.elementpanels.PanelTab;

/**
 * This class centralizes all the operations for conversation structures and nodes. It has two panels, a panel to
 * represent the conversation graphically (RepresentationPanel), and a panel to display and edit the content of nodes
 * (LinesPanel). It also has a status bar which informs the user of the status of the application
 */
public class ConversationPanel extends ElementPanel {

	/**
	 * Required
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 * 
	 * @param conversationDataControl
	 *            Controller of the conversation
	 */
	public ConversationPanel( ConversationDataControl conversationDataControl ) {
		addTab(new ConversationEditionPanelTab(conversationDataControl));
	}

	private class ConversationEditionPanelTab extends PanelTab {
		private ConversationDataControl sDataControl;
		
		public ConversationEditionPanelTab(ConversationDataControl sDataControl) {
			super(TextConstants.getText( "Conversation.Title" ), sDataControl);
			this.sDataControl = sDataControl;
		}

		@Override
		protected JComponent getTabComponent() {
			return new ConversationEditionPanel(sDataControl);
		}
	}
}
