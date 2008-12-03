package es.eucm.eadventure.editor.control.controllers.scene;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.ElementReference;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class ItemReferencesListDataControl extends DataControl {

	/**
	 * Scene controller that contains this element reference.
	 */
	private SceneDataControl sceneDataControl;

	/**
	 * List of item references.
	 */
	private List<ElementReference> itemReferencesList;

	/**
	 * List of item reference controllers.
	 */
	private List<ElementReferenceDataControl> itemReferencesDataControlList;

	/**
	 * Constructor.
	 * 
	 * @param sceneDataControl
	 *            Link to the parent scene controller
	 * @param itemReferencesList
	 *            List of item references
	 */
	public ItemReferencesListDataControl( SceneDataControl sceneDataControl, List<ElementReference> itemReferencesList ) {
		this.sceneDataControl = sceneDataControl;
		this.itemReferencesList = itemReferencesList;

		// Create subcontrollers
		itemReferencesDataControlList = new ArrayList<ElementReferenceDataControl>( );
		for( ElementReference itemReference : itemReferencesList )
			itemReferencesDataControlList.add( new ElementReferenceDataControl( sceneDataControl, itemReference ) );
	}

	/**
	 * Returns the list of item reference controllers.
	 * 
	 * @return List of item reference controllers
	 */
	public List<ElementReferenceDataControl> getItemReferences( ) {
		return itemReferencesDataControlList;
	}

	/**
	 * Returns the last item reference controller of the list.
	 * 
	 * @return Last item reference controller
	 */
	public ElementReferenceDataControl getLastItemReference( ) {
		return itemReferencesDataControlList.get( itemReferencesDataControlList.size( ) - 1 );
	}

	/**
	 * Returns the id of the scene that contains this item references list.
	 * 
	 * @return Parent scene id
	 */
	public String getParentSceneId( ) {
		return sceneDataControl.getId( );
	}

	@Override
	public Object getContent( ) {
		return itemReferencesList;
	}

	@Override
	public int[] getAddableElements( ) {
		return new int[] { Controller.ITEM_REFERENCE };
	}

	@Override
	public boolean canAddElement( int type ) {
		// It can always add new NPC references
		return type == Controller.ITEM_REFERENCE;
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

		if( type == Controller.ITEM_REFERENCE ) {
			// Take the list of the items
			String[] items = controller.getIdentifierSummary( ).getItemIds( );

			// If the list has elements, show the dialog with the options
			if( items.length > 0 ) {
				String selectedItem = controller.showInputDialog( TextConstants.getText( "Operation.AddItemReferenceTitle" ), TextConstants.getText( "Operation.AddItemReferenceMessage" ), items );

				// If some value was selected
				if( selectedItem != null ) {
					ElementReference newElementReference = new ElementReference( selectedItem, 0, 0 );
					itemReferencesList.add( newElementReference );
					itemReferencesDataControlList.add( new ElementReferenceDataControl( sceneDataControl, newElementReference ) );
					controller.dataModified( );
					elementAdded = true;
				}
			}

			// If the list had no elements, show an error dialog
			else
				controller.showErrorDialog( TextConstants.getText( "Operation.AddItemReferenceTitle" ), TextConstants.getText( "Operation.AddItemReferenceErrorNoItems" ) );
		}

		return elementAdded;
	}

	@Override
	public boolean deleteElement( DataControl dataControl ) {
		boolean elementDeleted = false;

		if( itemReferencesList.remove( dataControl.getContent( ) ) ) {
			itemReferencesDataControlList.remove( dataControl );
			controller.dataModified( );
			elementDeleted = true;
		}

		return elementDeleted;
	}

	@Override
	public boolean moveElementUp( DataControl dataControl ) {
		boolean elementMoved = false;
		int elementIndex = itemReferencesList.indexOf( dataControl.getContent( ) );

		if( elementIndex > 0 ) {
			itemReferencesList.add( elementIndex - 1, itemReferencesList.remove( elementIndex ) );
			itemReferencesDataControlList.add( elementIndex - 1, itemReferencesDataControlList.remove( elementIndex ) );
			controller.dataModified( );
			elementMoved = true;
		}

		return elementMoved;
	}

	@Override
	public boolean moveElementDown( DataControl dataControl ) {
		boolean elementMoved = false;
		int elementIndex = itemReferencesList.indexOf( dataControl.getContent( ) );

		if( elementIndex < itemReferencesList.size( ) - 1 ) {
			itemReferencesList.add( elementIndex + 1, itemReferencesList.remove( elementIndex ) );
			itemReferencesDataControlList.add( elementIndex + 1, itemReferencesDataControlList.remove( elementIndex ) );
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
		// Iterate through each item
		for( ElementReferenceDataControl elementReferenceDataControl : itemReferencesDataControlList )
			elementReferenceDataControl.updateFlagSummary( varFlagSummary );
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
		for( ElementReferenceDataControl elementReferenceDataControl : itemReferencesDataControlList )
			count += elementReferenceDataControl.countIdentifierReferences( id );

		return count;
	}

	@Override
	public void replaceIdentifierReferences( String oldId, String newId ) {
		// Iterate through each item
		for( ElementReferenceDataControl elementReferenceDataControl : itemReferencesDataControlList )
			elementReferenceDataControl.replaceIdentifierReferences( oldId, newId );
	}

	@Override
	public void deleteIdentifierReferences( String id ) {
		int i = 0;

		// Check every item reference
		while( i < itemReferencesList.size( ) ) {
			if( itemReferencesList.get( i ).getIdTarget( ).equals( id ) ) {
				itemReferencesList.remove( i );
				itemReferencesDataControlList.remove( i );
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
