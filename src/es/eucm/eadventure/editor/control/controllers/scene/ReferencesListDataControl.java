package es.eucm.eadventure.editor.control.controllers.scene;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.ElementReference;
import es.eucm.eadventure.common.data.chapter.scenes.Scene;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.Searchable;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;
import es.eucm.eadventure.editor.gui.otherpanels.ScenePreviewEditionPanel;

/**
 * Data control for the list of references in the scene
 */
public class ReferencesListDataControl extends DataControl{
	
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
	private int playerPositionInAllReferences;
	
	/**
	 * The player isn't in all references
	 */
	public static final int NO_PLAYER=-1; 
	
	/**
	 * Tell us if the player image path has been changed
	 */
	private boolean imagePathHasChanged;
		
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
		this.playerPositionInAllReferences = NO_PLAYER;
		this.imagePathHasChanged = false;
		// Check if one of references has layer -1: if it is true, it means that element references has no layer. 
		// Create subcontrollers

		boolean hasLayer = hasLayer();
		for( ElementReference itemReference : itemReferencesList ){
			int counter = count(itemReference);
			ElementReferenceDataControl erdc = new ElementReferenceDataControl( sceneDataControl, itemReference, Controller.ITEM_REFERENCE,counter) ;
			insertInOrder(new ElementContainer(erdc,-1,null),hasLayer);
		}
		
		for (ElementReference atrezzoReference : atrezzoReferencesList){
			int counter = count(atrezzoReference);
			ElementReferenceDataControl erdc = new ElementReferenceDataControl(sceneDataControl, atrezzoReference, Controller.ATREZZO_REFERENCE,counter);
			insertInOrder(new ElementContainer(erdc,-1,null),hasLayer);
		}

		for (ElementReference npcReference : npcReferencesList){
			int counter = count(npcReference);
			ElementReferenceDataControl erdc  =  new ElementReferenceDataControl(sceneDataControl, npcReference, Controller.NPC_REFERENCE,counter);
			insertInOrder(new ElementContainer(erdc,-1,null),hasLayer);
		}
		
		// insert player
		// by default, if player don´t have layer, we give it to him.
		if (playerImagePath!=null && (!Controller.getInstance().isPlayTransparent()) && sceneDataControl.isAllowPlayer()){
			int layer;
			if (sceneDataControl.getPlayerLayer()==Scene.PLAYER_WITHOUT_LAYER)
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
			if (element.getErdc().getElementId().equals(er.getTargetId()))
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
			if (itemReferencesList.get(0).getLayer() == Scene.PLAYER_WITHOUT_LAYER)
				return false;
			else 
				return true;
		} else if (!atrezzoReferencesList.isEmpty()){
				if (atrezzoReferencesList.get(0).getLayer() == Scene.PLAYER_WITHOUT_LAYER)
					return false;
				else 
					return true;
		} else if (!npcReferencesList.isEmpty()){
			if (npcReferencesList.get(0).getLayer() == Scene.PLAYER_WITHOUT_LAYER)
				return false;
			else 
				return true;
		}
		return false;
	}
	
	
	public Image getPlayerImage(){
		if (playerPositionInAllReferences==NO_PLAYER)
			return null;
		else{
			if (imagePathHasChanged){
				allReferencesDataControl.get(playerPositionInAllReferences).setImage(AssetsController.getImage( this.playerImagePath ));
				imagePathHasChanged = false;
			}
		//	if (allReferences!=null)
				return allReferencesDataControl.get(playerPositionInAllReferences).getImage();
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
        	if (!empty){
        		if (hasLayer){
        			if( element.getLayer() <= allReferencesDataControl.get( i ).getLayer() ) {
        				allReferencesDataControl.add( i,  element);
        				added = true;
        			}
        		}else {
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
		List<ElementReferenceDataControl> list = new ArrayList<ElementReferenceDataControl>();
		for (ElementContainer element : allReferencesDataControl) {
			if (element.getErdc() != null && element.getErdc().getType() == Controller.ITEM_REFERENCE) {
				list.add(element.getErdc());
			}
		}
		return list;
//		return itemReferencesDataControlList;
	}

	/**
	 * Returns the list of atrezzo item reference controllers.
	 * 
	 * @return List of atrezzo item reference controllers
	 */
	public List<ElementReferenceDataControl> getAtrezzoReferences( ) {
		List<ElementReferenceDataControl> list = new ArrayList<ElementReferenceDataControl>();
		for (ElementContainer element : allReferencesDataControl) {
			if (element.getErdc() != null && element.getErdc().getType() == Controller.ATREZZO_REFERENCE) {
				list.add(element.getErdc());
			}
		}
		return list;
	}

	/**
	 * Returns the list of npc reference controllers.
	 * 
	 * @return List of npc reference controllers
	 */
	public List<ElementReferenceDataControl> getNPCReferences( ) {
		List<ElementReferenceDataControl> list = new ArrayList<ElementReferenceDataControl>();
		for (ElementContainer element : allReferencesDataControl) {
			if (element.getErdc() != null && element.getErdc().getType() == Controller.NPC_REFERENCE) {
				list.add(element.getErdc());
			}
		}
		return list;
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
	public boolean addElement( int type, String id ) {
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
					ElementContainer ec = new ElementContainer(erdc,-1,null);
					lastElementContainer = ec;
					reassignLayerAllReferencesDataControl(insertInOrder(ec,false));
					elementAdded = true;
				}
			}
			else
				controller.showErrorDialog( TextConstants.getText( "Operation.AddItemReferenceTitle" ), TextConstants.getText( "Operation.AddItemReferenceErrorNoItems" ) );
		}

		
		if( type == Controller.ATREZZO_REFERENCE ) {
			String[] items = controller.getIdentifierSummary( ).getAtrezzoIds();

			// If the list has elements, show the dialog with the options
			if( items.length > 0 ) {
				String selectedItem = controller.showInputDialog( TextConstants.getText( "Operation.AddAtrezzoReferenceTitle" ), TextConstants.getText( "Operation.AddAtrezzoReferenceMessage" ), items );
				if( selectedItem != null ) {
					ElementReference newElementReference = new ElementReference( selectedItem, 50, 50 );
					int counter = count(newElementReference);
					ElementReferenceDataControl erdc = new ElementReferenceDataControl( sceneDataControl, newElementReference,type,counter );
					atrezzoReferencesList.add( newElementReference );
					ElementContainer ec = new ElementContainer(erdc,-1,null);
					lastElementContainer = ec;
					reassignLayerAllReferencesDataControl(insertInOrder(ec,false));
					elementAdded = true;
				}
			}
			else
				controller.showErrorDialog( TextConstants.getText( "Operation.AddAtrezzoReferenceTitle" ), TextConstants.getText( "Operation.AddReferenceErrorNoAtrezzo" ) );
		}

		if( type == Controller.NPC_REFERENCE ) {
			String[] items = controller.getIdentifierSummary( ).getNPCIds();
			if( items.length > 0 ) {
				String selectedItem = controller.showInputDialog( TextConstants.getText( "Operation.AddNPCReferenceTitle" ), TextConstants.getText( "Operation.AddNPCReferenceMessage" ), items );
				if( selectedItem != null ) {
					ElementReference newElementReference = new ElementReference( selectedItem, 50, 50 );
					int counter = count(newElementReference);
					ElementReferenceDataControl erdc = new ElementReferenceDataControl( sceneDataControl, newElementReference,type,counter );
					npcReferencesList.add( newElementReference );
					ElementContainer ec = new ElementContainer(erdc,-1,null);
					lastElementContainer = ec;
					reassignLayerAllReferencesDataControl(insertInOrder(ec,false));
					elementAdded = true;
				}
			}
			else
				controller.showErrorDialog( TextConstants.getText( "Operation.AddNPCReferenceTitle" ), TextConstants.getText( "Operation.AddReferenceErrorNoNPC" ) );
		}

		return elementAdded;
	}
	
	private void reassignLayerAllReferencesDataControl(int index){
		for (int i = index; i<allReferencesDataControl.size();i++){
			allReferencesDataControl.get(i).setLayer(i);
			if (allReferencesDataControl.get(i).isPlayer())
				playerPositionInAllReferences = i;
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
			if (index>=0 && index < allReferencesDataControl.size()){
				allReferencesDataControl.remove(index);
				reassignLayerAllReferencesDataControl(index);
			}
			
		}
	}
	
	@Override
	public boolean deleteElement( DataControl dataControl, boolean askConfirmation ) {
		boolean elementDeleted = false;
		if (dataControl != null){
			itemReferencesList.remove( dataControl.getContent( ) );
			atrezzoReferencesList.remove( dataControl.getContent( ) );
			npcReferencesList.remove( dataControl.getContent( ) );
			delete(dataControl);
			elementDeleted = true;
		}
		return elementDeleted;
	}
	
	public void addElement(ElementContainer element) {
		if (element.getErdc().getType() == Controller.ITEM_REFERENCE)
			itemReferencesList.add((ElementReference) element.getErdc().getContent());
		else if (element.getErdc().getType() == Controller.ATREZZO_REFERENCE)
			atrezzoReferencesList.add((ElementReference) element.getErdc().getContent());
		else if (element.getErdc().getType() == Controller.NPC_REFERENCE)
			npcReferencesList.add((ElementReference) element.getErdc().getContent());
		allReferencesDataControl.add(element.getLayer(), element);
		reassignLayerAllReferencesDataControl(element.getLayer());
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
			index = playerPositionInAllReferences;
			player = true;
		}
		if (index>0){
			allReferencesDataControl.add(index-1, allReferencesDataControl.remove(index));
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
			moveUp(dataControl);
			elementMoved = true;
		}
		else {
			moveUp(dataControl);
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
			index = playerPositionInAllReferences;
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
			moveDown(dataControl);
			elementMoved = true;
		} else {
			moveDown(dataControl);
			elementMoved = true;
		}


		return elementMoved;
	}

	@Override
	public String renameElement( String name ) {
		return null;
	}

	@Override
	public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {
		for (ElementContainer element : allReferencesDataControl) {
			if (!element.isPlayer())
				element.getErdc().updateVarFlagSummary(varFlagSummary);
		}
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

		for (ElementContainer element : allReferencesDataControl) {
			if (!element.isPlayer())
				count += element.getErdc().countIdentifierReferences(id);
		}
		return count;
	}

	@Override
	public void replaceIdentifierReferences( String oldId, String newId ) {
		for (ElementContainer element : allReferencesDataControl) {
			if (!element.isPlayer())
				element.getErdc().replaceIdentifierReferences(oldId, newId);
		}
	}

	@Override
	public void deleteIdentifierReferences( String id ) {
		deleteIdentifierFromReferenceList(itemReferencesList, id);
		deleteIdentifierFromReferenceList(atrezzoReferencesList, id);
		deleteIdentifierFromReferenceList(npcReferencesList, id);
	}
	
	private void deleteIdentifierFromReferenceList(List<ElementReference> list, String id ) {
		int i = 0;
		while (i < list.size()) {
			if (list.get(i).getTargetId().equals(id)) {
				deleteReferenceFromAll(npcReferencesList.get(i));
				list.remove(i);
			} else
				i++;
		}
	}
	
	private void deleteReferenceFromAll(Object reference) {
		int i = 0;
		while ( i < allReferencesDataControl.size()) {
			ElementContainer element = allReferencesDataControl.get(i);
			if (!element.isPlayer() && element.getErdc().getContent() == reference) {
				allReferencesDataControl.remove(i);
			} else
				i++;
		}
	}

	@Override
	public boolean canBeDuplicated( ) {
		return false;
	}

	public boolean containsDataControl(ElementReferenceDataControl dataControl) {
		for (ElementContainer container : allReferencesDataControl) {
			if (!container.isPlayer() && container.getErdc() == dataControl)
				return true;
		}
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
	public void setLastElementContainer(ElementContainer lastElementContainer) {
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
		return playerPositionInAllReferences;
	}

	public void setPlayerPosition(int playerPosition) {
		this.playerPositionInAllReferences= playerPosition;
		this.sceneDataControl.setPlayerLayer(playerPosition);
		
	}
	
	public void deletePlayer(){
		if (playerPositionInAllReferences!=NO_PLAYER){
			allReferencesDataControl.remove(playerPositionInAllReferences);
			reassignLayerAllReferencesDataControl(playerPositionInAllReferences);
			playerPositionInAllReferences  = NO_PLAYER;
			sceneDataControl.setPlayerLayer(Scene.PLAYER_NO_ALLOWED);
		}
	}
	
	// this function was made to insert player in correct position in SwapPlayerModeTool
	// CAUTION!! dont check if has layer or if it is allowed, because where it is call that has been checked
	//			 dont call to setPlayerLayer() because it has been checked
	public void restorePlayer(){
		ElementContainer ec = new ElementContainer(null,sceneDataControl.getPlayerLayer(),AssetsController.getImage( this.playerImagePath ));
		int layer = insertInOrder(ec,true);
		reassignLayerAllReferencesDataControl(layer);
	}
	
	public void addPlayer(){
		if (sceneDataControl.isAllowPlayer()){
			ElementContainer ec = new ElementContainer(null,0,AssetsController.getImage( this.playerImagePath ));
			int layer = insertInOrder(ec,true);
			reassignLayerAllReferencesDataControl(layer);
			sceneDataControl.setPlayerLayer(layer);
		}
	
	}
	
	public void changeImagePlayerPath(String imagePath) {
		this.playerImagePath = imagePath;
		this.imagePathHasChanged = true;
		if (allReferencesDataControl.size()==0) {
			playerPositionInAllReferences=0;
			reassignLayerAllReferencesDataControl(insertInOrder(new ElementContainer(null,0,AssetsController.getImage( this.playerImagePath )),true));
		}
	}

	@Override
	public void recursiveSearch() {
		if (this.getAtrezzoReferences() != null)
			for (DataControl dc : this.getAtrezzoReferences())
				dc.recursiveSearch();
		if (this.getItemReferences() != null)
			for (DataControl dc : this.getItemReferences())
				dc.recursiveSearch();
		if (this.getNPCReferences() != null)
			for (DataControl dc : this.getNPCReferences())
				dc.recursiveSearch();
	}

	public void setPlayerPositionInAllReferences(int playerPositionInAllReferences) {
		this.playerPositionInAllReferences = playerPositionInAllReferences;
	}
	
	
	
	@Override
	public List<Searchable> getPathToDataControl(Searchable dataControl) {
		List<Searchable> list = new ArrayList<Searchable>();
		for (ElementContainer container : allReferencesDataControl) {
			if (container.getErdc() != null)
				list.add(container.getErdc());
		}
		return getPathFromChild(dataControl, list);
	}

	/**
	 * Catch the type of the element reference control data and return the associated scene preview category
	 * 
	 * @param type
	 * @return
	 * 			the scene preview category
	 */
	public static int transformType(int type){
		int category = 0;
		if( type == Controller.ITEM_REFERENCE ) 
			category = ScenePreviewEditionPanel.CATEGORY_OBJECT;
		else if( type == Controller.ATREZZO_REFERENCE )
			category = ScenePreviewEditionPanel.CATEGORY_ATREZZO;
		else if( type == Controller.NPC_REFERENCE )
			 category = ScenePreviewEditionPanel.CATEGORY_CHARACTER;
		else if (type == -1)
			category = ScenePreviewEditionPanel.CATEGORY_PLAYER;
		return category;
	}

}
