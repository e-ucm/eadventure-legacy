package es.eucm.eadventure.editor.control.controllers.scene;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import es.eucm.eadventure.common.data.adventure.DescriptorData;
import es.eucm.eadventure.common.data.chapter.ElementReference;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.character.PlayerDataControl;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;
import es.eucm.eadventure.editor.gui.treepanel.nodes.scene.ReferenceListener;

public class ReferencesListDataControl extends DataControl{

	
	/**
	 * Vertical's split panel initial divider location
	 */
	public static final int VERTICAL_INITIAL_POSITION = 515;
	
	/**
	 * Horizontal`s split panel initial divider location
	 */
	public static final int HORIZONTAL_INITIAL_POSITION = 280;
	
	/**
	 * Player image path
	 */
	private String playerImagePath;
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
	//private List<ElementContainer> allReferences;
	
	/**
	 * List of all elements order by number of layer (or y position when they have the same layer "-1")
	 */
	private List<ElementContainer> allReferencesDataControl;
	
	/**
	 * The last introduced element referenced or player (in a ElementContainer object)
	 */
	private ElementContainer lastElementContainer;
	
	/**
	 * The player position in allReferencesDataControl
	 */
	private int playerPosition;
	
	/**
	 * Listener to inform of new element creation (and create new tree node)
	 */
	private ReferenceListener addNewReferenceListener;
	
	/**
	 * Tell us if the player image path has been changed
	 */
	private boolean imagePathHasChanged;
	
	/**
	 * Vertical's split panel divider location
	 */
	private int verticalSplitPosition;
	
	/**
	 * Horizontal's split panel divider location
	 */
	private int horizontalSplitPosition;
	
	/**
	 * Constructor.
	 * 
	 * @param sceneDataControl
	 *            Link to the parent scene controller
	 * @param itemReferencesList
	 *            List of item references
	 */
	public ReferencesListDataControl( String playerImagePath,SceneDataControl sceneDataControl, List<ElementReference> itemReferencesList
										, List<ElementReference> atrezzoReferencesList, List<ElementReference> npcReferencesList) {
		this.playerImagePath = playerImagePath;
		this.sceneDataControl = sceneDataControl;
		this.itemReferencesList = itemReferencesList;
		this.atrezzoReferencesList = atrezzoReferencesList;
		this.npcReferencesList = npcReferencesList;
		this.allReferencesDataControl = new ArrayList<ElementContainer>();
		this.lastElementContainer = null;
		this.playerPosition = -1;
		this.addNewReferenceListener = null;
		this.imagePathHasChanged = false;
		this.horizontalSplitPosition = this.HORIZONTAL_INITIAL_POSITION;
		this.verticalSplitPosition = this.VERTICAL_INITIAL_POSITION;
		// Check if one of references has layer -1: if it is true, it means that element references has not layer. 
		// Create subcontrollers
		itemReferencesDataControlList = new ArrayList<ElementReferenceDataControl>( );
		boolean hasLayer = hasLayer();
		for( ElementReference itemReference : itemReferencesList ){
			int counter = count(itemReference);
			ElementReferenceDataControl erdc = new ElementReferenceDataControl( sceneDataControl, itemReference, Controller.ITEM_REFERENCE,counter) ;
			itemReferencesDataControlList.add(erdc );
			insertInOrder(new ElementContainer(erdc,-1,null),hasLayer);
		}
		
		atrezzoReferencesDataControlList = new ArrayList<ElementReferenceDataControl>();
		for (ElementReference atrezzoReference : atrezzoReferencesList){
			int counter = count(atrezzoReference);
			ElementReferenceDataControl erdc = new ElementReferenceDataControl(sceneDataControl, atrezzoReference, Controller.ATREZZO_REFERENCE,counter);
			atrezzoReferencesDataControlList.add(erdc );
			insertInOrder(new ElementContainer(erdc,-1,null),hasLayer);
		}
		npcReferencesDataControlList = new ArrayList<ElementReferenceDataControl>();
		for (ElementReference npcReference : npcReferencesList){
			int counter = count(npcReference);
			ElementReferenceDataControl erdc  =  new ElementReferenceDataControl(sceneDataControl, npcReference, Controller.NPC_REFERENCE,counter);
			npcReferencesDataControlList.add(erdc);
			insertInOrder(new ElementContainer(erdc,-1,null),hasLayer);
		}
		
		// insert player
		// by default, if player don´t have layer, we give it to him.
		if (playerImagePath!=null && (!Controller.getInstance().isPlayTransparent()) && sceneDataControl.isAllowPlayer()){
			int layer;
			if (sceneDataControl.getPlayerLayer()==-1)
				layer = 0;
			else 
				layer = sceneDataControl.getPlayerLayer();
			reassignLayerAllReferencesDataControl(insertInOrder(new ElementContainer(null,layer,AssetsController.getImage( this.playerImagePath )),true));
		}
	}

	private int count(ElementReference er){
		Iterator<ElementContainer> it = allReferencesDataControl.iterator();
		int count = 0;
		while (it.hasNext()){
			ElementContainer element = it.next();
			if (!element.isPlayer()){
			if (element.getErdc().getElementId().equals(er.getIdTarget()))
				count++;
			}
		}
		return count;
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
	
	
	public Image getPlayerImage(){
		if (playerPosition==-1)
			return null;
		else{
			if (imagePathHasChanged){
				allReferencesDataControl.get(playerPosition).setImage(AssetsController.getImage( this.playerImagePath ));
				imagePathHasChanged = false;
			}
		//	if (allReferences!=null)
				return allReferencesDataControl.get(playerPosition).getImage();
		}
			
	}
	
	
	/**
	 * Insert in order in allReferencesDataControl attribute
	 * 
	 * @param element
	 * 				The element container to be added
	 * @param	hasLayer
	 * 				Take either layer or depth value to order value
	 * @param playerLayer
	 * 				Take the layer if player has it, or the y position if the player has not layer.
	 * 
	 * @return i
	 * 		returns the position where the element has been inserted. It will be use to reassign layer
	 **/
	public int  insertInOrder(ElementContainer element, boolean hasLayer){
		boolean added = false;
        int i = 0;
        boolean empty = allReferencesDataControl.size()==0 ;
        // While the element has not been added, and
        // we haven't checked every previous element
        while( !added && (i < allReferencesDataControl.size( ) || empty) ) {
            
            // Insert the element in the correct position
        	if (!empty){
        		if (hasLayer){
            			//check the layer
        			if( element.getLayer() <= allReferencesDataControl.get( i ).getLayer() ) {
        				allReferencesDataControl.add( i,  element);
        				//reassignLayerAllReferencesDataControl(i);
        				added = true;
        			}
        		}else {
        			//check the y position
        			if( element.getY() <= Math.round(allReferencesDataControl.get( i ).getY()) ) {
        				allReferencesDataControl.add( i,  element);
            			reassignLayerAllReferencesDataControl(i);
            			added = true;
            		}
        		}
        		i++;
        	}else {
        		allReferencesDataControl.add( i,  element);
    			if (!hasLayer)
    				reassignLayerAllReferencesDataControl(i);
    			added = true;
    			i++;
        	}
        	
        }
        
        // If the element wasn't added, add it in the last position
        if( !added ){
        	//element.setLayer(i);
        	allReferencesDataControl.add( element );
        	if (!hasLayer)
        		reassignLayerAllReferencesDataControl(i-1);
        	
        }
          return i-1;  
	}
	
	/**
	 * Merge all references in one list
	 * 
	 * @return 
	 * 		The list that contains all references data control;
	 */
	public List<ElementContainer> getAllReferencesDataControl(){
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
					int counter = count(newElementReference);
					ElementReferenceDataControl erdc = new ElementReferenceDataControl( sceneDataControl, newElementReference,type,counter );
					itemReferencesList.add( newElementReference );
					itemReferencesDataControlList.add( erdc );
					ElementContainer ec = new ElementContainer(erdc,-1,null);
					lastElementContainer = ec;
					reassignLayerAllReferencesDataControl(insertInOrder(ec,false));
					controller.dataModified( );
					elementAdded = true;
					addNewReferenceListener.addNewNodeElement(type);
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
					int counter = count(newElementReference);
					ElementReferenceDataControl erdc = new ElementReferenceDataControl( sceneDataControl, newElementReference,type,counter );
					atrezzoReferencesList.add( newElementReference );
					atrezzoReferencesDataControlList.add( erdc );
					ElementContainer ec = new ElementContainer(erdc,-1,null);
					lastElementContainer = ec;
					reassignLayerAllReferencesDataControl(insertInOrder(ec,false));
					controller.dataModified( );
					elementAdded = true;
					addNewReferenceListener.addNewNodeElement(type);
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
					int counter = count(newElementReference);
					ElementReferenceDataControl erdc = new ElementReferenceDataControl( sceneDataControl, newElementReference,type,counter );
					npcReferencesList.add( newElementReference );
					npcReferencesDataControlList.add( erdc );
					ElementContainer ec = new ElementContainer(erdc,-1,null);
					lastElementContainer = ec;
					reassignLayerAllReferencesDataControl(insertInOrder(ec,false));
					controller.dataModified( );
					elementAdded = true;
					addNewReferenceListener.addNewNodeElement(type);
				}
			}

			// If the list had no elements, show an error dialog
			else
				controller.showErrorDialog( TextConstants.getText( "Operation.AddNPCReferenceTitle" ), TextConstants.getText( "Operation.AddReferenceErrorNoNPC" ) );
		}

		return elementAdded;
	}
	
	private void reassignLayerAllReferencesDataControl(int index){

		for (int i = index; i<allReferencesDataControl.size();i++){
			allReferencesDataControl.get(i).setLayer(i);
			if (allReferencesDataControl.get(i).isPlayer())
				playerPosition=i;
		}
		
	}

	/**
	 * Delete in allReferencesDataControl updating the layer.
	 * 
	 * @param	
	 * 		dataControl the issue to delete
	 */
	private void delete(DataControl dataControl){
		if (dataControl != null){
			int index=0;
			
				
				for (index=0; index < allReferencesDataControl.size();index++)
				if (!allReferencesDataControl.get(index).isPlayer())	
					if (allReferencesDataControl.get(index).getErdc().equals(dataControl))
						break;
			if (index>=0){
			allReferencesDataControl.remove(index);
			reassignLayerAllReferencesDataControl(index);
			}
			
		}
	}
	
	@Override
	public boolean deleteElement( DataControl dataControl ) {
		boolean elementDeleted = false;
		if (dataControl != null){
		if( itemReferencesList.remove( dataControl.getContent( ) ) ) {
			itemReferencesDataControlList.remove( dataControl );
		}

		if( atrezzoReferencesList.remove( dataControl.getContent( ) ) ) {
			atrezzoReferencesDataControlList.remove( dataControl );
		}

		if( npcReferencesList.remove( dataControl.getContent( ) ) ) {
			npcReferencesDataControlList.remove( dataControl );
		}
		// delete in allReferencesDataControl
		delete(dataControl);
		controller.dataModified( );
		elementDeleted = true;
		addNewReferenceListener.deleteNodeElement();
		
		}
		//if it is a player, we don´t allow to delete it
		 
			

		return elementDeleted;
	}
	
	private void moveUp(DataControl dataControl){
		
		boolean player;
		int index=0;
		if (dataControl != null){
			player = false;
			for (index=0; index < allReferencesDataControl.size();index++)
			if (!allReferencesDataControl.get(index).isPlayer())	
				if (allReferencesDataControl.get(index).getErdc().equals(dataControl))
					break;
		}
		else {
			index = playerPosition;
			player = true;
		}
		if (index>0){
			//change the elements
			allReferencesDataControl.add(index-1, allReferencesDataControl.remove(index));
			//update element layer
			allReferencesDataControl.get(index).setLayer(index);
			allReferencesDataControl.get(index-1).setLayer(index-1);
			if (player)
				setPlayerPosition(index-1);
			if (allReferencesDataControl.get(index).isPlayer())
				setPlayerPosition(index);
		}
	}

	@Override
	public boolean moveElementUp( DataControl dataControl ) {
		boolean elementMoved = false;
		if (dataControl!=null){
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
		}// if it is a player
		else {
			moveUp(dataControl);
			controller.dataModified();
			elementMoved = true;
		}

		return elementMoved;
	}
	
	
	private void moveDown(DataControl dataControl){
		
		boolean player;
		int index=0;
		if (dataControl != null){
			player=false;
			for (index=0; index < allReferencesDataControl.size();index++)
				if (!allReferencesDataControl.get(index).isPlayer())
					if (allReferencesDataControl.get(index).getErdc().equals(dataControl))
						break;
		}else {
			index = playerPosition;
			player=true;
		}
		if (index >=0 && index<allReferencesDataControl.size()-1){
			//change the elements
			allReferencesDataControl.add(index+1,allReferencesDataControl.remove(index));
			//update element layer
			allReferencesDataControl.get(index).setLayer(index);
			allReferencesDataControl.get(index+1).setLayer(index+1);
			if (player)
				setPlayerPosition(index+1);
			if (allReferencesDataControl.get(index).isPlayer())
				setPlayerPosition(index);
		}
	}

	@Override
	public boolean moveElementDown( DataControl dataControl ) {
		boolean elementMoved = false;
		
		if (dataControl != null){
		int itemElementIndex = itemReferencesList.indexOf( dataControl.getContent( ) );
		int atrezzoElementIndex = atrezzoReferencesList.indexOf( dataControl.getContent());
		int npcElementIndex = npcReferencesList.indexOf( dataControl.getContent());
		
		if( itemElementIndex >= 0 && itemElementIndex <= itemReferencesList.size( ) - 1 ) {
			//itemReferencesList.add( itemElementIndex + 1, itemReferencesList.remove( itemElementIndex ) );
			//itemReferencesDataControlList.add( itemElementIndex + 1, itemReferencesDataControlList.remove( itemElementIndex ) );
			moveDown(dataControl);
			controller.dataModified( );
			elementMoved = true;
		} else if( atrezzoElementIndex >= 0 && atrezzoElementIndex <= atrezzoReferencesList.size( ) - 1 ) {
			//atrezzoReferencesList.add( atrezzoElementIndex + 1, atrezzoReferencesList.remove( atrezzoElementIndex ) );
			//atrezzoReferencesDataControlList.add( atrezzoElementIndex + 1, atrezzoReferencesDataControlList.remove( atrezzoElementIndex ) );
			moveDown(dataControl);
			controller.dataModified( );
			elementMoved = true;
		} else if( npcElementIndex >= 0 && npcElementIndex <= npcReferencesList.size( ) - 1 ) {
			//npcReferencesList.add( npcElementIndex + 1, npcReferencesList.remove( npcElementIndex ) );
			//npcReferencesDataControlList.add( npcElementIndex + 1, npcReferencesDataControlList.remove( npcElementIndex ) );
			moveDown(dataControl);
			controller.dataModified( );
			elementMoved = true;
		}
		} else {
			moveDown(dataControl);
			controller.dataModified();
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
	 * Give the last introduced element container
	 * @return
	 * 		The last introduced reference
	 */
	public ElementContainer getLastElementContainer() {
		return lastElementContainer;
	}

	/**
	 * Change the last element container
	 * 
	 * @param lastElementContainer
	 * 			the new element container
	 */
	public void setLastElementContainer(
			ElementContainer lastElementContainer) {
		this.lastElementContainer = lastElementContainer;
	}

	public SceneDataControl getSceneDataControl() {
		return sceneDataControl;
	}
	
	/**
	 * 	Put all id of the references in a string array
	 * 
	 * @return String[]
	 * 			Array of strings with the name of each element reference
	 */
	public String[] getAllReferencesId(){
		String[] cont = new String[allReferencesDataControl.size()];
		for (int i=0; i<cont.length;i++)
			if (allReferencesDataControl.get(i).isPlayer())
				cont[i] = "Player";
			else
				cont[i] = allReferencesDataControl.get(i).getErdc().getElementId();
		return cont;
	}

	public int getPlayerPosition() {
		return playerPosition;
	}

	public void setPlayerPosition(int playerPosition) {
		this.playerPosition = playerPosition;
		this.sceneDataControl.setPlayerLayer(playerPosition);
	}
	
	public void deletePlayer(){
		allReferencesDataControl.remove(playerPosition);
		reassignLayerAllReferencesDataControl(playerPosition);
		playerPosition  = -1;
		// -2 indica que no queremos que tenga layer, frente a -1 que solo indica que no tiene layer
		// documentarlo mejor y poner constantes
		sceneDataControl.setPlayerLayer(-2);
	}
	
	public void addPlayer(){
		ElementContainer ec = new ElementContainer(null,0,AssetsController.getImage( this.playerImagePath ));
		int layer = insertInOrder(ec,true);
		reassignLayerAllReferencesDataControl(layer);
		sceneDataControl.setPlayerLayer(layer);
	
	}
	
	/**
	 * Changes the listener
	 * 
	 * @param anrl
	 * 			the new AddNewReferenceListener
	 */
	public void setAddNewReferenceListener(ReferenceListener anrl) {
		this.addNewReferenceListener = anrl;
	}

	
	public void changeImagePlayerPath(String imagePath) {
		this.playerImagePath = imagePath;
		this.imagePathHasChanged = true;
		if (allReferencesDataControl.size()==0) {
			playerPosition=0;
			reassignLayerAllReferencesDataControl(insertInOrder(new ElementContainer(null,0,AssetsController.getImage( this.playerImagePath )),true));
		}
			
		
	}

	public int getVerticalSplitPosition() {
		return verticalSplitPosition;
	}

	public void setVerticalSplitPosition(int verticalSplitPosition) {
		this.verticalSplitPosition = verticalSplitPosition;
	}

	public int getHorizontalSplitPosition() {
		return horizontalSplitPosition;
	}

	public void setHorizontalSplitPosition(int horizontalSplitPosition) {
		this.horizontalSplitPosition = horizontalSplitPosition;
	}
}
