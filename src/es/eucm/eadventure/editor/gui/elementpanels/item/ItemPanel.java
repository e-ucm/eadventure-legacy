package es.eucm.eadventure.editor.gui.elementpanels.item;

import javax.swing.JComponent;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.item.ItemDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.ElementPanel;
import es.eucm.eadventure.editor.gui.elementpanels.PanelTab;
import es.eucm.eadventure.editor.gui.elementpanels.general.ActionsListPanel;

public class ItemPanel extends ElementPanel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param itemDataControl
	 *            Item controller
	 */
	public ItemPanel( ItemDataControl itemDataControl ) {
		this.addTab(new ItemLooksPanelTab(itemDataControl));
		this.addTab(new ItemDocPanelTab(itemDataControl));
		this.addTab(new ActionsPanelTab(itemDataControl));
	}
	
	private class ItemDocPanelTab extends PanelTab {
		private ItemDataControl itemDataControl;

		public ItemDocPanelTab(ItemDataControl itemDataControl) {
			super(TextConstants.getText( "Item.DocPanelTitle" ), itemDataControl);
			this.itemDataControl = itemDataControl;
		}

		@Override
		protected JComponent getTabComponent() {
			return new ItemDocPanel( itemDataControl);
		}
	}
	
	private class ItemLooksPanelTab extends PanelTab {
		private ItemDataControl itemDataControl;

		public ItemLooksPanelTab(ItemDataControl itemDataControl) {
			super(TextConstants.getText( "Item.LookPanelTitle" ), itemDataControl);
			this.itemDataControl = itemDataControl;
		}

		@Override
		protected JComponent getTabComponent() {
			return new ItemLooksPanel( itemDataControl );
		}
	}
	
	private class ActionsPanelTab extends PanelTab {
		private ItemDataControl itemDataControl;

		public ActionsPanelTab(ItemDataControl itemDataControl) {
			super(TextConstants.getText( "Item.ActionsPanelTitle" ), itemDataControl.getActionsList());
			this.itemDataControl = itemDataControl;
		}

		@Override
		protected JComponent getTabComponent() {
			return new ActionsListPanel ( itemDataControl.getActionsList());
		}
	}

}
