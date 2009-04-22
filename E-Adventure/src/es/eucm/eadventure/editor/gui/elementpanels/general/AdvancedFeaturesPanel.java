package es.eucm.eadventure.editor.gui.elementpanels.general;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.general.AdvancedFeaturesDataControl;
import es.eucm.eadventure.editor.control.controllers.globalstate.GlobalStateListDataControl;
import es.eucm.eadventure.editor.control.controllers.macro.MacroListDataControl;
import es.eucm.eadventure.editor.control.controllers.timer.TimersListDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.ElementPanel;
import es.eucm.eadventure.editor.gui.elementpanels.PanelTab;
import es.eucm.eadventure.editor.gui.elementpanels.globalstate.GlobalStatesListPanel;
import es.eucm.eadventure.editor.gui.elementpanels.macro.MacrosListPanel;
import es.eucm.eadventure.editor.gui.elementpanels.timer.TimersListPanel;

public class AdvancedFeaturesPanel extends ElementPanel {

	/**
	 * Required
	 */
	private static final long serialVersionUID = 6602692300239491332L;

	public AdvancedFeaturesPanel (AdvancedFeaturesDataControl advancedFeaturesDataControl){
		addTab(new TimersPanelTab(advancedFeaturesDataControl.getTimersList()));
		addTab(new GlobalStatesPanelTab(advancedFeaturesDataControl.getGlobalStatesListDataControl()));
		addTab(new MacrosPanelTab(advancedFeaturesDataControl.getMacrosListDataControl()));
	}
	
	private class TimersPanelTab extends PanelTab {
		private TimersListDataControl timersListDataControl;

		public TimersPanelTab(TimersListDataControl timersListDataControl) {
			super(TextConstants.getText( "TimersList.Title" ), timersListDataControl);
			setIcon(new ImageIcon( "img/icons/timers.png" ));
			this.timersListDataControl = timersListDataControl;
		}

		@Override
		protected JComponent getTabComponent() {
			return new TimersListPanel( timersListDataControl);
		}
	}

	private class GlobalStatesPanelTab extends PanelTab {
		private GlobalStateListDataControl globalStateListDataControl;

		public GlobalStatesPanelTab(GlobalStateListDataControl globalStateDataControl) {
			super(TextConstants.getText( "GlobalStatesList.Title" ), globalStateDataControl);
			setIcon(new ImageIcon( "img/icons/groups16.png" ));
			this.globalStateListDataControl = globalStateDataControl;
		}

		@Override
		protected JComponent getTabComponent() {
			return new GlobalStatesListPanel( globalStateListDataControl);
		}
	}

	private class MacrosPanelTab extends PanelTab {
		private MacroListDataControl macroListDataControl;

		public MacrosPanelTab(MacroListDataControl macroListDataControl) {
			super(TextConstants.getText( "MacrosList.Title" ), macroListDataControl);
			setIcon(new ImageIcon( "img/icons/macros.png" ));
			this.macroListDataControl = macroListDataControl;
		}

		@Override
		protected JComponent getTabComponent() {
			return new MacrosListPanel( macroListDataControl);
		}
	}
	
}
