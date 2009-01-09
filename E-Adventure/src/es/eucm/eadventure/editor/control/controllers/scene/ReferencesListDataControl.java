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
	
	/**
	 * List of atrezzo references.
	 */
	private List<ElementReference> atrezzoReferencesList;
	
	/**
	 * List of non-player character references.
	 */
	private List<ElementReference> npcReferencesList;

	/**
	 * List of item reference controllers.
	 */
	private List<ElementReferenceDataControl> itemReferencesDataControlList;

	/**
	 * List of atrezzo reference controllers.
	 */
	private List<ElementReferenceDataControl> atrezzoReferencesDataControlList;
	
	/**
	 * List of non-player character reference controllers.
	 */
	private List<ElementReferenceDataControl> npcReferencesDataControlList;
	
	/**
	 * List of all elements order by number of layer (or y position when they have the same layer "-1")
	 */
	private List<ElementReference> allReferences;
	
	/**
	 * List of all elements order by number of layer (or y position when they have the same layer "-1")
	 */
	private List<ElementReferenceDataControl> allReferencesDataControl;
	
	/**
	 * The last introduced element referenced 
	 */
	private ElementReferenceDataControl lastElementReferenceDataControl;
	
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
		this.allReferences = new ArrayList<ElementReference>();
		this.allReferencesDataControl = new ArrayList<ElementReferenceDataControl>();
		this.lastElementReferenceDataControl = null;
		// Check if one of references has layer -1: if it is true, it means that element references has not layer. 
		// Create subcontrollers
		itemReferencesDataControlList = new ArrayList<ElementReferenceDataControl>( );
		boolean hasLayer = hasLayer();
		for( ElementReference itemReference : itemReferencesList ){
			ElementReferenceDataControl erdc = new ElementReferenceDataControl( sceneDataControl, itemReference, Controller.ITEM_REFERENCE) ;
			itemReferencesDataControlList.add(erdc );
			insertInOrder(erdc,hasLayer);
			insertInOrder(itemReference,hasLayer);
		}
		
		atrezzoReferencesDataControlList = new ArrayList<ElementReferenceDataControl>();
		for (ElementReference atrezzoReference : atrezzoReferencesList){
			ElementReferenceDataControl erdc = new ElementReferenceDataControl(sceneDataControl, atrezzoReference, Controller.ATREZZO_REFERENCE);
			atrezzoReferencesDataControlList.add(erdc );
			insertInOrder(erdc,hasLayer);
			insertInOrder(atrezzoReference, hasLayer);
		}
		npcReferencesDataControlList = new ArrayList<ElementReferenceDataControl>();
		for (ElementReference npcReference : npcReferencesList){
			ElementReferenceDataControl erdc  =  new ElementReferenceDataControl(sceneDataControl, npcReference, Controller.NPC_REFERENCE);
			npcReferencesDataControlList.add(erdc);
			insertInOrder(erdc,hasLayer);
			insertInOrder(npcReference,hasLayer());
		}
	}
	
	/**
	 * This method analyze the references finding if references has layer. It is easy, we must only inspect one reference.
	 * If this reference has the value "-1" in it "layer" attribute, it means that neither of elements has layer.  
	 * 
	 * @return
	 * 			true, if there are not one references with -1
	 */
	private boolean hasLayer(){
	 
		
		if (!itemReferencesList.isEmpty()){
			if (itemReferencesList.get(0).getLayer() == -1)
				return false;
			else 
				return true;
		}else if (!atrezzoReferencesList.isEmpty()){
				if (atrezzoReferencesList.get(0).getLayer() == -1)
					return false;
				else 
					return true;
		}if (!npcReferencesList.isEmpty()){
			if (npcReferencesList.get(0).getLayer() == -1)
				return false;
			else 
				return true;
		}
		return false;
	}
	
	
	/**
	 * Insert in order in allReferences attribute
	 * 
	 * @param element
	 * 				The element reference to be added
	 * @param	hasLayer
	 * 				Take either layer or depth value to order value
	 **/
	public void insertInOrder(ElementReference element, boolean hasLayer){
		boolean added = false;
        int i = 0;
        boolean empty = allReferences.size()==0 ;
        // While the element has not been added, and
        // we haven't checked every previous element
        while( !added && (i < allReferences.size( ) || empty) )  {
            
            // Insert the element in the correct position
        	if (hasLayer){
        		if (!empty){
        			if( element.getLayer() <= allReferences.get( i ).getLayer() ) {
        			allReferences.add( i,  element);
        			reassignLayerAllReferences(i);
        			added = true;
        			}
        		}else {
        			allReferences.add( i,  element);
        			reassignLayerAllReferences(i);
        			added = true;
        		}
        		i++;
        	}else {
        		if (!empty){
        		if( Math.round(element.getY()) <= Math.round(allReferences.get( i ).getY()) ) {
        			element.setLayer(i);
        			allReferences.add( i,  element);
        			reassignLayerAllReferences(i);
        			added = true;
        		}
        		}else {
        			element.setLayer(i);
        			allReferences.add( i,  element);
        			reassignLayerAllReferences(i);
        			added = true;
        		}
        		i++;
        	}
        	
        }
        
        // If the element wasn't added, add it in the last position
        if( !added ){
            element.setLayer(i);
        	allReferences.add( element );
        	reassignLayerAllReferences(i);
        }
	}
	
	/**
	 * Insert in order in allReferencesDataControl attribute
	 * 
	 * @param element
	 * 				The element reference to be added
	 * @param	hasLayer
	 * 				Take either layer or depth value to order value
	 **/
	public void insertInOrder(ElementReferenceDataControl element, boolean hasLayer){
		boolean added = false;
        int i = 0;
        boolean empty = allReferencesDataControl.size()==0 ;
        // While the element has not been added, and
        // we haven't checked every previous element
        while( !added && (i < allReferencesDataControl.size( ) || empty) ) {
            
            // Insert the element in the correct position
        	if (hasLayer){
        		if (!empty){
        			if( element.getElementReference().getLayer() <= allReferencesDataControl.get( i ).getElementReference().getLayer() ) {
        				allReferencesDataControl.add( i,  element);
        				reassignLayerAllReferencesDataControl(i);
        				added = true;
        			}
        		}else {
        			allReferencesDataControl.add( i,  element);
        			reassignLayerAllReferencesDataControl(i);
        			added = true;
        		}
        		i++;
        	}else {
        		if (!empty){
        		if( Math.round(element.getElementReference().getY()) <= Math.round(allReferencesDataControl.get( i ).getElementReference().getY()) ) {
        			element.getElementReference().setLayer(i);
        			allReferencesDataControl.add( i,  element);
        			reassignLayerAllReferencesDataControl(i);
        			added = true;
        		}
        		}else {
        			element.getElementReference().setLayer(i);
        			allReferencesDataControl.add( i,  element);
        			reassignLayerAllReferencesDataControl(i);
        			added = true;
        		}
        		i++;
        	}
        	
        }
        
        // If the element wasn't added, add it in the last position
        if( !added ){
        	element.getElementReference().setLayer(i);
        	allReferencesDataControl.add( element );
        	reassignLayerAllReferencesDataControl(i);
        	
        }
            
	}
	
	/**
	 * Merge all references in one list
	 * 
	 * @return 
	 * 		The list that contains all references data control;
	 */
	public List<ElementReferenceDataControl> getAllReferencesDataControl(){
		return allReferencesDataControl;
		
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

	//TODO ver si se puede devolver allReferences
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
					ElementReference newElementReference = new ElementReference( selectedItem, 50, 50 );
					ElementReferenceDataControl erdc = new ElementReferenceDataControl( sceneDataControl, newElementReference,type );
					lastElementReferenceDataControl = erdc;
					itemReferencesList.add( newElementReference );
					itemReferencesDataControlList.add( erdc );
					insertInOrder(newElementReference,false);
					insertInOrder(erdc,false);
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
					ElementReference newElementReference = new ElementReference( selectedItem, 50, 50 );
					ElementReferenceDataControl erdc = new ElementReferenceDataControl( sceneDataControl, newElementReference, type );
					lastElementReferenceDataControl = erdc;
					atrezzoReferencesList.add( newElementReference );
					atrezzoReferencesDataControlList.add( erdc );
					insertInOrder(newElementReference,false);
					insertInOrder(erdc,false);
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
					ElementReference newElementReference = new ElementReference( selectedItem, 50, 50 );
					ElementReferenceDataControl erdc = new ElementReferenceDataControl( sceneDataControl, newElementReference, type );
					lastElementReferenceDataControl = erdc;
					npcReferencesList.add( newElementReference );
					npcReferencesDataControlList.add( erdc );
					insertInOrder(newElementReference,false);
					insertInOrder(erdc,false);
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
	
	
	private void reassignLayerAllReferences(int index){

		for (int i = index; i<allReferences.size();i++){
			allReferences.get(i).setLayer(i);
		}
		
	}
	
	private void reassignLayerAllReferencesDataControl(int index){

		for (int i = index; i<allReferences.size();i++){
			allReferencesDataControl.get(i).getElementReference().setLayer(i);
		}
		
	}

	/**
	 * Delete in allReferences updating the layer, and also deletes in allReferencesDataControl
	 * 
	 * @param	
	 * 		dataControl the issue to delete
	 */
	private void delete(DataControl dataControl){
		int index = allReferencesDataControl.indexOf(dataControl);
		allReferences.remove(dataControl.getContent());
		allReferencesDataControl.remove(dataControl);
		reassignLayerAllReferences(index);
		reassignLayerAllReferencesDataControl(index);
		
	}
	
	@Override
	public boolean deleteElement( DataControl dataControl ) {
		boolean elementDeleted = false;
		
		if( itemReferencesList.remove( dataControl.getContent( ) ) ) {
			itemReferencesDataControlList.remove( dataControl );
			delete(dataControl);
			controller.dataModified( );
			elementDeleted = true;
		}

		if( atrezzoReferencesList.remove( dataControl.getContent( ) ) ) {
			atrezzoReferencesDataControlList.remove( dataControl );
			delete(dataControl);
			controller.dataModified( );
			elementDeleted = true;
		}

		if( npcReferencesList.remove( dataControl.getContent( ) ) ) {
			npcReferencesDataControlList.remove( dataControl );
			delete(dataControl);
			controller.dataModified( );
			elementDeleted = true;
		}

		return elementDeleted;
	}
	
	private void moveUp(DataControl dataControl){
		int index = allReferences.indexOf(dataControl.getContent());
		if (index>0){
			//change the elements
			allReferences.add(index-1, allReferences.remove(index));
			//update element layer
			allReferences.get(index).setLayer(index);
			allReferences.get(index-1).setLayer(index-1);
			//change in data control container
			allReferencesDataControl.add(index-1,allReferencesDataControl.remove(index));
			
		}
	}

	@Override
	public boolean moveElementUp( DataControl dataControl ) {
		boolean elementMoved = false;
		int itemElementIndex = itemReferencesList.indexOf( dataControl.getContent( ) );
		int atrezzoElementIndex = atrezzoReferencesList.indexOf( dataControl.getContent( ) );
		int npcElementIndex = npcReferencesList.indexOf( dataControl.getContent( ) );
		// check if element is item, atrezzo or npc
		if( itemElementIndex >= 0 ) {
			//itemReferencesList.add( itemElementIndex - 1, itemReferencesList.remove( itemElementIndex ) );
			//itemReferencesDataControlList.add( itemElementIndex - 1, itemReferencesDataControlList.remove( itemElementIndex ) );
			moveUp(dataControl);
			controller.dataModified( );
			elementMoved = true;
		} else if (atrezzoElementIndex >= 0) {
			//atrezzoReferencesList.add( atrezzoElementIndex - 1, atrezzoReferencesList.remove( atrezzoElementIndex ) );
			//atrezzoReferencesDataControlList.add( atrezzoElementIndex - 1, atrezzoReferencesDataControlList.remove( atrezzoElementIndex ) );
			moveUp(dataControl);
			controller.dataModified( );
			elementMoved = true;
		} else if (npcElementIndex >= 0) {
			//npcReferencesList.add( npcElementIndex - 1, npcReferencesList.remove( npcElementIndex ) );
			//npcReferencesDataControlList.add( npcElementIndex - 1, npcReferencesDataControlList.remove( npcElementIndex ) );
			moveUp(dataControl);
			controller.dataModified( );
			elementMoved = true;
		}

		return elementMoved;
	}
	
	
	private void moveDown(DataControl dataControl){
		int index = allReferences.indexOf(dataControl.getContent());
		if (index >=0 && index<allReferences.size()-1){
			//change the elements
			allReferences.add(index+1,allReferences.remove(index));
			//update element layer
			allReferences.get(index).setLayer(index);
			allReferences.get(index+1).setLayer(index+1);
			//change in data control container
			allReferencesDataControl.add(index+1,allReferencesDataControl.remove(index));
		}
	}

	@Override
	public boolean moveElementDown( DataControl dataControl ) {
		boolean elementMoved = false;
		int itemElementIndex = itemReferencesList.indexOf( dataControl.getContent( ) );
		int atrezzoElementIndex = atrezzoReferencesList.indexOf( dataControl.getContent());
		int npcElementIndex = npcReferencesList.indexOf( dataControl.getContent());
		
		if( itemElementIndex >= 0 && itemElementIndex < itemReferencesList.size( ) - 1 ) {
			//itemReferencesList.add( itemElementIndex + 1, itemReferencesList.remove( itemElementIndex ) );
			//itemReferencesDataControlList.add( itemElementIndex + 1, itemReferencesDataControlList.remove( itemElementIndex ) );
			moveDown(dataControl);
			controller.dataModified( );
			elementMoved = true;
		} else if( atrezzoElementIndex >= 0 && atrezzoElementIndex < atrezzoReferencesList.size( ) - 1 ) {
			//atrezzoReferencesList.add( atrezzoElementIndex + 1, atrezzoReferencesList.remove( atrezzoElementIndex ) );
			//atrezzoReferencesDataControlList.add( atrezzoElementIndex + 1, atrezzoReferencesDataControlList.remove( atrezzoElementIndex ) );
			moveDown(dataControl);
			controller.dataModified( );
			elementMoved = true;
		} else if( npcElementIndex >= 0 && npcElementIndex < npcReferencesList.size( ) - 1 ) {
			//npcReferencesList.add( npcElementIndex + 1, npcReferencesList.remove( npcElementIndex ) );
			//npcReferencesDataControlList.add( npcElementIndex + 1, npcReferencesDataControlList.remove( npcElementIndex ) );
			moveDown(dataControl);
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

	/**
	 * Give the last introduced element reference data control
	 * @return
	 * 		The last introduced reference
	 */
	public ElementReferenceDataControl getLastElementReferenceDataControl() {
		return lastElementReferenceDataControl;
	}

	/**
	 * Change the last element reference data control
	 * 
	 * @param lastElementReferenceDataControl
	 */
	public void setLastElementReferenceDataControl(
			ElementReferenceDataControl lastElementReferenceDataControl) {
		this.lastElementReferenceDataControl = lastElementReferenceDataControl;
	}
	
	
}
