package es.eucm.eadventure.editor.gui.elementpanels.atrezzo;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.atrezzo.AtrezzoDataControl;
import es.eucm.eadventure.editor.control.controllers.atrezzo.AtrezzoListDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.ElementPanel;
import es.eucm.eadventure.editor.gui.elementpanels.PanelTab;
import es.eucm.eadventure.editor.gui.elementpanels.general.ResizeableListPanel;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.ResizeableCellRenderer;

public class AtrezzoListPanel extends ElementPanel {
	
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
		addTab(new AtrezzoListPanelTab(atrezzoListDataControl2));
	}
	
	private class AtrezzoListPanelTab extends PanelTab {
		private AtrezzoListDataControl sDataControl;
		
		public AtrezzoListPanelTab(AtrezzoListDataControl sDataControl) {
			super(TextConstants.getText( "AtrezzoList.Title" ), sDataControl);
			this.sDataControl = sDataControl;
		}

		@Override
		protected JComponent getTabComponent() {
			JPanel atrezzoListPanel = new JPanel();
			atrezzoListPanel.setLayout( new BorderLayout( ) );
			List<DataControl> dataControlList = new ArrayList<DataControl>();
			for (AtrezzoDataControl item : sDataControl.getAtrezzoList()) {
				dataControlList.add(item);
			}
			ResizeableCellRenderer renderer = new AtrezzoCellRenderer();
			atrezzoListPanel.add(new ResizeableListPanel(dataControlList, renderer, "AtrezzoListPanel"), BorderLayout.CENTER);
			return atrezzoListPanel;
		}
	}

}
