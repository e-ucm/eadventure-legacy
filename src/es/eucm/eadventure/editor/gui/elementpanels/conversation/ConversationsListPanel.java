package es.eucm.eadventure.editor.gui.elementpanels.conversation;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.conversation.ConversationDataControl;
import es.eucm.eadventure.editor.control.controllers.conversation.ConversationsListDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.ElementPanel;
import es.eucm.eadventure.editor.gui.elementpanels.PanelTab;
import es.eucm.eadventure.editor.gui.elementpanels.general.ResizeableListPanel;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.ResizeableCellRenderer;

public class ConversationsListPanel extends ElementPanel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 * 
	 * @param conversationsListDataControl
	 *            Books list controller
	 */
	public ConversationsListPanel( ConversationsListDataControl conversationsListDataControl ) {
		addTab(new ConversationsListPanelTab(conversationsListDataControl));
	}

	private class ConversationsListPanelTab extends PanelTab {
		private ConversationsListDataControl sDataControl;
		
		public ConversationsListPanelTab(ConversationsListDataControl sDataControl) {
			super(TextConstants.getText( "ConversationsList.Title" ), sDataControl);
			this.sDataControl = sDataControl;
		}

		@Override
		protected JComponent getTabComponent() {
			JPanel conversationsListPanel = new JPanel();
			conversationsListPanel.setLayout( new BorderLayout( ) );
			List<DataControl> dataControlList = new ArrayList<DataControl>();
			for (ConversationDataControl item : sDataControl.getConversations()) {
				dataControlList.add(item);
			}
			ResizeableCellRenderer renderer = new ConversationCellRenderer();
			conversationsListPanel.add(new ResizeableListPanel(dataControlList, renderer, "ConversationsListPanel"), BorderLayout.CENTER);
			return conversationsListPanel;
		}
	}

}
