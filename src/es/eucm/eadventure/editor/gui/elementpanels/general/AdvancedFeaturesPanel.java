/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author López Mañas, E., Pérez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, available at http://e-adventure.e-ucm.es.
 * 
 * <e-Adventure> is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * <e-Adventure>; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */
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

    public AdvancedFeaturesPanel( AdvancedFeaturesDataControl advancedFeaturesDataControl ) {

        addTab( new TimersPanelTab( advancedFeaturesDataControl.getTimersList( ) ) );
        addTab( new GlobalStatesPanelTab( advancedFeaturesDataControl.getGlobalStatesListDataControl( ) ) );
        addTab( new MacrosPanelTab( advancedFeaturesDataControl.getMacrosListDataControl( ) ) );
    }

    private class TimersPanelTab extends PanelTab {

        private TimersListDataControl timersListDataControl;

        public TimersPanelTab( TimersListDataControl timersListDataControl ) {

            super( TextConstants.getText( "TimersList.Title" ), timersListDataControl );
            setIcon( new ImageIcon( "img/icons/timers.png" ) );
            this.timersListDataControl = timersListDataControl;
        }

        @Override
        protected JComponent getTabComponent( ) {

            return new TimersListPanel( timersListDataControl );
        }
    }

    private class GlobalStatesPanelTab extends PanelTab {

        private GlobalStateListDataControl globalStateListDataControl;

        public GlobalStatesPanelTab( GlobalStateListDataControl globalStateDataControl ) {

            super( TextConstants.getText( "GlobalStatesList.Title" ), globalStateDataControl );
            setIcon( new ImageIcon( "img/icons/groups16.png" ) );
            this.globalStateListDataControl = globalStateDataControl;
        }

        @Override
        protected JComponent getTabComponent( ) {

            return new GlobalStatesListPanel( globalStateListDataControl );
        }
    }

    private class MacrosPanelTab extends PanelTab {

        private MacroListDataControl macroListDataControl;

        public MacrosPanelTab( MacroListDataControl macroListDataControl ) {

            super( TextConstants.getText( "MacrosList.Title" ), macroListDataControl );
            setIcon( new ImageIcon( "img/icons/macros.png" ) );
            this.macroListDataControl = macroListDataControl;
        }

        @Override
        protected JComponent getTabComponent( ) {

            return new MacrosListPanel( macroListDataControl );
        }
    }

}
