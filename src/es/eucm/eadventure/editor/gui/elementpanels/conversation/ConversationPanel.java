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
package es.eucm.eadventure.editor.gui.elementpanels.conversation;

import javax.swing.JComponent;

import es.eucm.eadventure.common.gui.TextConstants;
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

            super( TextConstants.getText( "Conversation.Title" ), sDataControl );
            this.sDataControl = sDataControl;
        }

        @Override
        protected JComponent getTabComponent( ) {

            return new ConversationEditionPanel( sDataControl );
        }
    }
}
