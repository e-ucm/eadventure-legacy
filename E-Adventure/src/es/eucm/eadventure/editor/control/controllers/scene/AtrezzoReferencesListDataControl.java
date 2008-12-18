package es.eucm.eadventure.editor.control.controllers.scene;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.ElementReference;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class AtrezzoReferencesListDataControl extends DataControl {

	/**
	 * Scene controller that contains this element reference.
	 */
	private SceneDataControl sceneDataControl;

	/**
	 * List of atrezzo item references.
	 */
	private List<ElementReference> atrezzoReferencesList;

	/**
	 * List of atrezzo item reference controllers.
	 */
	private List<ElementReferenceDataControl> atrezzoReferencesDataControlList;

	/**
	 * Constructor.
	 * 
	 * @param sceneDataControl
	 *            Link to the parent scene controller
	 * @param atrezzoReferencesList
	 *            List of item references
	 */
	public AtrezzoReferencesListDataControl( SceneDataControl sceneDataControl, List<ElementReference> atrezzoReferencesList ) {
		this.sceneDataControl = sceneDataControl;
		this.atrezzoReferencesList = atrezzoReferencesList;

		// Create subcontrollers
		atrezzoReferencesDataControlList = new ArrayList<ElementReferenceDataControl>( );
		for( ElementReference atrezzoReference : atrezzoReferencesList )
			atrezzoReferencesDataControlList.add( new ElementReferenceDataControl( sceneDataControl, atrezzoReference ) );
	}

	/**
	 * Returns the list of atrezzo item reference controllers.
	 * 
	 * @return List of atrezzo item reference controllers
	 */
	public List<ElementReferenceDataControl> getAtrezzoReferences( ) {
		return atrezzoReferencesDataControlList;
	}

	/**
	 * Returns the last atrezzo item reference controller of the list.
	 * 
	 * @return Last atrezzo item reference controller
	 */
	public ElementReferenceDataControl getLastAtrezzoReference( ) {
		return atrezzoReferencesDataControlList.get( atrezzoReferencesDataControlList.size( ) - 1 );
	}

	/**
	 * Returns the id of the scene that contains this atrezzo item references list.
	 * 
	 * @return Parent scene id
	 */
	public String getParentSceneId( ) {
		return sceneDataControl.getId( );
	}

	@Override
	public Object getContent( ) {
		return atrezzoReferencesList;
	}

	@Override
	public int[] getAddableElements( ) {
		return new int[] { Controller.ATREZZO_REFERENCE };
	}

	@Override
	public boolean canAddElement( int type ) {
		// It can always add new NPC references
		return type == Controller.ATREZZO_REFERENCE;
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

		if( type == Controller.ATREZZO_REFERENCE ) {
			// Take the list of the atrezzo items
			String[] items = controller.getIdentifierSummary( ).getAtrezzoIds( );

			// If the list has elements, show the dialog with the options
			if( items.length > 0 ) {
				String selectedItem = controller.showInputDialog( TextConstants.getText( "Operation.AddAtrezzoReferenceTitle" ), TextConstants.getText( "Operation.AddAtrezzoReferenceMessage" ), items );

				// If some value was selected
				if( selectedItem != null ) {
					ElementReference newElementReference = new ElementReference( selectedItem, 0, 0 );
					atrezzoReferencesList.add( newElementReference );
					atrezzoReferencesDataControlList.add( new ElementReferenceDataControl( sceneDataControl, newElementReference ) );
					controller.dataModified( );
					elementAdded = true;
				}
			}

			// If the list had no elements, show an error dialog
			else
				controller.showErrorDialog( TextConstants.getText( "Operation.AddAtrezzoReferenceTitle" ), TextConstants.getText( "Operation.AddAtrezzoReferenceErrorNoAtrezzo" ) );
		}

		return elementAdded;
	}

	@Override
	public boolean deleteElement( DataControl dataControl ) {
		boolean elementDeleted = false;

		if( atrezzoReferencesList.remove( dataControl.getContent( ) ) ) {
			atrezzoReferencesDataControlList.remove( dataControl );
			controller.dataModified( );
			elementDeleted = true;
		}

		return elementDeleted;
	}

	@Override
	public boolean moveElementUp( DataControl dataControl ) {
		boolean elementMoved = false;
		int elementIndex = atrezzoReferencesList.indexOf( dataControl.getContent( ) );

		if( elementIndex > 0 ) {
			atrezzoReferencesList.add( elementIndex - 1, atrezzoReferencesList.remove( elementIndex ) );
			atrezzoReferencesDataControlList.add( elementIndex - 1, atrezzoReferencesDataControlList.remove( elementIndex ) );
			controller.dataModified( );
			elementMoved = true;
		}

		return elementMoved;
	}

	@Override
	public boolean moveElementDown( DataControl dataControl ) {
		boolean elementMoved = false;
		int elementIndex = atrezzoReferencesList.indexOf( dataControl.getContent( ) );

		if( elementIndex < atrezzoReferencesList.size( ) - 1 ) {
			atrezzoReferencesList.add( elementIndex + 1, atrezzoReferencesList.remove( elementIndex ) );
			atrezzoReferencesDataControlList.add( elementIndex + 1, atrezzoReferencesDataControlList.remove( elementIndex ) );
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
	public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {
		// Iterate through each item
		/*for( ElementReferenceDataControl elementReferenceDataControl : atrezzoReferencesDataControlList )
			elementReferenceDataControl.updateVarFlagSummary( varFlagSummary );*/
		// Atrezzo items doesn´t have var/flags
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

		// Iterate through each item
		for( ElementReferenceDataControl elementReferenceDataControl : atrezzoReferencesDataControlList )
			count += elementReferenceDataControl.countIdentifierReferences( id );

		return count;
	}

	@Override
	public void replaceIdentifierReferences( String oldId, String newId ) {
		// Iterate through each item
		for( ElementReferenceDataControl elementReferenceDataControl : atrezzoReferencesDataControlList )
			elementReferenceDataControl.replaceIdentifierReferences( oldId, newId );
	}

	@Override
	public void deleteIdentifierReferences( String id ) {
		int i = 0;

		// Check every item reference
		while( i < atrezzoReferencesList.size( ) ) {
			if( atrezzoReferencesList.get( i ).getIdTarget( ).equals( id ) ) {
				atrezzoReferencesList.remove( i );
				atrezzoReferencesDataControlList.remove( i );
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
