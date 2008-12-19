package es.eucm.eadventure.editor.control.controllers.scene;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.ElementReference;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class ReferencesListDataControl extends DataControl {

	/**
	 * Scene controller that contains this element reference.
	 */
	private SceneDataControl sceneDataControl;

	/**
	 * List of item references.
	 */
	private List<ElementReference> itemReferencesList;
	
	private List<ElementReference> atrezzoReferencesList;
	
	private List<ElementReference> npcReferencesList;

	/**
	 * List of item reference controllers.
	 */
	private List<ElementReferenceDataControl> itemReferencesDataControlList;

	private List<ElementReferenceDataControl> atrezzoReferencesDataControlList;
	
	private List<ElementReferenceDataControl> npcReferencesDataControlList;
	
	
	/**
	 * Constructor.
	 * 
	 * @param sceneDataControl
	 *            Link to the parent scene controller
	 * @param itemReferencesList
	 *            List of item references
	 */
	public ReferencesListDataControl( SceneDataControl sceneDataControl, List<ElementReference> itemReferencesList
										, List<ElementReference> atrezzoReferencesList, List<ElementReference> npcReferencesList) {
		this.sceneDataControl = sceneDataControl;
		this.itemReferencesList = itemReferencesList;
		this.atrezzoReferencesList = atrezzoReferencesList;
		this.npcReferencesList = npcReferencesList;

		// Create subcontrollers
		itemReferencesDataControlList = new ArrayList<ElementReferenceDataControl>( );
		for( ElementReference itemReference : itemReferencesList )
			itemReferencesDataControlList.add( new ElementReferenceDataControl( sceneDataControl, itemReference ) );
		
		atrezzoReferencesDataControlList = new ArrayList<ElementReferenceDataControl>();
		for (ElementReference atrezzoReference : atrezzoReferencesList)
			atrezzoReferencesDataControlList.add( new ElementReferenceDataControl(sceneDataControl, atrezzoReference));
		
		npcReferencesDataControlList = new ArrayList<ElementReferenceDataControl>();
		for (ElementReference npcReference : npcReferencesList)
			npcReferencesDataControlList.add( new ElementReferenceDataControl(sceneDataControl, npcReference));
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
	 * Returns the list of npc reference controllers.
	 * 
	 * @return List of npc reference controllers
	 */
	public List<ElementReferenceDataControl> getNPCReferences( ) {
		return npcReferencesDataControlList;
	}

	/**
	 * Returns the last npc reference controller of the list.
	 * 
	 * @return Last npc reference controller
	 */
	public ElementReferenceDataControl getLastNPCReference( ) {
		return npcReferencesDataControlList.get( npcReferencesDataControlList.size( ) - 1 );
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
		return new int[] { Controller.ITEM_REFERENCE , Controller.ATREZZO_REFERENCE, Controller.NPC_REFERENCE};
	}

	@Override
	public boolean canAddElement( int type ) {
		// It can always add new NPC references
		return type == Controller.ITEM_REFERENCE || type == Controller.ATREZZO_REFERENCE || type == Controller.NPC_REFERENCE;
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

		
		if( type == Controller.ATREZZO_REFERENCE ) {
			// Take the list of the items
			String[] items = controller.getIdentifierSummary( ).getAtrezzoIds();

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
				controller.showErrorDialog( TextConstants.getText( "Operation.AddAtrezzoReferenceTitle" ), TextConstants.getText( "Operation.AddReferenceErrorNoAtrezzo" ) );
		}

		if( type == Controller.NPC_REFERENCE ) {
			// Take the list of the items
			String[] items = controller.getIdentifierSummary( ).getNPCIds();

			// If the list has elements, show the dialog with the options
			if( items.length > 0 ) {
				String selectedItem = controller.showInputDialog( TextConstants.getText( "Operation.AddNPCReferenceTitle" ), TextConstants.getText( "Operation.AddNPCReferenceMessage" ), items );

				// If some value was selected
				if( selectedItem != null ) {
					ElementReference newElementReference = new ElementReference( selectedItem, 0, 0 );
					npcReferencesList.add( newElementReference );
					npcReferencesDataControlList.add( new ElementReferenceDataControl( sceneDataControl, newElementReference ) );
					controller.dataModified( );
					elementAdded = true;
				}
			}

			// If the list had no elements, show an error dialog
			else
				controller.showErrorDialog( TextConstants.getText( "Operation.AddNPCReferenceTitle" ), TextConstants.getText( "Operation.AddReferenceErrorNoNPC" ) );
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

		if( atrezzoReferencesList.remove( dataControl.getContent( ) ) ) {
			atrezzoReferencesDataControlList.remove( dataControl );
			controller.dataModified( );
			elementDeleted = true;
		}

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
		int itemElementIndex = itemReferencesList.indexOf( dataControl.getContent( ) );
		int atrezzoElementIndex = atrezzoReferencesList.indexOf( dataControl.getContent( ) );
		int npcElementIndex = npcReferencesList.indexOf( dataControl.getContent( ) );
		
		if( itemElementIndex > 0 ) {
			itemReferencesList.add( itemElementIndex - 1, itemReferencesList.remove( itemElementIndex ) );
			itemReferencesDataControlList.add( itemElementIndex - 1, itemReferencesDataControlList.remove( itemElementIndex ) );
			controller.dataModified( );
			elementMoved = true;
		} else if (atrezzoElementIndex > 0) {
			atrezzoReferencesList.add( atrezzoElementIndex - 1, atrezzoReferencesList.remove( atrezzoElementIndex ) );
			atrezzoReferencesDataControlList.add( atrezzoElementIndex - 1, atrezzoReferencesDataControlList.remove( atrezzoElementIndex ) );
			controller.dataModified( );
			elementMoved = true;
		} else if (npcElementIndex > 0) {
			npcReferencesList.add( npcElementIndex - 1, npcReferencesList.remove( npcElementIndex ) );
			npcReferencesDataControlList.add( npcElementIndex - 1, npcReferencesDataControlList.remove( npcElementIndex ) );
			controller.dataModified( );
			elementMoved = true;
		}

		return elementMoved;
	}

	@Override
	public boolean moveElementDown( DataControl dataControl ) {
		boolean elementMoved = false;
		int itemElementIndex = itemReferencesList.indexOf( dataControl.getContent( ) );
		int atrezzoElementIndex = atrezzoReferencesList.indexOf( dataControl.getContent());
		int npcElementIndex = npcReferencesList.indexOf( dataControl.getContent());
		
		if( itemElementIndex >= 0 && itemElementIndex < itemReferencesList.size( ) - 1 ) {
			itemReferencesList.add( itemElementIndex + 1, itemReferencesList.remove( itemElementIndex ) );
			itemReferencesDataControlList.add( itemElementIndex + 1, itemReferencesDataControlList.remove( itemElementIndex ) );
			controller.dataModified( );
			elementMoved = true;
		} else if( atrezzoElementIndex >= 0 && atrezzoElementIndex < atrezzoReferencesList.size( ) - 1 ) {
			atrezzoReferencesList.add( atrezzoElementIndex + 1, atrezzoReferencesList.remove( atrezzoElementIndex ) );
			atrezzoReferencesDataControlList.add( atrezzoElementIndex + 1, atrezzoReferencesDataControlList.remove( atrezzoElementIndex ) );
			controller.dataModified( );
			elementMoved = true;
		} else if( npcElementIndex >= 0 && npcElementIndex < npcReferencesList.size( ) - 1 ) {
			npcReferencesList.add( npcElementIndex + 1, npcReferencesList.remove( npcElementIndex ) );
			npcReferencesDataControlList.add( npcElementIndex + 1, npcReferencesDataControlList.remove( npcElementIndex ) );
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
		for( ElementReferenceDataControl elementReferenceDataControl : itemReferencesDataControlList )
			elementReferenceDataControl.updateVarFlagSummary( varFlagSummary );
		for( ElementReferenceDataControl elementReferenceDataControl : atrezzoReferencesDataControlList )
			elementReferenceDataControl.updateVarFlagSummary( varFlagSummary );
		for( ElementReferenceDataControl elementReferenceDataControl : npcReferencesDataControlList )
			elementReferenceDataControl.updateVarFlagSummary( varFlagSummary );
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
		for( ElementReferenceDataControl elementReferenceDataControl : atrezzoReferencesDataControlList )
			count += elementReferenceDataControl.countIdentifierReferences( id );
		for( ElementReferenceDataControl elementReferenceDataControl : npcReferencesDataControlList )
			count += elementReferenceDataControl.countIdentifierReferences( id );

		return count;
	}

	@Override
	public void replaceIdentifierReferences( String oldId, String newId ) {
		// Iterate through each item
		for( ElementReferenceDataControl elementReferenceDataControl : itemReferencesDataControlList )
			elementReferenceDataControl.replaceIdentifierReferences( oldId, newId );
		for( ElementReferenceDataControl elementReferenceDataControl : atrezzoReferencesDataControlList )
			elementReferenceDataControl.replaceIdentifierReferences( oldId, newId );
		for( ElementReferenceDataControl elementReferenceDataControl : npcReferencesDataControlList )
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
			} else
				i++;
		}
		
		i = 0;
		// Check every item reference
		while( i < atrezzoReferencesList.size( ) ) {
			if( atrezzoReferencesList.get( i ).getIdTarget( ).equals( id ) ) {
				atrezzoReferencesList.remove( i );
				atrezzoReferencesDataControlList.remove( i );
			} else
				i++;
		}

		i = 0;
		// Check every item reference
		while( i < npcReferencesList.size( ) ) {
			if( npcReferencesList.get( i ).getIdTarget( ).equals( id ) ) {
				npcReferencesList.remove( i );
				npcReferencesDataControlList.remove( i );
			} else
				i++;
		}
	}

	@Override
	public boolean canBeDuplicated( ) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean containsDataControl(ElementReferenceDataControl dataControl) {
		if (itemReferencesDataControlList.contains(dataControl))
			return true;
		if (atrezzoReferencesDataControlList.contains(dataControl))
			return true;
		if (npcReferencesDataControlList.contains(dataControl))
			return true;
		return false;
	}
}
