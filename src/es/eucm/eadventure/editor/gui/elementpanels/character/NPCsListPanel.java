package es.eucm.eadventure.editor.gui.elementpanels.character;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.character.NPCDataControl;
import es.eucm.eadventure.editor.control.controllers.character.NPCsListDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.general.ResizeableListPanel;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.ResizeableCellRenderer;

public class NPCsListPanel extends JPanel {

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
		setLayout( new BorderLayout( ) );
		setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AtrezzoList.Title" ) ) );
		List<DataControl> dataControlList = new ArrayList<DataControl>();
		for (NPCDataControl item : npcsListDataControl.getNPCs()) {
			dataControlList.add(item);
		}
		ResizeableCellRenderer renderer = new NPCCellRenderer();
		setLayout( new BorderLayout( ) );
		setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "NPCsList.Title" ) ) );
		add(new ResizeableListPanel(dataControlList, renderer), BorderLayout.CENTER);
	}
}
