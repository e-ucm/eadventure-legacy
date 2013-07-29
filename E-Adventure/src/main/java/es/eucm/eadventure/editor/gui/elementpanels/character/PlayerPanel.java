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
package es.eucm.eadventure.editor.gui.elementpanels.character;

import javax.swing.JComponent;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.character.NPCDataControl;
import es.eucm.eadventure.editor.control.controllers.character.PlayerDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.ElementPanel;
import es.eucm.eadventure.editor.gui.elementpanels.PanelTab;

public class PlayerPanel extends ElementPanel {

    /**
     * Required.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     * 
     * @param playerDataControl
     *            Player controller
     */
    public PlayerPanel( PlayerDataControl dataControl ) {

        if( dataControl.buildResourcesTab( ) ) {
            addTab( new LooksPanelTab( dataControl ) );
        }
        addTab( new DialogPanelTab( dataControl ) );
        addTab( new DocPanelTab( dataControl ) );
    }

    private class DocPanelTab extends PanelTab {

        private NPCDataControl dataControl;

        public DocPanelTab( NPCDataControl dataControl ) {

            super( TC.get( "NPC.DocPanelTitle" ), dataControl );
            this.dataControl = dataControl;
        }

        @Override
        protected JComponent getTabComponent( ) {

            return new NPCDocPanel( dataControl, true );
        }
    }

    private class LooksPanelTab extends PanelTab {

        private NPCDataControl dataControl;

        public LooksPanelTab( NPCDataControl dataControl ) {

            super( TC.get( "NPC.LookPanelTitle" ), dataControl );
            this.dataControl = dataControl;
        }

        @Override
        protected JComponent getTabComponent( ) {

            return new NPCLooksPanel( dataControl );
        }
    }

    private class DialogPanelTab extends PanelTab {

        private NPCDataControl dataControl;

        public DialogPanelTab( NPCDataControl dataControl ) {

            super( TC.get( "NPC.DialogPanelTitle" ), dataControl );
            setToolTipText( TC.get( "NPC.DialogPanelTip" ) );
            this.dataControl = dataControl;
        }

        @Override
        protected JComponent getTabComponent( ) {

            return new NPCDialogPanel( dataControl );
        }
    }

}
