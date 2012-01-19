/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *          research group.
 *   
 *    Copyright 2005-2012 <e-UCM> research group.
 *  
 *     <e-UCM> is a research group of the Department of Software Engineering
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
 * This file is part of <e-Adventure>, version 1.4.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       <e-Adventure> is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      <e-Adventure> is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.gui.elementpanels.character;

import javax.swing.JComponent;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.character.NPCDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.ElementPanel;
import es.eucm.eadventure.editor.gui.elementpanels.PanelTab;
import es.eucm.eadventure.editor.gui.elementpanels.general.ActionsListPanel;

public class NPCPanel extends ElementPanel {

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
    public NPCPanel( NPCDataControl dataControl ) {

        addTab( new LooksPanelTab( dataControl ) );
        addTab( new DocPanelTab( dataControl ) );
        addTab( new DialogPanelTab( dataControl ) );
        addTab( new ActionsPanelTab( dataControl ) );
    }

    private class DocPanelTab extends PanelTab {

        private NPCDataControl dataControl;

        public DocPanelTab( NPCDataControl dataControl ) {

            super( TC.get( "NPC.DocPanelTitle" ), dataControl );
            this.dataControl = dataControl;
        }

        @Override
        protected JComponent getTabComponent( ) {

            return new NPCDocPanel( dataControl );
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

    private class ActionsPanelTab extends PanelTab {

        private NPCDataControl dataControl;

        public ActionsPanelTab( NPCDataControl dataControl ) {

            super( TC.get( "NPC.ActionsPanelTitle" ), dataControl.getActionsList( ) );
            setToolTipText( TC.get( "NPC.ActionsPanelTip" ) );
            this.dataControl = dataControl;
        }

        @Override
        protected JComponent getTabComponent( ) {

            return new ActionsListPanel( dataControl.getActionsList( ) );
        }
    }
}
