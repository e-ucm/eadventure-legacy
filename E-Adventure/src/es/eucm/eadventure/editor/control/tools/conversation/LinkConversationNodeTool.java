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
package es.eucm.eadventure.editor.control.tools.conversation;

import es.eucm.eadventure.common.data.chapter.conversation.line.ConversationLine;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNode;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNodeView;
import es.eucm.eadventure.common.gui.TC;
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
            controller.showErrorDialog( TC.get( "Conversation.OperationLinkNode" ), TC.get( "Conversation.ErrorLinkNode" ) );

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
                    father.addLine( new ConversationLine( ConversationLine.PLAYER, TC.get( "ConversationLine.DefaultText" ) ) );

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
            father.addLine( new ConversationLine( ConversationLine.PLAYER, TC.get( "ConversationLine.DefaultText" ) ) );
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
