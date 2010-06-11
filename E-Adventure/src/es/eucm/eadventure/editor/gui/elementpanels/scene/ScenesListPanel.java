/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *         research group.
 *  
 *   Copyright 2005-2010 <e-UCM> research group.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *         http://e-adventure.e-ucm.es/contributors
 * 
 *   <e-UCM> is a research group of the Department of Software Engineering
 *         and Artificial Intelligence at the Complutense University of Madrid
 *         (School of Computer Science).
 * 
 *         C Profesor Jose Garcia Santesmases sn,
 *         28040 Madrid (Madrid), Spain.
 * 
 *         For more info please visit:  <http://e-adventure.e-ucm.es> or
 *         <http://www.e-ucm.es>
 * 
 * ****************************************************************************
 * 
 * This file is part of <e-Adventure>, version 1.2.
 * 
 *     <e-Adventure> is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     <e-Adventure> is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 * 
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.gui.elementpanels.scene;

import javax.swing.JComponent;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.scene.ScenesListDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.ElementPanel;
import es.eucm.eadventure.editor.gui.elementpanels.PanelTab;
import es.eucm.eadventure.editor.gui.otherpanels.SceneLinksPanel;

public class ScenesListPanel extends ElementPanel {

    /**
     * Required.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     * 
     * @param scenesListDataControl
     *            Scenes list controller
     */
    public ScenesListPanel( ScenesListDataControl scenesListDataControl ) {

        this.addTab( new SceneListPanelTab( scenesListDataControl ) );
    }

    private class SceneListPanelTab extends PanelTab {

        private ScenesListDataControl scenesListDataControl;

        public SceneListPanelTab( ScenesListDataControl scenesListDataControl ) {

            super( TC.get( "ScenesList.Title" ), scenesListDataControl );
            this.setHelpPath( "scenes/Scenes_General.html" );
            this.scenesListDataControl = scenesListDataControl;
        }

        @Override
        protected JComponent getTabComponent( ) {

            return new SceneLinksPanel( scenesListDataControl );
        }
    }

}
