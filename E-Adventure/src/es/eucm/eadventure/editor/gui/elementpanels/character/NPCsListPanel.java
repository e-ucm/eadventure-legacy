package es.eucm.eadventure.editor.gui.elementpanels.character;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.character.NPCDataControl;
import es.eucm.eadventure.editor.control.controllers.character.NPCsListDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.ElementPanel;
import es.eucm.eadventure.editor.gui.elementpanels.PanelTab;
import es.eucm.eadventure.editor.gui.elementpanels.general.ResizeableListPanel;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.ResizeableCellRenderer;

public class NPCsListPanel extends ElementPanel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Constructor.
	 * 
	 * @param npcsListDataControl
	 *            Characters list controller
	 */
	public NPCsListPanel( NPCsListDataControl npcsListDataControl ) {
		addTab(new NPCsListPanelTab(npcsListDataControl));
	}
	
	private class NPCsListPanelTab extends PanelTab {
		private NPCsListDataControl sDataControl;
		
		public NPCsListPanelTab(NPCsListDataControl sDataControl) {
			super(TextConstants.getText( "NPCsList.Title" ), sDataControl);
			this.sDataControl = sDataControl;
		}

		@Override
		protected JComponent getTabComponent() {
			JPanel npcsListPanel = new JPanel();
			npcsListPanel.setLayout( new BorderLayout( ) );
			List<DataControl> dataControlList = new ArrayList<DataControl>();
			for (NPCDataControl item : sDataControl.getNPCs()) {
				dataControlList.add(item);
			}
			ResizeableCellRenderer renderer = new NPCCellRenderer();
			npcsListPanel.add(new ResizeableListPanel(dataControlList, renderer, "NPCsListPanel"), BorderLayout.CENTER);
			return npcsListPanel;
		}
	}

}
