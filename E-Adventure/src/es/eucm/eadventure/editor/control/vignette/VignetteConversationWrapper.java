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

package es.eucm.eadventure.editor.control.vignette;

import java.util.List;

import es.eucm.eadventure.common.data.chapter.conversation.Conversation;
import es.eucm.eadventure.common.data.chapter.conversation.GraphConversation;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.conversation.ConversationDataControl;
import es.eucm.eadventure.editor.control.controllers.conversation.GraphConversationDataControl;


public class VignetteConversationWrapper {

    private ConversationDataControl dataControl;
    
    private GraphConversation conversation;
    
    private VignetteUICallback callback;
    
    public VignetteConversationWrapper (ConversationDataControl dataControl){
        this.dataControl = dataControl;
        this.conversation = (GraphConversation)dataControl.getConversation( );
    }

    
    public ConversationDataControl getDataControl( ) {
    
        return dataControl;
    }

    
    public void setDataControl( ConversationDataControl dataControl ) {
    
        this.dataControl = dataControl;
    }

    
    public GraphConversation getConversation( ) {
    
        return conversation;
    }

    
    public void updateConversation( GraphConversation newConversation ) {
    
        // Replace old conversation with the new one
        for (int i=0; i<Controller.getInstance( ).getSelectedChapterDataControl( ).getConversationsList( ).getConversations( ).size( );i++){
            ConversationDataControl gdc=Controller.getInstance( ).getSelectedChapterDataControl( ).getConversationsList( ).getConversations().get( i );
            if (gdc.getId( ).equals( this.conversation.getId( ))){
                this.conversation = newConversation;
                this.dataControl = new GraphConversationDataControl(newConversation);
                Controller.getInstance( ).getSelectedChapterDataControl( ).getConversationsList( ).getConversations().remove( i );
                Controller.getInstance( ).getSelectedChapterDataControl( ).getConversationsList( ).getConversations().add( i, this.dataControl );
                
                List<Conversation> conversations = (List<Conversation>)(Controller.getInstance( ).getSelectedChapterDataControl( ).getConversationsList( ).getContent( ));
                conversations.remove( i );
                conversations.add( i, this.conversation );
                
                this.callback.updateConversation( this.dataControl );
                break; 
            }
        }
        
    }

    
    public String getId( ) {
    
        return conversation.getVignetteId( );
    }

    
    public void setId( String id ) {
        conversation.setVignetteId( id );
        if (conversation.getVignetteId( )!=null){
            if (this.callback!=null){
                callback.updatePermission( VignetteUICallback.OPERATION_IMPORT, true );
            }
        }
    }

    public boolean getPermission(int operation){
        if (operation == VignetteUICallback.OPERATION_IMPORT){
            return conversation.getVignetteId( )!=null;
        }else if (operation == VignetteUICallback.OPERATION_EXPORT){
            return true;
        }
        return false;
    }
    
    public void exportToVignette(){
        VignetteController.exportConversation( this );
    }
    
    public void importFromVignette(){
        VignetteController.importConversation( this );
    }


    
    public VignetteUICallback getCallback( ) {
    
        return callback;
    }


    
    public void setCallback( VignetteUICallback callback ) {
    
        this.callback = callback;
    }
}
