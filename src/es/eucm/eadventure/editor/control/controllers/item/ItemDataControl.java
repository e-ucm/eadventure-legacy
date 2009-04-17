package es.eucm.eadventure.editor.control.controllers.item;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.elements.Item;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.DataControlWithResources;
import es.eucm.eadventure.editor.control.controllers.general.ActionsListDataControl;
import es.eucm.eadventure.editor.control.controllers.general.ResourcesDataControl;
import es.eucm.eadventure.editor.control.tools.general.commontext.ChangeDescriptionTool;
import es.eucm.eadventure.editor.control.tools.general.commontext.ChangeDetailedDescriptionTool;
import es.eucm.eadventure.editor.control.tools.general.commontext.ChangeDocumentationTool;
import es.eucm.eadventure.editor.control.tools.general.commontext.ChangeNameTool;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class ItemDataControl extends DataControlWithResources {

	/**
	 * Contained item.
	 */
	private Item item;

	/**
	 * List of resources.
	 */
	private List<Resources> resourcesList;

	/**
	 * List of resources controllers.
	 */
	private List<ResourcesDataControl> resourcesDataControlList;

	/**
	 * Actions list controller.
	 */
	private ActionsListDataControl actionsListDataControl;

	/**
	 * The resources that must be used in the previews.
	 */
	private int selectedResources;

	/**
	 * Constructor.
	 * 
	 * @param item
	 *            Contained item
	 */
	public ItemDataControl( Item item ) {
		this.item = item;
		this.resourcesList = item.getResources( );

		selectedResources = 0;

		// Add a new resource if the list is empty
		if( resourcesList.size( ) == 0 )
			resourcesList.add( new Resources( ) );

		// Create the subcontrollers
		resourcesDataControlList = new ArrayList<ResourcesDataControl>( );
		for( Resources resources : resourcesList )
			resourcesDataControlList.add( new ResourcesDataControl( resources, Controller.ITEM ) );

		actionsListDataControl = new ActionsListDataControl( item.getActions( ), this );
	}

	/**
	 * Returns the list of resources controllers.
	 * 
	 * @return Resources controllers
	 */
	public List<ResourcesDataControl> getResources( ) {
		return resourcesDataControlList;
	}

	/**
	 * Returns the number of resources blocks contained.
	 * 
	 * @return Number of resources blocks
	 */
	public int getResourcesCount( ) {
		return resourcesDataControlList.size( );
	}

	/**
	 * Returns the last resources controller of the list.
	 * 
	 * @return Last resources controller
	 */
	public ResourcesDataControl getLastResources( ) {
		return resourcesDataControlList.get( resourcesDataControlList.size( ) - 1 );
	}

	/**
	 * Returns the actions list controller.
	 * 
	 * @return Actions list controller
	 */
	public ActionsListDataControl getActionsList( ) {
		return actionsListDataControl;
	}

	/**
	 * Returns the selected resources block of the list.
	 * 
	 * @return Selected block of resources
	 */
	public int getSelectedResources( ) {
		return selectedResources;
	}

	/**
	 * Returns the path to the selected preview image.
	 * 
	 * @return Path to the image, null if not present
	 */
	public String getPreviewImage( ) {
		return resourcesDataControlList.get( selectedResources ).getAssetPath( "image" );
	}

	/**
	 * Returns the id of the item.
	 * 
	 * @return Item's id
	 */
	public String getId( ) {
		return item.getId( );
	}

	/**
	 * Returns the documentation of the item.
	 * 
	 * @return Item's documentation
	 */
	public String getDocumentation( ) {
		return item.getDocumentation( );
	}

	/**
	 * Returns the name of the item.
	 * 
	 * @return Item's name
	 */
	public String getName( ) {
		return item.getName( );
	}

	/**
	 * Returns the brief description of the item.
	 * 
	 * @return Item's description
	 */
	public String getBriefDescription( ) {
		return item.getDescription( );
	}

	/**
	 * Returns the detailed description of the item.
	 * 
	 * @return Item's detailed description
	 */
	public String getDetailedDescription( ) {
		return item.getDetailedDescription( );
	}

	/**
	 * Sets the new selected resources block of the list.
	 * 
	 * @param selectedResources
	 *            New selected block of resources
	 */
	public void setSelectedResources( int selectedResources ) {
		this.selectedResources = selectedResources;
	}

	/**
	 * Sets the new documentation of the item.
	 * 
	 * @param documentation
	 *            Documentation of the item
	 */
	public void setDocumentation( String documentation ) {
		controller.addTool(new ChangeDocumentationTool(item, documentation));
	}

	/**
	 * Sets the new name of the item.
	 * 
	 * @param name
	 *            Name of the item
	 */
	public void setName( String name ) {
		controller.addTool(new ChangeNameTool(item, name));
	}

	/**
	 * Sets the new brief description of the item.
	 * 
	 * @param description
	 *            Description of the item
	 */
	public void setBriefDescription( String description ) {
		controller.addTool(new ChangeDescriptionTool(item, description));
	}

	/**
	 * Sets the new detailed description of the item.
	 * 
	 * @param detailedDescription
	 *            Detailed description of the item
	 */
	public void setDetailedDescription( String detailedDescription ) {
		controller.addTool(new ChangeDetailedDescriptionTool(item, detailedDescription));
	}

	@Override
	public Object getContent( ) {
		return item;
	}

	@Override
	public int[] getAddableElements( ) {
		//return new int[] { Controller.RESOURCES };
		return new int[] {};
	}

	@Override
	public boolean canAddElement( int type ) {
		// It can always add new resources
		//return type == Controller.RESOURCES;
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
		return true;
	}

	@Override
	public boolean addElement( int type, String id ) {
		boolean elementAdded = false;

		if( type == Controller.RESOURCES ) {
			Resources newResources = new Resources( );
			resourcesList.add( newResources );
			resourcesDataControlList.add( new ResourcesDataControl( newResources, Controller.ITEM ) );
			//controller.dataModified( );
			elementAdded = true;
		}

		return elementAdded;
	}

	@Override
	public boolean deleteElement( DataControl dataControl, boolean askConfirmation ) {
		boolean elementDeleted = false;

		// Delete the block only if it is not the last one
		if( resourcesList.size( ) > 1 ) {
			if( resourcesList.remove( dataControl.getContent( ) ) ) {
				int resourcesIndex = resourcesDataControlList.indexOf( dataControl );
				resourcesDataControlList.remove( dataControl );

				// Decrease the selected index if necessary
				if( selectedResources > 0 && selectedResources >= resourcesIndex )
					selectedResources--;

				//controller.dataModified( );
				elementDeleted = true;
			}
		}

		// If it was the last one, show an error message
		else
			controller.showErrorDialog( TextConstants.getText( "Operation.DeleteResourcesTitle" ), TextConstants.getText( "Operation.DeleteResourcesErrorLastResources" ) );

		return elementDeleted;
	}

	@Override
	public boolean moveElementUp( DataControl dataControl ) {
		boolean elementMoved = false;
		int elementIndex = resourcesList.indexOf( dataControl.getContent( ) );

		if( elementIndex > 0 ) {
			resourcesList.add( elementIndex - 1, resourcesList.remove( elementIndex ) );
			resourcesDataControlList.add( elementIndex - 1, resourcesDataControlList.remove( elementIndex ) );
			//controller.dataModified( );
			elementMoved = true;
		}

		return elementMoved;
	}

	@Override
	public boolean moveElementDown( DataControl dataControl ) {
		boolean elementMoved = false;
		int elementIndex = resourcesList.indexOf( dataControl.getContent( ) );

		if( elementIndex < resourcesList.size( ) - 1 ) {
			resourcesList.add( elementIndex + 1, resourcesList.remove( elementIndex ) );
			resourcesDataControlList.add( elementIndex + 1, resourcesDataControlList.remove( elementIndex ) );
			//controller.dataModified( );
			elementMoved = true;
		}

		return elementMoved;
	}

	@Override
	public String renameElement(String name ) {
		boolean elementRenamed = false;
		String oldItemId = item.getId( );
		String references = String.valueOf( controller.countIdentifierReferences( oldItemId ) );

		// Ask for confirmation
		if(name != null || controller.showStrictConfirmDialog( TextConstants.getText( "Operation.RenameItemTitle" ), TextConstants.getText( "Operation.RenameElementWarning", new String[] { oldItemId, references } ) ) ) {

			// Show a dialog asking for the new item id
			String newItemId = name;
			if (name == null)
				newItemId = controller.showInputDialog( TextConstants.getText( "Operation.RenameItemTitle" ), TextConstants.getText( "Operation.RenameItemMessage" ), oldItemId );

			// If some value was typed and the identifiers are different
			if( newItemId != null && !newItemId.equals( oldItemId ) && controller.isElementIdValid( newItemId ) ) {
				item.setId( newItemId );
				controller.replaceIdentifierReferences( oldItemId, newItemId );
				controller.getIdentifierSummary( ).deleteItemId( oldItemId );
				controller.getIdentifierSummary( ).addItemId( newItemId );
				//controller.dataModified( );
				elementRenamed = true;
			}
		}

		if (elementRenamed)
			return oldItemId;
		return null;
	}

	@Override
	public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {
		actionsListDataControl.updateVarFlagSummary( varFlagSummary );
	}

	@Override
	public boolean isValid( String currentPath, List<String> incidences ) {
		boolean valid = true;

		// Iterate through the resources
		for( int i = 0; i < resourcesDataControlList.size( ); i++ ) {
			String resourcesPath = currentPath + " >> " + TextConstants.getElementName( Controller.RESOURCES ) + " #" + ( i + 1 );
			valid &= resourcesDataControlList.get( i ).isValid( resourcesPath, incidences );
		}

		// Spread the call to the actions
		valid &= actionsListDataControl.isValid( currentPath, incidences );

		return valid;
	}

	@Override
	public int countAssetReferences( String assetPath ) {
		int count = 0;

		// Iterate through the resources
		for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
			count += resourcesDataControl.countAssetReferences( assetPath );

		// Add the references in the actions
		count += actionsListDataControl.countAssetReferences( assetPath );

		return count;
	}
	
	public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes){
		// Iterate through the resources
		for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
			resourcesDataControl.getAssetReferences( assetPaths, assetTypes );

		// Add the references in the actions
		actionsListDataControl.getAssetReferences( assetPaths, assetTypes );
	}


	@Override
	public void deleteAssetReferences( String assetPath ) {
		// Iterate through the resources
		for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
			resourcesDataControl.deleteAssetReferences( assetPath );

		// Delete the references from the actions
		actionsListDataControl.deleteAssetReferences( assetPath );
	}

	@Override
	public int countIdentifierReferences( String id ) {
		return actionsListDataControl.countIdentifierReferences( id );
	}

	@Override
	public void replaceIdentifierReferences( String oldId, String newId ) {
		actionsListDataControl.replaceIdentifierReferences( oldId, newId );
	}

	@Override
	public void deleteIdentifierReferences( String id ) {
		actionsListDataControl.deleteIdentifierReferences( id );
	}

	@Override
	public boolean canBeDuplicated( ) {
		return true;
	}

	@Override
	public void recursiveSearch() {
		check(this.getBriefDescription(), TextConstants.getText("Search.BriefDescription"));
		check(this.getDetailedDescription(), TextConstants.getText("Search.DetailedDescription"));
		check(this.getDocumentation(), TextConstants.getText("Search.Documentation"));
		check(this.getId(), "ID");
		check(this.getName(), TextConstants.getText("Search.Name"));
		check(this.getPreviewImage(), TextConstants.getText("Search.PreviewImage"));
		this.getActionsList().recursiveSearch();
	}
	
	@Override
	public List<DataControl> getPathToDataControl(DataControl dataControl) {
		List<DataControl> path = getPathFromChild(dataControl, resourcesDataControlList);
		if (path != null) return path;
		path = getPathFromChild(dataControl, actionsListDataControl);
		return path;
	}

}
