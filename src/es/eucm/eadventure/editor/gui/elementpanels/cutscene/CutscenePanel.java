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
package es.eucm.eadventure.editor.gui.elementpanels.cutscene;

import javax.swing.JComponent;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.cutscene.CutsceneDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.ElementPanel;
import es.eucm.eadventure.editor.gui.elementpanels.PanelTab;

/**
 * Does not need to implement Updateable, as ElementPanel already implements
 * Updateable
 * 
 * @author Javier
 * 
 */
public class CutscenePanel extends ElementPanel {

    /**
     * Required.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     * 
     * @param cutsceneDataControl
     *            Cutscene controller
     */
    public CutscenePanel( CutsceneDataControl cutsceneDataControl ) {

        addTab( new CutsceneAppPanelTab( cutsceneDataControl ) );
        addTab( new CutsceneEndPanelTab( cutsceneDataControl ) );
        addTab( new CutsceneDocPanelTab( cutsceneDataControl ) );
    }

    private class CutsceneAppPanelTab extends PanelTab {

        private CutsceneDataControl sDataControl;

        public CutsceneAppPanelTab( CutsceneDataControl sDataControl ) {

            super( TC.get( "Cutscene.App" ), sDataControl );
            this.sDataControl = sDataControl;
        }

        @Override
        protected JComponent getTabComponent( ) {

            return new CutsceneLooksPanel( sDataControl );
        }
    }

    private class CutsceneDocPanelTab extends PanelTab {

        private CutsceneDataControl sDataControl;

        public CutsceneDocPanelTab( CutsceneDataControl sDataControl ) {

            super( TC.get( "Cutscene.Doc" ), sDataControl );
            this.sDataControl = sDataControl;
        }

        @Override
        protected JComponent getTabComponent( ) {

            return new CutsceneDocPanel( sDataControl );
        }
    }

    private class CutsceneEndPanelTab extends PanelTab {

        private CutsceneDataControl sDataControl;

        public CutsceneEndPanelTab( CutsceneDataControl sDataControl ) {

            super( TC.get( "Cutscene.CutsceneEnd" ), sDataControl );
            this.sDataControl = sDataControl;
        }

        @Override
        protected JComponent getTabComponent( ) {

            return new CutsceneEndPanel( sDataControl );
        }
    }

}
