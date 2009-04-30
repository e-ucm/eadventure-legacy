package es.eucm.eadventure.editor.gui.elementpanels.cutscene;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.cutscene.CutsceneDataControl;
import es.eucm.eadventure.editor.control.controllers.cutscene.CutscenesListDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.ElementPanel;
import es.eucm.eadventure.editor.gui.elementpanels.PanelTab;
import es.eucm.eadventure.editor.gui.elementpanels.general.ResizeableListPanel;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.ResizeableCellRenderer;

public class CutscenesListPanel extends ElementPanel {

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
		addTab(new CutscenesListPanelTab(cutscenesListDataControl));
	}

	private class CutscenesListPanelTab extends PanelTab {
		private CutscenesListDataControl sDataControl;
		
		public CutscenesListPanelTab(CutscenesListDataControl sDataControl) {
			super(TextConstants.getText( "CutscenesList.Title" ), sDataControl);
			this.sDataControl = sDataControl;
		}

		@Override
		protected JComponent getTabComponent() {
			JPanel cutscenesListPanel = new JPanel();
			cutscenesListPanel.setLayout( new BorderLayout( ) );
			List<DataControl> dataControlList = new ArrayList<DataControl>();
			for (CutsceneDataControl item : sDataControl.getCutscenes()) {
				dataControlList.add(item);
			}
			ResizeableCellRenderer renderer = new CutsceneCellRenderer();
			cutscenesListPanel.add(new ResizeableListPanel(dataControlList, renderer, "CutscenesListPanel"), BorderLayout.CENTER);
			return cutscenesListPanel;
		}
	}

}
