package es.eucm.eadventure.editor.gui.elementpanels.cutscene;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.cutscene.CutsceneDataControl;
import es.eucm.eadventure.editor.control.controllers.cutscene.CutscenesListDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.general.ResizeableListPanel;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.ResizeableCellRenderer;

public class CutscenesListPanel extends JPanel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * 
	 * @param cutscenesListDataControl
	 *            Cutscenes list controller
	 */
	public CutscenesListPanel( CutscenesListDataControl cutscenesListDataControl ) {
		setLayout( new BorderLayout( ) );
		setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "CutscenesList.Title" ) ) );
		List<DataControl> dataControlList = new ArrayList<DataControl>();
		for (CutsceneDataControl item : cutscenesListDataControl.getCutscenes()) {
			dataControlList.add(item);
		}
		ResizeableCellRenderer renderer = new CutsceneCellRenderer();
		add(new ResizeableListPanel(dataControlList, renderer, "CutscenesListPanel"), BorderLayout.CENTER);
	}

}
