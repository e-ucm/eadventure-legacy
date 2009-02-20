package es.eucm.eadventure.editor.control.controllers.character;

import java.util.List;

import es.eucm.eadventure.common.data.chapter.ConversationReference;
import es.eucm.eadventure.editor.control.controllers.ConditionsController;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.tools.general.ChangeTargetIdTool;
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
		return conversationReference.getTargetId( );
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
		controller.addTool(new ChangeTargetIdTool(conversationReference, idTarget));
		// If the value is different
		//if( !idTarget.equals( conversationReference.getTargetId( ) ) ) {
			// Set the new id target, update the tree and modify the data
		//	conversationReference.setTargetId( idTarget );
		//	controller.updateTree( );
		//	controller.dataModified( );
		//}
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
	public boolean deleteElement( DataControl dataControl, boolean askConfirmation ) {
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
	public String renameElement( String name ) {
		return null;
	}

	@Override
	public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {
		// Update the flag summary with the conditions
		ConditionsController.updateVarFlagSummary( varFlagSummary, conversationReference.getConditions( ) );
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
		return conversationReference.getTargetId( ).equals( id ) ? 1 : 0;
	}

	@Override
	public void replaceIdentifierReferences( String oldId, String newId ) {
		if( conversationReference.getTargetId( ).equals( oldId ) )
			conversationReference.setTargetId( newId );
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

	@Override
	public void recursiveSearch() {
		check(this.getDocumentation(), "Documentation");
		check(this.getConditions(), "Conditions");
		check(this.getIdTarget(), "ID Target");
	}

}
