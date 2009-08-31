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
package es.eucm.eadventure.editor.control.controllers.conversation;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.data.chapter.conversation.Conversation;
import es.eucm.eadventure.common.data.chapter.conversation.GraphConversation;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNode;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.Searchable;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class ConversationsListDataControl extends DataControl {

    /**
     * List of conversations.
     */
    private List<Conversation> conversationsList;

    /**
     * List of conversation controllers.
     */
    private List<ConversationDataControl> conversationsDataControlList;

    /**
     * Constructor.
     * 
     * @param conversationsList
     *            List of conversations
     */
    public ConversationsListDataControl( List<Conversation> conversationsList ) {

        this.conversationsList = conversationsList;

        // Create the subcontrollers
        conversationsDataControlList = new ArrayList<ConversationDataControl>( );
        for( Conversation conversation : conversationsList ) {
            if( conversation.getType( ) == Conversation.GRAPH )
                conversationsDataControlList.add( new GraphConversationDataControl( (GraphConversation) conversation ) );
        }
    }

    /**
     * Returns the list of conversation controllers.
     * 
     * @return Conversation controllers
     */
    public List<ConversationDataControl> getConversations( ) {

        return conversationsDataControlList;
    }

    /**
     * Returns the last conversation controller from the list.
     * 
     * @return Last conversation controller
     */
    public ConversationDataControl getLastConversation( ) {

        return conversationsDataControlList.get( conversationsDataControlList.size( ) - 1 );
    }

    @Override
    public Object getContent( ) {

        return conversationsList;
    }

    @Override
    public int[] getAddableElements( ) {

        return new int[] { Controller.CONVERSATION_GRAPH };
    }

    @Override
    public boolean canAddElement( int type ) {

        // It can always add new characters
        return type == Controller.CONVERSATION_GRAPH;
    }

    @Override
    public boolean canBeDeleted( ) {

        return false;
    }

    @Override
    public boolean canBeMoved( ) {

        return false;
    }

    @Override
    public boolean canBeRenamed( ) {

        return false;
    }

    @Override
    public boolean addElement( int type, String conversationId ) {

        boolean elementAdded = false;

        if( type == Controller.CONVERSATION_GRAPH ) {

            // Show a dialog asking for the conversation id
            if( conversationId == null )
                conversationId = controller.showInputDialog( TextConstants.getText( "Operation.AddConversationTitle" ), TextConstants.getText( "Operation.AddConversationMessage" ), TextConstants.getText( "Operation.AddConversationDefaultValue" ) );

            // If some value was typed and the identifier is valid
            if( conversationId != null && controller.isElementIdValid( conversationId ) ) {
                Conversation newConversation = new GraphConversation( conversationId );
                ConversationDataControl newConversationDataControl = new GraphConversationDataControl( (GraphConversation) newConversation );

                // Add the new conversation
                conversationsList.add( newConversation );
                conversationsDataControlList.add( newConversationDataControl );
                controller.getIdentifierSummary( ).addConversationId( conversationId );
                //controller.dataModified( );
                elementAdded = true;
            }
        }

        return elementAdded;
    }

    @Override
    public boolean duplicateElement( DataControl dataControl ) {

        if( !( dataControl instanceof GraphConversationDataControl ) )
            return false;

        try {
            GraphConversation newElement = (GraphConversation) ( ( (GraphConversation) ( dataControl.getContent( ) ) ).clone( ) );
            String id = newElement.getId( );
            int i = 1;
            do {
                id = newElement.getId( ) + i;
                i++;
            } while( !controller.isElementIdValid( id, false ) );
            newElement.setId( id );
            conversationsList.add( newElement );
            conversationsDataControlList.add( new GraphConversationDataControl( newElement ) );
            controller.getIdentifierSummary( ).addConversationId( id );
            return true;
        }
        catch( CloneNotSupportedException e ) {
            ReportDialog.GenerateErrorReport( e, true, "Could not clone conversation" );
            return false;
        }
    }

    @Override
    public String getDefaultId( int type ) {

        return TextConstants.getText( "Operation.AddConversationDefaultValue" );
    }

    @Override
    public boolean deleteElement( DataControl dataControl, boolean askConfirmation ) {

        boolean elementDeleted = false;
        String conversationId = ( (ConversationDataControl) dataControl ).getId( );
        String references = String.valueOf( controller.countIdentifierReferences( conversationId ) );

        // Ask for confirmation
        if( !askConfirmation || controller.showStrictConfirmDialog( TextConstants.getText( "Operation.DeleteElementTitle" ), TextConstants.getText( "Operation.DeleteElementWarning", new String[] { conversationId, references } ) ) ) {
            if( conversationsList.remove( dataControl.getContent( ) ) ) {
                conversationsDataControlList.remove( dataControl );
                controller.deleteIdentifierReferences( conversationId );
                controller.getIdentifierSummary( ).deleteConversationId( conversationId );
                //controller.dataModified( );
                elementDeleted = true;
            }
        }

        return elementDeleted;
    }

    @Override
    public boolean moveElementUp( DataControl dataControl ) {

        boolean elementMoved = false;
        int elementIndex = conversationsList.indexOf( dataControl.getContent( ) );

        if( elementIndex > 0 ) {
            conversationsList.add( elementIndex - 1, conversationsList.remove( elementIndex ) );
            conversationsDataControlList.add( elementIndex - 1, conversationsDataControlList.remove( elementIndex ) );
            //controller.dataModified( );
            elementMoved = true;
        }

        return elementMoved;
    }

    @Override
    public boolean moveElementDown( DataControl dataControl ) {

        boolean elementMoved = false;
        int elementIndex = conversationsList.indexOf( dataControl.getContent( ) );

        if( elementIndex < conversationsList.size( ) - 1 ) {
            conversationsList.add( elementIndex + 1, conversationsList.remove( elementIndex ) );
            conversationsDataControlList.add( elementIndex + 1, conversationsDataControlList.remove( elementIndex ) );
            //controller.dataModified( );
            elementMoved = true;
        }

        return elementMoved;
    }

    @Override
    public String renameElement( String name ) {

        return null;
    }

    @Override
    public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {

        // Iterate through each conversation
        for( ConversationDataControl conversationDataControl : conversationsDataControlList )
            conversationDataControl.updateVarFlagSummary( varFlagSummary );
    }

    @Override
    public boolean isValid( String currentPath, List<String> incidences ) {

        boolean valid = true;

        // Update the current path
        currentPath += " >> " + TextConstants.getElementName( Controller.CONVERSATIONS_LIST );

        // Iterate through the conversations
        for( ConversationDataControl conversationDataControl : conversationsDataControlList ) {
            String conversationPath = currentPath + " >> " + conversationDataControl.getId( );
            valid &= conversationDataControl.isValid( conversationPath, incidences );
        }

        return valid;
    }

    @Override
    public int countAssetReferences( String assetPath ) {

        int count = 0;

        // Iterate through each conversation
        for( ConversationDataControl conversationDataControl : conversationsDataControlList )
            count += conversationDataControl.countAssetReferences( assetPath );

        return count;
    }

    @Override
    public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {

        // Iterate through each conversation
        for( ConversationDataControl conversationDataControl : conversationsDataControlList )
            conversationDataControl.getAssetReferences( assetPaths, assetTypes );
    }

    @Override
    public void deleteAssetReferences( String assetPath ) {

        // Iterate through each conversation
        for( ConversationDataControl conversationDataControl : conversationsDataControlList )
            conversationDataControl.deleteAssetReferences( assetPath );
    }

    @Override
    public int countIdentifierReferences( String id ) {

        int count = 0;

        // Iterate through each conversation
        for( ConversationDataControl conversationDataControl : conversationsDataControlList )
            count += conversationDataControl.countIdentifierReferences( id );

        return count;
    }

    @Override
    public void replaceIdentifierReferences( String oldId, String newId ) {

        // Iterate through each conversation
        for( ConversationDataControl conversationDataControl : conversationsDataControlList )
            conversationDataControl.replaceIdentifierReferences( oldId, newId );
    }

    @Override
    public void deleteIdentifierReferences( String id ) {

        // Spread the call to every conversation
        for( ConversationDataControl conversationDataControl : conversationsDataControlList )
            conversationDataControl.deleteIdentifierReferences( id );
    }

    @Override
    public boolean canBeDuplicated( ) {

        return false;
    }

    /**
     * Sets all the effects of all the conversations to notConsumed. This is
     * indispensable for the RUN option to work properly. If this is not invoked
     * before debugRun() effects might not get executed
     */
    public void resetAllConversationNodes( ) {

        for( Conversation convData : conversationsList ) {
            for( ConversationNode node : convData.getAllNodes( ) ) {
                node.resetEffect( );
            }

        }
    }

    @Override
    public void recursiveSearch( ) {

        for( ConversationDataControl dc : this.conversationsDataControlList )
            dc.recursiveSearch( );
    }

    @Override
    public List<Searchable> getPathToDataControl( Searchable dataControl ) {

        return getPathFromChild( dataControl, conversationsDataControlList );
    }
}
