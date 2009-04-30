package es.eucm.eadventure.editor.gui.elementpanels.item;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.item.ItemDataControl;
import es.eucm.eadventure.editor.control.controllers.item.ItemsListDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.ElementPanel;
import es.eucm.eadventure.editor.gui.elementpanels.PanelTab;
import es.eucm.eadventure.editor.gui.elementpanels.general.ResizeableListPanel;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.ResizeableCellRenderer;

public class ItemsListPanel extends ElementPanel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor.
	 * 
	 * @param itemsListDataControl2
	 *            Items list controller
	 */
	public ItemsListPanel( ItemsListDataControl itemsListDataControl2 ) {
		addTab(new ItemsListPanelTab(itemsListDataControl2));
	}

	private class ItemsListPanelTab extends PanelTab {
		private ItemsListDataControl sDataControl;
		
		public ItemsListPanelTab(ItemsListDataControl sDataControl) {
			super(TextConstants.getText( "ItemsList.Title" ), sDataControl);
			this.sDataControl = sDataControl;
		}

		@Override
		protected JComponent getTabComponent() {
			JPanel itemsListPanel = new JPanel();
			itemsListPanel.setLayout( new BorderLayout( ) );
			List<DataControl> dataControlList = new ArrayList<DataControl>();
			for (ItemDataControl item : sDataControl.getItems()) {
				dataControlList.add(item);
			}
			ResizeableCellRenderer renderer = new ItemCellRenderer();
			itemsListPanel.add(new ResizeableListPanel(dataControlList, renderer, "ItemsListPanel"));
			return itemsListPanel;
		}
	}

}
