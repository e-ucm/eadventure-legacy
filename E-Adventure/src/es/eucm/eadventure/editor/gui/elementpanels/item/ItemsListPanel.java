package es.eucm.eadventure.editor.gui.elementpanels.item;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.item.ItemDataControl;
import es.eucm.eadventure.editor.control.controllers.item.ItemsListDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.general.ResizeableListPanel;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.ResizeableCellRenderer;

public class ItemsListPanel extends JPanel {

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
		List<DataControl> dataControlList = new ArrayList<DataControl>();
		for (ItemDataControl item : itemsListDataControl2.getItems()) {
			dataControlList.add(item);
		}
		ResizeableCellRenderer renderer = new ItemCellRenderer();
		JPanel panel = new ResizeableListPanel(dataControlList, renderer);
		setLayout(new BorderLayout());
		setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "ItemsList.Title" ) ) );
		add(panel, BorderLayout.CENTER);
	}
}
