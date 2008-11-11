package es.eucm.eadventure.editor.control.controllers.scene;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapterdata.ElementReference;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.data.supportdata.FlagSummary;
import es.eucm.eadventure.editor.gui.TextConstants;

public class NPCReferencesListDataControl extends DataControl {

	/**
	 * Scene controller that contains this element reference.
	 */
	private SceneDataControl sceneDataControl;

	/**
	 * List of NPC references.
	 */
	private List<ElementReference> npcReferencesList;

	/**
	 * List of npc reference controllers.
	 */
	private List<ElementReferenceDataControl> npcReferencesDataControlList;

	/**
	 * Constructor.
	 * 
	 * @param sceneDataControl
	 *            Link to the parent scene controller
	 * @param npcReferencesList
	 *            List of NPC references
	 */
	public NPCReferencesListDataControl( SceneDataControl sceneDataControl, List<ElementReference> npcReferencesList ) {
		this.sceneDataControl = sceneDataControl;
		this.npcReferencesList = npcReferencesList;

		// Create subcontrollers
		npcReferencesDataControlList = new ArrayList<ElementReferenceDataControl>( );
		for( ElementReference npcReference : npcReferencesList )
			npcReferencesDataControlList.add( new ElementReferenceDataControl( sceneDataControl, npcReference ) );
	}

	/**
	 * Returns the list of NPC reference controllers.
	 * 
	 * @return List of NPC reference controllers
	 */
	public List<ElementReferenceDataControl> getNPCReferences( ) {
		return npcReferencesDataControlList;
	}

	/**
	 * Returns the last NPC reference controller in the list.
	 * 
	 * @return Last NPC reference controller
	 */
	public ElementReferenceDataControl getLastNPCReference( ) {
		return npcReferencesDataControlList.get( npcReferencesDataControlList.size( ) - 1 );
	}

	/**
	 * Returns the id of the scene that contains this NPC references list.
	 * 
	 * @return Parent scene id
	 */
	public String getParentSceneId( ) {
		return sceneDataControl.getId( );
	}

	@Override
	public Object getContent( ) {
		return npcReferencesList;
	}

	@Override
	public int[] getAddableElements( ) {
		return new int[] { Controller.NPC_REFERENCE };
	}

	@Override
	public boolean canAddElement( int type ) {
		// It can always add new NPC references
		return type == Controller.NPC_REFERENCE;
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

		if( type == Controller.NPC_REFERENCE ) {
			// Take the list of the characters
			String[] npcs = controller.getIdentifierSummary( ).getNPCIds( );

			// If the list has elements, show the dialog with the options
			if( npcs.length > 0 ) {
				String selectedNPC = controller.showInputDialog( TextConstants.getText( "Operation.AddNPCReferenceTitle" ), TextConstants.getText( "Operation.AddNPCReferenceMessage" ), npcs );

				// If some value was selected
				if( selectedNPC != null ) {
					ElementReference newElementReference = new ElementReference( selectedNPC, 0, 0 );
					npcReferencesList.add( newElementReference );
					npcReferencesDataControlList.add( new ElementReferenceDataControl( sceneDataControl, newElementReference ) );
					controller.dataModified( );
					elementAdded = true;
				}
			}

			// If the list had no elements, show an error dialog
			else
				controller.showErrorDialog( TextConstants.getText( "Operation.AddNPCReferenceTitle" ), TextConstants.getText( "Operation.AddNPCReferenceErrorNoNPCs" ) );
		}

		return elementAdded;
	}

	@Override
	public boolean deleteElement( DataControl dataControl ) {
		boolean elementDeleted = false;

		if( npcReferencesList.remove( dataControl.getContent( ) ) ) {
			npcReferencesDataControlList.remove( dataControl );
			controller.dataModified( );
			elementDeleted = true;
		}

		return elementDeleted;
	}

	@Override
	public boolean moveElementUp( DataControl dataControl ) {
		boolean elementMoved = false;
		int elementIndex = npcReferencesList.indexOf( dataControl.getContent( ) );

		if( elementIndex > 0 ) {
			npcReferencesList.add( elementIndex - 1, npcReferencesList.remove( elementIndex ) );
			npcReferencesDataControlList.add( elementIndex - 1, npcReferencesDataControlList.remove( elementIndex ) );
			controller.dataModified( );
			elementMoved = true;
		}

		return elementMoved;
	}

	@Override
	public boolean moveElementDown( DataControl dataControl ) {
		boolean elementMoved = false;
		int elementIndex = npcReferencesList.indexOf( dataControl.getContent( ) );

		if( elementIndex < npcReferencesList.size( ) - 1 ) {
			npcReferencesList.add( elementIndex + 1, npcReferencesList.remove( elementIndex ) );
			npcReferencesDataControlList.add( elementIndex + 1, npcReferencesDataControlList.remove( elementIndex ) );
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
		// Iterate through each character
		for( ElementReferenceDataControl elementReferenceDataControl : npcReferencesDataControlList )
			elementReferenceDataControl.updateFlagSummary( flagSummary );
	}

	@Override
	public boolean isValid( String currentPath, List<String> incidences ) {
		return true;
	}

	@Override
	public int countAssetReferences( String assetPath ) {
		return 0;
	}
	
	public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes){
		// Do nothing
	}

	@Override
	public void deleteAssetReferences( String assetPath ) {
	// Do nothing
	}

	@Override
	public int countIdentifierReferences( String id ) {
		int count = 0;

		// Iterate through each character
		for( ElementReferenceDataControl elementReferenceDataControl : npcReferencesDataControlList )
			count += elementReferenceDataControl.countIdentifierReferences( id );

		return count;
	}

	@Override
	public void replaceIdentifierReferences( String oldId, String newId ) {
		// Iterate through each character
		for( ElementReferenceDataControl elementReferenceDataControl : npcReferencesDataControlList )
			elementReferenceDataControl.replaceIdentifierReferences( oldId, newId );
	}

	@Override
	public void deleteIdentifierReferences( String id ) {
		int i = 0;

		// Check every character reference
		while( i < npcReferencesList.size( ) ) {
			if( npcReferencesList.get( i ).getIdTarget( ).equals( id ) ) {
				npcReferencesList.remove( i );
				npcReferencesDataControlList.remove( i );
			}

			else
				i++;
		}
	}

	@Override
	public boolean canBeDuplicated( ) {
		return false;
	}
}
