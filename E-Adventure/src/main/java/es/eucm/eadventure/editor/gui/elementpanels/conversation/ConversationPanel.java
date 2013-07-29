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
package es.eucm.eadventure.editor.gui.elementpanels.conversation;

import javax.swing.JComponent;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.conversation.ConversationDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.ElementPanel;
import es.eucm.eadventure.editor.gui.elementpanels.PanelTab;

/**
 * This class centralizes all the operations for conversation structures and
 * nodes. It has two panels, a panel to represent the conversation graphically
 * (RepresentationPanel), and a panel to display and edit the content of nodes
 * (LinesPanel). It also has a status bar which informs the user of the status
 * of the application
 */
public class ConversationPanel extends ElementPanel {

    /**
     * Required
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     * 
     * @param conversationDataControl
     *            Controller of the conversation
     */
    public ConversationPanel( ConversationDataControl conversationDataControl ) {

        addTab( new ConversationEditionPanelTab( conversationDataControl ) );
    }

    private class ConversationEditionPanelTab extends PanelTab {

        private ConversationDataControl sDataControl;

        public ConversationEditionPanelTab( ConversationDataControl sDataControl ) {

            super( TC.get( "Conversation.Title" ), sDataControl );
            this.sDataControl = sDataControl;
        }

        @Override
        protected JComponent getTabComponent( ) {

            return new ConversationEditionPanel( sDataControl );
        }
    }
}
