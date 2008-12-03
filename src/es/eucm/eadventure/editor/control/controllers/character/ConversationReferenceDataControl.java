package es.eucm.eadventure.editor.control.controllers.character;

import java.util.List;

import es.eucm.eadventure.common.data.chapter.ConversationReference;
import es.eucm.eadventure.editor.control.controllers.ConditionsController;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class ConversationReferenceDataControl extends DataControl {

	/**
	 * Contained conversation reference.
	 */
	private ConversationReference conversationReference;

	/**
	 * Conditions controller.
	 */
	private ConditionsController conditionsController;

	/**
	 * Contructor.
	 * 
	 * @param conversationReference
	 *            Conversation reference of the data control structure
	 */
	public ConversationReferenceDataControl( ConversationReference conversationReference ) {
		this.conversationReference = conversationReference;

		// Create subcontrollers
		conditionsController = new ConditionsController( conversationReference.getConditions( ) );
	}

	/**
	 * Returns the conditions of the conversation reference.
	 * 
	 * @return Conditions of the conversation reference
	 */
	public ConditionsController getConditions( ) {
		return conditionsController;
	}

	/**
	 * Returns the idTarget of the conversations reference.
	 * 
	 * @return idTarget of the conversation reference
	 */
	public String getIdTarget( ) {
		return conversationReference.getIdTarget( );
	}

	/**
	 * Returns the documentation of the conversation reference.
	 * 
	 * @return Conversation reference's documentation
	 */
	public String getDocumentation( ) {
		return conversationReference.getDocumentation( );
	}

	/**
	 * Sets the new idTarget of the conversation reference.
	 * 
	 * @param idTarget
	 *            New id of the conversation reference
	 */
	public void setIdTarget( String idTarget ) {
		// If the value is different
		if( !idTarget.equals( conversationReference.getIdTarget( ) ) ) {
			// Set the new id target, update the tree and modify the data
			conversationReference.setIdTarget( idTarget );
			controller.updateTree( );
			controller.dataModified( );
		}
	}

	/**
	 * Sets the new documentation of the conversation reference.
	 * 
	 * @param documentation
	 *            Documentation of the conversation reference
	 */
	public void setDocumentation( String documentation ) {
		// If the value is different
		if( !documentation.equals( conversationReference.getDocumentation( ) ) ) {
			// Set the new documentation and modify the data
			conversationReference.setDocumentation( documentation );
			controller.dataModified( );
		}
	}

	@Override
	public Object getContent( ) {
		return conversationReference;
	}

	@Override
	public int[] getAddableElements( ) {
		return new int[] {};
	}

	@Override
	public boolean canAddElement( int type ) {
		return false;
	}

	@Override
	public boolean canBeDeleted( ) {
		return true;
	}

	@Override
	public boolean canBeMoved( ) {
		return true;
	}

	@Override
	public boolean canBeRenamed( ) {
		return false;
	}

	@Override
	public boolean addElement( int type ) {
		return false;
	}

	@Override
	public boolean deleteElement( DataControl dataControl ) {
		return false;
	}

	@Override
	public boolean moveElementUp( DataControl dataControl ) {
		return false;
	}

	@Override
	public boolean moveElementDown( DataControl dataControl ) {
		return false;
	}

	@Override
	public boolean renameElement( ) {
		return false;
	}

	@Override
	public void updateFlagSummary( VarFlagSummary varFlagSummary ) {
		// Update the flag summary with the conditions
		ConditionsController.updateFlagSummary( varFlagSummary, conversationReference.getConditions( ) );
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
		return conversationReference.getIdTarget( ).equals( id ) ? 1 : 0;
	}

	@Override
	public void replaceIdentifierReferences( String oldId, String newId ) {
		if( conversationReference.getIdTarget( ).equals( oldId ) )
			conversationReference.setIdTarget( newId );
	}

	@Override
	public void deleteIdentifierReferences( String id ) {
	// Do nothing
	}

	@Override
	public boolean canBeDuplicated( ) {
		// TODO Auto-generated method stub
		return false;
	}

}
