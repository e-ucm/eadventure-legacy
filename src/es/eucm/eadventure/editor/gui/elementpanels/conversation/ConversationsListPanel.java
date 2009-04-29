package es.eucm.eadventure.editor.gui.elementpanels.conversation;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.conversation.ConversationDataControl;
import es.eucm.eadventure.editor.control.controllers.conversation.ConversationsListDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.general.ResizeableListPanel;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.ResizeableCellRenderer;

public class ConversationsListPanel extends JPanel {

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
		setLayout( new BorderLayout( ) );
		setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "ConversationList.Title" ) ) );
		List<DataControl> dataControlList = new ArrayList<DataControl>();
		for (ConversationDataControl item : conversationsListDataControl.getConversations()) {
			dataControlList.add(item);
		}
		ResizeableCellRenderer renderer = new ConversationCellRenderer();
		add(new ResizeableListPanel(dataControlList, renderer, "ConversationsListPanel"), BorderLayout.CENTER);

	}

}
