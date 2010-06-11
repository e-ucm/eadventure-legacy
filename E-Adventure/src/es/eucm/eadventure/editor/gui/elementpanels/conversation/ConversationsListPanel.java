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
package es.eucm.eadventure.editor.gui.elementpanels.conversation;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.conversation.ConversationDataControl;
import es.eucm.eadventure.editor.control.controllers.conversation.ConversationsListDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.ElementPanel;
import es.eucm.eadventure.editor.gui.elementpanels.PanelTab;
import es.eucm.eadventure.editor.gui.elementpanels.general.ResizeableListPanel;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.ResizeableCellRenderer;

public class ConversationsListPanel extends ElementPanel {

    /**
     * Required.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     * 
     * @param conversationsListDataControl
     *            Books list controller
     */
    public ConversationsListPanel( ConversationsListDataControl conversationsListDataControl ) {

        addTab( new ConversationsListPanelTab( conversationsListDataControl ) );
    }

    private class ConversationsListPanelTab extends PanelTab {

        private ConversationsListDataControl sDataControl;

        public ConversationsListPanelTab( ConversationsListDataControl sDataControl ) {

            super( TC.get( "ConversationsList.Title" ), sDataControl );
            this.sDataControl = sDataControl;
        }

        @Override
        protected JComponent getTabComponent( ) {

            JPanel conversationsListPanel = new JPanel( );
            conversationsListPanel.setLayout( new BorderLayout( ) );
            List<DataControl> dataControlList = new ArrayList<DataControl>( );
            for( ConversationDataControl item : sDataControl.getConversations( ) ) {
                dataControlList.add( item );
            }
            ResizeableCellRenderer renderer = new ConversationCellRenderer( );
            conversationsListPanel.add( new ResizeableListPanel( dataControlList, renderer, "ConversationsListPanel" ), BorderLayout.CENTER );
            return conversationsListPanel;
        }
    }

}
