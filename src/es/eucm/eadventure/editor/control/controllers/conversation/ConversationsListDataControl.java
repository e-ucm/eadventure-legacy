package es.eucm.eadventure.editor.control.controllers.conversation;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.conversation.Conversation;
import es.eucm.eadventure.common.data.chapter.conversation.GraphConversation;
import es.eucm.eadventure.common.data.chapter.conversation.TreeConversation;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.data.support.FlagSummary;
import es.eucm.eadventure.editor.gui.TextConstants;

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
			if( conversation.getType( ) == Conversation.TREE )
				conversationsDataControlList.add( new TreeConversationDataControl( (TreeConversation) conversation ) );
			else if( conversation.getType( ) == Conversation.GRAPH )
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

	/**
	 * Returns the info of the conversations contained in the list.
	 * 
	 * @return Array with the information of the conversations. It contains the identifier of each conversation, and the
	 *         number of lines
	 */
	public String[][] getConversationsInfo( ) {
		String[][] conversationsInfo = null;

		// Create the list for the conversations
		conversationsInfo = new String[conversationsList.size( )][2];

		// Fill the array with the info
		for( int i = 0; i < conversationsList.size( ); i++ ) {
			conversationsInfo[i][0] = conversationsList.get( i ).getId( );
			conversationsInfo[i][1] = TextConstants.getText( "ConversationsList.LinesNumber", String.valueOf( conversationsDataControlList.get( i ).getConversationLineCount( ) ) );
		}

		return conversationsInfo;
	}

	@Override
	public Object getContent( ) {
		return conversationsList;
	}

	@Override
	public int[] getAddableElements( ) {
		return new int[] { Controller.CONVERSATION_TREE, Controller.CONVERSATION_GRAPH };
	}

	@Override
	public boolean canAddElement( int type ) {
		// It can always add new characters
		return type == Controller.CONVERSATION_TREE || type == Controller.CONVERSATION_GRAPH;
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
	public boolean addElement( int type ) {
		boolean elementAdded = false;

		if( type == Controller.CONVERSATION_TREE || type == Controller.CONVERSATION_GRAPH ) {

			// Show a dialog asking for the conversation id
			String conversationId = controller.showInputDialog( TextConstants.getText( "Operation.AddConversationTitle" ), TextConstants.getText( "Operation.AddConversationMessage" ), TextConstants.getText( "Operation.AddConversationDefaultValue" ) );

			// If some value was typed and the identifier is valid
			if( conversationId != null && controller.isElementIdValid( conversationId ) ) {
				Conversation newConversation = null;
				ConversationDataControl newConversationDataControl = null;

				// Create the new conversation
				if( type == Controller.CONVERSATION_TREE ) {
					newConversation = new TreeConversation( conversationId );
					newConversationDataControl = new TreeConversationDataControl( (TreeConversation) newConversation );
				}

				else if( type == Controller.CONVERSATION_GRAPH ) {
					newConversation = new GraphConversation( conversationId );
					newConversationDataControl = new GraphConversationDataControl( (GraphConversation) newConversation );
				}

				// Add the new conversation
				conversationsList.add( newConversation );
				conversationsDataControlList.add( newConversationDataControl );
				controller.getIdentifierSummary( ).addConversationId( conversationId );
				controller.dataModified( );
				elementAdded = true;
			}
		}

		return elementAdded;
	}

	@Override
	public boolean deleteElement( DataControl dataControl ) {
		boolean elementDeleted = false;
		String conversationId = ( (ConversationDataControl) dataControl ).getId( );
		String references = String.valueOf( controller.countIdentifierReferences( conversationId ) );

		// Ask for confirmation
		if( controller.showStrictConfirmDialog( TextConstants.getText( "Operation.DeleteElementTitle" ), TextConstants.getText( "Operation.DeleteElementWarning", new String[] { conversationId, references } ) ) ) {
			if( conversationsList.remove( dataControl.getContent( ) ) ) {
				conversationsDataControlList.remove( dataControl );
				controller.deleteIdentifierReferences( conversationId );
				controller.getIdentifierSummary( ).deleteConversationId( conversationId );
				controller.dataModified( );
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
			controller.dataModified( );
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
			controller.dataModified( );
			elementMoved = true;
		}

		return elementMoved;
	}

	@Override
	public boolean renameElement( ) {
		return false;
	}

	@Override
	public void updateFlagSummary( FlagSummary flagSummary ) {
		// Iterate through each conversation
		for( ConversationDataControl conversationDataControl : conversationsDataControlList )
			conversationDataControl.updateFlagSummary( flagSummary );
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
}
