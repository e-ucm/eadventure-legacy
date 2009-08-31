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
package es.eucm.eadventure.editor.control.tools.conversation;

import es.eucm.eadventure.common.data.chapter.conversation.line.ConversationLine;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNode;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNodeView;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.conversation.ConversationDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;

public class LinkConversationNodeTool extends Tool {

    private ConversationNodeView fatherView;

    private ConversationNodeView childView;

    private Controller controller;

    private ConversationDataControl dataControl;

    public LinkConversationNodeTool( ConversationDataControl _dataControl, ConversationNodeView _fatherView, ConversationNodeView _childView ) {

        this.fatherView = _fatherView;
        this.childView = _childView;
        this.dataControl = _dataControl;
        this.controller = Controller.getInstance( );
    }

    @Override
    public boolean canRedo( ) {

        return true;
    }

    @Override
    public boolean canUndo( ) {

        return true;
    }

    @Override
    public boolean combine( Tool other ) {

        return false;
    }

    @Override
    public boolean doTool( ) {

        boolean nodeLinked = false;

        // If it is not possible to link the node to the given one, show a message
        if( !dataControl.canLinkNodeTo( fatherView, childView ) )
            controller.showErrorDialog( TextConstants.getText( "Conversation.OperationLinkNode" ), TextConstants.getText( "Conversation.ErrorLinkNode" ) );

        // If it can be linked
        else {
            boolean linkNode = true;

            // If the node has an effect, ask for confirmation (for the effect will be deleted)
            //if( fatherView.hasEffects( ) )
            //linkNode = controller.showStrictConfirmDialog( TextConstants.getText( "Conversation.OperationLinkNode" ), TextConstants.getText( "Conversation.ErrorLinkNode" ) );

            // If the node must be linked
            if( linkNode ) {
                // Take the complete nodes
                ConversationNode father = (ConversationNode) fatherView;
                ConversationNode child = (ConversationNode) childView;

                // Add the new child
                father.addChild( child );

                // If the father is an option node, add a new line
                if( father.getType( ) == ConversationNodeView.OPTION )
                    father.addLine( new ConversationLine( ConversationLine.PLAYER, TextConstants.getText( "ConversationLine.DefaultText" ) ) );

                // The node was successfully linked
                nodeLinked = true;
                dataControl.updateAllConditions( );
            }
        }

        return nodeLinked;
    }

    @Override
    public boolean redoTool( ) {

        // Take the complete nodes
        ConversationNode father = (ConversationNode) fatherView;
        ConversationNode child = (ConversationNode) childView;
        // Add the new child
        father.addChild( child );
        // If the father is an option node, add a new line
        if( father.getType( ) == ConversationNodeView.OPTION )
            father.addLine( new ConversationLine( ConversationLine.PLAYER, TextConstants.getText( "ConversationLine.DefaultText" ) ) );
        dataControl.updateAllConditions( );
        controller.updatePanel( );
        return true;
    }

    @Override
    public boolean undoTool( ) {

        // Take the complete nodes
        ConversationNode father = (ConversationNode) fatherView;
        ConversationNode child = (ConversationNode) childView;

        // Add the new child
        int index = -1;
        for( int i = 0; i < father.getChildCount( ); i++ ) {
            if( father.getChild( i ) == child ) {
                index = i;
                father.removeChild( i );
                break;
            }
        }

        // If the father is an option node, add a new line
        if( father.getType( ) == ConversationNodeView.OPTION && index != -1 )
            father.removeLine( index );
        dataControl.updateAllConditions( );
        controller.updatePanel( );
        return true;
    }

}
