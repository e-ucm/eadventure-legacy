package es.eucm.eadventure.editor.gui.elementpanels.atrezzo;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.atrezzo.AtrezzoDataControl;
import es.eucm.eadventure.editor.control.controllers.atrezzo.AtrezzoListDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.general.ResizeableListPanel;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.ResizeableCellRenderer;

public class AtrezzoListPanel extends JPanel{
	
	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param atrezzoListDataControl2
	 *            Items list controller
	 */
	public AtrezzoListPanel( AtrezzoListDataControl atrezzoListDataControl2 ) {
		setLayout( new BorderLayout( ) );
		setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AtrezzoList.Title" ) ) );
		List<DataControl> dataControlList = new ArrayList<DataControl>();
		for (AtrezzoDataControl item : atrezzoListDataControl2.getAtrezzoList()) {
			dataControlList.add(item);
		}
		ResizeableCellRenderer renderer = new AtrezzoCellRenderer();
		add(new ResizeableListPanel(dataControlList, renderer, "AtrezzoListPanel"), BorderLayout.CENTER);
	}
}
