/*******************************************************************************
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the e-UCM
 *          research group.
 *   
 *    Copyright 2005-2012 e-UCM research group.
 *  
 *     e-UCM is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *  
 *          C Profesor Jose Garcia Santesmases sn,
 *          28040 Madrid (Madrid), Spain.
 *  
 *          For more info please visit:  <http://e-adventure.e-ucm.es> or
 *          <http://www.e-ucm.es>
 *  
 *  ****************************************************************************
 * This file is part of eAdventure, version 1.5.
 * 
 *   You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with Adventure.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.gui.elementpanels.general;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import es.eucm.eadventure.common.gui.TC;
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

            super( TC.get( "TimersList.Title" ), timersListDataControl );
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

            super( TC.get( "GlobalStatesList.Title" ), globalStateDataControl );
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

            super( TC.get( "MacrosList.Title" ), macroListDataControl );
            setIcon( new ImageIcon( "img/icons/macros.png" ) );
            this.macroListDataControl = macroListDataControl;
        }

        @Override
        protected JComponent getTabComponent( ) {

            return new MacrosListPanel( macroListDataControl );
        }
    }

}
