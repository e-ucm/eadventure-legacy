package es.eucm.eadventure.editor.control.controllers.atrezzo;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.elements.Atrezzo;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.DataControlWithResources;
import es.eucm.eadventure.editor.control.controllers.general.ResourcesDataControl;
import es.eucm.eadventure.editor.control.tools.general.ChangeDescriptionTool;
import es.eucm.eadventure.editor.control.tools.general.ChangeDetailedDescriptionTool;
import es.eucm.eadventure.editor.control.tools.general.ChangeDocumentationTool;
import es.eucm.eadventure.editor.control.tools.general.ChangeNameTool;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class AtrezzoDataControl extends DataControlWithResources {

	/**
	 * Contained atrezzo item.
	 */
	private Atrezzo atrezzo;

	/**
	 * List of resources.
	 */
	private List<Resources> resourcesList;

	/**
	 * List of resources controllers.
	 */
	private List<ResourcesDataControl> resourcesDataControlList;

	/**
	 * The resources that must be used in the previews.
	 */
	private int selectedResources;
	
	/**
	 * Constructor.
	 * 
	 * @param atrezzo
	 *            Contained atrezzo item
	 */
	public AtrezzoDataControl( Atrezzo atrezzo ) {
		this.atrezzo = atrezzo;
		this.resourcesList = atrezzo.getResources( );

		selectedResources = 0;

		// Add a new resource if the list is empty
		if( resourcesList.size( ) == 0 )
			resourcesList.add( new Resources( ) );

		// Create the subcontrollers
		resourcesDataControlList = new ArrayList<ResourcesDataControl>( );
		for( Resources resources : resourcesList )
			resourcesDataControlList.add( new ResourcesDataControl( resources, Controller.ATREZZO ) );

		
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
	 * Returns the id of the atrezzo item.
	 * 
	 * @return Atrezzo's id
	 */
	public String getId( ) {
		return atrezzo.getId( );
	}

	/**
	 * Returns the documentation of the atrezzo item.
	 * 
	 * @return Atrezzo's documentation
	 */
	public String getDocumentation( ) {
		return atrezzo.getDocumentation( );
	}

	/**
	 * Returns the name of the atrezzo item.
	 * 
	 * @return Atrezzo's name
	 */
	public String getName( ) {
		return atrezzo.getName( );
	}

	/**
	 * Returns the brief description of the atrezzo item.
	 * 
	 * @return Atrezzo's description
	 */
	public String getBriefDescription( ) {
		return atrezzo.getDescription( );
	}

	/**
	 * Returns the detailed description of the atrezzo item.
	 * 
	 * @return Atrezzo's detailed description
	 */
	public String getDetailedDescription( ) {
		return atrezzo.getDetailedDescription( );
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
	 * Sets the new documentation of the atrezzo item.
	 * 
	 * @param documentation
	 *            Documentation of the atrezzo item
	 */
	public void setDocumentation( String documentation ) {
		controller.addTool(new ChangeDocumentationTool(atrezzo, documentation));
	}

	/**
	 * Sets the new name of the atrezzo item.
	 * 
	 * @param name
	 *            Name of the atrezzo item
	 */
	public void setName( String name ) {
		controller.addTool(new ChangeNameTool(atrezzo, name));
	}

	/**
	 * Sets the new brief description of the atrezzo item.
	 * 
	 * @param description
	 *            Description of the atrezzo item
	 */
	public void setBriefDescription( String description ) {
		controller.addTool(new ChangeDescriptionTool(atrezzo, description));
	}

	/**
	 * Sets the new detailed description of the atrezzo item.
	 * 
	 * @param detailedDescription
	 *            Detailed description of the atrezzo item
	 */
	public void setDetailedDescription( String detailedDescription ) {
		controller.addTool(new ChangeDetailedDescriptionTool(atrezzo, detailedDescription));
	}

	@Override
	public Object getContent( ) {
		return atrezzo;
	}

	@Override
	public int[] getAddableElements( ) {
		//return new int[] { Controller.RESOURCES };
		return new int[] {};
	}

	@Override
	public boolean canAddElement( int type ) {
		// It can always add new resources
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
	public boolean addElement( int type ) {
		boolean elementAdded = false;

		if( type == Controller.RESOURCES ) {
			Resources newResources = new Resources( );
			resourcesList.add( newResources );
			resourcesDataControlList.add( new ResourcesDataControl( newResources, Controller.ATREZZO ) );
			//controller.dataModified( );
			elementAdded = true;
		}

		return elementAdded;
	}

	@Override
	public boolean deleteElement( DataControl dataControl , boolean askConfirmation) {
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
	public String renameElement( String name ) {
		boolean elementRenamed = false;
		String oldAtrezzoId = atrezzo.getId( );
		String references = String.valueOf( controller.countIdentifierReferences( oldAtrezzoId ) );

		// Ask for confirmation 
		if(name != null || controller.showStrictConfirmDialog( TextConstants.getText( "Operation.RenameAtrezzoTitle" ), TextConstants.getText( "Operation.RenameElementWarning", new String[] { oldAtrezzoId, references } ) ) ) {

			// Show a dialog asking for the new atrezzo item id
			String newAtrezzoId = name;
			if (name == null)
				newAtrezzoId = controller.showInputDialog( TextConstants.getText( "Operation.RenameAtrezzoTitle" ), TextConstants.getText( "Operation.RenameAtrezzoMessage" ), oldAtrezzoId );

			// If some value was typed and the identifiers are different
			if( newAtrezzoId != null && !newAtrezzoId.equals( oldAtrezzoId ) && controller.isElementIdValid( newAtrezzoId ) ) {
				atrezzo.setId( newAtrezzoId );
				controller.replaceIdentifierReferences( oldAtrezzoId, newAtrezzoId );
				controller.getIdentifierSummary( ).deleteAtrezzoId( oldAtrezzoId );
				controller.getIdentifierSummary( ).addAtrezzoId( newAtrezzoId );
				//controller.dataModified( );
				elementRenamed = true;
			}
		}

		if (elementRenamed)
			return oldAtrezzoId;
		else
			return null;
	}

	@Override
	public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {
		//This method is empty because the atrezzo items there aren´t interactive, and its haven´t associated flags/vars 
	}

	@Override
	public boolean isValid( String currentPath, List<String> incidences ) {
		boolean valid = true;

		// Iterate through the resources
		for( int i = 0; i < resourcesDataControlList.size( ); i++ ) {
			String resourcesPath = currentPath + " >> " + TextConstants.getElementName( Controller.RESOURCES ) + " #" + ( i + 1 );
			valid &= resourcesDataControlList.get( i ).isValid( resourcesPath, incidences );
		}


		return valid;
	}

	@Override
	public int countAssetReferences( String assetPath ) {
		int count = 0;

		// Iterate through the resources
		for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
			count += resourcesDataControl.countAssetReferences( assetPath );

		return count;
	}
	
	public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes){
		// Iterate through the resources
		for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
			resourcesDataControl.getAssetReferences( assetPaths, assetTypes );

	}


	@Override
	public void deleteAssetReferences( String assetPath ) {
		// Iterate through the resources
		for( ResourcesDataControl resourcesDataControl : resourcesDataControlList )
			resourcesDataControl.deleteAssetReferences( assetPath );
	}

	@Override
	public int countIdentifierReferences( String id ) {
		return 0;
	}

	@Override
	public void replaceIdentifierReferences( String oldId, String newId ) {
		//This method is empty
	}

	@Override
	public void deleteIdentifierReferences( String id ) {
		//This method is empty 
	}

	@Override
	public boolean canBeDuplicated( ) {
		return true;
	}

}
