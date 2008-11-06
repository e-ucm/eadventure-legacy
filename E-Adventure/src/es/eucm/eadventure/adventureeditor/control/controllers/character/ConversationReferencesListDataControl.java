package es.eucm.eadventure.adventureeditor.control.controllers.character;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.adventureeditor.control.Controller;
import es.eucm.eadventure.adventureeditor.control.controllers.DataControl;
import es.eucm.eadventure.adventureeditor.control.controllers.general.ResourcesDataControl;
import es.eucm.eadventure.adventureeditor.data.chapterdata.ConversationReference;
import es.eucm.eadventure.adventureeditor.data.supportdata.FlagSummary;
import es.eucm.eadventure.adventureeditor.gui.TextConstants;

public class ConversationReferencesListDataControl extends DataControl {

	/**
	 * List of conversation references.
	 */
	private List<ConversationReference> conversationReferencesList;

	/**
	 * Conversation references controllers.
	 */
	private List<ConversationReferenceDataControl> conversationReferencesDataControlList;

	/**
	 * Constructor.
	 * 
	 * @param conversationReferences
	 *            List of conversation references
	 */
	public ConversationReferencesListDataControl( List<ConversationReference> conversationReferences ) {
		this.conversationReferencesList = conversationReferences;

		// Create the subcontrollers
		conversationReferencesDataControlList = new ArrayList<ConversationReferenceDataControl>( );
		for( ConversationReference conversationReference : conversationReferencesList )
			conversationReferencesDataControlList.add( new ConversationReferenceDataControl( conversationReference ) );
	}

	/**
	 * Returns the list of conversation reference controllers.
	 * 
	 * @return Conversation reference controllers
	 */
	public List<ConversationReferenceDataControl> getConversationReferences( ) {
		return conversationReferencesDataControlList;
	}

	/**
	 * Returns the last conversation reference controller of the list.
	 * 
	 * @return Last conversation reference controller
	 */
	public ConversationReferenceDataControl getLastConversationReference( ) {
		return new ConversationReferenceDataControl( conversationReferencesList.get( conversationReferencesList.size( ) - 1 ) );
	}

	/**
	 * Returns the info of the conversation references contained in the list.
	 * 
	 * @return Array with the information of the conversation references. It contains the identifier of the referenced
	 *         conversation, and if this reference has conditions or not
	 */
	public String[][] getConversationReferencesInfo( ) {
		String[][] conversationReferencesInfo = null;

		// Create the list for the conversation references
		conversationReferencesInfo = new String[conversationReferencesList.size( )][3];

		// Fill the array with the info
		for( int i = 0; i < conversationReferencesList.size( ); i++ ) {
			ConversationReference conversationReference = conversationReferencesList.get( i );
			conversationReferencesInfo[i][0] = conversationReference.getIdTarget( );

			if( conversationReference.getConditions( ).isEmpty( ) )
				conversationReferencesInfo[i][1] = TextConstants.getText( "GeneralText.No" );
			else
				conversationReferencesInfo[i][1] = TextConstants.getText( "GeneralText.Yes" );
		}

		return conversationReferencesInfo;
	}

	@Override
	public Object getContent( ) {
		return conversationReferencesList;
	}

	@Override
	public int[] getAddableElements( ) {
		return new int[] { Controller.CONVERSATION_REFERENCE };

	}

	@Override
	public boolean canAddElement( int type ) {
		// It can always add new conversation references
		return type == Controller.CONVERSATION_REFERENCE;
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

		if( type == Controller.CONVERSATION_REFERENCE ) {
			// Take the list of the conversations
			String[] conversations = controller.getIdentifierSummary( ).getConversationsIds( );

			// If the list has elements, show the dialog with the options
			if( conversations.length > 0 ) {
				String selectedConversation = controller.showInputDialog( TextConstants.getText( "Operation.AddConversationReferenceTitle" ), TextConstants.getText( "Operation.AddConversationReferenceMessage" ), conversations );

				// If some value was selected
				if( selectedConversation != null ) {
					ConversationReference newConversationReference = new ConversationReference( selectedConversation );
					conversationReferencesList.add( newConversationReference );
					conversationReferencesDataControlList.add( new ConversationReferenceDataControl( newConversationReference ) );
					controller.dataModified( );
					elementAdded = true;
				}
			}

			// If the list had no elements, show an error dialog
			else
				controller.showErrorDialog( TextConstants.getText( "Operation.AddConversationReferenceTitle" ), TextConstants.getText( "Operation.AddConversationReferenceErrorNoConversations" ) );
		}

		return elementAdded;
	}

	@Override
	public boolean deleteElement( DataControl dataControl ) {
		boolean elementDeleted = false;

		if( conversationReferencesList.remove( dataControl.getContent( ) ) ) {
			conversationReferencesDataControlList.remove( dataControl );
			controller.dataModified( );
			elementDeleted = true;
		}

		return elementDeleted;
	}

	@Override
	public boolean moveElementUp( DataControl dataControl ) {
		boolean elementMoved = false;
		int elementIndex = conversationReferencesList.indexOf( dataControl.getContent( ) );

		if( elementIndex > 0 ) {
			conversationReferencesList.add( elementIndex - 1, conversationReferencesList.remove( elementIndex ) );
			conversationReferencesDataControlList.add( elementIndex - 1, conversationReferencesDataControlList.remove( elementIndex ) );
			controller.dataModified( );
			elementMoved = true;
		}

		return elementMoved;
	}

	@Override
	public boolean moveElementDown( DataControl dataControl ) {
		boolean elementMoved = false;
		int elementIndex = conversationReferencesList.indexOf( dataControl.getContent( ) );

		if( elementIndex < conversationReferencesList.size( ) - 1 ) {
			conversationReferencesList.add( elementIndex + 1, conversationReferencesList.remove( elementIndex ) );
			conversationReferencesDataControlList.add( elementIndex + 1, conversationReferencesDataControlList.remove( elementIndex ) );
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
		// Iterate through the conversation references
		for( ConversationReferenceDataControl conversationReferenceDataControl : conversationReferencesDataControlList )
			conversationReferenceDataControl.updateFlagSummary( flagSummary );
	}

	@Override
	public boolean isValid( String currentPath, List<String> incidences ) {
		return true;
	}

	@Override
	public int countAssetReferences( String assetPath ) {
		return 0;
	}
	
	@Override
	public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {
		// Do nothing
	}

	@Override
	public void deleteAssetReferences( String assetPath ) {
	// Do nothing
	}

	@Override
	public int countIdentifierReferences( String id ) {
		int count = 0;

		// Iterate through the conversation references
		for( ConversationReferenceDataControl conversationReferenceDataControl : conversationReferencesDataControlList )
			count += conversationReferenceDataControl.countIdentifierReferences( id );

		return count;
	}

	@Override
	public void replaceIdentifierReferences( String oldId, String newId ) {
		// Iterate through the conversation references
		for( ConversationReferenceDataControl conversationReferenceDataControl : conversationReferencesDataControlList )
			conversationReferenceDataControl.replaceIdentifierReferences( oldId, newId );
	}

	@Override
	public void deleteIdentifierReferences( String id ) {
		int i = 0;

		// Check every conversation reference
		while( i < conversationReferencesList.size( ) ) {
			if( conversationReferencesList.get( i ).getIdTarget( ).equals( id ) ) {
				conversationReferencesList.remove( i );
				conversationReferencesDataControlList.remove( i );
			}

			else
				i++;
		}
	}

	@Override
	public boolean canBeDuplicated( ) {
		// TODO Auto-generated method stub
		return false;
	}
}
