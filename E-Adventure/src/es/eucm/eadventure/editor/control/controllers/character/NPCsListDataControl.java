package es.eucm.eadventure.editor.control.controllers.character;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.elements.NPC;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.item.ItemDataControl;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class NPCsListDataControl extends DataControl {

	/**
	 * List of characters.
	 */
	private List<NPC> npcsList;

	/**
	 * List of character controller.
	 */
	private List<NPCDataControl> npcsDataControlList;

	/**
	 * Constructor.
	 * 
	 * @param npcsList
	 *            List of characters
	 */
	public NPCsListDataControl( List<NPC> npcsList ) {
		this.npcsList = npcsList;

		// Create the subcontrollers
		npcsDataControlList = new ArrayList<NPCDataControl>( );
		for( NPC npc : npcsList )
			npcsDataControlList.add( new NPCDataControl( npc ) );
	}

	/**
	 * Returns the list of NPC controllers.
	 * 
	 * @return NPC controllers
	 */
	public List<NPCDataControl> getNPCs( ) {
		return npcsDataControlList;
	}

	/**
	 * Returns the last NPC controller of the list.
	 * 
	 * @return Last NPC controller
	 */
	public NPCDataControl getLastNPC( ) {
		return npcsDataControlList.get( npcsDataControlList.size( ) - 1 );
	}

	/**
	 * Returns the info of the characters contained in the list.
	 * 
	 * @return Array with the information of the characters. It contains the identifier of each character, and the
	 *         number of conversations
	 */
	public String[][] getNPCsInfo( ) {
		String[][] npcsInfo = null;

		// Create the list for the characters
		npcsInfo = new String[npcsList.size( )][2];

		// Fill the array with the info
		for( int i = 0; i < npcsList.size( ); i++ ) {
			NPC npc = npcsList.get( i );
			npcsInfo[i][0] = npc.getId( );
			npcsInfo[i][1] = TextConstants.getText( "NPCsList.ConversationsNumber", String.valueOf( npc.getConversationReferences( ).size( ) ) );
		}

		return npcsInfo;
	}

	@Override
	public Object getContent( ) {
		return npcsList;
	}

	@Override
	public int[] getAddableElements( ) {
		return new int[] { Controller.NPC };
	}

	@Override
	public boolean canAddElement( int type ) {
		// It can always add new characters
		return type == Controller.NPC;
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

		if( type == Controller.NPC ) {

			// Show a dialog asking for the character id
			String npcId = controller.showInputDialog( TextConstants.getText( "Operation.AddNPCTitle" ), TextConstants.getText( "Operation.AddNPCMessage" ), TextConstants.getText( "Operation.AddNPCDefaultValue" ) );

			// If some value was typed and the identifier is valid
			if( npcId != null && controller.isElementIdValid( npcId ) ) {
				// Add thew new character
				NPC newNPC = new NPC( npcId );
				npcsList.add( newNPC );
				npcsDataControlList.add( new NPCDataControl( newNPC ) );
				controller.getIdentifierSummary( ).addNPCId( npcId );
				controller.dataModified( );
				elementAdded = true;
			}
		}

		return elementAdded;
	}

	@Override
	public boolean deleteElement( DataControl dataControl ) {
		boolean elementDeleted = false;
		String npcId = ( (NPCDataControl) dataControl ).getId( );
		String references = String.valueOf( controller.countIdentifierReferences( npcId ) );

		// Ask for confirmation
		if( controller.showStrictConfirmDialog( TextConstants.getText( "Operation.DeleteElementTitle" ), TextConstants.getText( "Operation.DeleteElementWarning", new String[] { npcId, references } ) ) ) {
			if( npcsList.remove( dataControl.getContent( ) ) ) {
				npcsDataControlList.remove( dataControl );
				controller.deleteIdentifierReferences( npcId );
				controller.getIdentifierSummary( ).deleteNPCId( npcId );
				controller.dataModified( );
				elementDeleted = true;
			}
		}

		return elementDeleted;
	}

	@Override
	public boolean moveElementUp( DataControl dataControl ) {
		boolean elementMoved = false;
		int elementIndex = npcsList.indexOf( dataControl.getContent( ) );

		if( elementIndex > 0 ) {
			npcsList.add( elementIndex - 1, npcsList.remove( elementIndex ) );
			npcsDataControlList.add( elementIndex - 1, npcsDataControlList.remove( elementIndex ) );
			controller.dataModified( );
			elementMoved = true;
		}

		return elementMoved;
	}

	@Override
	public boolean moveElementDown( DataControl dataControl ) {
		boolean elementMoved = false;
		int elementIndex = npcsList.indexOf( dataControl.getContent( ) );

		if( elementIndex < npcsList.size( ) - 1 ) {
			npcsList.add( elementIndex + 1, npcsList.remove( elementIndex ) );
			npcsDataControlList.add( elementIndex + 1, npcsDataControlList.remove( elementIndex ) );
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
	public void updateFlagSummary( VarFlagSummary varFlagSummary ) {
		// Iterate through each character
		for( NPCDataControl npcDataControl : npcsDataControlList )
			npcDataControl.updateFlagSummary( varFlagSummary );
	}

	@Override
	public boolean isValid( String currentPath, List<String> incidences ) {
		boolean valid = true;

		// Update the current path
		currentPath += " >> " + TextConstants.getElementName( Controller.NPCS_LIST );

		// Iterate through the characters
		for( NPCDataControl npcDataControl : npcsDataControlList ) {
			String npcPath = currentPath + " >> " + npcDataControl.getId( );
			valid &= npcDataControl.isValid( npcPath, incidences );
		}

		return valid;
	}

	@Override
	public int countAssetReferences( String assetPath ) {
		int count = 0;

		// Iterate through each character
		for( NPCDataControl npcDataControl : npcsDataControlList )
			count += npcDataControl.countAssetReferences( assetPath );

		return count;
	}
	
	@Override
	public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {
		// Iterate through each item
		for( NPCDataControl npcDataControl : npcsDataControlList )
			npcDataControl.getAssetReferences( assetPaths, assetTypes );
	}


	@Override
	public void deleteAssetReferences( String assetPath ) {
		// Iterate through each character
		for( NPCDataControl npcDataControl : npcsDataControlList )
			npcDataControl.deleteAssetReferences( assetPath );
	}

	@Override
	public int countIdentifierReferences( String id ) {
		int count = 0;

		// Iterate through each character
		for( NPCDataControl npcDataControl : npcsDataControlList )
			count += npcDataControl.countIdentifierReferences( id );

		return count;
	}

	@Override
	public void replaceIdentifierReferences( String oldId, String newId ) {
		// Iterate through each character
		for( NPCDataControl npcDataControl : npcsDataControlList )
			npcDataControl.replaceIdentifierReferences( oldId, newId );
	}

	@Override
	public void deleteIdentifierReferences( String id ) {
		// Spread the call to every character
		for( NPCDataControl npcDataControl : npcsDataControlList )
			npcDataControl.deleteIdentifierReferences( id );
	}

	@Override
	public boolean canBeDuplicated( ) {
		return false;
	}
}
